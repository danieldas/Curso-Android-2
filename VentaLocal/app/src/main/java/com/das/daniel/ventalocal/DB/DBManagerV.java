package com.das.daniel.ventalocal.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Daniel on 28/09/2017.
 */
public abstract class DBManagerV {
    private DBHelper dbHelper;
    private SQLiteDatabase db;



    public DBManagerV(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void cerrar(){
        db.close();
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    abstract public void insertarVenta(String idVenta, String observacion, String cantidadVenta, String fecha, String fkProd);

}
