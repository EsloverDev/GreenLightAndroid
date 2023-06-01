package com.example.greenlightproject.presentacion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.greenlightproject.R;

public class MainActivity extends AppCompatActivity {

    private EditText edUsuario, edContrasena;
    private Button btIniciar, btRegistro;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aquí se referencia el EditText y el Button con sus correspondientes en el layout activity_main.xml por
        //medio del id.
        edUsuario = findViewById(R.id.usuarioInicio);
        btIniciar = findViewById(R.id.btnIniciar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.savefreepik);

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, UbicacionesActivity.class);
                //Esta línea permite capturar el dato que se escriba como usuario para pasarlo a la siguiente actividad
                mainIntent.putExtra("nombre", edUsuario.getText().toString());
                startActivity(mainIntent);
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 8);
        }
        return super.dispatchTouchEvent(ev);
    }
}