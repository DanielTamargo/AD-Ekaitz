package com.tamargo.ventanas;

import com.tamargo.misc.PlaySound;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio {
    private JFrame ventanaInicio;

    private JPanel panel;
    private JLabel logo;
    private JButton b_nuevaPartida;
    private JButton b_continuar;
    private JButton b_historial;
    private JButton b_salir;
    private JButton b_previousSong;
    private JLabel l_cancion;
    private JButton b_nextSong;
    private JSpinner spinnerVolumen;

    private int indexCancion = 0;
    private String[] canciones = {"song-nemesis.wav", "song-buttercup.wav"};
    private String[] nombreCanciones = {"Nemesis (Youtube Song)", "Buttercup Moonflower (Mix)"};

    private PlaySound pm;

    public Inicio() {

        spinnerVolumen.setValue(50);

        pm = new PlaySound();
        pm.playSound(canciones[indexCancion], true, -50);

        logo.setIcon(new ImageIcon("assets/logo_600x338.png"));
        //b_nuevaPartida.setIcon(new ImageIcon("assets/boton_nuevaPartida.png"));
        b_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaInicio.dispose();
            }
        });
        b_nuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nueva Partida");
                NuevaPartida np = new NuevaPartida();
                frame.setContentPane(np.getPanel());
                np.setVentanaNuevaPartida(frame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Debería centrarlo pero en mi ventana me lo genera abajo a la derecha hm

                frame.setVisible(true);
                ventanaInicio.dispose();
            }
        });
        b_previousSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion--;
                if (indexCancion < 0)
                    indexCancion = canciones.length - 1;
                cambiarCancion();
            }
        });
        b_nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion++;
                if (indexCancion >= canciones.length)
                    indexCancion = 0;
                cambiarCancion();
            }
        });
        spinnerVolumen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerVolumen.getValue() > 100)
                    spinnerVolumen.setValue(100);
                else if ((int) spinnerVolumen.getValue() < 0)
                    spinnerVolumen.setValue(0);

                float volumen = (float) (int) spinnerVolumen.getValue();
                volumen = (float) ((volumen - 100) * 0.80);
                pm.setVolume(volumen);
            }
        });
    }

    public void cambiarCancion() {

        String cancion = nombreCanciones[indexCancion];
        l_cancion.setText(cancion);

        float volumen = (float) (int) spinnerVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);

        pm.stopSong();
        pm = new PlaySound();
        pm.playSound(canciones[indexCancion], true, volumen);


    }

    public String[] getCanciones() {
        return canciones;
    }

    public void setCanciones(String[] canciones) {
        this.canciones = canciones;
    }

    public String[] getNombreCanciones() {
        return nombreCanciones;
    }

    public void setNombreCanciones(String[] nombreCanciones) {
        this.nombreCanciones = nombreCanciones;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new Inicio().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

}
