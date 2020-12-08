package com.tamargo.modelolistas;

import com.tamargo.modelo.Dato;

import java.io.Serializable;
import java.util.ArrayList;

public class Datos implements Serializable {

    private ArrayList<Dato> lista = new ArrayList<>();

    public Datos() {
    }

    public Datos(ArrayList<Dato> lista) {
        this.lista = lista;
    }

    public void addDato(Dato dato) {
        lista.add(dato);
    }

    public ArrayList<Dato> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Dato> lista) {
        this.lista = lista;
    }
}
