package com.example.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDesc;
    private Task task;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("New Task");

        }
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);

        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
            App.getInstance().getDatabase().taskDao()
                    .update(pos, editTitle.getText().toString(), editDesc.getText().toString());
        }
    }

    public void save(View view) {

        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        if (task != null) {
            task.setDesc(desc);
            task.setTitle(title);
            App.getInstance().getDatabase().taskDao().update(task);
        } else {
            task = new Task();
            task.setDesc(desc);
            task.setTitle(title);
            task.setId(0);
            App.getInstance().getDatabase().taskDao().insert(task);
            String uid = FirebaseAuth.getInstance().getUid();
            FirebaseFirestore.getInstance()
                    .collection("tasks")
                    .document(uid)
                    .set(task)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FormActivity.this, "Успешно",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FormActivity.this, "Ошибка",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}
