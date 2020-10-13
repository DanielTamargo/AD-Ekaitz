package com.tamargo.modelo;

import java.io.Serializable;

public class Evento implements Serializable {

    private int id;
    private String descripcion;
    private int efecto; // 1 = Nuevo arma, 2 = Puzle oportuno, 3 = Subida de nivel

    public Evento(int id, String descripcion, int efecto) {
        this.id = id;
        this.descripcion = descripcion;
        this.efecto = efecto;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", descripcion='" + descripcion.substring(0, 10) + "..." + '\'' +
                ", efecto=" + efecto +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEfecto() {
        return efecto;
    }

    public void setEfecto(int efecto) {
        this.efecto = efecto;
    }
}
