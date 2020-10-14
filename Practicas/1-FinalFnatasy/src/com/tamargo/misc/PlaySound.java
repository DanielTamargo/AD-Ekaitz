package com.tamargo.misc;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class PlaySound {

    Clip clip;

    public void stopSong() {
        clip.stop();
    }

    public void playSound(String cancion, Boolean loop) {

        try {
            File rutaCancion = new File("assets/" + cancion);

            if (rutaCancion.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(rutaCancion);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                if (loop)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-30);

                clip.start();

            } else {
                System.out.println("No existe la canción: " + cancion);
            }

        } catch (Exception ex) {
            System.out.println("Error al reproducir la canción: " + cancion);
            System.out.println(ex);
        }

    }

}
