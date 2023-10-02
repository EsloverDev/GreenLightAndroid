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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.greenlightproject.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CrudActivity extends AppCompatActivity {

    private EditText et_nombre, et_password, et_email, et_telefono, et_pais, et_ciudad, et_localidad, et_documento;
    private ActionBar actionBar;
    private Button btnCrear, btnBuscar, btnModificar, btnEliminar, btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        btnCrear = findViewById(R.id.btnRegProd);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregado exitosamente", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.greenwood))
                        .setAction("Deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showNotification("GreenLight",
                                        "Quieres agregar un perfil diferente?");
                            }
                        })
                        .show();
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

    List<String> conectToSQL() {

        List<String> array = new ArrayList<>();

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String password = et_password.getText().toString();
        String email = et_email.getText().toString();
        String telefono = et_telefono.getText().toString();
        String pais = et_pais.getText().toString();
        String ciudad = et_ciudad.getText().toString();
        String localidad = et_localidad.getText().toString();
        String documento = et_documento.getText().toString();

        if (!nombre.isEmpty() && !password.isEmpty() && !email.isEmpty() && !telefono.isEmpty() && !pais.isEmpty() && !ciudad.isEmpty() && !localidad.isEmpty() && !documento.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("password", password);
            registro.put("email", email);
            registro.put("telefono", telefono);
            registro.put("pais", pais);
            registro.put("ciudad", ciudad);
            registro.put("localidad", localidad);
            registro.put("documento", documento);

            db.insert("perfiles", null, registro);
            db.close();

            et_nombre.setText("");
            et_password.setText("");
            et_email.setText("");
            et_telefono.setText("");
            et_pais.setText("");
            et_ciudad.setText("");
            et_localidad.setText("");
            et_documento.setText("");

            SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
            Cursor c = dbRead.rawQuery("SELECT nombre, password, email, telefono, pais, ciudad, localidad, documento FROM perfiles", null);

            if (c.moveToFirst()) {
                do {
                    String column1 = c.getString(0);
                    String column2 = c.getString(1);
                    String column3 = c.getString(2);
                    String column4 = c.getString(3);
                    String column5 = c.getString(4);
                    String column6 = c.getString(5);
                    String column7 = c.getString(6);
                    String column8 = c.getString(7);
                    array.add(column1);
                    array.add(column2);
                    array.add(column3);
                    array.add(column4);
                    array.add(column5);
                    array.add(column6);
                    array.add(column7);
                    array.add(column8);
                    System.out.println("paso producto: " + column1);
                } while (c.moveToNext());
            }
            c.close();
            dbRead.close();
        }
        return array;
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
