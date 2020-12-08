package com.tamargo.modelolistas;

import com.tamargo.modelo.Empresa;

import java.io.Serializable;
import java.util.ArrayList;

public class Empresas implements Serializable {

    ArrayList<Empresa> lista = new ArrayList<>();

    public Empresas() {
    }

    public Empresas(ArrayList<Empresa> lista) {
        this.lista = lista;
    }

    public void addEmpresa(Empresa empresa) {
        lista.add(empresa);
    }

    public ArrayList<Empresa> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Empresa> lista) {
        this.lista = lista;
    }


}
