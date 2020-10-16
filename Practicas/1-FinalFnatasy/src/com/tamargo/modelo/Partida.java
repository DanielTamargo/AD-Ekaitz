package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Partida implements Serializable {

    private int id;
    private int ronda;
    private boolean finalizada;
    private Grupo grupo;
    private ArrayList<Enemigo> enemigosDerrotados = new ArrayList<>();
    private ArrayList<Evento> eventosPasados = new ArrayList<>();
    private String fecha;

    public Partida() {
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
        return "Partida{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", ronda=" + ronda +
                ", grupo=" + grupo +
                ", enemigosDerrotados=" + enemigosDerrotados +
                ", eventosPasados=" + eventosPasados +
                '}';
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
