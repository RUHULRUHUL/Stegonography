package com.example.stegonography.sidemenubar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.cryptofun.C_Decription_page_one;
import com.example.stegonography.cryptofun.C_Encription_page_one;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class Cryptomenu_Activity extends AppCompatActivity {


    private ImageView encrip,decrip;
    private ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptomenu_);

        encrip=findViewById(R.id.cruptofun_encrip_id);
        decrip=findViewById(R.id.cruptofun_decrep_id);


        home=findViewById(R.id.homeid);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cryptomenu_Activity.this, Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });
        encrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cryptomenu_Activity.this, C_Encription_page_one.class);
                startActivity(intent);
            }
        });
        decrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cryptomenu_Activity.this, C_Decription_page_one.class);
                startActivity(intent);
            }
        });

    }

//    public void onBackPressed() {
//
//        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
//        alertdialog.setTitle("Warning");
//        alertdialog.setMessage("Are you sure you Want to exit app");
//        alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                Intent intent=new Intent(secAddition.this,addition.class);
////                startActivity(intent);
////                secAddition.this.finish();
//
//                finishAffinity();
//                System.exit(0);
//
//
//            }
//        });
//
//        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        AlertDialog alert=alertdialog.create();
//        alertdialog.show();
//
//    }
}
