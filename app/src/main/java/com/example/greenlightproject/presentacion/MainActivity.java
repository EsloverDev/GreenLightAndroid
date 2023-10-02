package com.example.greenlightproject.presentacion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenlightproject.MainMenu;
import com.example.greenlightproject.R;

import com.example.greenlightproject.repository.AdminSQLiteOpenHelper;
import com.example.greenlightproject.repository.CrudActivity;


public class MainActivity extends AppCompatActivity {

    private EditText edCorreo, edContrasena;
    private Button btInicio, btRegistro;
    private ActionBar actionBar;

    AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aquí se referencia el EditText y el Button con sus correspondientes en el layout activity_main.xml por
        //medio del id.
        edCorreo = findViewById(R.id.correoInicio);
        edContrasena = findViewById(R.id.contrasenaInicio);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.savefreepik);

        btInicio = findViewById(R.id.btnIniciar);
        btRegistro = findViewById(R.id.btnRegistrar);

        btInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Cursor cursor = dbHelper.consultarUsuPas(edCorreo.getText().toString(), edContrasena.getText().toString());
                    if (cursor.getCount() > 0) {
                        Intent mainIntent = new Intent(MainActivity.this, MainMenu.class);
                        //Esta línea permite capturar el dato que se escriba como usuario para pasarlo a la siguiente actividad
                        mainIntent.putExtra("nombre", edCorreo.getText().toString());
                        startActivity(mainIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                    edCorreo.setText("");
                    edContrasena.setText("");
                    edCorreo.findFocus();
                } catch (SQLException e){
                    e.printStackTrace();
                }



            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CrudActivity.class);
                startActivity(i);
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
}