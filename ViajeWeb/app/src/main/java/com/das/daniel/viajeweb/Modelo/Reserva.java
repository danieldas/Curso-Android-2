package com.das.daniel.viajeweb.Modelo;

/**
 * Created by Daniel on 16/09/2017.
 */
public class Reserva {
String IdReserva, Nombre, Apellido, Ci, FkViaje;

    public Reserva(String idReserva, String nombre, String apellido, String ci, String fkViaje) {
        IdReserva = idReserva;
        Nombre = nombre;
        Apellido = apellido;
        Ci = ci;
        FkViaje = fkViaje;
    }

    public String getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(String idReserva) {
        IdReserva = idReserva;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCi() {
        return Ci;
    }

    public void setCi(String ci) {
        Ci = ci;
    }

    public String getFkViaje() {
        return FkViaje;
    }

    public void setFkViaje(String fkViaje) {
        FkViaje = fkViaje;
    }
}
