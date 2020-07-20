package com.example.floatingdialog;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ID_READ_PHONE_STATE = 1;
    public static final int REQUEST_ID_SYSTEM_WINDOW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermissions();
    }

    private  boolean checkAndRequestPermissions() {
        int read_Phone_State = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (read_Phone_State != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_ID_READ_PHONE_STATE);
        }
//Alert window with explanation before navigating to Settings page
        if (!Settings.canDrawOverlays(this)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("System Alert Window permission necessary");
            alertBuilder.setMessage("User needs to manually set this permission from Settings to allow floating dialog." +
                    "Not Needed when launched from Play Store.");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivityForResult(myIntent,REQUEST_ID_SYSTEM_WINDOW );
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }else
            setContentView(R.layout.activity_main);

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check if received result code is equal our requested code for draw permission
        if (requestCode == REQUEST_ID_SYSTEM_WINDOW) {
                if (Settings.canDrawOverlays(this)) {
                    setContentView(R.layout.activity_main);
                }else
                    //Exit app since permission not set
                    System.exit(0);
            }
        }
    }