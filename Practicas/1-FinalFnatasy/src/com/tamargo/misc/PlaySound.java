package com.tamargo.misc;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class PlaySound {

    Clip clip;
    FloatControl volume;

    public void stopSong() {
        clip.stop();
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
        } catch (Exception e) {
            System.out.println("No puedes configurar el volumen sin antes definir el sonido a reproducir.");
            System.out.println(e);
        }
    }

    // Importante: volumen es un rango de -80 hasta 0
    public void playSound(String cancion, Boolean loop, float volumen) {

        volumen = ajustarLimitesVolumen(volumen);

        try {
            File rutaCancion = new File("assets/" + cancion);

            if (rutaCancion.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(rutaCancion);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                if (loop)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(volumen);

                clip.start();
            } else {
                System.out.println("No existe la canción: " + cancion);
            }

        } catch (Exception ex) {
            System.out.println("Error al reproducir la canción: " + cancion);
        }

    }

}
