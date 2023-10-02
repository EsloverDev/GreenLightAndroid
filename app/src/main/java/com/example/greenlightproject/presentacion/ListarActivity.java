package com.example.greenlightproject.presentacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import com.example.greenlightproject.R;
import com.example.greenlightproject.casosDeUso.PerfilesCasosDeUso;
import com.example.greenlightproject.modelo.Perfil;
import com.example.greenlightproject.repository.AdminSQLiteOpenHelper;
import com.example.greenlightproject.repository.perfilesRepository;

import java.util.ArrayList;
import java.util.List;

public class ListarActivity extends AppCompatActivity {

    private EditText et_nombre, et_password, et_email, et_telefono, et_pais, et_ciudad, et_localidad, et_documento;
    private Button btn;
    private ActionBar actionBar;
    private perfilesRepository perfilDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        refreshList();

        btn = findViewById(R.id.btnSQL);

        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                PerfilesCasosDeUso perfil = new PerfilesCasosDeUso();
                Perfil perfilDB = perfil.crearPerfil(et_nombre.getText().toString(), et_password.getText().toString(), et_email.getText().toString(), et_telefono.getText().toString(), et_pais.getText().toString(), et_ciudad.getText().toString(), et_localidad.getText().toString(), et_documento.getText().toString());

                AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(getApplicationContext());
                perfilesRepository.write(dbHelper.getWritableDatabase(), perfilDB);

                refreshList();
            }
        });

        Button btnLimpiar = findViewById(R.id.btnBorrar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                perfilDB.clear();
                refreshList();
            }
        });

        Button btnActualiza = findViewById(R.id.btnActualiza);
        btnActualiza.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                int idUser = perfilDB.getFirstIdTable();
                perfilDB.updateRecord(idUser, "nombre defecto", "password", "email", "telefono", "pais", "ciudad", "localidad", "documento");
                refreshList();
            }
        });

        Button btnAgregar = findViewById(R.id.btnSave);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            Perfil newPerfil = new Perfil();

            public void onClick(View v) {
                String nombre = et_nombre.getText().toString();
                String password = et_password.getText().toString();
                String email = et_email.getText().toString();
                String telefono = et_telefono.getText().toString();
                String pais = et_pais.getText().toString();
                String ciudad = et_ciudad.getText().toString();
                String localidad = et_localidad.getText().toString();
                String documento = et_documento.getText().toString();

                // Inicializa dbHelper para obtener una instancia de SQLiteDatabase
                AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                List<String> array = new ArrayList<>();

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
                        } while (c.moveToNext());
                    }
                    c.close();
                    dbRead.close();
                }
                refreshList();
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

    void refreshList(){
        List<String> your_array_list = getDataUsersSQL();
        setListData(your_array_list);
    }

    List<String> getDataUsersSQL() {
        List<String> array = null;
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);

        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        if (dbRead !=null) {
            Cursor c = dbRead.rawQuery("SELECT _id, nombre, password, email, telefono, pais, ciudad, localidad, documento FROM perfiles", null);
            if (c.moveToFirst()) {
                array = new ArrayList<>();
                do {
                    String columnRes = c.getString(0) + " : " + c.getString(1) + " : " + c.getString(2) + " : " + c.getString(3) + " : " + c.getString(4) + " : " + c.getString(5) + " : " + c.getString(6) + " : " + c.getString(7) + " -> " + c.getString(8);

                    array.add(columnRes);
                    System.out.println("get data usuario: " + columnRes);
                } while (c.moveToNext());
            }
            c.close();
            dbRead.close();
        }
        return array;
    }

    private void setListData(List<String> your_array_list) {
        ListView lv = (ListView) findViewById(R.id.listViewData);

        if (your_array_list == null) {
            your_array_list = new ArrayList<>();
            lv.setEmptyView(findViewById(android.R.id.empty));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);
    }

}
