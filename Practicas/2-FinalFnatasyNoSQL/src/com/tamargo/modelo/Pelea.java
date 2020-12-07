package com.tamargo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Pelea implements Serializable {

    private int id;
    private Grupo grupo;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private int resultado; // 0 = derrota, 1 = victoria, 2 = escape...

    public Pelea(int id, Grupo grupo, ArrayList<Enemigo> enemigos, int resultado) {
        this.id = id;
        this.grupo = grupo;
        this.enemigos = enemigos;
        this.resultado = resultado;
    }

    public void addEnemigo(Enemigo enemigo) {
        enemigos.add(enemigo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(ArrayList<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}
