package com.example.ayurveda_remedies;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etAge;
    private Spinner spinnerDosha;
    private Button btnSave, btnTakeQuiz, btnFeedback;
    private CardView cardQuizResults;
    private TextView tvDoshaResult, tvVataScore, tvPittaScore, tvKaphaScore;
    private ProgressBar pbVataProfile, pbPittaProfile, pbKaphaProfile;
    private SharedPreferences preferences;
    
    // Profile picture components
    private CircleImageView ivProfilePicture;
    private ImageButton btnEditProfilePic;
    private Uri photoUri;
    
    // Activity result launchers
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private ActivityResultLauncher<String> storagePermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences = getSharedPreferences("AyurvedaPrefs", MODE_PRIVATE);
        
        // Initialize activity result launchers
        initializeActivityResultLaunchers();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        spinnerDosha = findViewById(R.id.spinnerDosha);
        btnSave = findViewById(R.id.btnSave);
        btnTakeQuiz = findViewById(R.id.btnTakeQuiz);
        
        // Profile picture views
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnEditProfilePic = findViewById(R.id.btnEditProfilePic);
        
        cardQuizResults = findViewById(R.id.cardQuizResults);
        tvDoshaResult = findViewById(R.id.tvDoshaResult);
        tvVataScore = findViewById(R.id.tvVataScore);
        tvPittaScore = findViewById(R.id.tvPittaScore);
        tvKaphaScore = findViewById(R.id.tvKaphaScore);
        pbVataProfile = findViewById(R.id.pbVataProfile);
        pbPittaProfile = findViewById(R.id.pbPittaProfile);
        pbKaphaProfile = findViewById(R.id.pbKaphaProfile);

        // Setup spinner
        String[] doshaTypes = {"Not Set", "Vata", "Pitta", "Kapha", "Vata-Pitta", "Pitta-Kapha", "Vata-Kapha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doshaTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDosha.setAdapter(adapter);

        // Load saved data
        loadProfile();
        loadQuizResults();
        loadProfilePicture();

        btnSave.setOnClickListener(v -> saveProfile());
        btnTakeQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, DoshaQuizActivity.class);
            startActivity(intent);
        });
        
        btnFeedback = findViewById(R.id.btnFeedback);
        btnFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        });
        
        btnEditProfilePic.setOnClickListener(v -> showImagePickerDialog());
    }

    private void loadProfile() {
        String name = preferences.getString("user_name", "");
        int age = preferences.getInt("user_age", 0);
        String dosha = preferences.getString("user_dosha", "Not Set");

        etName.setText(name);
        if (age > 0) {
            etAge.setText(String.valueOf(age));
        }

        // Set dosha spinner
        ArrayAdapter adapter = (ArrayAdapter) spinnerDosha.getAdapter();
        int position = adapter.getPosition(dosha);
        if (position >= 0) {
            spinnerDosha.setSelection(position);
        }
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String dosha = spinnerDosha.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = 0;
        if (!ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", name);
        editor.putInt("user_age", age);
        editor.putString("user_dosha", dosha);
        editor.apply();

        Toast.makeText(this, getString(R.string.profile_saved), Toast.LENGTH_SHORT).show();
        finish();
    }
    
    private void loadQuizResults() {
        int vataScore = preferences.getInt("quiz_vata", -1);
        int pittaScore = preferences.getInt("quiz_pitta", -1);
        int kaphaScore = preferences.getInt("quiz_kapha", -1);
        
        if (vataScore != -1 && pittaScore != -1 && kaphaScore != -1) {
            cardQuizResults.setVisibility(View.VISIBLE);
            
            int total = vataScore + pittaScore + kaphaScore;
            int vataPercent = total > 0 ? (vataScore * 100) / total : 0;
            int pittaPercent = total > 0 ? (pittaScore * 100) / total : 0;
            int kaphaPercent = total > 0 ? (kaphaScore * 100) / total : 0;
            
            // Determine dominant
            String dominant = "Balanced";
            if (vataScore > pittaScore && vataScore > kaphaScore) dominant = "Vata";
            else if (pittaScore > vataScore && pittaScore > kaphaScore) dominant = "Pitta";
            else if (kaphaScore > vataScore && kaphaScore > pittaScore) dominant = "Kapha";
            
            tvDoshaResult.setText("Dominant Dosha: " + dominant);
            tvVataScore.setText("Vata: " + vataPercent + "%");
            tvPittaScore.setText("Pitta: " + pittaPercent + "%");
            tvKaphaScore.setText("Kapha: " + kaphaPercent + "%");
            
            pbVataProfile.setMax(100);
            pbPittaProfile.setMax(100);
            pbKaphaProfile.setMax(100);
            
            pbVataProfile.setProgress(vataPercent);
            pbPittaProfile.setProgress(pittaPercent);
            pbKaphaProfile.setProgress(kaphaPercent);
        } else {
            cardQuizResults.setVisibility(View.GONE);
        }
    }
    
    private void initializeActivityResultLaunchers() {
        // Camera launcher
        cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && photoUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        if (bitmap != null) {
                            ivProfilePicture.setImageBitmap(bitmap);
                            saveProfilePicture(bitmap);
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
        
        // Gallery launcher
        galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ivProfilePicture.setImageBitmap(bitmap);
                            saveProfilePicture(bitmap);
                        } catch (IOException e) {
                            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        );
        
        // Camera permission launcher
        cameraPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
                }
            }
        );
        
        // Storage permission launcher
        storagePermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
    
    private void showImagePickerDialog() {
        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Profile Picture");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    checkCameraPermission();
                    break;
                case 1:
                    checkStoragePermission();
                    break;
                case 2:
                    dialog.dismiss();
                    break;
            }
        });
        builder.show();
    }
    
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    
    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // Below Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this,
                        getPackageName() + ".fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                cameraLauncher.launch(cameraIntent);
            }
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }
    
    private File createImageFile() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "PROFILE_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    
    private void saveProfilePicture(Bitmap bitmap) {
        try {
            // Compress bitmap to reduce size
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("profile_picture", encodedImage);
            editor.apply();
            
            Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save profile picture", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadProfilePicture() {
        String encodedImage = preferences.getString("profile_picture", null);
        if (encodedImage != null) {
            try {
                byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                ivProfilePicture.setImageBitmap(bitmap);
            } catch (Exception e) {
                // If loading fails, keep default image
            }
        }
    }
}
