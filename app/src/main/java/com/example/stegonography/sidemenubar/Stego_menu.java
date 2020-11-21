package com.example.stegonography.sidemenubar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;
import com.example.stegonography.stegofun.Decreption_page_one;
import com.example.stegonography.stegofun.Encryption_page_one;

public class Stego_menu extends AppCompatActivity {

    private ImageView encript,decript,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stego_menu);

        encript=findViewById(R.id.encription_id);
        decript=findViewById(R.id.decription_id);
        home=findViewById(R.id.homeid);



        encript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Stego_menu.this, Encryption_page_one.class);
                startActivity(intent);

            }
        });

        decript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Stego_menu.this, Decreption_page_one.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Stego_menu.this, Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
