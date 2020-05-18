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

import com.airbnb.lottie.LottieAnimationView;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BoardFragment extends Fragment {

    TextView main, second;
    Button button;
    LottieAnimationView lottie;



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
        main = view.findViewById(R.id.main_text);
        lottie = view.findViewById(R.id.lottie_json);
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
                view.setBackgroundColor(Color.LTGRAY);
                lottie.setAnimation(R.raw.facebook);
                main.setText("Facebook");
                main.setTextColor(Color.BLUE);
                button.setVisibility(view.GONE);
                break;
            case 1:
                view.setBackgroundColor(Color.YELLOW);
                lottie.setAnimation(R.raw.whatsapp);
                main.setText("WhatsApp");
                main.setTextColor(Color.GREEN);
                button.setVisibility(view.GONE);

                break;
            case 2:
                view.setBackgroundColor(Color.DKGRAY);
                main.setTextColor(Color.MAGENTA);
                lottie.setAnimation(R.raw.instagram);
                main.setText("Instagramm");
                break;
        }

    }
    private void saveIsShown(){
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", true).apply();
    }
}
