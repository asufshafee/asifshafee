package com.example.pk.reviewcollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000;
    private boolean mIsBackButtonPressed;
    MyApplication app;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app=new MyApplication(getApplicationContext());

         prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);




        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!mIsBackButtonPressed) {
                    if (prefs.getBoolean("islogin",false ))
                    {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("islogin", true);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), new_feeds.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("islogin", false);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

            }

        }, SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();

    }

}

