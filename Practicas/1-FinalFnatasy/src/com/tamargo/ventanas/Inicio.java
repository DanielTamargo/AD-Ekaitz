package com.tamargo.ventanas;

import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
    private JSlider sliderVolumen;

    private int indexCancion = 0;
    private final String[] canciones = AdministradorRutasArchivos.canciones;
    private final String[] nombreCanciones = AdministradorRutasArchivos.nombreCanciones;
    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;

    private float volumen = -40;
    private PlaySound pm;

    public Inicio() {

        sliderVolumen.setValue(50);
        volumen = (float) (int) sliderVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);

        cambiarCancion();

        logo.setIcon(new ImageIcon("assets/logo_600x338.png"));
        //b_nuevaPartida.setIcon(new ImageIcon("assets/boton_nuevaPartida.png"));
        b_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaInicio.dispose();
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen + 20);
            }
        });
        b_nuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nueva Partida");
                NuevaPartida np = new NuevaPartida();
                frame.setContentPane(np.getPanel());
                np.setVentanaNuevaPartida(frame);
                np.setVentanaInicio(ventanaInicio);
                np.setVolumen(volumen);
                np.setIndexCancion(indexCancion);
                np.setPm(pm);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Debería centrarlo pero en mi ventana me lo genera abajo a la derecha hm

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);

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
        sliderVolumen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) sliderVolumen.getValue() > 100)
                    sliderVolumen.setValue(100);
                else if ((int) sliderVolumen.getValue() < 0)
                    sliderVolumen.setValue(0);

                volumen = (float) (int) sliderVolumen.getValue();
                volumen = (float) ((volumen - 100) * 0.80);
                //System.out.println(volumen);
                pm.setVolume(volumen);
            }
        });
        b_historial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);
            }
        });
    }

    public void cambiarCancion() {

        String cancion = nombreCanciones[indexCancion];
        l_cancion.setText(cancion);

        float volumen = (float) (int) sliderVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);

        if (pm != null)
            pm.stopSong();
        pm = new PlaySound();
        pm.playSound(canciones[indexCancion], true, volumen);

    }

    public String[] getCanciones() {
        return canciones;
    }

    public String[] getNombreCanciones() {
        return nombreCanciones;
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
