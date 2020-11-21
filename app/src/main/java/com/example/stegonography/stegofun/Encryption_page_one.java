package com.example.stegonography.stegofun;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ayush.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.ayush.imagesteganographylibrary.Text.ImageSteganography;
import com.ayush.imagesteganographylibrary.Text.TextEncoding;
import com.example.stegonography.Alertdialog;
import com.example.stegonography.Encrip_decrip_algorithm.Stegenography;
import com.example.stegonography.R;
import com.example.stegonography.imagesave.Savestorage;
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

public class Encryption_page_one extends AppCompatActivity implements TextEncodingCallback {

    private static final int PICK_PHOTO =10 ;
    private ImageView showimage;
    private Button choseimg;
    private Button encrypt;
    private EditText inputmessege,inputkey;
    private File file;
    private Bitmap bmp;
    private Bitmap encodbitmap;
    private Bitmap encodeimage;
    private long start,end;
   private ImageSteganography imageSteganography;
   private TextEncoding textEncoding;

   private Savestorage savestorage;

   private byte[] bytes;


   private ImageView encodedimageview;

    private String messege,key;
    private Uri imageuri;
    private String imagedata;
    private Alertdialog alertdialog;
    private Encryption_Result encryption_result;

    private ProgressDialog save;


    private ImageView home;
    private ProgressDialog progressDialog;

    public Stegenography stegenography;

    private  String filepath;


    private long starttime,endtime;
    private long totaltime;





    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption_page_one);

        showimage=findViewById(R.id.showimageid);
        choseimg=findViewById(R.id.chose_image_id);
        encrypt=findViewById(R.id.encriptid);
        inputmessege=findViewById(R.id.editText);
        inputkey=findViewById(R.id.input_key_id);

//        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
//        editor = pref.edit();



        stegenography=new Stegenography();

        progressDialog=new ProgressDialog(Encryption_page_one.this);
        progressDialog.setTitle("image encryption");
        progressDialog.setMessage("please wait image encyption");

        encryption_result=new Encryption_Result();

        /*

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Encryption_page_one.this,Appdrawer.class);
                startActivity(intent);

//
//                Dexter.withContext(Encryption_page_one.this)
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override public void onPermissionGranted(PermissionGrantedResponse response)
//                            {
//                                final Bitmap imgToSave = encodeimage;
//
//                                save = new ProgressDialog(Encryption_page_one.this);
//                                save.setMessage("Saving, Please Wait...");
//                                save.setTitle("Saving Image");
//                                save.setIndeterminate(false);
//                                save.setCancelable(false);
//                                save.show();
//
//                                Thread PerformEncoding = new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        saveToInternalStorage(imgToSave);
//                                    }
//                                });
//
//                                PerformEncoding.start();
//
//                            }
//                            @Override public void onPermissionDenied(PermissionDeniedResponse response)
//                            {
//
//                            }
//                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
//                            {
//                                token.continuePermissionRequest();
//
//                            }
//                        }).check();








            }
        });

         */


        choseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(Encryption_page_one.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Tack Image"), PICK_PHOTO);

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

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messege=inputmessege.getText().toString();
                key=inputkey.getText().toString();



                if (TextUtils.isEmpty(messege))
                {
                    Toast.makeText(Encryption_page_one.this, "input messege", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(key))
                {
                    Toast.makeText(Encryption_page_one.this, "input key", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(imagedata))
                {
                    Toast.makeText(Encryption_page_one.this, "please attach image", Toast.LENGTH_SHORT).show();

                }
                else {


                    try {

                        starttime=System.currentTimeMillis();
                        ImageSteganography imageSteganography = new ImageSteganography(messege, key, bmp);
                        TextEncoding textEncoding = new TextEncoding(Encryption_page_one.this, Encryption_page_one.this);
                        textEncoding.execute(imageSteganography);
                        endtime=System.currentTimeMillis();
                        totaltime=endtime-starttime;


                    }
                    catch (Exception e)
                    {
                        Toast.makeText(encryption_result, "error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }




                    /*
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    encodbitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] bytes = stream.toByteArray();

                    Intent intent=new Intent(Encryption_page_one.this,Encryption_Result.class);
                    intent.putExtra("time",totaltime);
                    intent.putExtra("image",bytes);
                    startActivity(intent);
                    finish();

                     */


                }




            }
        });


    }

    private void savetouristoragewithimage(Bitmap bitmapimage)
    {
        progressDialog.show();
        OutputStream fOut;
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "Encoded"+""+String.valueOf(System.currentTimeMillis())+"_image_" + ".PNG"); // the File to save ,
        try {
            fOut = new FileOutputStream(file);


            Uri uri=Uri.fromFile(file);
            filepath=uri.toString();
            Log.d("filepath",filepath);


            bitmapimage.compress(Bitmap.CompressFormat.PNG, 80, fOut); // saving the Bitmap to a file
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
            //save.dismiss();




            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            Encryption_page_one.this.sendBroadcast(intent);
            progressDialog.dismiss();

            //Toast.makeText(this, "image has been saved .cheak from gallery...", Toast.LENGTH_SHORT).show();


//            whether_encoded.post(new Runnable() {
//                @Override
//                public void run() {
//                    save.dismiss();
//                }
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
            //save.dismiss();
            progressDialog.dismiss();
        }

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

            Uri uri=Uri.fromFile(file);
            filepath=uri.toString();
            Log.d("filepath",filepath);


            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            Encryption_page_one.this.sendBroadcast(intent);

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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "no image sellect", Toast.LENGTH_SHORT).show();
                //error
                return;
            }
            try {
                imageuri = data.getData();
                imagedata=imageuri.toString();
               // bmp= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                bmp=MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                //file= new File(String.valueOf(imageuri));


                showimage.setImageBitmap(bmp);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTextEncoding() {



    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography imageSteganography)
    {
        progressDialog.show();

        /*
//        this.imageSteganography = imageSteganography;
//
//        if (imageSteganography != null && imageSteganography.isEncoded()){
//
//            //encrypted image bitmap is extracted from result object
//            encodbitmap = imageSteganography.getEncoded_image();
//
//            //set text and image to the UI component.
//            //textView.setText("Encoded");
//            showimage.setImageBitmap(encodbitmap);


         */

        if (imageSteganography != null && imageSteganography.isEncoded())
        {


            calculate(imageSteganography);


        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "no image selllect", Toast.LENGTH_SHORT).show();
        }


    }

    private void calculate(ImageSteganography imageSteganography) {


        encodeimage = imageSteganography.getEncoded_image();




        savetouristoragewithimage(encodeimage);

        /*
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        encodeimage.compress(Bitmap.CompressFormat.PNG, 90, stream);
//        bytes = stream.toByteArray();
//
//        //if (bytes.length>)
//
//        String bitmaptostringconvert = Base64.encodeToString(bytes, Base64.DEFAULT);
//        editor.putString("image",bitmaptostringconvert);
//        editor.commit();
//        editor.apply();


         */


      if (TextUtils.isEmpty(filepath))
      {
          progressDialog.dismiss();
          Toast.makeText(this, "please attach file  ", Toast.LENGTH_SHORT).show();

      }
      else {
          progressDialog.dismiss();
          Toast.makeText(this, "total time: "+String.valueOf(totaltime), Toast.LENGTH_SHORT).show();
          Intent intent=new Intent(Encryption_page_one.this,Encryption_Result.class);
          intent.putExtra("time",totaltime);
          intent.putExtra("file_path",filepath);

          progressDialog.dismiss();
          startActivity(intent);
          finish();
      }
    }

    /*

    @Override
    public void onStartTextEncoding() {

    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography imageSteganography) {


        this.imageSteganography = imageSteganography;
        if (imageSteganography != null && imageSteganography.isEncoded()) {
            end=System.currentTimeMillis()-start;
            encodeimage = imageSteganography.getEncoded_image();


            //alertdialog.alertView("Encription Complete",getApplicationContext());

            Intent intent = new Intent(this, Encryption_Result.class);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            encodeimage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
            intent.putExtra("image",bytes);
            intent.putExtra("time",end);
            startActivity(intent);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

     */
}
