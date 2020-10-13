package com.tamargo.ventanas;

import javax.swing.*;
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

    public Inicio() {

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
