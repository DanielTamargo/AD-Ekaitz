package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.Grupo;
import com.tamargo.modelo.ListaPartidas;
import com.tamargo.modelo.Partida;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Inicio {
    private JFrame ventanaInicio;

    private JPanel panel;
    private JLabel logo;
    private JButton b_nuevaPartida;
    private JButton b_continuar;
    private JButton b_historial;
    private JButton b_salir;
    private JButton b_previousSong;
    private JLabel l_cancion;
    private JButton b_nextSong;
    private JSpinner spinnerVolumen;
    private JSlider sliderVolumen;

    private int indexCancion = 0;
    private final String[] canciones = AdministradorRutasArchivos.canciones;
    private final String[] nombreCanciones = AdministradorRutasArchivos.nombreCanciones;
    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;

    private float volumen = -40;
    private PlaySound pm;
    private ListaPartidas listaPartidas;
    private ListaPartidas listaPartidasContinuar = new ListaPartidas();

    private boolean iniciado = false;

    public Inicio(JFrame ventanaInicio) {

        logo.setIcon(new ImageIcon("assets/logo_600x338.png"));
        sliderVolumen.setValue(50);
        volumen = (float) (int) sliderVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);

        cambiarCancion();
        comprobarPartidasContinuar();

        this.ventanaInicio = ventanaInicio;
        this.ventanaInicio.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                //System.out.println("Volviendo a Inicio!");
                if (iniciado) {
                    indexCancion = pm.getIndexCancion();
                    cambiarNombreCancion();
                    comprobarPartidasContinuar();
                } else {
                    iniciado = true;
                }
            }
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                //System.out.println("Se ha ocultado la ventana Inicio!");
            }
        });

        //b_nuevaPartida.setIcon(new ImageIcon("assets/boton_nuevaPartida.png"));
        b_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaInicio.dispose();
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen);
            }
        });
        b_nuevaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nueva Partida");
                NuevaPartida np = new NuevaPartida();
                frame.setContentPane(np.getPanel());
                np.setVentanaNuevaPartida(frame);
                np.setVentanaInicio(ventanaInicio);
                np.setVolumen(volumen);
                np.setIndexCancion(indexCancion);
                np.setPm(pm);
                np.setSliderVolumenInicio(sliderVolumen);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Debería centrarlo pero en mi ventana me lo genera abajo a la derecha hm

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);

                frame.setVisible(true);
                ventanaInicio.dispose();
            }
        });
        b_previousSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion--;
                if (indexCancion < 0)
                    indexCancion = canciones.length - 1;
                cambiarCancion();
            }
        });
        b_nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion++;
                if (indexCancion >= canciones.length)
                    indexCancion = 0;
                cambiarCancion();
            }
        });
        sliderVolumen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) sliderVolumen.getValue() > 100)
                    sliderVolumen.setValue(100);
                else if ((int) sliderVolumen.getValue() < 0)
                    sliderVolumen.setValue(0);

                volumen = (float) (int) sliderVolumen.getValue();
                volumen = (float) ((volumen - 100) * 0.80);
                //System.out.println(volumen);
                pm.setVolume(volumen);
            }
        });
        b_historial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);
            }
        });
        b_continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO AL INICIAR LA VENTANA COMPROBAR QUE EXISTE EL FICHERO partidascontinuar.xml


                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);

                JFrame frame = new JFrame("InicioContinuar");
                InicioContinuar p = new InicioContinuar();
                frame.setContentPane(p.getPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                p.setPm(pm);
                p.setVolumen(volumen);
                p.setIndexCancion(indexCancion);
                p.setVentanaInicio(ventanaInicio);
                p.setVentanaInicioContinuar(frame);
                p.setListaPartidas(listaPartidasContinuar);
                p.setSliderVolumen(sliderVolumen);
                p.actualizarJList();

                ventanaInicio.dispose();

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public void comprobarPartidasContinuar() {
        listaPartidasContinuar.getLista().clear(); // Vaciamos la lista para no repetir los elementos porque cada vez que volvemos a inicio, se ejecuta el método que añade partidas
        listaPartidas = new LeerDatosBase().leerListaPartidas();
        if (listaPartidas.getLista().size() > 0) {
            boolean existen = false;
            for (Partida p: listaPartidas.getLista()) {
                if (!p.getFinalizada()) {
                    listaPartidasContinuar.addPartida(p);
                    existen = true;
                }
            }
            if (existen)
                b_continuar.setEnabled(true);
        }
    }

    public void cambiarNombreCancion() {
        String cancion = nombreCanciones[indexCancion];
        l_cancion.setText(cancion);
    }

    public void cambiarCancion() {
        cambiarNombreCancion();
        float volumen = (float) (int) sliderVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);
        if (pm != null)
            pm.stopSong();
        else
            pm = new PlaySound(indexCancion);
        pm.playSound(canciones[indexCancion], true, volumen);
    }

    public String[] getCanciones() {
        return canciones;
    }

    public String[] getNombreCanciones() {
        return nombreCanciones;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new Inicio(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

}
