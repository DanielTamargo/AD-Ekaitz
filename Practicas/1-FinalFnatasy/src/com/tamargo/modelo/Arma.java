package com.tamargo.modelo;

import java.io.Serializable;

public class Arma implements Serializable {

    private int id;
    private String nombre;
    private String tipo;
    private int ataqueFis;
    private int ataqueMag;
    private int defensaFis;
    private int defensaMag;
    private String imagen;
    private int rareza;

    public Arma(int id, String nombre, String tipo, int ataqueFis, int ataqueMag, int defensaFis, int defensaMag, String imagen, int rareza) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataqueFis = ataqueFis;
        this.ataqueMag = ataqueMag;
        this.defensaFis = defensaFis;
        this.defensaMag = defensaMag;
        this.imagen = imagen;
        this.rareza = rareza;
    }

    @Override
    public String toString() {
        return "Arma{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ataqueFis=" + ataqueFis +
                ", ataqueMag=" + ataqueMag +
                ", defensaFis=" + defensaFis +
                ", defensaMag=" + defensaMag +
                ", imagen='" + imagen + '\'' +
                ", rareza=" + rareza +
                '}';
    }

    public int getRareza() {
        return rareza;
    }

    public void setRareza(int rareza) {
        this.rareza = rareza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAtaqueFis() {
        return ataqueFis;
    }

    public void setAtaqueFis(int ataqueFis) {
        this.ataqueFis = ataqueFis;
    }

    public int getAtaqueMag() {
        return ataqueMag;
    }

    public void setAtaqueMag(int ataqueMag) {
        this.ataqueMag = ataqueMag;
    }

    public int getDefensaFis() {
        return defensaFis;
    }

    public void setDefensaFis(int defensaFis) {
        this.defensaFis = defensaFis;
    }

    public int getDefensaMag() {
        return defensaMag;
    }

    public void setDefensaMag(int defensaMag) {
        this.defensaMag = defensaMag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
