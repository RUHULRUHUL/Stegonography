package com.example.stegonography.cryptofun;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class C_Encription_result extends AppCompatActivity {

    private TextView  time;
    private TextView textView;

    private long total_time;

//    private long sec;
//    private long milisecond;


    private String encriptext;
    private ImageView home;
    private ImageView share,download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__encription_result);

        time=findViewById(R.id.timecalculate_id);
        textView=findViewById(R.id.encription_result_text);
        home=findViewById(R.id.homid);
        share=findViewById(R.id.shareid);
        download=findViewById(R.id.downloadid);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text whatever you want", textView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Intent intent=new Intent(C_Encription_result.this,C_Decription_page_one.class);
                startActivity(intent);
                finish();

                Toast.makeText(C_Encription_result.this, "All Text Copied,please past decription", Toast.LENGTH_SHORT).show();


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String subject="Encription Text ";
                String body=encriptext;
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,""));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_Encription_result.this, Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });


        try {

            total_time=getIntent().getLongExtra("time",0);

            long sec =total_time/1000;
            long milisecond=total_time%1000;

            time.setText(String.valueOf(sec)+"."+String.valueOf(milisecond)+" ms");
            //time.setText(String.format("%s.%s", String.valueOf(sec), String.valueOf(milisecond)));

            encriptext=getIntent().getStringExtra("text");
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }



          textView.setText(encriptext);





    }
}
