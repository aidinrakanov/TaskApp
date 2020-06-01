package com.example.taskapp.ui.Firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.models.Task;

import java.util.ArrayList;

public class FirestoreAdapter extends RecyclerView.Adapter <FirestoreAdapter.ViewHolder> {
    private ArrayList<Task> list;

    public FirestoreAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firestore_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 0) {
            holder.holder2.setBackgroundResource(R.drawable.tasks_gradient);
        } else {
            holder.holder2.setBackgroundResource(R.drawable.tasks2_gradient);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Title, Desc;

        private LinearLayout holder2;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Title = itemView.findViewById(R.id.FS_text_title);
            Desc = itemView.findViewById(R.id.FS_text_description);
            holder2 = itemView.findViewById(R.id.FS_holder);
        }
        public void bind(Task task) {
            Title.setText(task.getTitle());
            Desc.setText(task.getDesc());

        }
    }
}

