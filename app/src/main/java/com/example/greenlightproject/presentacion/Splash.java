package com.example.greenlightproject.presentacion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenlightproject.R;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends AppCompatActivity {
    ProgressBar barraDeCarga = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intencion = new Intent(Splash.this, MainActivity.class);
                startActivity(intencion);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 5000);
        barraDeCarga = (ProgressBar) findViewById(R.id.progressBar);
        barraDeCarga.setProgress(0);
        barraDeCarga.setMax(100);
        barraDeCarga.setVisibility(View.VISIBLE);
    }
}
