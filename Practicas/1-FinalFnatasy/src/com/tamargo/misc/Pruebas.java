package com.tamargo.misc;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Personaje;
import com.tamargo.playlist.PlayPlaylist;
import com.tamargo.playlist.PlayPlaylistManager;

import javax.swing.*;
import java.util.ArrayList;

public class Pruebas {

    public static void main(String[] args) {

        String[] canciones = AdministradorRutasArchivos.canciones;

        PlaySound pm = new PlaySound();
        pm.playSound(canciones[0], true, -40);
        JOptionPane.showMessageDialog(null, "Parar", "Parar", JOptionPane.INFORMATION_MESSAGE);
        pm.stopSong();
        JOptionPane.showMessageDialog(null, "Reanudar", "Reanudar", JOptionPane.INFORMATION_MESSAGE);
        pm.resumeSong();
        JOptionPane.showMessageDialog(null, "Terminar", "Terminar", JOptionPane.INFORMATION_MESSAGE);


/*
        String[] playlistLista = AdministradorRutasArchivos.playlistRPG;

        PlayPlaylistManager playlist = new PlayPlaylistManager(playlistLista, 1, -40);
        playlist.start();

        JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);

        playlist.stop(); //<- deprecado pero para este caso en concreto funciona*/

    }

}
