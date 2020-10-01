package com.tamargo;

import com.tamargo.ventanas.ImagePanel;
import com.tamargo.ventanas.VentanaPrincipal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("VentanaPrincipal");
        VentanaPrincipal vp = new VentanaPrincipal();
        frame.setContentPane(vp.getPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //frame.setSize(100, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
