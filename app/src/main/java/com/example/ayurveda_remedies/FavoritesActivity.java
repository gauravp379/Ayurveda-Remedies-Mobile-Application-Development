package com.example.ayurveda_remedies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.adapters.RemedyAdapter;
import com.example.ayurveda_remedies.data.RemedyRepository;
import com.example.ayurveda_remedies.models.Remedy;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvFavorites;
    private LinearLayout tvEmptyMessage;
    private RemedyAdapter adapter;
    private RemedyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        repository = RemedyRepository.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Favorites");
        toolbar.setNavigationOnClickListener(v -> finish());

        rvFavorites = findViewById(R.id.rvFavorites);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        loadFavorites();
    }

    private void loadFavorites() {
        List<Remedy> allRemedies = repository.getAllRemedies();
        List<Remedy> favorites = new ArrayList<>();

        for (Remedy remedy : allRemedies) {
            if (remedy.isFavorite()) {
                favorites.add(remedy);
            }
        }

        if (favorites.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
        } else {
            rvFavorites.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);

            adapter = new RemedyAdapter(favorites, new RemedyAdapter.OnRemedyClickListener() {
                @Override
                public void onRemedyClick(Remedy remedy) {
                    Intent intent = new Intent(FavoritesActivity.this, RemedyDetailActivity.class);
                    intent.putExtra("remedy", remedy);
                    startActivity(intent);
                }

                @Override
                public void onFavoriteClick(Remedy remedy) {
                    remedy.setFavorite(false);
                    loadFavorites(); // Refresh the list
                }
            });

            rvFavorites.setLayoutManager(new LinearLayoutManager(this));
            rvFavorites.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }
}
