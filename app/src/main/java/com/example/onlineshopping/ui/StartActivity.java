package com.example.onlineshopping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineshopping.R;

public class StartActivity extends AppCompatActivity {
    private static int splashScreen = 4000 ;
    Animation bottomAnim , topAnim ;
    ImageView imageView ;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        imageView = findViewById(R.id.imageView);
        imageView.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(StartActivity.this , MainActivity.class);
                startActivity(i);
                finish();
            }
        },splashScreen);
    }
}