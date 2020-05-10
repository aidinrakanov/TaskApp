package com.example.taskapp.ui.onBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BoardFragment extends Fragment {

    TextView textTitle, textDesc;
    Button button;



    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final OnBoardActivity onBoardActivity = (OnBoardActivity) getActivity();
        textTitle = view.findViewById(R.id.textTitle);
        textDesc = view.findViewById(R.id.textDesc);
        button = view.findViewById(R.id.button_Getstart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIsShown();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        ImageView image = view.findViewById(R.id.imageView);
        int pos = getArguments().getInt("pos");
        switch (pos){
            case 0:
                view.setBackgroundColor(Color.YELLOW);
                image.setImageResource(R.drawable.kitkat01);
                textTitle.setText("Kitkat");
                textDesc.setText("android 4.4");
                button.setVisibility(view.GONE);
                break;
            case 1:
                view.setBackgroundColor(Color.LTGRAY);
                image.setImageResource(R.drawable.nougat);
                textTitle.setText("Nougat");
                textDesc.setText("android 7");
                button.setVisibility(view.GONE);

                break;
            case 2:
                view.setBackgroundColor(Color.GRAY);
                image.setImageResource(R.drawable.oreo2);
                textTitle.setText("Oreo");
                textDesc.setText("android 8.0");

                break;
        }

    }
    private void saveIsShown(){
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", true).apply();
    }
}
