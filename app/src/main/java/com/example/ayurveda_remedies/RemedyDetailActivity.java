package com.example.ayurveda_remedies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ayurveda_remedies.models.Remedy;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;

public class RemedyDetailActivity extends AppCompatActivity {

    private Remedy remedy;
    private ImageView ivRemedyDetailImage;
    private TextView tvRemedyName, tvCategoryName, tvPrepTime, tvDifficulty;
    private TextView tvDescription, tvIngredients, tvPreparation, tvBenefits, tvPrecautions;
    private MaterialButton btnFavorite, btnShare;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy_detail);

        // Get remedy from intent
        remedy = (Remedy) getIntent().getSerializableExtra("remedy");
        if (remedy == null) {
            finish();
            return;
        }

        initViews();
        setupToolbar();
        populateData();
        setupButtons();
    }

    private void initViews() {
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        ivRemedyDetailImage = findViewById(R.id.ivRemedyDetailImage);
        tvRemedyName = findViewById(R.id.tvRemedyName);
        tvCategoryName = findViewById(R.id.tvCategoryName);
        tvPrepTime = findViewById(R.id.tvPrepTime);
        tvDifficulty = findViewById(R.id.tvDifficulty);
        tvDescription = findViewById(R.id.tvDescription);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvPreparation = findViewById(R.id.tvPreparation);
        tvBenefits = findViewById(R.id.tvBenefits);
        tvPrecautions = findViewById(R.id.tvPrecautions);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        
        collapsingToolbar.setTitle(remedy.getName());
    }

    private void populateData() {
        // Basic Info
        tvRemedyName.setText(remedy.getName());
        tvCategoryName.setText(remedy.getCategoryName());
        tvPrepTime.setText(remedy.getPreparationTime() + " min");
        tvDifficulty.setText(remedy.getDifficultyText());

        // Image
        if (remedy.getImageResource() != 0) {
            ivRemedyDetailImage.setImageResource(remedy.getImageResource());
        }

        // Description
        if (remedy.getDetailedDescription() != null && !remedy.getDetailedDescription().isEmpty()) {
            tvDescription.setText(remedy.getDetailedDescription());
        } else {
            tvDescription.setText(remedy.getShortDescription());
        }

        // Ingredients
        if (remedy.getIngredients() != null && !remedy.getIngredients().isEmpty()) {
            StringBuilder ingredientsText = new StringBuilder();
            for (int i = 0; i < remedy.getIngredients().size(); i++) {
                ingredientsText.append("• ").append(remedy.getIngredients().get(i));
                if (i < remedy.getIngredients().size() - 1) {
                    ingredientsText.append("\n");
                }
            }
            tvIngredients.setText(ingredientsText.toString());
        }

        // Preparation
        if (remedy.getPreparation() != null && !remedy.getPreparation().isEmpty()) {
            StringBuilder preparationText = new StringBuilder();
            for (int i = 0; i < remedy.getPreparation().size(); i++) {
                preparationText.append((i + 1)).append(". ").append(remedy.getPreparation().get(i));
                if (i < remedy.getPreparation().size() - 1) {
                    preparationText.append("\n\n");
                }
            }
            tvPreparation.setText(preparationText.toString());
        }

        // Benefits
        if (remedy.getBenefits() != null && !remedy.getBenefits().isEmpty()) {
            StringBuilder benefitsText = new StringBuilder();
            for (int i = 0; i < remedy.getBenefits().size(); i++) {
                benefitsText.append("✓ ").append(remedy.getBenefits().get(i));
                if (i < remedy.getBenefits().size() - 1) {
                    benefitsText.append("\n");
                }
            }
            tvBenefits.setText(benefitsText.toString());
        }

        // Precautions
        if (remedy.getPrecautions() != null && !remedy.getPrecautions().isEmpty()) {
            StringBuilder precautionsText = new StringBuilder();
            for (int i = 0; i < remedy.getPrecautions().size(); i++) {
                precautionsText.append("⚠ ").append(remedy.getPrecautions().get(i));
                if (i < remedy.getPrecautions().size() - 1) {
                    precautionsText.append("\n");
                }
            }
            tvPrecautions.setText(precautionsText.toString());
        }
    }

    private void setupButtons() {
        // Favorite button
        updateFavoriteButton();
        btnFavorite.setOnClickListener(v -> {
            remedy.setFavorite(!remedy.isFavorite());
            updateFavoriteButton();
        });

        // Share button
        btnShare.setOnClickListener(v -> shareRemedy());
    }

    private void updateFavoriteButton() {
        if (remedy.isFavorite()) {
            btnFavorite.setText(R.string.remove_from_favorites);
            btnFavorite.setIcon(getDrawable(R.drawable.ic_favorite));
        } else {
            btnFavorite.setText(R.string.add_to_favorites);
            btnFavorite.setIcon(getDrawable(R.drawable.ic_favorite_border));
        }
    }

    private void shareRemedy() {
        StringBuilder shareText = new StringBuilder();
        shareText.append("🌿 ").append(remedy.getName()).append(" 🌿\n\n");
        shareText.append(remedy.getShortDescription()).append("\n\n");
        
        if (remedy.getIngredients() != null && !remedy.getIngredients().isEmpty()) {
            shareText.append("Ingredients:\n");
            for (String ingredient : remedy.getIngredients()) {
                shareText.append("• ").append(ingredient).append("\n");
            }
        }
        
        shareText.append("\nDiscover more Ayurvedic remedies in the Ayurveda Remedies app!");

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, remedy.getName());
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        startActivity(Intent.createChooser(shareIntent, "Share Remedy"));
    }
}
