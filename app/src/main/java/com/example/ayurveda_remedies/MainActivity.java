package com.example.ayurveda_remedies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.adapters.CategoryAdapter;
import com.example.ayurveda_remedies.adapters.RemedyAdapter;
import com.example.ayurveda_remedies.data.RemedyRepository;
import com.example.ayurveda_remedies.models.Category;
import com.example.ayurveda_remedies.models.Remedy;
import com.example.ayurveda_remedies.utils.WellnessTipManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories, rvPopularRemedies;
    private CategoryAdapter categoryAdapter;
    private RemedyAdapter remedyAdapter;
    private RemedyRepository repository;
    private EditText etSearch;
    private FloatingActionButton fabFavorites;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "AyurvedaPrefs";
    private static final String KEY_FAVORITES = "favorites";
    private List<Remedy> allRemedies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        repository = RemedyRepository.getInstance();
        allRemedies = repository.getAllRemedies();
        loadFavorites();

        initViews();
        setupToolbar();
        setupCategories();
        setupPopularRemedies();
        setupSearch();
        setupFavorites();
    }

    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvPopularRemedies = findViewById(R.id.rvPopularRemedies);
        etSearch = findViewById(R.id.etSearch);
        fabFavorites = findViewById(R.id.fabFavorites);
        
        // Filter icon click
        ImageView ivFilter = findViewById(R.id.ivFilter);
        ivFilter.setOnClickListener(v -> showFilterDialog());
        
        // Display daily wellness tip
        TextView tvDailyTip = findViewById(R.id.tvDailyTip);
        tvDailyTip.setText(WellnessTipManager.getTipOfTheDay());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Load profile picture and username from SharedPreferences
        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);
    }

    private void setupCategories() {
        List<Category> categories = repository.getAllCategories();
        
        categoryAdapter = new CategoryAdapter(categories, category -> {
            // Navigate to remedies of this category
            Intent intent = new Intent(MainActivity.this, RemedyListActivity.class);
            intent.putExtra("category_id", category.getId());
            intent.putExtra("category_name", category.getName());
            startActivity(intent);
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvCategories.setLayoutManager(gridLayoutManager);
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupPopularRemedies() {
        List<Remedy> popularRemedies = repository.getPopularRemedies(10);
        
        remedyAdapter = new RemedyAdapter(popularRemedies, new RemedyAdapter.OnRemedyClickListener() {
            @Override
            public void onRemedyClick(Remedy remedy) {
                Intent intent = new Intent(MainActivity.this, RemedyDetailActivity.class);
                intent.putExtra("remedy", remedy);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Remedy remedy) {
                toggleFavorite(remedy);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPopularRemedies.setLayoutManager(layoutManager);
        rvPopularRemedies.setAdapter(remedyAdapter);

        // View All click
        TextView tvViewAll = findViewById(R.id.tvViewAll);
        tvViewAll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RemedyListActivity.class);
            intent.putExtra("show_all", true);
            startActivity(intent);
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    performSearch(s.toString());
                } else {
                    // Reset to popular remedies
                    remedyAdapter.updateRemedies(repository.getPopularRemedies(10));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String query) {
        List<Remedy> searchResults = repository.searchRemedies(query);
        remedyAdapter.updateRemedies(searchResults);
    }

    private void setupFavorites() {
        fabFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void toggleFavorite(Remedy remedy) {
        remedy.setFavorite(!remedy.isFavorite());
        
        // Update SharedPreferences
        Set<String> favorites = preferences.getStringSet(KEY_FAVORITES, new HashSet<>());
        Set<String> updatedFavorites = new HashSet<>(favorites);
        
        if (remedy.isFavorite()) {
            updatedFavorites.add(remedy.getId());
            Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
        } else {
            updatedFavorites.remove(remedy.getId());
            Toast.makeText(this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show();
        }
        
        preferences.edit().putStringSet(KEY_FAVORITES, updatedFavorites).apply();
        remedyAdapter.notifyDataSetChanged();
    }

    private void loadFavorites() {
        Set<String> favorites = preferences.getStringSet(KEY_FAVORITES, new HashSet<>());
        for (Remedy remedy : allRemedies) {
            remedy.setFavorite(favorites.contains(remedy.getId()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
        // Refresh profile picture and name in case they were updated
        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);
        if (remedyAdapter != null) {
            remedyAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_dosha_quiz) {
            startActivity(new Intent(this, DoshaQuizActivity.class));
            return true;
        } else if (id == R.id.action_seasonal) {
            showSeasonalRemedies();
            return true;
        } else if (id == R.id.action_daily_wellness) {
            startActivity(new Intent(MainActivity.this, DailyWellnessActivity.class));
            return true;
        } else if (id == R.id.action_calendar) {
            startActivity(new Intent(this, CalendarActivity.class));
            return true;
        } else if (id == R.id.action_progress) {
            startActivity(new Intent(this, ProgressActivity.class));
            return true;
        } else if (id == R.id.action_chatbot) {
            startActivity(new Intent(this, ChatbotActivity.class));
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void showSeasonalRemedies() {
        List<Remedy> seasonalRemedies = repository.getSeasonalRemedies();
        if (seasonalRemedies.isEmpty()) {
            Toast.makeText(this, "No seasonal remedies available", Toast.LENGTH_SHORT).show();
        } else {
            remedyAdapter.updateRemedies(seasonalRemedies);
            Toast.makeText(this, "Showing remedies for current season", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showFilterDialog() {
        String[] options = {"Sort by Popularity", "Sort by Difficulty (Easy First)", 
                           "Sort by Preparation Time", "Filter by Category", "Show All Remedies"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter & Sort Options")
               .setItems(options, (dialog, which) -> {
                   List<Remedy> filteredRemedies;
                   switch (which) {
                       case 0: // Sort by Popularity
                           filteredRemedies = repository.getPopularRemedies(allRemedies.size());
                           remedyAdapter.updateRemedies(filteredRemedies);
                           Toast.makeText(this, "Sorted by popularity", Toast.LENGTH_SHORT).show();
                           break;
                       case 1: // Sort by Difficulty
                           filteredRemedies = new ArrayList<>(allRemedies);
                           filteredRemedies.sort((r1, r2) -> Integer.compare(r1.getDifficulty(), r2.getDifficulty()));
                           remedyAdapter.updateRemedies(filteredRemedies);
                           Toast.makeText(this, "Sorted by difficulty", Toast.LENGTH_SHORT).show();
                           break;
                       case 2: // Sort by Preparation Time
                           filteredRemedies = new ArrayList<>(allRemedies);
                           filteredRemedies.sort((r1, r2) -> Integer.compare(r1.getPreparationTime(), r2.getPreparationTime()));
                           remedyAdapter.updateRemedies(filteredRemedies);
                           Toast.makeText(this, "Sorted by preparation time", Toast.LENGTH_SHORT).show();
                           break;
                       case 3: // Filter by Category
                           showCategoryFilterDialog();
                           break;
                       case 4: // Show All
                           remedyAdapter.updateRemedies(allRemedies);
                           Toast.makeText(this, "Showing all remedies", Toast.LENGTH_SHORT).show();
                           break;
                   }
               });
        builder.create().show();
    }
    
    private void showCategoryFilterDialog() {
        List<Category> categories = repository.getAllCategories();
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).getName();
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category")
               .setItems(categoryNames, (dialog, which) -> {
                   Category selected = categories.get(which);
                   List<Remedy> filteredRemedies = repository.getRemediesByCategory(selected.getId());
                   remedyAdapter.updateRemedies(filteredRemedies);
                   Toast.makeText(this, "Showing " + selected.getName(), Toast.LENGTH_SHORT).show();
               });
        builder.create().show();
    }
}
