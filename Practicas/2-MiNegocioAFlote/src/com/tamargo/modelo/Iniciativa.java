package com.tamargo.modelo;

import java.io.Serializable;

public class Iniciativa implements Serializable {

    private int id;
    private String nombre;
    private int duracion; //dias
    private int nivelNecesario;
    private int coste;
    private int ganancias;

    public Iniciativa(int id, String nombre, int duracion, int nivelNecesario, int coste, int ganancias) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.nivelNecesario = nivelNecesario;
        this.coste = coste;
        this.ganancias = ganancias;
    }

    @Override
    public String toString() {
        return "Iniciativa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", nivelNecesario=" + nivelNecesario +
                ", coste=" + coste +
                ", ganancias=" + ganancias +
                '}';
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getNivelNecesario() {
        return nivelNecesario;
    }

    public void setNivelNecesario(int nivelNecesario) {
        this.nivelNecesario = nivelNecesario;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    public int getGanancias() {
        return ganancias;
    }

    public void setGanancias(int ganancias) {
        this.ganancias = ganancias;
    }
}
