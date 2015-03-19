package com.example.crescenttechticketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class splash extends Activity {
    //MediaPlayer mysound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //mysound = MediaPlayer.create(splash.this, R.raw.cross);
        //mysound.start();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(splash.this, IntegratedClass.class);
                    startActivity(i);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //mysound.release();
        finish();
    }

}
