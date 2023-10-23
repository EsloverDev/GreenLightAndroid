package com.example.greenlightproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE perfiles(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, password TEXT, email TEXT, telefono TEXT, pais TEXT, ciudad TEXT, localidad TEXT, documento TEXT)";
    private static final String DB_NAME = "greenlight";
    private static final int DB_VERSION = 2;

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Método para guardar usuarios en la base de datos de la app (SQLite)
    public void insertarReg(String nombre, String password, String email, String telefono,
                            String pais, String ciudad, String localidad, String documento) {

        // Crear un registro (ContentValues) con los datos ingresados.
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("password", password);
        registro.put("email", email);
        registro.put("telefono", telefono);
        registro.put("pais", pais);
        registro.put("ciudad", ciudad);
        registro.put("localidad", localidad);
        registro.put("documento", documento);

        // Insertar el registro en la tabla "perfiles".
        this.getWritableDatabase().insert("perfiles", null, registro);

    }

    //Método que permite validar si el usuario existe
    public Cursor consultarUsuPas(String usu, String pas) throws SQLException {
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query("perfiles", new String[]{"_id", "nombre",
                "password", "email", "telefono", "pais", "ciudad", "localidad", "documento"},
                "email like '"+usu+"' and password like '"+pas+"'",null,null,
                null,null);
        return mcursor;
    }
}
