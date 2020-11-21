package com.example.stegonography.cryptofun;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.Encrip_decrip_algorithm.CryptoEncrypt;
import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class C_Decription_page_one extends AppCompatActivity {

    private EditText inputmessege;
    private EditText inputkey;
    private ImageView home;

    private Button decreption_button;

    private String messege,key;

    private String decripresult;
    private CryptoEncrypt cryptoEncrypt;
    private String decription_result;
    private long totaltime;
    private long starttime,endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__decription_page_one);

        cryptoEncrypt=new CryptoEncrypt();
        inputmessege=findViewById(R.id.editText);
        inputkey=findViewById(R.id.keyid);
        decreption_button=findViewById(R.id.decrypt);

        home=findViewById(R.id.homeid);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_Decription_page_one.this, Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });

        decreption_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messege=inputmessege.getText().toString();

                key=inputkey.getText().toString();

                if (TextUtils.isEmpty(messege))
                {
                    Toast.makeText(C_Decription_page_one.this, "please input messege", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(key))
                {
                    Toast.makeText(C_Decription_page_one.this, "please input key", Toast.LENGTH_SHORT).show();
                }

                else {
                    try {
                        starttime=System.currentTimeMillis();
                        decripresult=cryptoEncrypt.getCryptoDecrypt(messege,key);
                        endtime=System.currentTimeMillis();
                        totaltime=endtime-starttime;

                    }catch (Exception e)
                    {
                        Toast.makeText(C_Decription_page_one.this, "should be problemes decreption", Toast.LENGTH_SHORT).show();
                    }

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(C_Decription_page_one.this);
                    dialog.setTitle( "" )
                            .setIcon(R.drawable.stegofun)
                            .setMessage("Decreption Complete")
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                    //Toast.makeText(C_Decription_page_one.this, "decrep"+decripresult, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(C_Decription_page_one.this, C_Decription_result.class);
                                    intent.putExtra("text",decripresult);
                                    intent.putExtra("time",totaltime);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();




                }

            }

        });
        /*
        decreption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messege=inputmessege.getText().toString();

                key=inputkey.getText().toString();

                if (TextUtils.isEmpty(messege))
                {
                    Toast.makeText(C_Decription_page_one.this, "please input messege", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(key))
                {
                    Toast.makeText(C_Decription_page_one.this, "please input key", Toast.LENGTH_SHORT).show();
                }

                else {
                    try {
                        decripresult=cryptoEncrypt.getCryptoDecrypt(messege,key);
                    }catch (Exception e)
                    {
                        Toast.makeText(C_Decription_page_one.this, "should be problemes decreption", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(C_Decription_page_one.this, "decrep"+decripresult, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(C_Decription_page_one.this,C_Decription_result.class);
                    intent.putExtra("text",decripresult);
                    intent.putExtra("time",totaltime);
                    startActivity(intent);
                }

            }
        });


         */


    }

//    @Override
//    public long gettiime(long time) {
//        totaltime=time;
//    }
}
