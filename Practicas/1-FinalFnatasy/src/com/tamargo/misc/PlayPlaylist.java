package com.tamargo.misc;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class PlayPlaylist extends Thread {

    Clip clip;
    FloatControl volume;
    private String[] playlist;
    private int index = 0;
    private float volumen;

    @Override
    public void run() {
        super.run();
        while (true) {
            if (playlist.length > 0)
                playSound(playlist[index], volumen);
        }
    }

    public PlayPlaylist(String[] playlist, int index, float volumen) {
        this.playlist = playlist;
        if (index > playlist.length)
            index = 0;
        this.index = index;
        this.volumen = volumen;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPlaylist(String[] playlist) {
        //  Lo suyo sería matar este hilo y generar otra playlist
        stopSong();
        this.playlist = playlist;
    }

    public float ajustarLimitesVolumen(float volumen) {
        if (volumen > 0)
            volumen = 0;
        else if (volumen < -80)
            volumen = -80;

        return volumen;
    }

    public void setVolume(float volumen) {

        volumen = ajustarLimitesVolumen(volumen);

        try {
            //volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // <- No hace falta
            volume.setValue(volumen);
            //System.out.println(volumen);
        } catch (Exception e) {
            //System.out.println("No puedes configurar el volumen sin antes definir el sonido a reproducir.");
            //System.out.println(e);
        }
    }

    public void stopSong() {
        try {
            clip.stop();
        } catch (Exception e) {
            //System.out.println(e);
            //System.out.println("Error al parar la canción.");
        }
    }

    public void playSound(String cancion, float volumen) {

        volumen = ajustarLimitesVolumen(volumen);

        try {
            File rutaCancion = new File("./assets/" + cancion);

            if (rutaCancion.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(rutaCancion);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(volumen);

                clip.start();

                sleep(clip.getMicrosecondLength() / 1000);

                index++;
                if (index >= playlist.length)
                    index = 0;

            } else {
                System.out.println("No existe la canción: " + cancion);
            }

        } catch (Exception ex) {
            //System.out.println(ex);
            System.out.println("Error al reproducir la canción: " + cancion);
        }

    }



}
