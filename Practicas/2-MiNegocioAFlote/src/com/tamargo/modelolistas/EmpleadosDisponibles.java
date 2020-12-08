package com.tamargo.modelolistas;

import com.tamargo.modelo.EmpleadoDisponible;

import java.io.Serializable;
import java.util.ArrayList;

public class EmpleadosDisponibles implements Serializable {

    ArrayList<EmpleadoDisponible> lista = new ArrayList<>();

    public EmpleadosDisponibles() {
    }

    public EmpleadosDisponibles(ArrayList<EmpleadoDisponible> lista) {
        this.lista = lista;
    }

    public void addEmpleadoDisponible(EmpleadoDisponible empleadoDisponible) {
        lista.add(empleadoDisponible);
    }

    public ArrayList<EmpleadoDisponible> getLista() {
        return lista;
    }

    public void setLista(ArrayList<EmpleadoDisponible> lista) {
        this.lista = lista;
    }


}
