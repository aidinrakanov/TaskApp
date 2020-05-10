package com.example.taskapp.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter <GalleryAdapter.ViewHolder> {
    private ArrayList<String> files;

    public GalleryAdapter (ArrayList<String> files) {
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list.setText((position + 1) + ") " +files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView list;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            list = itemView.findViewById(R.id.filename);
        }
    }
}

