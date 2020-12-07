package com.tamargo;

import com.tamargo.modelo.*;
import com.tamargo.ventanas.Inicio;

import javax.swing.*;
import java.util.ArrayList;


public class Main {

    /**
     * Lanza el juego
     */
    public static void main(String[] args) {

        escribirDatosBase(); // Escribimos todos los datos base
        leerDatosBase(); // Leemos todos los datos base
        if (new LeerDatosBase().leerListaPartidas().getLista().size() <= 0)
            generandoFicheroXMLBase(); //TODO ¡cuidado! ejecutar este método fuera de este if sobreescribirá las partidas

        // Lanzamos la ventana
        JFrame frame = new JFrame("Inicio");
        Inicio inicio = new Inicio(frame);
        frame.setContentPane(inicio.getPanel());
        inicio.setVentanaInicio(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Escribe los siguientes datos base
     */
    public static void escribirDatosBase() {
        System.out.println("\n----- Escribiendo todos los datos base:\n");
        EscribirDatosBase escribir = new EscribirDatosBase();

        escribir.escribirAtributosBase();
        escribir.escribirEnemigosBase();
        escribir.escribirArmasBase();
        escribir.escribirPersonajesBase();
        escribir.escribirEventosBase();
        System.out.println();
    }

    /**
     * Lee todos los datos base, simplemente para dar feedback visual de que va bien
     */
    public static void leerDatosBase() {
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
    }

    /**
     * Este método sobreescribirá el fichero partidas insertando dos partidas base
     */
    public static void generandoFicheroXMLBase() {

        EscribirDatosBase escribir = new EscribirDatosBase();
        LeerDatosBase leer = new LeerDatosBase();

        ArrayList<Enemigo> enemigos = leer.leerEnemigosBase();
        ArrayList<Personaje> personajes = leer.leerPersonajesBase();
        ArrayList<Evento> eventos = leer.leerEventosBase();

        // Genero dos partidas ficticias similares (por tener 2) tirando de algunos datos para generar un XML de prueba
        // Partida 1
        ArrayList<Personaje> personajesGrupo = new ArrayList<>();
        personajesGrupo.add(personajes.get(0));
        personajesGrupo.add(personajes.get(1));
        personajesGrupo.add(personajes.get(4));
        Grupo grupo = new Grupo(1, personajesGrupo, 3);
        grupo.setRondasGanadas(0);

        ArrayList<Enemigo> enemigosDerrotados = new ArrayList<>();
        enemigosDerrotados.add(enemigos.get(0));
        enemigosDerrotados.add(enemigos.get(1));
        enemigosDerrotados.add(enemigos.get(0));

        ArrayList<Evento> eventosPasados = new ArrayList<>();
        eventosPasados.add(eventos.get(0));

        Partida partida1 = new Partida(1, grupo.getRondasGanadas() + 1, false, grupo, enemigosDerrotados, eventosPasados);
        partida1.setFecha("19-10-2020 01:23:04");
        // Partida 2
        ArrayList<Personaje> personajesGrupo2 = new ArrayList<>();
        personajesGrupo2.add(personajes.get(1));
        personajesGrupo2.add(personajes.get(2));
        personajesGrupo2.add(personajes.get(3));
        Grupo grupo2 = new Grupo(1, personajesGrupo2, 3);
        grupo2.setRondasGanadas(0);
        ArrayList<Enemigo> enemigosDerrotados2 = new ArrayList<>();
        enemigosDerrotados2.add(enemigos.get(0));
        enemigosDerrotados2.add(enemigos.get(1));
        enemigosDerrotados2.add(enemigos.get(0));
        enemigosDerrotados2.add(enemigos.get(2));
        enemigosDerrotados2.add(enemigos.get(3));
        ArrayList<Evento> eventosPasados2 = new ArrayList<>();
        eventosPasados2.add(eventos.get(0));
        Partida partida2 = new Partida(2, grupo2.getRondasGanadas() + 1, false, grupo2, enemigosDerrotados2, eventosPasados2);

        // Lista partidas con las 2 partidas
        ListaPartidas listaPartidas = new ListaPartidas();
        listaPartidas.addPartida(partida1);
        listaPartidas.addPartida(partida2);

        // Escribimos el XML
        escribir.escribirListaPartidas(listaPartidas);

        // Leemos el XML
        ListaPartidas listaPartidasLeidas = leer.leerListaPartidas();
        for (Partida p: listaPartidasLeidas.getLista()) {
            System.out.println(p);
        }


    }

}
