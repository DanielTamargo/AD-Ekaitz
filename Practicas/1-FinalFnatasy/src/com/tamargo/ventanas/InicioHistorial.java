package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.Partida;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class InicioHistorial {
    private JFrame ventanaInicioHistorial;
    private JFrame ventanaInicio;

    private JPanel panel;
    private JPanel panelNormal;
    private JPanel panelXML;
    private JScrollPane panelPersonalizable;
    private JTextPane textPaneXML;

    private JButton b_vistaNormal;
    private JButton b_vistaXML;
    private JButton b_volver1;
    private JButton b_volver2;
    private JButton b_next;
    private JButton b_previous;


    private float volumen = -40;
    private String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;
    private ArrayList<Partida> partidas = new ArrayList<>();
    private int index = 0;
    private int numPartidasPorPag = 5;

    public InicioHistorial(JFrame ventanaInicioHistorial) {
        this.ventanaInicioHistorial = ventanaInicioHistorial;
        panelXML.setVisible(false);
        ventanaInicioHistorial.pack();
        cargarPartidas();
        cargarDatosVistaNormal();
        cargarDatosVistaXML();

        if (partidas.size() <= numPartidasPorPag)
            b_next.setEnabled(false);

        b_vistaXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                panelXML.setVisible(true);
                panelNormal.setVisible(false);
                ventanaInicioHistorial.pack();
            }
        });
        b_vistaNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                panelXML.setVisible(false);
                panelNormal.setVisible(true);
                ventanaInicioHistorial.pack();
            }
        });
        b_volver1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
        b_volver2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
        b_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);

                index++;
                if ((index + 1) * numPartidasPorPag > partidas.size())
                    b_next.setEnabled(false);
                b_previous.setEnabled(true);

                cargarDatosVistaNormal();
            }
        });
        b_previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);

                index--;
                if (index == 0)
                    b_previous.setEnabled(false);
                b_next.setEnabled(true);

                cargarDatosVistaNormal();
            }
        });
    }

    public void cargarPartidas() {
        partidas = new LeerDatosBase().leerListaPartidas().getLista();
        Map<LocalDateTime, Partida> listaOrdenada = new TreeMap<LocalDateTime, Partida>();
        for (Partida p: partidas) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(p.getFecha(), formatter);
            listaOrdenada.put(dateTime, p);
        }
        partidas.clear();
        partidas.addAll(listaOrdenada.values());
        Collections.reverse(partidas);
    }

    public void cargarDatosVistaXML() {
        textPaneXML.setText(new LeerDatosBase().leerFicheroXMLPartidas().toString());
    }

    public void cargarDatosVistaNormal() {

        Dimension dim = new Dimension();

        panelPersonalizable.setLayout(null);
        panelPersonalizable.removeAll();
        panelPersonalizable.setWheelScrollingEnabled(true);
        panelPersonalizable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //panelPersonalizable = new JScrollPane (panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        dim.setSize(500, 600);
        panelPersonalizable.setPreferredSize(dim);
        panelPersonalizable.setMinimumSize(dim);
        panelPersonalizable.setMaximumSize(dim);
        panelPersonalizable.setWheelScrollingEnabled(true);

        int panelX = 10;
        int panelY = 10;

        Font fuenteLabels = new Font("Unispace", Font.PLAIN, 9);

        for (int i = (index * 5); i < (numPartidasPorPag * (index + 1)); i++) {
            Partida p;
            try {
                p = partidas.get(i);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                p = null;
            }

            if (p != null) {
                Personaje pj1 = p.getGrupo().getPersonajes().get(0);
                Personaje pj2 = p.getGrupo().getPersonajes().get(1);
                Personaje pj3 = p.getGrupo().getPersonajes().get(2);

                JPanel panel = new JPanel();
                panel.setLayout(null);
                dim.setSize(475, 110);
                panel.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.setPreferredSize(dim);
                panel.setMinimumSize(dim);
                panel.setMaximumSize(dim);
                panelPersonalizable.add(panel);
                panel.setBounds(panelX, panelY, dim.width, dim.height);

                JButton botonPJ1 = new JButton("");
                botonPJ1.setIcon(new ImageIcon("assets/" + pj1.getImagen()));
                botonPJ1.setFocusPainted(false);
                botonPJ1.setBorderPainted(false);
                dim.setSize(100, 100);
                botonPJ1.setPreferredSize(dim);
                botonPJ1.setMaximumSize(dim);
                botonPJ1.setMaximumSize(dim);
                panel.add(botonPJ1);
                botonPJ1.setBounds(5, 5, dim.width, dim.height);

                JButton botonPJ2 = new JButton("");
                botonPJ2.setIcon(new ImageIcon("assets/" + pj2.getImagen()));
                botonPJ2.setFocusPainted(false);
                botonPJ2.setBorderPainted(false);
                dim.setSize(100, 100);
                botonPJ2.setPreferredSize(dim);
                botonPJ2.setMaximumSize(dim);
                botonPJ2.setMaximumSize(dim);
                panel.add(botonPJ2);
                botonPJ2.setBounds(105, 5, dim.width, dim.height);

                JButton botonPJ3 = new JButton("");
                botonPJ3.setIcon(new ImageIcon("assets/" + pj3.getImagen()));
                botonPJ3.setFocusPainted(false);
                botonPJ3.setBorderPainted(false);
                dim.setSize(100, 100);
                botonPJ3.setPreferredSize(dim);
                botonPJ3.setMaximumSize(dim);
                botonPJ3.setMaximumSize(dim);
                panel.add(botonPJ3);
                botonPJ3.setBounds(205, 5, dim.width, dim.height);

                JLabel label0 = new JLabel(String.format("%s", p.getFecha()));
                dim.setSize(160, 30);
                label0.setPreferredSize(dim);
                label0.setMaximumSize(dim);
                label0.setMinimumSize(dim);
                label0.setFont(fuenteLabels);
                panel.add(label0);
                label0.setBounds(310, 0, dim.width, dim.height);

                JLabel label01;
                if (p.getFinalizada())
                    label01 = new JLabel(String.format("Terminada: %s", "No"));
                else
                    label01 = new JLabel(String.format("Terminada: %s", "Sí"));
                dim.setSize(160, 30);
                label01.setPreferredSize(dim);
                label01.setMaximumSize(dim);
                label01.setMinimumSize(dim);
                label01.setFont(fuenteLabels);
                panel.add(label01);
                label01.setBounds(310, 12, dim.width, dim.height);

                JLabel label1 = new JLabel(String.format("id: %3d       Ronda: %4d", p.getId(), p.getRonda()));
                dim.setSize(160, 30);
                label1.setPreferredSize(dim);
                label1.setMaximumSize(dim);
                label1.setMinimumSize(dim);
                label1.setFont(fuenteLabels);
                panel.add(label1);
                label1.setBounds(310, 24, dim.width, dim.height);

                JLabel label2 = new JLabel(String.format("Enemigos derrotados: %4d", p.getEnemigosDerrotados().size()));
                dim.setSize(160, 30);
                label2.setPreferredSize(dim);
                label2.setMaximumSize(dim);
                label2.setMinimumSize(dim);
                label2.setFont(fuenteLabels);
                panel.add(label2);
                label2.setBounds(310, 34, dim.width, dim.height);

                JLabel label3 = new JLabel(String.format("%-12s (Nivel %2d)", pj1.getNombre(), pj1.getAtributos().getNivel()));
                dim.setSize(160, 30);
                label3.setPreferredSize(dim);
                label3.setMaximumSize(dim);
                label3.setMinimumSize(dim);
                label3.setFont(fuenteLabels);
                panel.add(label3);
                label3.setBounds(310, 55, dim.width, dim.height);

                JLabel label4 = new JLabel(String.format("%-12s (Nivel %2d)", pj2.getNombre(), pj2.getAtributos().getNivel()));
                dim.setSize(160, 30);
                label4.setPreferredSize(dim);
                label4.setMaximumSize(dim);
                label4.setMinimumSize(dim);
                label4.setFont(fuenteLabels);
                panel.add(label4);
                label4.setBounds(310, 65, dim.width, dim.height);

                JLabel label5 = new JLabel(String.format("%-12s (Nivel %2d)", pj3.getNombre(), pj3.getAtributos().getNivel()));
                dim.setSize(160, 30);
                label5.setPreferredSize(dim);
                label5.setMaximumSize(dim);
                label5.setMinimumSize(dim);
                label5.setFont(fuenteLabels);
                panel.add(label5);
                label5.setBounds(310, 75, dim.width, dim.height);

                botonPJ1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listenerBotonPJs(pj1);
                    }
                });
                botonPJ2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listenerBotonPJs(pj2);
                    }
                });
                botonPJ3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listenerBotonPJs(pj3);
                    }
                });

                panelY += 118;
            }
        }

    }

    public void listenerBotonPJs(Personaje pj) {
        PlaySound ps = new PlaySound();
        ps.playSound(nombreSonidos[1], false, volumen);

        JFrame frame = new JFrame("Datos " + pj.getNombre());
        VentanaRondaDatos vrd = new VentanaRondaDatos(pj.getNombre(), pj.getImagen(), pj.getAtributos(), frame);
        frame.setContentPane(vrd.getPanel());
        vrd.setVentanaRonda(ventanaInicioHistorial);
        vrd.setVentanaRondaDatos(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        ventanaInicioHistorial.setEnabled(false);
    }

    public void volver() {
        PlaySound ps = new PlaySound();
        ps.playSound(nombreSonidos[3], false, volumen);

        ventanaInicioHistorial.dispose();
        ventanaInicio.setVisible(true);
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InicioHistorial");
        frame.setContentPane(new InicioHistorial(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
