package com.tamargo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        int indexPlaylist = 0;
        int indexCancion = 0;
        float volumen = -40;

        PlayList playList = new PlayList(indexPlaylist, indexCancion, volumen);
        playList.setVolumenDelControladorVolumen(volumen);
        playList.start();

        // Parar la canción
        teclado.nextLine();
        playList.stopSong();

        // Reanudar la canción
        teclado.nextLine();
        playList.resumeSong();
        
        // Siguiente canción
        teclado.nextLine();
        playList.nextSong();

        // Canción anterior
        teclado.nextLine();
        playList.previousSong();

        // Cambiar de playlist
        teclado.nextLine();
        indexPlaylist = 1;
        playList.cambiarPlayList(indexPlaylist);

        // Siguiente playlist
        teclado.nextLine();
        playList.nextPlayList();

        // Playlist anterior
        teclado.nextLine();
        playList.previousPlayList();

        // Siguiente canción
        teclado.nextLine();
        playList.nextSong();

        // Canción anterior
        teclado.nextLine();
        playList.previousSong();

        // Reproducir playlist 2, en concreto: haikyuu - the quick freak
        teclado.nextLine();
        playList.cambiarPlayListEligiendoCancion(0, 1);

        // Terminar la playlist
        teclado.nextLine();
        playList.finalizar();


    }

}
