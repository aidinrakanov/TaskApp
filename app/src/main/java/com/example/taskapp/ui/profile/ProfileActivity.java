package com.example.taskapp.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;
import com.example.taskapp.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText editName;
    ImageView face;
    private String avatarUrl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        editName = findViewById(R.id.editName);
        face = findViewById(R.id.avatarka);
        getData2();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
//            imageUri = data.getData();
//            face.setImageURI(imageUri);
            Glide.with(this).load(data.getData()).circleCrop().into(face);
            upload(data.getData());
        }
    }

    private void upload(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getUid();
        final StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child(uid + ".jpg");
        UploadTask uploadTask = reference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();
            }})
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
                    Log.e("Profile", "downloadUrl: " + downloadUrl);
                    avatarUrl = downloadUrl.toString();
                    updateAvatarInfo(downloadUrl);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Ошибка",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAvatarInfo(Uri downloadUrl) {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("users").document(uid)
                .update("avatar", downloadUrl.toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "Успешно",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("users").document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            editName.setText(user.getName());
                            showImage(user.getAvatar());
                        }
                    }
                });
    }

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(face);
    }

    public void save_profile(View view) {
        String uid = FirebaseAuth.getInstance().getUid();
        String name = editName.getText().toString().trim();
        User user = new User("Aidin", 26, avatarUrl);
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Успешно",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}
