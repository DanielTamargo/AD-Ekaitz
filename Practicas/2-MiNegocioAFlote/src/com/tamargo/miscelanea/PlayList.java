package com.tamargo.miscelanea;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PlayList extends Thread {

    String nombre = "[Music] ";
    Clip clip;
    FloatControl controladorVolumen;
    float volumen = -40;
    int indexCancion = 0;
    int indexPlayList = 0;

    JLabel tituloPlaylist;
    JLabel tituloCancion;
    JTextPane cancionesPlaylist;

    ArrayList<String[]> playlists = AdministradorRutasArchivos.playLists();
    ArrayList<String[]> nombresCancionesPlayLists = AdministradorRutasArchivos.nombresCancionesPlayLists();

    //0=error, 1=nextSong, 2=prevSong, 3=finalizar, 4=playlist, 5=nextPlayList, 6=prevPlayList, 7=playlist+cancion
    private int accion = 0;
    private boolean reproduciendo = true;

    public PlayList() {}

    public PlayList(int indexPlaylist, int indexCancion, float volumen, JLabel tituloPlaylist, JLabel tituloCancion, JTextPane cancionesPlaylist) {
        this.indexPlayList = indexPlaylist;
        this.indexCancion = indexCancion;
        this.volumen = volumen;

        this.tituloPlaylist = tituloPlaylist;
        this.tituloCancion = tituloCancion;
        this.cancionesPlaylist = cancionesPlaylist;
    }

    @Override
    public void run() {
        while (reproduciendo) {
            volcarDatosPlayList();
            volumen = ajustarLimitesVolumen(volumen);
            String nombreArchivoCancion = playlists.get(indexPlayList)[indexCancion];
            String nombreCancion = nombresCancionesPlayLists.get(indexPlayList)[indexCancion];

            try {
                File cancion = new File("./ficheros/canciones/" + nombreArchivoCancion);

                if (cancion.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(cancion);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);

                    controladorVolumen = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    controladorVolumen.setValue(volumen);

                    clip.start();
                    System.out.println(nombre + "Reproduciendo: " + nombreCancion);

                    try {
                        sleep(clip.getMicrosecondLength() / 1000);
                        sigueReproduciendo();
                    } catch (InterruptedException ex) {
                        switch (accion) {
                            case 0 -> System.out.println(nombre + "¿Error?");
                            case 1 -> System.out.println(nombre + "Reproduciendo la siguiente canción...");
                            case 2 -> System.out.println(nombre + "Reproduciendo la canción anterior...");
                            case 3 -> {
                                System.out.println(nombre + "Parando la playlist...");
                                reproduciendo = false;
                            }
                            case 4 -> System.out.println(nombre + "Cambiando la playlist...");
                            case 5 -> System.out.println(nombre + "Reproduciendo la siguiente playlist...");
                            case 6 -> System.out.println(nombre + "Reproduciendo la playlist anterior...");
                            case 7 -> System.out.println(nombre + "Cambiando la playlist y eligiendo '" + nombresCancionesPlayLists.get(indexPlayList)[indexCancion] + "'");
                            default -> System.out.println(nombre + "¿¿¿Error???");
                        }
                        accion = 0;
                    }

                } else {
                    System.out.println(nombre + "No existe la canción: " + nombreArchivoCancion);
                    reproduciendo = false;
                }

            } catch (Exception ex) {
                System.out.println(nombre + "Error al reproducir la canción: " + nombreArchivoCancion);
                reproduciendo = false;
            }
        }
    }

    public void setDatos(JLabel tituloPlaylist, JLabel tituloCancion, JTextPane cancionesPlaylist) {
        this.tituloPlaylist = tituloPlaylist;
        this.tituloCancion = tituloCancion;
        this.cancionesPlaylist = cancionesPlaylist;
        volcarDatosPlayList();
    }

    private void volcarDatosPlayList() {
        // Nombres
        String[] listaNombres;
        if (indexPlayList == 0)
            listaNombres = AdministradorRutasArchivos.nombresPlaylist1;
        else
            listaNombres = AdministradorRutasArchivos.nombresPlaylist2;
        String[] listaTitulosPlaylist = AdministradorRutasArchivos.titulosPlaylists;

        tituloPlaylist.setText(listaTitulosPlaylist[indexPlayList]);
        tituloCancion.setText(listaNombres[indexCancion]);

        // Text pane
        cancionesPlaylist.setText("");
        StyledDocument doc = cancionesPlaylist.getStyledDocument();

        Style style = cancionesPlaylist.addStyle("PlaylistStyle", null);
        StyleConstants.setForeground(style, Color.DARK_GRAY);

        try {
            boolean resetearColor = false;
            for (String str : listaNombres) {
                if (str.equalsIgnoreCase(listaNombres[indexCancion])) {
                    StyleConstants.setForeground(style, Color.BLUE);
                    resetearColor = true;
                }

                doc.insertString(doc.getLength(), str, style);
                doc.insertString(doc.getLength(), "\n", style);

                if (resetearColor) {
                    StyleConstants.setForeground(style, Color.DARK_GRAY);
                    resetearColor = false;
                }
            }
        } catch (BadLocationException | IndexOutOfBoundsException ignored) {}
    }

    public void sigueReproduciendo() {
        indexCancion++;
        System.out.println(nombre + "Reproduciendo la siguiente canción...");
        if (indexCancion >= playlists.get(indexPlayList).length)
            indexCancion = 0;
    }

    public void cambiarPlayListEligiendoCancion(int indexPlaylist, int indexCancion) {
        this.indexPlayList = indexPlaylist;
        this.indexCancion = indexCancion;
        clip.stop();
        accion = 7;
        interrupt();
    }

    public void cambiarPlayList(int indexPlaylist) {
        this.indexPlayList = indexPlaylist;
        indexCancion = 0;
        clip.stop();
        accion = 4;
        interrupt();
    }

    public void nextPlayList() {
        try {
            clip.stop();
        } catch (Exception ignored) {}
        indexCancion = 0;
        indexPlayList++;
        accion = 5;
        if (indexPlayList >= playlists.size())
            indexPlayList = 0;
        interrupt();
    }

    public void previousPlayList() {
        try {
            clip.stop();
        } catch (Exception ignored) {}
        indexCancion = 0;
        indexPlayList--;
        accion = 6;
        if (indexPlayList < 0)
            indexPlayList = playlists.size() - 1;
        interrupt();
    }

    public void nextSong() {
        try {
            clip.stop();
        } catch (Exception ignored) {}
        indexCancion++;
        accion = 1;
        if (indexCancion >= playlists.get(indexPlayList).length)
            indexCancion = 0;
        interrupt();
    }

    public void previousSong() {
        try {
            clip.stop();
        } catch (Exception ignored) {}
        indexCancion--;
        accion = 2;
        if (indexCancion < 0)
            indexCancion = playlists.get(indexPlayList).length - 1;
        interrupt();
    }

    public void finalizar() {
        try {
            clip.stop();
        } catch (Exception ignored) {}
        reproduciendo = false;
        accion = 3;
        interrupt();
    }

    public void resumeSong() {
        try {
            clip.start();
            System.out.println("Reanudando la canción...");
        } catch (Exception ignored) { }
    }

    public void stopSong() {
        try {
            clip.stop();
            System.out.println("Parando la canción...");
        } catch (Exception ignored) { }
    }

    public float ajustarLimitesVolumen(float volumen) {
        if (volumen > 0)
            volumen = 0;
        else if (volumen < -80)
            volumen = -80;
        return volumen;
    }

    public void setVolumen(float volumen) {
        this.volumen = ajustarLimitesVolumen(volumen);
        try {
            controladorVolumen.setValue(this.volumen);
        } catch (Exception ignored) { }
    }

}
