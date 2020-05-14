package com.example.taskapp.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.FormActivity;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;

import java.util.ArrayList;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.example.taskapp.ui.OnItemClickListener;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundResource(R.color.lightBlue);
        } else {
            holder.itemView.setBackgroundResource(R.color.Gray);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Title, Desc;
        private LinearLayout linearLayout;
        Task task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.holder);
            Title = itemView.findViewById(R.id.text_title);
            Desc = itemView.findViewById(R.id.text_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FormActivity.class);
                    Task task = new Task();
                    task.setTitle(Title.getText().toString());
                    task.setDesc(Desc.getText().toString());
                    intent.putExtra("task", task);
                    intent.putExtra("pos", list.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Удалить");
                    builder.setMessage("Вы уверены?");
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int pos = list.get(getAdapterPosition()).getId();
                            App.getInstance().getDatabase().taskDao().deleteByIdList(pos);
                        }
                    })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }});
                    builder.show();
                    return false;
                }}); }


                public void bind(Task task) {
                    Title.setText(task.getTitle());
                    Desc.setText(task.getDesc());

                }
            }
        }

