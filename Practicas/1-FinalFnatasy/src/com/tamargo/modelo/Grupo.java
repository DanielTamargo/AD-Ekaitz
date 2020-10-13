package com.tamargo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Grupo implements Serializable {

    private int id;
    private ArrayList<Personaje> personajes = new ArrayList<>();
    private int atributoEspecial;
    // 1 = Inteligencia (puzles), 2 = Crueldad (5% ejecutar),
    // 3 = Sinergía (estadísticas + 1), 4 = Curtido (10% critico)

    public Grupo(int id, ArrayList<Personaje> personajes, int atributoEspecial) {
        this.id = id;
        this.personajes = personajes;
        this.atributoEspecial = atributoEspecial;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", personajes=" + personajes +
                ", atributoEspecial=" + atributoEspecial +
                '}';
    }

    public void addPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public int getAtributoEspecial() {
        return atributoEspecial;
    }

    public void setAtributoEspecial(int atributoEspecial) {
        this.atributoEspecial = atributoEspecial;
    }
}
