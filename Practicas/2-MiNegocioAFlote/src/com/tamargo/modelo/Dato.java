package com.tamargo.modelo;

import java.io.Serializable;

public class Dato implements Serializable {

    private String tipo;
    private String dato;

    public Dato(String tipo, String dato, int id) {
        this.tipo = tipo;
        this.dato = dato;
    }

    @Override
    public String toString() {
        return "DatoBase{" +
                "tipo='" + tipo + '\'' +
                ", dato='" + dato + '\'' +
                '}';
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
