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

import com.example.stegonography.Alertdialog;
import com.example.stegonography.Encrip_decrip_algorithm.CryptoEncrypt;
import com.example.stegonography.Encrip_decrip_algorithm.EncryptionAlgorithm;
import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;

public class C_Encription_page_one extends AppCompatActivity{

    private EditText inputmessege;
    private EditText inputkey;
    private Button encryp;
    private ImageView home;

    private String encrypresult;
    private CryptoEncrypt cryptoEncrypt;

    private String messege,key;

    private long starttime,endtime;
    private long totaltime;

    private Alertdialog alertdialog;
    private EncryptionAlgorithm encryptionAlgorithm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__encription_page_one);

        inputmessege=findViewById(R.id.editText);
        inputkey=findViewById(R.id.inputkeyid);
        encryp=findViewById(R.id.encriptid);

        home=findViewById(R.id.homeid);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(C_Encription_page_one.this,Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });

        cryptoEncrypt=new CryptoEncrypt();
        alertdialog=new Alertdialog();



        encryp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key=inputkey.getText().toString();
                messege=inputmessege.getText().toString();

                if (TextUtils.isEmpty(messege))
                {
                    inputmessege.setError("please input messege");
                }
                else if (TextUtils.isEmpty(key))
                {
                    inputkey.setError("please input messege");
                }
                else {
                    starttime=System.currentTimeMillis();
                    encrypresult=cryptoEncrypt.getCryptoEncrypt(messege,key);




                    //totaltime=encryptionAlgorithm.finaltime;
                    //Toast.makeText(C_Encription_page_one.this, "text: "+encrypresult+"Total: "+String.valueOf(totaltime), Toast.LENGTH_LONG).show();


                    if (TextUtils.isEmpty(encrypresult))
                    {
                        Toast.makeText(C_Encription_page_one.this, "you have no encripted text", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(C_Encription_page_one.this);
                        dialog.setTitle( "" )
                                .setIcon(R.drawable.stegofun)
                                .setMessage("Encription Complete")
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialoginterface, int i) {

                                        endtime=System.currentTimeMillis();
                                        totaltime=endtime-starttime;

                                        Toast.makeText(C_Encription_page_one.this, "time: "+String.valueOf(totaltime), Toast.LENGTH_LONG).show();

                                        dialoginterface.dismiss();
                                        Intent intent=new Intent(C_Encription_page_one.this,C_Encription_result.class);
                                        intent.putExtra("text",encrypresult);
                                        intent.putExtra("time",totaltime);
                                        startActivity(intent);
                                        finish();

                                    }
                                }).show();


                    }





                }



            }
        });

    }

}
