package com.tamargo.modelo;

import java.io.Serializable;

public class Personaje implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;

    private Arma arma;
    private TipoPersonaje tipo;
    private Atributos atributos;

    private int experienciaConseguida = 0;
    private int experienciaNecesaria = 100;
    private int nivelesSubidos = 0;

    private String imagen;

    public Personaje(int id, String nombre, String descripcion, Arma arma, TipoPersonaje tipo,
                     Atributos atributos, int experienciaConseguida, int experienciaNecesaria,
                     int nivelesSubidos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.arma = arma;
        this.tipo = tipo;
        this.atributos = atributos;
        this.experienciaConseguida = experienciaConseguida;
        this.experienciaNecesaria = experienciaNecesaria;
        this.nivelesSubidos = nivelesSubidos;
        this.imagen = imagen;
    }

    public Personaje(int id, String nombre, String descripcion, Arma arma, TipoPersonaje tipo, Atributos atributos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.arma = arma;
        this.tipo = tipo;
        this.atributos = atributos;
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo.name() +
                ", imagen='" + imagen + '\'' +
                '}';
    }

    public void subirNivel() {
        // Si la experiencia es igual o mayor que la necesaria para subir de nivel, subimos de nivel
        // y aumentamos la experiencia necesaria para subir otro nivel
        // También contemplamos la posiblidad de subir varios niveles a la vez
        if (experienciaConseguida >= experienciaNecesaria) {
            experienciaConseguida-= experienciaNecesaria;
            atributos.subirNivel();
            nivelesSubidos ++;
            experienciaNecesaria += 50;
            subirNivel();
        }
    }

    // Por cada nivel subido dejaremos que suba 5 puntos de habilidad, y luego reiniciaremos los nivelesSubidos
    public void reiniciarNivelesSubidos() {
        nivelesSubidos = 0;
    }

    public int getNivelesSubidos() {
        return nivelesSubidos;
    }

    public void setNivelesSubidos(int nivelesSubidos) {
        this.nivelesSubidos = nivelesSubidos;
    }

    public int getExperienciaConseguida() {
        return experienciaConseguida;
    }

    public void setExperienciaConseguida(int experienciaConseguida) {
        this.experienciaConseguida = experienciaConseguida;
    }

    public int getExperienciaNecesaria() {
        return experienciaNecesaria;
    }

    public void setExperienciaNecesaria(int experienciaNecesaria) {
        this.experienciaNecesaria = experienciaNecesaria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public TipoPersonaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonaje tipo) {
        this.tipo = tipo;
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
