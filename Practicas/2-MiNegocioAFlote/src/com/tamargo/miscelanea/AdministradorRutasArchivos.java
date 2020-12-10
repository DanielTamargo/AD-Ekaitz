package com.tamargo.miscelanea;

import java.util.ArrayList;

public class AdministradorRutasArchivos {

    public static String[] titulosPlaylists = { "Mentalidad Calmada", "Mentalidad Tiburón", "Mentalidad Anime" };

    public static String[] cancionesPlaylist1 = { "p1-1-dbz.wav", "p1-2-dbgt.wav" };
    public static String[] cancionesPlaylist2 = { "p2-1-unravel.wav", "p2-2-haikyuu-3.wav" };

    public static String[] nombresPlaylist1 = { "Opening - DBZ", "Opening - DBGT" };
    public static String[] nombresPlaylist2 = { "Unravel - Tokyo Ghoul", "Quick Freak - Haikyuu"};

    public static ArrayList<String[]> playLists() {
        ArrayList<String[]> playLists = new ArrayList<>();
        playLists.add(cancionesPlaylist1);
        playLists.add(cancionesPlaylist2);
        return playLists;
    }

    public static ArrayList<String[]> nombresCancionesPlayLists() {
        ArrayList<String[]> nombresCancionesPlayLists = new ArrayList<>();
        nombresCancionesPlayLists.add(nombresPlaylist1);
        nombresCancionesPlayLists.add(nombresPlaylist2);
        return nombresCancionesPlayLists;
    }

}
