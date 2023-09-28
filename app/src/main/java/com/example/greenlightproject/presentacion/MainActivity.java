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
import android.widget.Toast;

import com.example.greenlightproject.R;
import com.example.greenlightproject.interfaces.PerfilApi;
import com.example.greenlightproject.modelo.Perfil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        edContrasena = findViewById(R.id.passwordId);
        btIniciar = findViewById(R.id.btnIniciar);
        btRegistro = findViewById(R.id.btnRegistro);

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

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarPerfil();
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 8);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registrarPerfil(){
        String username = edUsuario.getText().toString();
        String password = edContrasena.getText().toString();

        Perfil p = new Perfil(username, password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.20.21:8080/").addConverterFactory(GsonConverterFactory.create()).build();

        PerfilApi perfilApi = retrofit.create(PerfilApi.class);
        Call<Perfil> call = perfilApi.registrarDatos(p);

        call.enqueue(new Callback<Perfil>() {

            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                Toast.makeText(MainActivity.this, "Registrado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}