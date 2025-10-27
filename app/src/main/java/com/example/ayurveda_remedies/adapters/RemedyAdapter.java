package com.example.ayurveda_remedies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.R;
import com.example.ayurveda_remedies.models.Remedy;
import com.example.ayurveda_remedies.utils.AnimationUtil;

import java.util.List;

public class RemedyAdapter extends RecyclerView.Adapter<RemedyAdapter.RemedyViewHolder> {

    private List<Remedy> remedies;
    private OnRemedyClickListener listener;

    public interface OnRemedyClickListener {
        void onRemedyClick(Remedy remedy);
        void onFavoriteClick(Remedy remedy);
    }

    public RemedyAdapter(List<Remedy> remedies, OnRemedyClickListener listener) {
        this.remedies = remedies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RemedyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_remedy, parent, false);
        return new RemedyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemedyViewHolder holder, int position) {
        Remedy remedy = remedies.get(position);
        holder.bind(remedy, listener);
        
        // Add staggered animation
        AnimationUtil.animateRecyclerItem(holder.itemView, holder.itemView.getContext(), position);
    }

    @Override
    public int getItemCount() {
        return remedies.size();
    }

    public void updateRemedies(List<Remedy> newRemedies) {
        this.remedies = newRemedies;
        notifyDataSetChanged();
    }

    static class RemedyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardRemedy;
        private ImageView ivRemedyImage;
        private ImageButton btnFavorite;
        private TextView tvCategory;
        private TextView tvRemedyName;
        private TextView tvRemedyDescription;
        private TextView tvDifficulty;
        private TextView tvPrepTime;

        public RemedyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRemedy = itemView.findViewById(R.id.cardRemedy);
            ivRemedyImage = itemView.findViewById(R.id.ivRemedyImage);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRemedyName = itemView.findViewById(R.id.tvRemedyName);
            tvRemedyDescription = itemView.findViewById(R.id.tvRemedyDescription);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
            tvPrepTime = itemView.findViewById(R.id.tvPrepTime);
        }

        public void bind(Remedy remedy, OnRemedyClickListener listener) {
            tvRemedyName.setText(remedy.getName());
            tvRemedyDescription.setText(remedy.getShortDescription());
            tvCategory.setText(remedy.getCategoryName());
            tvDifficulty.setText(remedy.getDifficultyText());
            tvPrepTime.setText(remedy.getPreparationTime() + " min");

            if (remedy.getImageResource() != 0) {
                ivRemedyImage.setImageResource(remedy.getImageResource());
            }

            // Update favorite icon
            if (remedy.isFavorite()) {
                btnFavorite.setImageResource(R.drawable.ic_favorite);
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_border);
            }

            cardRemedy.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemedyClick(remedy);
                }
            });

            btnFavorite.setOnClickListener(v -> {
                if (listener != null) {
                    AnimationUtil.pulse(btnFavorite);
                    listener.onFavoriteClick(remedy);
                }
            });
        }
    }
}
