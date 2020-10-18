package com.tamargo.ventanas;

import com.tamargo.modelo.Atributos;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaRondaDatos {
    private JFrame ventanaRonda;
    private JFrame ventanaRondaDatos;

    private JPanel panel;
    private JButton b_icono;
    private JLabel l_vitalidad;
    private JLabel l_fuerza;
    private JLabel l_poderMag;
    private JLabel l_destreza;
    private JLabel l_agilidad;
    private JLabel l_defFis;
    private JLabel l_defMag;
    private JLabel l_nombre;
    private JLabel l_nivel;

    private String nombre;
    private String icono;
    private Atributos atributos;

    public VentanaRondaDatos(String nombre, String icono, Atributos atributos, JFrame ventanaRondaDatos) {
        this.nombre = nombre;
        this.icono = icono;
        this.atributos = atributos;
        this.ventanaRondaDatos = ventanaRondaDatos;

        cargarDatos();

        this.ventanaRondaDatos.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                ventanaRonda.setEnabled(true);
                e.getWindow().dispose();
            }
        });
    }

    public void cargarDatos() {
        l_nombre.setText(nombre);
        b_icono.setIcon(new ImageIcon("assets/" + icono));

        if (atributos != null) {
            // Datos atributos
            l_nivel.setText("Nivel: " + atributos.getNivel());
            l_vitalidad.setText(String.format("%-15s %3d", "Vitalidad: ",atributos.getVitalidad()));
            l_fuerza.setText(String.format("%-15s %3d", "Fuerza: ",atributos.getFuerza()));
            l_poderMag.setText(String.format("%-15s %3d", "Poder Mágico: ",atributos.getPoderMagico()));
            l_destreza.setText(String.format("%-15s %3d", "Destreza: ",atributos.getDestreza()));
            l_agilidad.setText(String.format("%-15s %3d", "Agilidad: ",atributos.getAgilidad()));
            l_defFis.setText(String.format("%-15s %3d", "Def. Física: ",atributos.getDefensaFis()));
            l_defMag.setText(String.format("%-15s %3d", "Def. Mágica: ",atributos.getDefensaMag()));
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setVentanaRonda(JFrame ventanaRonda) {
        this.ventanaRonda = ventanaRonda;
    }

    public void setVentanaRondaDatos(JFrame ventanaRondaDatos) {
        this.ventanaRondaDatos = ventanaRondaDatos;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Datos Lady Sophie");
        frame.setContentPane(new VentanaRondaDatos("Lady Sophie", "icono-2-mago.png", null, frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
