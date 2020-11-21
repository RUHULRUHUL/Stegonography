package com.example.stegonography.cryptofun;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class C_Decription_result extends AppCompatActivity {

    private TextView time;
    private TextView resulttext;
    private ImageView home;

    private ImageView share,download;
    private String decription_result_text;
    private long totaltime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__decription_result);
        time=findViewById(R.id.timeid);
        resulttext=findViewById(R.id.decriptiontextid);

        share=findViewById(R.id.share_id);
        download=findViewById(R.id.downloadid);
        home=findViewById(R.id.homeid);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text whatever you want", resulttext.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(C_Decription_result.this, "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String subject=" Decription text";
                String body=decription_result_text ;
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,""));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_Decription_result.this, Appdrawer.class);
                startActivity(intent);
            }
        });

        try {
            decription_result_text=getIntent().getStringExtra("text");
            if (TextUtils.isEmpty(decription_result_text))
            {
                Toast.makeText(this, "no text", Toast.LENGTH_SHORT).show();
            }
            totaltime=getIntent().getLongExtra("time",0);
            long sec =totaltime/1000;
            long milisecond=totaltime%1000;

            time.setText(String.valueOf(sec)+"."+String.valueOf(milisecond)+" ms");

            resulttext.setText(decription_result_text);


        }catch (Exception e)
        {

        }

    }


}
