package com.tamargo.ventanas;

import javax.swing.*;

public class VentanaPrincipal {

    private JPanel panel;
    private JPanel foto;
    private JLabel labelPic;
    private JButton button1;
    private JTabbedPane tabbedPane1;

    private ImageIcon imagenTest = new ImageIcon("assets/Square_200x200.png");

    public JPanel getPanel() {
        return panel;
    }

    public JPanel getFoto() {
        return foto;
    }

    public void setFoto(JPanel foto) {
        this.foto = foto;
    }

    public VentanaPrincipal() {
        labelPic.setIcon(imagenTest);
        labelPic.setText("");
        button1.setIcon(imagenTest);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VentanaPrincipal().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
