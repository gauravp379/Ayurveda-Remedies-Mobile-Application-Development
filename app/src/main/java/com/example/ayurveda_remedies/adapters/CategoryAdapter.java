package com.example.ayurveda_remedies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.R;
import com.example.ayurveda_remedies.models.Category;
import com.example.ayurveda_remedies.utils.AnimationUtil;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, listener);
        
        // Add staggered animation
        AnimationUtil.animateRecyclerItem(holder.itemView, holder.itemView.getContext(), position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private CardView cardCategory;
        private CardView iconContainer;
        private ImageView ivCategoryIcon;
        private TextView tvCategoryName;
        private TextView tvRemedyCount;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardCategory = itemView.findViewById(R.id.cardCategory);
            iconContainer = itemView.findViewById(R.id.iconContainer);
            ivCategoryIcon = itemView.findViewById(R.id.ivCategoryIcon);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvRemedyCount = itemView.findViewById(R.id.tvRemedyCount);
        }

        public void bind(Category category, OnCategoryClickListener listener) {
            tvCategoryName.setText(category.getName());
            tvRemedyCount.setText(category.getRemedyCount() + " remedies");
            
            if (category.getIconResource() != 0) {
                ivCategoryIcon.setImageResource(category.getIconResource());
            }
            
            if (category.getColorResource() != 0) {
                iconContainer.setCardBackgroundColor(
                    itemView.getContext().getResources().getColor(category.getColorResource(), null)
                );
            }

            cardCategory.setOnClickListener(v -> {
                if (listener != null) {
                    AnimationUtil.pulse(cardCategory);
                    v.postDelayed(() -> listener.onCategoryClick(category), 150);
                }
            });
        }
    }
}
