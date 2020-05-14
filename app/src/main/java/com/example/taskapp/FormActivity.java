package com.example.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.taskapp.models.Task;

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

        if (getIntent().getSerializableExtra("task") != null) {
            Intent intent = getIntent();
            task = (Task) intent.getSerializableExtra("task");
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
            pos = intent.getIntExtra("pos",1);
            App.getInstance().getDatabase().taskDao()
             .update(pos, editTitle.getText().toString(), editDesc.getText().toString());

        }
    }

    public void save(View view) {
        if ((editTitle != null && editDesc != null)) {
            String title = editTitle.getText().toString().trim();
            String desc = editDesc.getText().toString().trim();
            if (task != null){
                task.setId(task.getId());
                App.getInstance().getDatabase().taskDao().update(pos, editTitle.getText().toString(), editDesc.getText().toString());
            } else {
                Task task = new Task();
                task.setDesc(desc);
                task.setTitle(title);
                App.getInstance().getDatabase().taskDao().insert(task);
            }
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}
