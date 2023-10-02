package com.example.greenlightproject.repository;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.greenlightproject.R;
import com.example.greenlightproject.presentacion.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CrudActivity extends AppCompatActivity {

    private EditText et_nombre, et_password, et_email, et_telefono, et_pais, et_ciudad, et_localidad, et_documento;
    private ActionBar actionBar;
    private Button btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar;

    AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);


        btnCrear = findViewById(R.id.btnRegUsu);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
// Llamar al método para ingresar los registros y obtener los valores ingresados por el usuario desde EditText.
                dbHelper.insertarReg(String.valueOf(et_nombre.getText().toString()),
                        String.valueOf(et_password.getText().toString()),
                        String.valueOf(et_email.getText().toString()),
                        String.valueOf(et_telefono.getText().toString()),
                        String.valueOf(et_pais.getText().toString()),
                        String.valueOf(et_ciudad.getText().toString()),
                        String.valueOf(et_localidad.getText().toString()),
                        String.valueOf(et_documento.getText().toString()));

                dbHelper.close();
                Toast.makeText(getApplicationContext(), "Usuario agregado exitosamente", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_password = (EditText) findViewById(R.id.txt_password);
        et_email = (EditText) findViewById(R.id.txt_email);
        et_telefono = (EditText) findViewById(R.id.txt_telefono);
        et_pais = (EditText) findViewById(R.id.txt_pais);
        et_ciudad = (EditText) findViewById(R.id.txt_ciudad);
        et_localidad = (EditText) findViewById(R.id.txt_localidad);
        et_documento = (EditText) findViewById(R.id.txt_documento);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.savefreepik);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 8);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void showNotification(String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MY_CHANNEL_ID",
                    "MY_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "MY_CHANNEL_ID")
                .setSmallIcon(R.mipmap.savefreepik)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        Intent intent = new Intent(getApplicationContext(), CrudActivity.class);

        PendingIntent p1 = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(p1);
        mNotificationManager.notify(0, mBuilder.build());
    }




    private void setListData(List<String> your_array_List) {
        ListView lv = (ListView) findViewById(R.id.listViewData);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_List );

        lv.setAdapter(arrayAdapter);
    }

}
