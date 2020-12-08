package com.tamargo.modelolistas;

import com.tamargo.modelo.EmpleadoContratado;

import java.io.Serializable;
import java.util.ArrayList;

public class EmpleadosContratados implements Serializable {

    ArrayList<EmpleadoContratado> lista = new ArrayList<>();

    public EmpleadosContratados() {
    }

    public EmpleadosContratados(ArrayList<EmpleadoContratado> lista) {
        this.lista = lista;
    }

    public void addEmpleadoContratado(EmpleadoContratado empleadoContratado) {
        lista.add(empleadoContratado);
    }

    public ArrayList<EmpleadoContratado> getLista() {
        return lista;
    }

    public void setLista(ArrayList<EmpleadoContratado> lista) {
        this.lista = lista;
    }


}
