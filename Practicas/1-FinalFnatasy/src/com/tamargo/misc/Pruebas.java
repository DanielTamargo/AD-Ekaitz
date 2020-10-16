package com.tamargo.misc;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.util.ArrayList;

public class Pruebas {

    public static void main(String[] args) {

        ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();

        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());
        System.out.println(personajes.get(2).calcularDanyoFis());


        /*
        String[] playlistLista = AdministradorRutasArchivos.playlistRPG;

        PlayPlaylist playlist = new PlayPlaylist(playlistLista, 1, -40);
        playlist.start();

        JOptionPane.showMessageDialog(null, "Vas a subir el volumen un poco", "Escuchando", JOptionPane.INFORMATION_MESSAGE);
        playlist.setVolume(-30);

        JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);

        playlist.stop(); //<- deprecado pero para este caso en concreto funciona
           */
    }

}
