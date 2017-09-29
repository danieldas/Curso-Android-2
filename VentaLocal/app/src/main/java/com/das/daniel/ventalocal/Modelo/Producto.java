package com.das.daniel.ventalocal.Modelo;

/**
 * Created by Daniel on 14/09/2017.
 */
public class Producto {

    //atributos
    private String _ID;
    private String precio;
    private String descripcion;
    private String cantidad;
    private String tipoProducto;
    private String estado;

    public Producto(){}
    public Producto(String _ID,  String precio, String descripcion, String cantidad, String tipoProducto, String estado) {
        this._ID = _ID;

        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
        this.estado = estado;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }





    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
