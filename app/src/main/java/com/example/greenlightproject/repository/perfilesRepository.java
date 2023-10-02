package com.example.greenlightproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.greenlightproject.modelo.Perfil;

import java.util.List;

public class perfilesRepository extends SQLiteOpenHelper {

    SQLiteDatabase dbWrite = null;
    SQLiteDatabase dbRead = null;

    static final String table = "perfiles";
    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE " + table + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, password TEXT, email TEXT, telefono TEXT, pais TEXT, ciudad TEXT, localidad TEXT, documento TEXT)";
    private static final String DELETE_TABLE_COMMAND = "DELETE FROM ";

    public perfilesRepository(Context context) {
        super(context, SQLBase.DB_NAME, null, SQLBase.DB_VERSION);
        dbWrite = getWritableDatabase();
        dbRead = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    public int getFirstIdTable(){
        int idRes = -1;
        if (dbRead != null) {
            Cursor c = dbRead.rawQuery("SELECT _id FROM"+table, null);
            if (c.moveToFirst()){
                do {
                    idRes = Integer.parseInt(c.getString(0));
                    break;
                } while(c.moveToNext());
            }
            c.close();
        }
        return idRes;
    }

    public boolean updateRecord(int id, String nombre, String password, String email, String telefono, String pais, String ciudad, String localidad, String documento) {
        boolean res = false;
        String sentencia = "UPDATE "+table+
                " SET nombre = '"+nombre +"' , "+
                "password = '"+password +"' , "+
                "email = '"+email +"' , "+
                "telefono = '"+telefono +"' , "+
                "pais = '"+pais +"' , "+
                "ciudad = '"+ciudad +"' , "+
                "localidad = '"+localidad +"' , "+
                "documento = '"+documento +"' "+
                " WHERE " +
                " _id = "+id+";";
        System.out.println("sentencia: "+sentencia);
        if (dbWrite != null) {
            dbWrite.execSQL(sentencia);
            res = true;
        }
        return res;
    }

    public boolean clear (){
        boolean res = false;
        if (dbWrite != null) {
            dbWrite.execSQL(DELETE_TABLE_COMMAND + " " +table);
        }
        return res;
    }

    public static boolean write(SQLiteDatabase dbWrite, Perfil perf) {
        boolean res = false;

        if (dbWrite != null){
            ContentValues cv = new ContentValues();
            cv.put("nombre", perf.getNombre());
            cv.put("password", perf.getPassword());
            cv.put("email", perf.getEmail());
            cv.put("telefono", perf.getTelefono());
            cv.put("pais", perf.getPais());
            cv.put("ciudad", perf.getCiudad());
            cv.put("localidad", perf.getLocalidad());
            cv.put("documento", perf.getDocumento());

            dbWrite.insert("perfiles", null, cv);
            dbWrite.close();
            res = true;
        }
        return res;
    }
}
