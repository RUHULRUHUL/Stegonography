package com.example.stegonography;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Alertdialog {
    public void alertView(String message, Context context) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle( "Hello" )
                .setIcon(R.drawable.stegofun)
                .setMessage(message)
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                }).show();
    }
}
