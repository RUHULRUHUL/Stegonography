package com.example.stegonography.stegofun;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ayush.imagesteganographylibrary.Text.AsyncTaskCallback.TextDecodingCallback;
import com.ayush.imagesteganographylibrary.Text.ImageSteganography;
import com.ayush.imagesteganographylibrary.Text.TextDecoding;
import com.example.stegonography.Encrip_decrip_algorithm.Stegenography;
import com.example.stegonography.R;
import com.example.stegonography.mainappdrawer.Appdrawer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Decreption_page_one extends AppCompatActivity implements TextDecodingCallback {

    private static final int PICK_PHOTO = 10;
    private Button chosebutton;
    private ImageView imageView;
    private EditText inputkey;
    private Button decrypt;

    private String imagedata;
    private ImageView home;
    private Uri imageuri;
    private Bitmap bmp;
    private byte[] bytes;

    private String decod_text;
    private Bitmap decode_bitmap;

    private ImageSteganography imageSteganography;
    private Stegenography stegenography;
    private long starttime,endtime;
    private long totaltime;


    private String messege;

    /*
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


     */
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decreption_page_one);

        stegenography=new Stegenography();
        chosebutton=findViewById(R.id.chosebuttonid);
        imageView=findViewById(R.id.imageviewid);
        inputkey=findViewById(R.id.inputkeyid);
        decrypt=findViewById(R.id.decryptid);
        home=findViewById(R.id.homeid);

        progressDialog=new ProgressDialog(Decreption_page_one.this);
        progressDialog.setTitle("Image Decyption");
        progressDialog.setMessage("please wait image full decryption");
        progressDialog.setCanceledOnTouchOutside(false);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Decreption_page_one.this, Appdrawer.class);
                startActivity(intent);
            }
        });
        chosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(Decreption_page_one.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String secret_key=inputkey.getText().toString();


                if (TextUtils.isEmpty(secret_key))
                {
                    Toast.makeText(Decreption_page_one.this, "please input key", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(imagedata))
                {
                    Toast.makeText(Decreption_page_one.this, "please attach image", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {

                        starttime=System.currentTimeMillis();

                        ImageSteganography imageSteganography = new ImageSteganography(secret_key, bmp);

                        //Making the TextDecoding object
                        TextDecoding textDecoding = new TextDecoding(Decreption_page_one.this, Decreption_page_one.this);

                        textDecoding.execute(imageSteganography);

                        endtime=System.currentTimeMillis();
                        totaltime=endtime-starttime;

                        /*
//                        decod_text=stegenography.decode(bmp);
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                        bytes = stream.toByteArray();

                         */
                    }catch (Exception e)
                    {
                        Toast.makeText(Decreption_page_one.this, "", Toast.LENGTH_SHORT).show();
                    }

                    /*
//                    Intent intent=new Intent(Decreption_page_one.this,Decreption_Result.class);
//                    intent.putExtra("time",totaltime);
//                    intent.putExtra("image",bytes);
//                    startActivity(intent);
//                    finish();

                     */
                }
                
                



            }
        });
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

                bmp= MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);

                if (imageuri!=null)
                {
                    imageView.setImageURI(imageuri);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTextEncoding() {

    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result)
    {

        if (result!=null)
        {
            progressDialog.show();
            calculate(result);
        }
        else {
            progressDialog.dismiss();
        }
       
        


    }

    private void calculate(ImageSteganography result) {

        if (result != null)
        {
            if (!result.isDecoded())
            {
                progressDialog.dismiss();
                //textView.setText("No message found");
                Toast.makeText(this, "you have no messege found", Toast.LENGTH_SHORT).show();

            }

            else {
                if (!result.isSecretKeyWrong()) {


                    if (result != null) {
                        if (!result.isDecoded())
                        {

                        }
                        //textView.setText("No message found");
                        else {
                            if (!result.isSecretKeyWrong()) {

                                messege=result.getMessage();


                                Toast.makeText(this, "total time: "+String.valueOf(totaltime), Toast.LENGTH_SHORT).show();

                                /*
                                //whether_encoded.setText("Encoded");

//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                                bytes = stream.toByteArray();
//
//                                String bitmaptostringconvert = Base64.encodeToString(bytes, Base64.DEFAULT);
//                                editor.putString("image",bitmaptostringconvert);
//                                editor.commit();
//                                editor.apply();


                                 */
                                Intent intent=new Intent(Decreption_page_one.this,Decreption_Result.class);
                                intent.putExtra("time",totaltime);
                                intent.putExtra("text",messege);
                                intent.putExtra("file_path",imagedata);

                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(this, "wron key input ,please vailde key", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        progressDialog.dismiss();

                    }


                    //textView.setText("Decoded");
                    //message.setText("" + result.getMessage());

//                    encodeimage = imageSteganography.getEncoded_image();
//                    //whether_encoded.setText("Encoded");
//                    encodedimageview.setImageBitmap(encodeimage);
//
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    encodeimage.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                    bytes = stream.toByteArray();
//
//                    Intent intent=new Intent(Encryption_page_one.this,Encryption_Result.class);
//                    intent.putExtra("time",totaltime);
//                    intent.putExtra("image",bytes);
//                    startActivity(intent);
//                    finish();
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                    bytes = stream.toByteArray();
//
//                    Intent intent=new Intent(Decreption_page_one.this,Decreption_Result.class);
//                    intent.putExtra("text",result.getMessage());
//                    intent.putExtra("image",bytes);
//                    intent.putExtra("time",totaltime);
//                    startActivity(intent);



                } else {
                    progressDialog.dismiss();
                    //textView.setText("Wrong secret key");
                    Toast.makeText(this, "you have to wrong  key", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "sellect image first", Toast.LENGTH_SHORT).show();
        }
    }



//    @Override
//    public void onStartTextEncoding() {
//
//    }
//
//    @Override
//    public void onCompleteTextEncoding(ImageSteganography imageSteganography) {
//
//        this.imageSteganography = imageSteganography;
//
//        if (imageSteganography != null){
//
//            /* If result.isDecoded() is false, it means no Message was found in 					the image. */
//            if (!imageSteganography.isDecoded())
//            {
//
//            }
////                textView.setText("No message found");
//
//            else{
//                /* If result.isSecretKeyWrong() is true, it means that secret key provided 				is wrong. */
//                if (!imageSteganography.isSecretKeyWrong()){
//                    //set the message to the UI component.
////                    textView.setText("Decoded");
////                    message.setText("" + result.getMessage());
//
//                    decod_text=imageSteganography.getMessage();
//
//                }
//                else {
//
//                }
//            }
//        }
//        else {
//            //If result is null it means that bitmap is null
//
//        }
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
