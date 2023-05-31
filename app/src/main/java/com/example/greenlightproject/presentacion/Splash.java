package com.example.greenlightproject.presentacion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

            final int totalProgress = 100;
            final int progressIncrement = 1;
            final int delayMillis = 50;
            final Handler manejador = new Handler();
            Runnable runnable = new Runnable() {
                int progress = 0;
                @Override
                public void run() {
                    if (progress <= totalProgress){
                        barraDeCarga.setProgress(progress);
                        progress += progressIncrement;
                        manejador.postDelayed(this, delayMillis);
                    }else{
                        //Se ha completado la carga
                        Intent intencion = new Intent(Splash.this, MainActivity.class);
                        startActivity(intencion);
                        finish();
                    }
                }
            };
            manejador.postDelayed(runnable, delayMillis);

            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    manejador.postDelayed(runnable, delayMillis);
                }
            };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 5000);
        barraDeCarga = (ProgressBar) findViewById(R.id.progressBar);
        barraDeCarga.setProgress(0);
        barraDeCarga.setMax(100);
        Drawable draw = getResources().getDrawable(R.drawable.custom_progess);
        barraDeCarga.setProgressDrawable(draw);
        barraDeCarga.setVisibility(View.VISIBLE);
    }
}
