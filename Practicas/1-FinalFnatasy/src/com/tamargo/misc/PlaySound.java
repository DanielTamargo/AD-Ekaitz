package com.tamargo.misc;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class PlaySound {

    Clip clip;
    FloatControl volume;
    int indexCancion = 0;

    public PlaySound() {
    }

    public PlaySound(int indexCancion) {
        this.indexCancion = indexCancion;
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

    public void resumeSong() {
        try {
            clip.start();
        } catch (Exception e) {}
    }

    public void stopSong() {
        try {
            clip.stop();
        } catch (Exception e) {
            //System.out.println(e);
            //System.out.println("Error al parar la canción.");
        }
    }

    public void playSound(String cancion, Boolean loop, float volumen) {

        volumen = ajustarLimitesVolumen(volumen);

        try {
            File rutaCancion = new File("./assets/" + cancion);

            if (rutaCancion.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(rutaCancion);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                if (loop)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(volumen);

                clip.start();

                // TODO playlist!!! ooooowoooooo clip.getMicrosecondLength();


            } else {
                System.out.println("No existe la canción: " + cancion);
            }

        } catch (Exception ex) {
            //System.out.println(ex);
            System.out.println("Error al reproducir la canción: " + cancion);
        }

    }

    public int getIndexCancion() {
        return indexCancion;
    }

    public void setIndexCancion(int indexCancion) {
        this.indexCancion = indexCancion;
    }
}
