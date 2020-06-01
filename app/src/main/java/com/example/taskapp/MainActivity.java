package com.example.taskapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taskapp.login.PhoneActivity;
import com.example.taskapp.models.Task;
import com.example.taskapp.models.User;
import com.example.taskapp.ui.OnItemClickListener;
import com.example.taskapp.ui.home.HomeFragment;
import com.example.taskapp.ui.home.TaskAdapter;
import com.example.taskapp.ui.onBoard.OnBoardActivity;
import com.example.taskapp.ui.profile.ProfileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Task task;
    ImageView avatar;
    TextView name;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShown()) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
            return;
        }


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(int_profile);
            }
        });
        avatar = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        name = navigationView.getHeaderView(0).findViewById(R.id.textView);
        loadImage();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void loadImage() {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()){
                            User user = documentSnapshot.toObject(User.class);
                            if (user.getAvatar() != null && user.getName() != null) {
                                name.setText(user.getName());
                                showAvatar(user.getAvatar());
                            }
                        }
                    }
                });
    }

    private void showAvatar(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(this.avatar);
    }

    private boolean isShown() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShown", false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                exit_sp();
                finish();
                break;
            case R.id.action_sort:
                sort();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit_sp() {
        SharedPreferences pref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        pref.edit().putBoolean("isShown", false).apply();
    }


    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, PhoneActivity.class));
        finish();
    }

    private boolean flag;
    private void sort(){
        if (flag) {
            Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            ((HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).sortList();
            flag = false;
        } else {
            Fragment navHostFragment1 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            ((HomeFragment) navHostFragment1.getChildFragmentManager().getFragments().get(0)).initList();
            flag = true;
        }
    }

}
