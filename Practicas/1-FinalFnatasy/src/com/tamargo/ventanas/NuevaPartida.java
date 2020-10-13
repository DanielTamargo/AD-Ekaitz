package com.tamargo.ventanas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuevaPartida {
    private JFrame ventanaNuevaPartida;

    private JPanel panel;
    private JLabel l_creaTuGrupo;
    private JLabel l_eligePersonajes;
    private JButton b_pj1;
    private JButton b_pj2;
    private JButton b_pj3;
    private JButton b_pj4;
    private JButton b_pj5;
    private JLabel l_fotoPJ;
    private JLabel l_nombre;
    private JLabel l_tipo;
    private JLabel l_arna;

    public NuevaPartida() {

        // Iconos botones
        b_pj1.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_pj2.setIcon(new ImageIcon("assets/icono-2-mago.png"));
        b_pj3.setIcon(new ImageIcon("assets/icono-3-arquero.png"));
        b_pj4.setIcon(new ImageIcon("assets/icono-4-guardian.png"));
        b_pj5.setIcon(new ImageIcon("assets/icono-5-asesino.png"));

        // Icono preview PJ
        l_fotoPJ.setIcon(new ImageIcon("assets/pjs-1-guerrero.png"));

        b_pj1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(1);
            }
        });
        b_pj2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(2);
            }
        });
        b_pj3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(3);
            }
        });
        b_pj4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(4);
            }
        });
        b_pj5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(5);
            }
        });
    }

    public void cambiarDatos(int index) {
        String foto = "assets/pjs-1-guerrero.png";
        if (index == 1)
            foto = "assets/pjs-1-guerrero.png";
        else if (index == 2)
            foto = "assets/pjs-2-mago.png";
        else if (index == 3)
            foto = "assets/pjs-3-arquero.png";
        else if (index == 4)
            foto = "assets/pjs-4-guardian.png";
        else if (index == 5)
            foto = "assets/pjs-5-asesino.png";

        l_fotoPJ.setIcon(new ImageIcon(foto));
    }

    public void setVentanaNuevaPartida(JFrame ventanaNuevaPartida) {
        this.ventanaNuevaPartida = ventanaNuevaPartida;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("NuevaPartida");
        frame.setContentPane(new NuevaPartida().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
