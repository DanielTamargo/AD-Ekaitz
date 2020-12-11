package com.tamargo.miscelanea;

import java.util.ArrayList;

public class AdministradorRutasArchivos {

    public static String[] titulosPlaylists = { "Mentalidad Calmada", "Mentalidad Tiburón" };

    public static String[] cancionesPlaylist1 = { "1-1-ayasake.wav", "1-2-emptycrown.wav", "1-3-buttercup.wav" };
    public static String[] cancionesPlaylist2 = { "2-1-challenges.wav", "2-2-desecreation.wav", "2-3-postmortem.wav" };

    public static String[] nombresPlaylist1 = { "Ayasake no Starmine", "Empty Crown", "Buttercup (Mix)" };
    public static String[] nombresPlaylist2 = { "Challenges", "Desecreation", "Postmortem"};

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
