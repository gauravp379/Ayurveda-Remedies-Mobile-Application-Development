package com.example.ayurveda_remedies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.adapters.RemedyAdapter;
import com.example.ayurveda_remedies.data.RemedyRepository;
import com.example.ayurveda_remedies.models.Remedy;

import java.util.List;

public class RemedyListActivity extends AppCompatActivity {

    private RecyclerView rvRemedies;
    private RemedyAdapter adapter;
    private RemedyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy_list);

        repository = RemedyRepository.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvRemedies = findViewById(R.id.rvRemedies);

        // Get category info from intent
        String categoryId = getIntent().getStringExtra("category_id");
        String categoryName = getIntent().getStringExtra("category_name");
        boolean showAll = getIntent().getBooleanExtra("show_all", false);

        // Set title
        if (showAll) {
            getSupportActionBar().setTitle("All Remedies");
        } else if (categoryName != null) {
            getSupportActionBar().setTitle(categoryName);
        }

        // Load remedies
        List<Remedy> remedies;
        if (showAll) {
            remedies = repository.getAllRemedies();
        } else if (categoryId != null) {
            remedies = repository.getRemediesByCategory(categoryId);
        } else {
            remedies = repository.getAllRemedies();
        }

        // Setup RecyclerView
        adapter = new RemedyAdapter(remedies, new RemedyAdapter.OnRemedyClickListener() {
            @Override
            public void onRemedyClick(Remedy remedy) {
                Intent intent = new Intent(RemedyListActivity.this, RemedyDetailActivity.class);
                intent.putExtra("remedy", remedy);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Remedy remedy) {
                remedy.setFavorite(!remedy.isFavorite());
                adapter.notifyDataSetChanged();
                Toast.makeText(RemedyListActivity.this, 
                    remedy.isFavorite() ? "Added to favorites" : "Removed from favorites", 
                    Toast.LENGTH_SHORT).show();
            }
        });

        rvRemedies.setLayoutManager(new LinearLayoutManager(this));
        rvRemedies.setAdapter(adapter);
    }
}
