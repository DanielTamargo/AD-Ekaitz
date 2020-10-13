package com.tamargo;

import com.tamargo.modelo.*;
import com.tamargo.ventanas.Inicio;

import javax.swing.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        // Escribimos todos los datos base:
        System.out.println("\n----- Escribiendo todos los datos base:\n");
        EscribirDatosBase escribir = new EscribirDatosBase();

        escribir.escribirAtributosBase();
        escribir.escribirEnemigosBase();
        escribir.escribirArmasBase();
        escribir.escribirPersonajesBase();
        escribir.escribirEventosBase();
        System.out.println();

        // Los leemos para ver que funciona bien
        System.out.println("----- Leyendo todos los datos base:\n");
        LeerDatosBase leer = new LeerDatosBase();

        ArrayList<Enemigo> enemigos = leer.leerEnemigosBase();
        System.out.println("-- Enemigos (DataInputStream) + Atributos (ObjectInputStream):");
        for (Enemigo e: enemigos) {
            System.out.println(e); // <- enemigos
            System.out.println("\t" + e.getAtributos()); // <- atributos de cada enemigo
        }
        System.out.println();

        ArrayList<Arma> armas = leer.leerArmasBase();
        System.out.println("-- Armas (ObjectInputStream):");
        for (Arma a: armas) {
            System.out.println(a); // <- todas las armas
        }
        System.out.println();

        System.out.println("-- Personajes (BufferedReader) + Atributos (ObjectInputStream):");
        ArrayList<Personaje> personajes = leer.leerPersonajesBase();
        for (Personaje p: personajes) {
            System.out.println(p);
            System.out.println("\t" + p.getAtributos());
        }
        System.out.println();

        System.out.println("-- Eventos (DataInputStream):");
        ArrayList<Evento> eventos = leer.leerEventosBase();
        for (Evento e: eventos) {
            System.out.println(e);
        }
        System.out.println();

        // Genero dos partidas ficticias similares (por tener 2) tirando de algunos datos para generar un XML de prueba
        ArrayList<Personaje> personajesGrupo = new ArrayList<>();
        personajesGrupo.add(personajes.get(0));
        personajesGrupo.add(personajes.get(1));
        personajesGrupo.add(personajes.get(4));
        Grupo grupo = new Grupo(1, personajesGrupo, 3);

        ArrayList<Enemigo> enemigosDerrotados = new ArrayList<>();
        enemigosDerrotados.add(enemigos.get(0));
        enemigosDerrotados.add(enemigos.get(1));
        enemigosDerrotados.add(enemigos.get(0));

        ArrayList<Evento> eventosPasados = new ArrayList<>();
        eventosPasados.add(eventos.get(0));

        Partida partida1 = new Partida(1, 4, grupo, enemigosDerrotados, eventosPasados);
        enemigosDerrotados.add(enemigos.get(2));
        enemigosDerrotados.add(enemigos.get(3));
        Partida partida2 = new Partida(2, 5, grupo, enemigosDerrotados, eventosPasados);

        ListaPartidas listaPartidas = new ListaPartidas();
        listaPartidas.addPartida(partida1);
        listaPartidas.addPartida(partida2);

        // Lanzamos la ventana
        JFrame frame = new JFrame("Inicio");
        Inicio inicio = new Inicio();
        frame.setContentPane(inicio.getPanel());
        inicio.setVentanaInicio(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Debería centrarlo pero en mi ventana me lo genera abajo a la derecha hmm

        int dx = 1920 / 4;
        int dy = 1080 / 4;
        frame.setLocation(dx, dy);

        frame.pack();
        frame.setVisible(true);
    }


}
