package com.example.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a.utilidades.Utilidades;

/**Esta clase hace la conexión a la base de datos**/
public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    /**Constructor**/
    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**Genera la tabla**/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_TODO);
    }

    /**Cada vez que se instala la app verifica si ya hay alguna existente
     * mira lo que valía la versión antigua y refresca a una nueva**/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS objeto");
        onCreate(db);
    }
}

