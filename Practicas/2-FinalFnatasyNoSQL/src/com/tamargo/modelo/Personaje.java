package com.tamargo.modelo;

import java.io.Serializable;
import java.util.Random;

public class Personaje implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;

    private Arma arma;
    private TipoPersonaje tipo;
    private Atributos atributos;

    private int experienciaConseguida = 0;
    private int experienciaNecesaria = 100;
    private int nivelesSubidos = 0;

    private String imagen;

    public Personaje(int id, String nombre, String descripcion, Arma arma, TipoPersonaje tipo,
                     Atributos atributos, int experienciaConseguida, int experienciaNecesaria,
                     int nivelesSubidos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.arma = arma;
        this.tipo = tipo;
        this.atributos = atributos;
        this.experienciaConseguida = experienciaConseguida;
        this.experienciaNecesaria = experienciaNecesaria;
        this.nivelesSubidos = nivelesSubidos;
        this.imagen = imagen;
    }

    public Personaje(int id, String nombre, String descripcion, Arma arma, TipoPersonaje tipo, Atributos atributos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.arma = arma;
        this.tipo = tipo;
        this.atributos = atributos;
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo.name() +
                ", imagen='" + imagen + '\'' +
                '}';
    }

    public void addExperienciaConseguida(int experiencia) {
        experienciaConseguida += experiencia;
        subirNivel();
    }

    public void subirNivel() {
        // Si la experiencia es igual o mayor que la necesaria para subir de nivel, subimos de nivel
        // y aumentamos la experiencia necesaria para subir otro nivel
        // También contemplamos la posiblidad de subir varios niveles a la vez
        if (experienciaConseguida >= experienciaNecesaria) {
            experienciaConseguida -= experienciaNecesaria;
            atributos.subirNivel();
            nivelesSubidos ++;
            experienciaNecesaria += 50;
            subirNivel();
        }
    }

    // TODO INVENTADA MÁXIMA, TOCARÁ BALANCEARLO NEXT LEVEL
    public int danyoTotalRecibido(int danyoFis, int danyoMag) {
        int danyoTotal = 0;
        Random r = new Random();
        if (tipo == TipoPersonaje.Arquero) {
            if (r.nextInt(100) + 1 <= atributos.getAgilidad() * 3 + 20)
                return -99999;
        } else {
            if (r.nextInt(100) + 1 <= atributos.getAgilidad() * 3)
                return -99999;
        }

        danyoTotal += danyoFisRecibido(danyoFis);
        danyoTotal += danyoMagRecibido(danyoMag);

        if (tipo == TipoPersonaje.Guardian) // Guardian reduce un 10% del daño
            danyoTotal = danyoTotal - (int)(danyoTotal * 0.10);
        return danyoTotal;
    }

    public int danyoMagRecibido(int danyoMag) {
        return danyoMag - (atributos.getDefensaMag() * 3 + arma.getDefensaMag() * 5);
    }

    public int danyoFisRecibido(int danyoFis) {
        return danyoFis - (atributos.getDefensaFis() * 3 + arma.getDefensaFis() * 5);
    }

    public int calcularVida() {
        return (atributos.getVitalidad() * 100) + (atributos.getNivel() * 3);
    }

    public int calcularDanyoFis() {
        int danyoFis = (atributos.getFuerza() * 7) + (arma.getAtaqueFis() * 15);;
        Random r = new Random();
        if (tipo == TipoPersonaje.Asesino) { // Asesino = +20% prob critico
            if (r.nextInt(100) + 1 <= atributos.getDestreza() * 3 + 20)
                danyoFis += (danyoFis * 0.15); //Pum critiquín
        } else {
            if (r.nextInt(100) + 1 <= atributos.getDestreza() * 3)
                danyoFis += (danyoFis * 0.15); //Pum critiquín
        }
        if (tipo == TipoPersonaje.Guerrero) // Guerrero = +15% daño fisico
            danyoFis += (danyoFis * 0.15);
        return danyoFis;
    }

    public int calcularDanyoMag() {
        int danyoMag = (atributos.getPoderMagico() * 7) + (arma.getAtaqueMag() * 15);
        Random r = new Random();
        if (tipo == TipoPersonaje.Asesino) { // Asesino = +20% prob critico
            if (r.nextInt(100) + 1 <= atributos.getDestreza() * 3 + 20)
                danyoMag += (danyoMag * 0.15); //Pum critiquín
        } else {
            if (r.nextInt(100) + 1 <= atributos.getDestreza() * 3)
                danyoMag += (danyoMag * 0.15); //Pum critiquín
        }
        if (tipo == TipoPersonaje.Mago) // Mago = +15% daño fisico
            danyoMag += (danyoMag * 0.15);
            return danyoMag;
    }



    // Por cada nivel subido dejaremos que suba 5 puntos de habilidad, y luego reiniciaremos los nivelesSubidos
    public void reiniciarNivelesSubidos() {
        nivelesSubidos = 0;
    }

    public int getNivelesSubidos() {
        return nivelesSubidos;
    }

    public void setNivelesSubidos(int nivelesSubidos) {
        this.nivelesSubidos = nivelesSubidos;
    }

    public int getExperienciaConseguida() {
        return experienciaConseguida;
    }

    public void setExperienciaConseguida(int experienciaConseguida) {
        this.experienciaConseguida = experienciaConseguida;
    }

    public int getExperienciaNecesaria() {
        return experienciaNecesaria;
    }

    public void setExperienciaNecesaria(int experienciaNecesaria) {
        this.experienciaNecesaria = experienciaNecesaria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public TipoPersonaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonaje tipo) {
        this.tipo = tipo;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
