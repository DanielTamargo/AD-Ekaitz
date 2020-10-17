package com.tamargo.ventanas;

import javax.swing.*;

public class VentanaRondaDatos {
    private JPanel panel;
    private JTextPane textPane1;

    public VentanaRondaDatos() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaRondaDatos");
        frame.setContentPane(new VentanaRondaDatos().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
