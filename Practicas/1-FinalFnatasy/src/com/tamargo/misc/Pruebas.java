package com.tamargo.misc;

import javax.swing.*;

public class Pruebas {

    public static void main(String[] args) {

        String[] playlistLista = AdministradorRutasArchivos.playlistRPG;

        PlayPlaylist playlist = new PlayPlaylist(playlistLista, 1, -40);
        playlist.start();

        JOptionPane.showMessageDialog(null, "Vas a subir el volumen un poco", "Escuchando", JOptionPane.INFORMATION_MESSAGE);
        playlist.setVolume(-30);

        JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);

        playlist.stop(); //<- deprecado pero para este caso en concreto funciona

    }

}
