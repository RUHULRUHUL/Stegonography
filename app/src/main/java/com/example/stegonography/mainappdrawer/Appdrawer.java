package com.example.stegonography.mainappdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.stegonography.R;
import com.example.stegonography.sidemenubar.Cryptomenu_Activity;
import com.example.stegonography.sidemenubar.Faq;
import com.example.stegonography.sidemenubar.Info;
import com.example.stegonography.sidemenubar.Stego_menu;
import com.google.android.material.navigation.NavigationView;

public class Appdrawer extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ImageView navigationBar_drawer_icon;
    private ConstraintLayout constraintLayout;
    private ImageView stegofun,cryptofun;


    private TextView stegofunsidnav,cryptofunsidnav,info,faq;
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
       stegofun=findViewById(R.id.stegofun_id);
       cryptofun=findViewById(R.id.sefeId);

       stegofunsidnav=findViewById(R.id.stegofunid);
        cryptofunsidnav=findViewById(R.id.cryptofunid);
       info=findViewById(R.id.infoid);
       faq=findViewById(R.id.faqid);

        ActionBar mActionBar=Appdrawer.this.getSupportActionBar();


        stegofunsidnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this, Stego_menu.class);
                startActivity(intent);

            }
        });

        cryptofunsidnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this, Cryptomenu_Activity.class);
                startActivity(intent);

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this, Info.class);
                startActivity(intent);

            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this, Faq.class);
                startActivity(intent);

            }
        });

        stegofun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this,Stego_menu.class);
                startActivity(intent);

            }
        });
        cryptofun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appdrawer.this,Cryptomenu_Activity.class);
                startActivity(intent);
            }
        });
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

    public void onBackPressed() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure you Want to exit app");
        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent=new Intent(secAddition.this,addition.class);
//                startActivity(intent);
//                secAddition.this.finish();

                finishAffinity();
                System.exit(0);

            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=alertdialog.create();
        alertdialog.show();

    }



}
