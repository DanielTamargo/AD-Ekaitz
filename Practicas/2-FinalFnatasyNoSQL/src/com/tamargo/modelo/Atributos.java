package com.tamargo.modelo;

import java.io.Serializable;

public class Atributos implements Serializable {

    private int id;
    private int nivel;
    private int vitalidad;
    private int fuerza;
    private int poderMagico;
    private int destreza; // +3% probabilidad critico
    private int agilidad; // +3% esquivar
    private int defensaFis;
    private int defensaMag;

    public Atributos(int id, int nivel, int vitalidad, int fuerza, int poderMagico, int destreza, int agilidad, int defensaFis, int defensaMag) {
        this.id = id;
        this.nivel = nivel;
        this.vitalidad = vitalidad;
        this.fuerza = fuerza;
        this.poderMagico = poderMagico;
        this.destreza = destreza;
        this.agilidad = agilidad;
        this.defensaFis = defensaFis;
        this.defensaMag = defensaMag;
    }

    @Override
    public String toString() {
        return "Atributos{" +
                "id=" + id +
                ", nivel=" + nivel +
                ", vitalidad=" + vitalidad +
                ", fuerza=" + fuerza +
                ", poderMagico=" + poderMagico +
                ", destreza=" + destreza +
                ", agilidad=" + agilidad +
                ", defensaFis=" + defensaFis +
                ", defensaMag=" + defensaMag +
                '}';
    }

    public void subirNivel() {
        nivel ++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVitalidad() {
        return vitalidad;
    }

    public void setVitalidad(int vitalidad) {
        this.vitalidad = vitalidad;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public void setPoderMagico(int poderMagico) {
        this.poderMagico = poderMagico;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getAgilidad() {
        return agilidad;
    }

    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
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
}
