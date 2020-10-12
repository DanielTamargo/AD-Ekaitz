package com.tamargo;

import com.tamargo.ventanas.Inicio;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inicio");
        Inicio inicio = new Inicio();
        frame.setContentPane(inicio.getPanel());
        inicio.setVentanaInicio(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
