package com.example.stegonography.stegofun;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stegonography.R;
import com.example.stegonography.imagesave.Savestorage;
import com.example.stegonography.mainappdrawer.Appdrawer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Decreption_Result extends AppCompatActivity {

    private TextView timetext;
    private ImageView imageView;
    private TextView resulttext;
    private ImageView share,setting,download;

    private ImageView home;
    private Savestorage savestorage;
    private Bitmap imagebitmap;
    private Bitmap dicriptionbitmap;

    private String messege;
    private byte[]bytearrayimage;
    private long time;
    private ProgressDialog save;
    private String imagebit=null;

    private String imagefilepath;
    private Uri uri;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decreption__result);

        savestorage=new Savestorage();
        timetext=findViewById(R.id.timeid);
        imageView=findViewById(R.id.imageviewid);
        resulttext=findViewById(R.id.textresultid);
        share=findViewById(R.id.shareid);
        setting=findViewById(R.id.settingid);
        download=findViewById(R.id.downloadid);




        home=findViewById(R.id.imageView6);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Decreption_Result.this, Appdrawer.class);
                startActivity(intent);
                finish();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String subject="It is a Encription and Decription android app";
                String body= messege.toString();
                if (TextUtils.isEmpty(body))
                {
                    Toast.makeText(Decreption_Result.this, "you have no text", Toast.LENGTH_SHORT).show();
                }
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,"Stegonography"));
            }
        });

        try {

//            pref = getApplicationContext().getSharedPreferences("MyPref", 0);
//            editor = pref.edit();
//            imagebit=pref.getString("image","");
//            bytearrayimage=decodeBase64(imagebit);

            imagefilepath=getIntent().getStringExtra("file_path");
            time=getIntent().getLongExtra("time",0);
            messege=getIntent().getStringExtra("text");

            uri= Uri.parse(imagefilepath);
            dicriptionbitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);




            //dicriptionbitmap=BitmapFactory.decodeByteArray(bytearrayimage, 0, bytearrayimage.length);


            long sec =time/1000;
            long milisecond=time%1000;

            timetext.setText(String.valueOf(sec)+"."+String.valueOf(milisecond)+" ms");

            resulttext.setText(messege);
            imageView.setImageBitmap(dicriptionbitmap);

        }catch (Exception e)
        {
            Toast.makeText(this, "error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }




//        dicriptionbitmap = BitmapFactory.decodeByteArray(bytearrayimage, 0, bytearrayimage.length);
//        imageView.setImageBitmap(dicriptionbitmap);
//        timetext.setText(String.valueOf(time)+"milsec");


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dexter.withContext(Decreption_Result.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                if (TextUtils.isEmpty(imagefilepath))
                                {
                                    Toast.makeText(Decreption_Result.this, "no image", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    final Bitmap imgToSave = dicriptionbitmap;

                                    save = new ProgressDialog(Decreption_Result.this);
                                    save.setMessage("Saving, Please Wait...");
                                    save.setTitle("Saving Image");
                                    save.setIndeterminate(false);
                                    save.setCancelable(false);
                                    save.show();

                                    Thread PerformEncoding = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            saveToInternalStorage(imgToSave);
                                            Intent intent=new Intent(Decreption_Result.this,Appdrawer.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    PerformEncoding.start();
                                }


                            }
                            @Override public void onPermissionDenied(PermissionDeniedResponse response)
                            {

                            }
                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                            {
                                token.continuePermissionRequest();

                            }
                        }).check();

            }
        });

//        download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                imagebitmap = enco;
////                Thread PerformEncoding = new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        saveToInternalStorage(imgToSave);
////                    }
////                });
////                save = new ProgressDialog(Encode.this);
////                save.setMessage("Saving, Please Wait...");
////                save.setTitle("Saving Image");
////                save.setIndeterminate(false);
////                save.setCancelable(false);
////                save.show();
////                PerformEncoding.start();
////
////                savestorage.imagesave(Decreption_Result.this,dicriptionbitmap);
//            }
//        });


    }

    public static byte[] decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return decodedByte;
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        OutputStream fOut;
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "Encoded"+""+String.valueOf(System.currentTimeMillis())+"_image_" + ".PNG"); // the File to save ,
        try {
            fOut = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
            save.dismiss();


            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            Decreption_Result.this.sendBroadcast(intent);

            //Toast.makeText(this, "image has been saved .cheak from gallery...", Toast.LENGTH_SHORT).show();


//            whether_encoded.post(new Runnable() {
//                @Override
//                public void run() {
//                    save.dismiss();
//                }
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            save.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
            save.dismiss();
        }
    }


}
