package com.tamargo.actividad8;

import java.io.Serializable;

class Departamento implements Serializable {
    private char[] nombre = new char[20];
    private char[] loc = new char[20];
    private int dep;

    public Departamento(char[] nombre, char[] loc, int dep) {
        this.nombre = nombre;
        this.loc = loc;
        this.dep = dep;
    }

    public Departamento() {
    }

    public char[] getNombre() {
        return nombre;
    }

    public void setNombre(char[] nombre) {
        this.nombre = nombre;
    }

    public char[] getLoc() {
        return loc;
    }

    public void setLoc(char[] loc) {
        this.loc = loc;
    }

    public int getDep() {
        return dep;
    }

    public void setDep(int dep) {
        this.dep = dep;
    }
}
