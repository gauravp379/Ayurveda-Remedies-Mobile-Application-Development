package com.example.ayurveda_remedies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurveda_remedies.R;
import com.example.ayurveda_remedies.model.Symptom;

import java.util.List;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.ViewHolder> {

    private List<Symptom> items;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public SymptomAdapter(List<Symptom> items, OnItemLongClickListener listener) {
        this.items = items;
        this.longClickListener = listener;
    }

    public void updateList(List<Symptom> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    public Symptom getItem(int pos) {
        return items.get(pos);
    }

    public List<Symptom> getItems() {
        return items;
    }

    @NonNull
    @Override
    public SymptomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomAdapter.ViewHolder holder, int position) {
        Symptom s = items.get(position);
        holder.tvDate.setText(s.date);
        holder.tvText.setText(s.text);

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClicked(holder.getAdapterPosition());
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvItemDate);
            tvText = itemView.findViewById(R.id.tvItemText);
        }
    }
}
