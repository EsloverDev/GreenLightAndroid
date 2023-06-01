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

    //onCreate carga el recurso XML:layout cuando se compila la aplicación

    /**
     * Método que se llama cuando se crea la actividad.
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se usa setContentView() para pasar la referencia al recurso de diseño, en este caso activity_splash
        setContentView(R.layout.activity_splash);

        barraDeCarga = (ProgressBar) findViewById(R.id.progressBar);
        barraDeCarga.setProgress(0);
        barraDeCarga.setMax(100);
        Drawable draw = getResources().getDrawable(R.drawable.custom_progess);
        barraDeCarga.setProgressDrawable(draw);
        barraDeCarga.setVisibility(View.VISIBLE);

            //Esta variable representa el progreso máximo que tiene la barra de carga en la pantalla de Splash
            final int totalProgress = 100;
            //En esta variable se selecciona el incremento del progreso, puede ser de 2 en 2 en este caso es de 1 en 1
            final int progressIncrement = 1;
            //Esta variable establece el tiempo del incremento del progreso en milisegundos
            final int delayMillis = 50;
            //Un handler es una clase que permite la comunicación entre un hilo principal y otros hilos secundarios
            final Handler manejador = new Handler();
            //Un Runnable es una interfaz que representa una tarea que se puede ejecutar mediante la imprlementación
            //del método run
            Runnable runnable = new Runnable() {
                //Esta variable funciona como un acumulador
                int progress = 0;
                @Override
                public void run() {
                    //si el acumulador es menor al progreso total, la progressbar se establece con el valor del
                    // acumulador, y al acumulador se le suma el valor de incremento que se definio anteriormente
                    if (progress <= totalProgress){
                        barraDeCarga.setProgress(progress);
                        progress += progressIncrement;
                        //El Handler utiliza el método postDelayed() el cuál utiliza el Runnable (this) y el retardo
                        //en milisegundos (delayMillis); si el progreso aún no ha alcanzado el valor máximo, el
                        //Runnable se vuelve a programar con otro retardo.
                        manejador.postDelayed(this, delayMillis);
                    }else{
                        //Cuando se ha completado la carga se lanza la siguiente actividad (MainActivity) y se finaliza
                        //la actividad actual
                        Intent intencion = new Intent(Splash.this, MainActivity.class);
                        startActivity(intencion);
                        finish();
                    }
                }
            };
            //Aquí se programa la ejecución del runnable con un retardo de delayMillis, así que después de 5000
            //milisegundos (5 segundos) se ejecutará el contenido del código runnable
            manejador.postDelayed(runnable, delayMillis);
            //TimerTask para programar la ejecución del Runnable después de 5 segundos
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    manejador.postDelayed(runnable, delayMillis);
                }
            };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 5000);

    }
}
