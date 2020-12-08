package com.tamargo.modelolistas;

import com.tamargo.modelo.IniciativaActiva;

import java.io.Serializable;
import java.util.ArrayList;

public class IniciativasActivas implements Serializable {

    ArrayList<IniciativaActiva> lista = new ArrayList<>();

    public IniciativasActivas() {
    }

    public IniciativasActivas(ArrayList<IniciativaActiva> lista) {
        this.lista = lista;
    }

    public void addIniciativaActiva(IniciativaActiva iniciativaActiva) {
        lista.add(iniciativaActiva);
    }

    public ArrayList<IniciativaActiva> getLista() {
        return lista;
    }

    public void setLista(ArrayList<IniciativaActiva> lista) {
        this.lista = lista;
    }
    
}
