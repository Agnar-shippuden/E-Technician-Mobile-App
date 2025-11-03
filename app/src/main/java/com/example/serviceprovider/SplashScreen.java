package com.example.serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    private static final int SPLASH_SCREEN = 3000;

    // variables here for splash screen
    Animation topAnim , bottomAnim ;
    ImageView splashImage ;
    TextView logo , slogan ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        // Animations jo splash screen ma han
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // yahan images and text define ho rahe han
        splashImage = findViewById(R.id.splashimage);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        splashImage.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        // ye code splash screen sa Login Activity jana k liye ....
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, OptionsActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN );

        // yahan khatam splash screen
    }
}