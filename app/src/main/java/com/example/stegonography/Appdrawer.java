package com.example.stegonography;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Appdrawer extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ImageView navigationBar_drawer_icon;
    private ConstraintLayout constraintLayout;
    private ImageView stegofun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdrawer);

        onSetNavigationDrawerEvents();


    }

    public void onSetNavigationDrawerEvents() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationBar_drawer_icon = (ImageView) findViewById(R.id.nav_drawer_icon);
       constraintLayout=findViewById(R.id.constrint_layout_id);
       stegofun=findViewById(R.id.cryptoFunId);

        ActionBar mActionBar=Appdrawer.this.getSupportActionBar();



        navigationBar_drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.nav_drawer_icon:
                        drawerLayout.openDrawer(navigationView, true);
                        break;
                    default:
                        drawerLayout.closeDrawer(navigationView, true);
                        break;

                }
            }
        });



    }



}
