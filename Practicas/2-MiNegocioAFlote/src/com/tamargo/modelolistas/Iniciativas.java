package com.tamargo.modelolistas;

import com.tamargo.modelo.Iniciativa;

import java.io.Serializable;
import java.util.ArrayList;

public class Iniciativas implements Serializable {

    ArrayList<Iniciativa> lista = new ArrayList<>();

    public Iniciativas() {
    }

    public Iniciativas(ArrayList<Iniciativa> lista) {
        this.lista = lista;
    }

    public void addIniciativa(Iniciativa iniciativa) {
        lista.add(iniciativa);
    }

    public ArrayList<Iniciativa> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Iniciativa> lista) {
        this.lista = lista;
    }
    
}
