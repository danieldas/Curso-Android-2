package com.das.daniel.ventalocal.DB;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Daniel on 28/09/2017.
 */
public class DBManagerVenta extends DBManagerV {


    public static final String NOMBRE_TABLA="venta";
    private static final String COL_ID="_id";
    private static final String COL_OBSERVACION="observacion";
    private static final String COL_CANTIDADVENTA="cantidadVenta";
    private static final String COL_FECHA="fecha";
    private static final String COL_FKPROD="fkProd";

    public static final String CREATE_TABLE="create table " + NOMBRE_TABLA +" ("+
            COL_ID+" integer PRIMARY KEY AUTOINCREMENT, "+
            COL_OBSERVACION+" text NOT NULL, "+
            COL_CANTIDADVENTA+" text NOT NULL, "+
            COL_FECHA+" text NOT NULL, "+
            COL_FKPROD+" integer NOT NULL); ";


    public DBManagerVenta(Context context) {
        super(context);
    }

    @Override
    public void cerrar() {
        super.getDb().close();
    }

    private ContentValues generarValores(String idVenta, String observacion, String cantidadVenta, String fecha, String fkProd) {
        ContentValues valores = new ContentValues();
        valores.put(COL_ID, idVenta);
        valores.put(COL_OBSERVACION, observacion);
        valores.put(COL_CANTIDADVENTA, cantidadVenta);
        valores.put(COL_FECHA, fecha);
        valores.put(COL_FKPROD, fkProd);

        return valores;
    }
    @Override
    public void insertarVenta(String idVenta, String observacion, String cantidadVenta, String fecha, String fkProd) {
        super.getDb().insert(NOMBRE_TABLA,null,
                generarValores(idVenta, observacion,cantidadVenta, fecha, fkProd));
    }
}
