package com.tamargo.modelo;

import java.io.Serializable;
import java.util.Random;

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
                ", imagen='" + imagen + '\'' +
                ", nivelInicial=" + nivelInicial +
                '}';
    }

    // INVENTADA MÁXIMA
    public int experienciaADar(int ronda) {
        int experienciaTotal = experiencia;
        if (atributos.getNivel() > nivelInicial) {
            int experienciaPlusPorNivel = 10 * nivelInicial + ronda;
            experienciaTotal += (experienciaPlusPorNivel * (atributos.getNivel() - nivelInicial) / 2);
        }
        return experienciaTotal;
    }

    public int danyoTotalRecibido(int danyoFis, int danyoMag) {
        int danyoTotal = 0;
        if (new Random().nextInt(100) + 1 <= atributos.getAgilidad() * 3)
            return -99999;
        danyoTotal += danyoFisRecibido(danyoFis);
        danyoTotal += danyoMagRecibido(danyoMag);
        return danyoTotal;
    }

    public int danyoMagRecibido(int danyoMag) {
        return danyoMag - (atributos.getDefensaMag() * 4);
    }

    public int danyoFisRecibido(int danyoFis) {
        return danyoFis - (atributos.getDefensaFis() * 4);
    }

    public int calcularVida() {
        return (atributos.getVitalidad() * 75) + (atributos.getNivel() * 3);
    }

    public int calcularDanyoFis() {
        int danyoFis = (atributos.getFuerza() * 10);
        Random r = new Random();
        if (r.nextInt(100) + 1  <= atributos.getDestreza() * 3)
            danyoFis += (danyoFis * 0.15); //Pum critiquín
        return danyoFis;
    }

    public int calcularDanyoMag() {
        int danyoMag = (atributos.getPoderMagico() * 10);
        Random r = new Random();
        if (r.nextInt(100) + 1  <= atributos.getDestreza() * 3)
            danyoMag += (danyoMag * 0.15); //Pum critiquín
        return danyoMag;
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
