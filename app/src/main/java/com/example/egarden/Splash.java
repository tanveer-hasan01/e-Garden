package com.example.egarden;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.egarden.databinding.ActivityMainBinding;
import com.example.egarden.databinding.ActivitySplashBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreference sharedPreference;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreference=SharedPreference.getPreferences(Splash.this);

      /*  Glide.with(Splash.this)
                .load(R.mipmap.ic_app_icon)
                .into(binding.imageView2);*/

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (sharedPreference.getName().equals("none"))
                {

                    Intent mainIntent = new Intent(Splash.this, login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                }else {
                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}