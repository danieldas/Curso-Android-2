package com.das.daniel.ventalocal.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.das.daniel.ventalocal.Modelo.Producto;

import java.util.List;

/**
 * Created by Daniel on 14/09/2017.
 */
public abstract class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;



    public DBManager(Context context) {
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

    abstract public void insertarProducto(String id, String descripcion, String cantidad, String precio,
                                            String estado, String tipo );
    abstract public void actualizarProducto(String id, String descripcion, String cantidad, String precio,
                                           String tipo );
    abstract public void darBajaProducto(String id, String estado );
    abstract public void actualizarCantidad(String id, String cantidad );

    abstract public Cursor cargarCursor();
    abstract public Cursor cargarCursorBuscar(String descripcion);

    abstract public List<Producto> getProductosList();
    abstract public List<Producto> Buscar(String descripcion);
}
