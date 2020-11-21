package com.example.stegonography.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, Appdrawer.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, 2000);


    }
}
