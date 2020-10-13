package com.tamargo.modelo;

import java.io.Serializable;

public class Enemigo implements Serializable {

    private int id;
    private String nombre;
    private int experiencia;
    private String descripcion;
    private Atributos atributos;
    private String imagen;
    private int nivelInicial;

    public Enemigo(int id, String nombre, int experiencia, String descripcion, Atributos atributos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.descripcion = descripcion;
        this.atributos = atributos;
        this.imagen = imagen;
        nivelInicial = atributos.getNivel();
    }

    @Override
    public String toString() {
        return "Enemigo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", experiencia=" + experiencia +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", nivelInicial=" + nivelInicial +
                '}';
    }

    public int experienciaADar() {
        int experienciaTotal = experiencia;
        if (atributos.getNivel() > nivelInicial) {
            int experienciaPlusPorNivel = 10 * nivelInicial;
            experienciaTotal += (experienciaPlusPorNivel * (atributos.getNivel() - nivelInicial) / 2);
        }
        return experienciaTotal;
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

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
