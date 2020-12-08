package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Empresa implements Serializable {

    private LocalDate fechaCreacion;
    private int id;
    private String nombre;
    private String ciudad;
    private LocalDate fechaActual;

    /**
     * Constructor para nuevas empresas en el juego
     */
    public Empresa(int id, String nombre, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;

        this.fechaCreacion = LocalDate.now();
        this.fechaActual = LocalDate.now();
    }

    /**
     * Constructor para generar empresas con fechas ya establecidas
     */
    public Empresa(LocalDate fechaCreacion, int id, String nombre, String ciudad, LocalDate fechaActual) {
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fechaActual = fechaActual;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "fechaCreacion=" + fechaCreacion +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fechaActual=" + fechaActual +
                '}';
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(LocalDate fechaActual) {
        this.fechaActual = fechaActual;
    }
}
