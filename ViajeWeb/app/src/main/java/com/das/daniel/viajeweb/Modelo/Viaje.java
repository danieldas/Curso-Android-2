package com.das.daniel.viajeweb.Modelo;

/**
 * Created by Daniel on 16/09/2017.
 */
public class Viaje {
String Codigo, Destino, Horario, Precio, Flota, Imagen;

    public Viaje(String codigo, String destino, String horario, String precio, String flota, String imagen) {
        Codigo = codigo;
        Destino = destino;
        Horario = horario;
        Precio = precio;
        Flota = flota;
        Imagen = imagen;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getFlota() {
        return Flota;
    }

    public void setFlota(String flota) {
        Flota = flota;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
