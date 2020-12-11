package com.tamargo;

import com.tamargo.datosbase.GenerarDatosBase;
import com.tamargo.exist.Coleccion;
import com.tamargo.ventanas.VentanaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Coleccion.comprobarColeccion();
        JFrame frame = new JFrame("Empresa en Auge");
        //VentanaPrincipal vc = new VentanaPrincipal(frame);
        frame.setContentPane(new VentanaPrincipal(frame, null).getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
