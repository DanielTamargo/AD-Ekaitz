package com.tamargo.modelolistas;

import com.tamargo.modelo.Departamento;

import java.io.Serializable;
import java.util.ArrayList;

public class Departamentos implements Serializable {

    ArrayList<Departamento> lista = new ArrayList<>();

    public Departamentos() {
    }

    public Departamentos(ArrayList<Departamento> lista) {
        this.lista = lista;
    }

    public void addDepartamento(Departamento departamento) {
        lista.add(departamento);
    }

    public ArrayList<Departamento> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Departamento> lista) {
        this.lista = lista;
    }

}
