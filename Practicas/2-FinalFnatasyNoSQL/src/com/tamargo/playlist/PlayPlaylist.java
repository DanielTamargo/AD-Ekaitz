package com.tamargo.playlist;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class PlayPlaylist {

    Clip clip;
    FloatControl volume;
    private String[] playlist;
    private int index = 0;
    private float volumen;

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
            volume.setValue(volumen);
        } catch (Exception e) { }
    }

    public void stopSong() {
        try {
            clip.stop();
        } catch (Exception e) { }
    }

    public synchronized void playSound(String cancion, float volumen) {

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

                Thread.sleep(clip.getMicrosecondLength() / 1000);

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
