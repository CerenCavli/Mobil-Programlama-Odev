package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.ceren.cavli.homework.R;

public class MainActivity extends AppCompatActivity {
    ImageView imgOpener;
    public static String firstname, lastname;
    public static int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ceren ÇAVLİ 20574060");

    }
}