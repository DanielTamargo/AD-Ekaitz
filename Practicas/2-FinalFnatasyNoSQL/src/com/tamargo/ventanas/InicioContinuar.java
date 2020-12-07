package com.tamargo.ventanas;

import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.Atributos;
import com.tamargo.modelo.ListaPartidas;
import com.tamargo.modelo.Partida;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioContinuar {

    private JFrame ventanaInicioContinuar;
    private JFrame ventanaInicio;
    private JSlider sliderVolumen;
    private float volumen;
    private int indexCancion;
    private PlaySound pm;
    private ListaPartidas listaPartidas;
    private Partida partida;

    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;

    private JPanel panel;
    private JList<Partida> listPartidas;
    private JButton b_volver;
    private JButton b_continuarPartida;
    private JButton b_grupoPJ1;
    private JButton b_grupoPJ2;
    private JButton b_grupoPJ3;
    private JLabel l_fechaGuardado;
    private JLabel l_rondas;
    private JLabel l_nombrePJ;
    private JLabel l_nivelPJ;
    private JLabel l_armaPJ;
    private JLabel l_vitalidad;
    private JLabel l_fuerza;
    private JLabel l_poderMag;
    private JLabel l_destreza;
    private JLabel l_agilidad;
    private JLabel l_defFis;
    private JLabel l_defMag;
    private JLabel l_enemDerrotados;

    private int indexPersonaje = 0;

    public InicioContinuar() {


        b_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen);

                ventanaInicioContinuar.dispose();
                ventanaInicio.setVisible(true);

            }
        });
        b_grupoPJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexPersonaje = 0;
                actualizarDatosPersonaje();
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                actualizarBorderPainteds(b_grupoPJ1);
            }
        });
        b_grupoPJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexPersonaje = 1;
                actualizarDatosPersonaje();
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                actualizarBorderPainteds(b_grupoPJ2);
            }
        });
        b_grupoPJ3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexPersonaje = 2;
                actualizarDatosPersonaje();
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                actualizarBorderPainteds(b_grupoPJ3);
            }
        });
        listPartidas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                Partida partidaElegida = (Partida) listPartidas.getSelectedValue();
                if (partidaElegida != null) {

                    partida = partidaElegida;
                    actualizarDatos();
                }

            }
        });
        b_continuarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);
                JFrame frame = new JFrame("Partida");

                VentanaPartida p = new VentanaPartida(frame);
                p.setPm(pm);
                p.setIndexCancion(indexCancion);
                p.setVolumen(volumen);
                p.actualizarJTextPane();
                p.configurarSliderVolumen();
                p.setSliderVolumenInicio(sliderVolumen);
                p.setVentanaInicio(ventanaInicio);
                p.setVentanaPartida(frame);
                p.setPartida(partida);
                p.actualizarRondasGanadas();
                p.inicializarTabPersonajes();

                frame.setContentPane(p.getPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                ventanaInicioContinuar.dispose();
            }
        });
    }

    public void actualizarBorderPainteds(JButton button) {
        b_grupoPJ1.setBorderPainted(false);
        b_grupoPJ2.setBorderPainted(false);
        b_grupoPJ3.setBorderPainted(false);
        button.setBorderPainted(true);
    }

    public void actualizarJList() {
        DefaultListModel<Partida> model = new DefaultListModel<>();

        for (Partida p : listaPartidas.getLista()) {
            if (!p.getFinalizada())
                model.addElement(p);
        }

        listPartidas.setModel(model);
        listPartidas.setSelectedIndex(0);
    }

    public void actualizarDatos() {
        actualizarDatosPartida();
        actualizarDatosPersonaje();
    }

    public void actualizarDatosPersonaje() {
        Personaje per = partida.getGrupo().getPersonajes().get(indexPersonaje);
        Atributos atr = per.getAtributos();

        // Datos base
        l_nombrePJ.setText(per.getNombre());
        l_nivelPJ.setText("Nivel: " + atr.getNivel());
        l_armaPJ.setText("Arma: " + per.getArma().getNombre());

        // Datos atributos
        l_vitalidad.setText(String.format("%-15s %3d", "Vitalidad: ",atr.getVitalidad()));
        l_fuerza.setText(String.format("%-15s %3d", "Fuerza: ",atr.getFuerza()));
        l_poderMag.setText(String.format("%-15s %3d", "Poder Mágico: ",atr.getPoderMagico()));
        l_destreza.setText(String.format("%-15s %3d", "Destreza: ",atr.getDestreza()));
        l_agilidad.setText(String.format("%-15s %3d", "Agilidad: ",atr.getAgilidad()));
        l_defFis.setText(String.format("%-15s %3d", "Def. Física: ",atr.getDefensaFis()));
        l_defMag.setText(String.format("%-15s %3d", "Def. Mágica: ",atr.getDefensaMag()));
    }

    public void actualizarDatosPartida() {
        l_fechaGuardado.setText(partida.getFecha());
        l_rondas.setText("Ronda actual: " + partida.getRonda());
        l_enemDerrotados.setText("Enemigos derrotados: " + partida.getEnemigosDerrotados().size());
        b_grupoPJ1.setIcon(new ImageIcon("assets/" + partida.getGrupo().getPersonajes().get(0).getImagen()));
        b_grupoPJ2.setIcon(new ImageIcon("assets/" + partida.getGrupo().getPersonajes().get(1).getImagen()));
        b_grupoPJ3.setIcon(new ImageIcon("assets/" + partida.getGrupo().getPersonajes().get(2).getImagen()));
    }

    public void setListaPartidas(ListaPartidas listaPartidas) {
        this.listaPartidas = listaPartidas;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setIndexCancion(int indexCancion) {
        this.indexCancion = indexCancion;
    }

    public void setPm(PlaySound pm) {
        this.pm = pm;
    }

    public void setVentanaInicioContinuar(JFrame ventanaInicioContinuar) {
        this.ventanaInicioContinuar = ventanaInicioContinuar;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public void setSliderVolumen(JSlider sliderVolumen) {
        this.sliderVolumen = sliderVolumen;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InicioContinuar");
        frame.setContentPane(new InicioContinuar().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
