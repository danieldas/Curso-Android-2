package com.das.daniel.ventalocal.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 14/09/2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ventasBD.sqlite";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBManagerProducto.CREATE_TABLE);
        db.execSQL(DBManagerVenta.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBManagerProducto.NOMBRE_TABLA);
        onCreate(db);
    }
}
