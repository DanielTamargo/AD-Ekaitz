package com.tamargo.modelo;

import com.tamargo.LeerDatosBase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Partida implements Serializable {

    private int id = 1;
    private int ronda = 1;
    private boolean finalizada = false;
    private Grupo grupo;
    private ArrayList<Enemigo> enemigosDerrotados = new ArrayList<>();
    private ArrayList<Evento> eventosPasados = new ArrayList<>();
    private String fecha;

    public Partida() {
        obtenerFecha();
    }

    public Partida(int ronda, boolean finalizada, Grupo grupo, ArrayList<Enemigo> enemigosDerrotados, ArrayList<Evento> eventosPasados) {
        this.id = 1; // sobreescribir más tarde con setId();
        this.finalizada = finalizada;
        this.ronda = ronda;
        this.grupo = grupo;
        this.enemigosDerrotados = enemigosDerrotados;
        this.eventosPasados = eventosPasados;
        obtenerFecha();
    }

    public Partida(Grupo grupo) {
        id = new LeerDatosBase().leerListaPartidas().getLista().size() + 1;
        this.grupo = grupo;
        obtenerFecha();
    }

    public Partida(int id, int ronda, boolean finalizada, Grupo grupo, ArrayList<Enemigo> enemigosDerrotados, ArrayList<Evento> eventosPasados) {
        this.id = id;
        this.finalizada = finalizada;
        this.ronda = ronda;
        this.grupo = grupo;
        this.enemigosDerrotados = enemigosDerrotados;
        this.eventosPasados = eventosPasados;
        obtenerFecha();
    }

    public void obtenerFecha() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        fecha = currentDateTime.format(formateadorFecha);
        //System.out.println(fecha);
    }

    @Override
    public String toString() {
        String nombrePj1 = grupo.getPersonajes().get(0).getNombre();
        String nombrePj2 = grupo.getPersonajes().get(1).getNombre();
        String nombrePj3 = grupo.getPersonajes().get(2).getNombre();
        int nivelPj1 = grupo.getPersonajes().get(0).getAtributos().getNivel();
        int nivelPj2 = grupo.getPersonajes().get(1).getAtributos().getNivel();
        int nivelPj3 = grupo.getPersonajes().get(2).getAtributos().getNivel();
        return String.format("Partida: %3d | %s | Ronda: %3d | Grupo: %s (%d), %s (%d), %s (%d)", id, fecha, ronda, nombrePj1, nivelPj1, nombrePj2, nivelPj2, nombrePj3, nivelPj3);
    }

    public void actualizarFecha() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        fecha = currentDateTime.format(formateadorFecha);
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public String getFecha() {
        return fecha;
    }

    public void addEventoPasado(Evento evento) {
        eventosPasados.add(evento);
    }

    public void addEnemigoDerrotado(Enemigo enemigo) {
        enemigosDerrotados.add(enemigo);
    }

    public boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public ArrayList<Enemigo> getEnemigosDerrotados() {
        return enemigosDerrotados;
    }

    public void setEnemigosDerrotados(ArrayList<Enemigo> enemigosDerrotados) {
        this.enemigosDerrotados = enemigosDerrotados;
    }

    public ArrayList<Evento> getEventosPasados() {
        return eventosPasados;
    }

    public void setEventosPasados(ArrayList<Evento> eventosPasados) {
        this.eventosPasados = eventosPasados;
    }
}
