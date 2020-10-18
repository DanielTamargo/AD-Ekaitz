package com.tamargo.misc;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Personaje;
import com.tamargo.playlist.PlayPlaylist;
import com.tamargo.playlist.PlayPlaylistManager;

import javax.swing.*;
import java.util.ArrayList;

public class Pruebas {

    public static void main(String[] args) {


        System.out.print(String.format("%-46s", "Lady Sophie"));
        System.out.print(String.format("%-46s", "Lord Kaplan"));
        System.out.print(String.format("%-46s", "Legomas"));
        System.out.println();
        String dato = "¡Legomas ha subido de nivel!";
        if (dato.contains("Lady Sophie"))
            System.out.print(String.format("%-46s", dato));
        else
            System.out.print(String.format("%-46s", ""));
        if (dato.contains("Lord Kaplan"))
            System.out.print(String.format("%-46s", dato));
        else
            System.out.print(String.format("%-46s", ""));
        if (dato.contains("Legomas"))
            System.out.print(String.format("%-46s", dato));
        else
            System.out.print(String.format("%-46s", ""));
        System.out.println();

        System.out.println();
        String danyo = "Daño a Dios del Cristal Olvidado: 99999";
        System.out.println(danyo.length());

        /*
        String[] canciones = AdministradorRutasArchivos.canciones;

        PlaySound pm = new PlaySound();
        pm.playSound(canciones[0], true, -40);
        JOptionPane.showMessageDialog(null, "Parar", "Parar", JOptionPane.INFORMATION_MESSAGE);
        pm.stopSong();
        JOptionPane.showMessageDialog(null, "Reanudar", "Reanudar", JOptionPane.INFORMATION_MESSAGE);
        pm.resumeSong();
        JOptionPane.showMessageDialog(null, "Terminar", "Terminar", JOptionPane.INFORMATION_MESSAGE);
*/

/*
        String[] playlistLista = AdministradorRutasArchivos.playlistRPG;

        PlayPlaylistManager playlist = new PlayPlaylistManager(playlistLista, 1, -40);
        playlist.start();

        JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);

        playlist.stop(); //<- deprecado pero para este caso en concreto funciona*/

    }

}
