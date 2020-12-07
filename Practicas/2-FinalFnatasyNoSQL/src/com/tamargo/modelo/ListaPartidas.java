package com.tamargo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaPartidas implements Serializable {

    private ArrayList<Partida> lista = new ArrayList<>();

    public ListaPartidas() {
    }

    public ListaPartidas(ArrayList<Partida> lista) {
        this.lista = lista;
    }

    public void addPartida(Partida partida) {
        lista.add(partida);
    }

    public ArrayList<Partida> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Partida> lista) {
        this.lista = lista;
    }

}
