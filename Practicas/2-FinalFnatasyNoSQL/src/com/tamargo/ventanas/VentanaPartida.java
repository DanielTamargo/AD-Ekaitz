package com.tamargo.ventanas;

import com.tamargo.EscribirDatosBase;
import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaPartida {

    // ELEMENTOS GENERALES
    private JFrame ventanaPartida;
    private JFrame ventanaInicio;
    private JPanel panel;
    private JTabbedPane tabbedPane1;

    private JPanel tabJuego;
    private JPanel tabPersonajes;
    private JPanel tabSonido;

    // ELEMENTOS TAB PERSONAJES
    private JButton b_grupoPJ1;
    private JButton b_grupoPJ2;
    private JButton b_grupoPJ3;
    private JLabel l_fotoPJ;
    private JLabel l_nombre;
    private JLabel l_tipo;
    private JLabel l_arma;
    private JTextArea l_descripcion;
    private JLabel l_atrNivel;
    private JLabel l_atrPuntosDisponibles;
    private JLabel l_atrVit;
    private JLabel l_atrFuerza;
    private JLabel l_atrPoder;
    private JLabel l_atrDestreza;
    private JLabel l_atrAgil;
    private JLabel l_atrDefFis;
    private JLabel l_atrDefMag;
    private JButton b_restar1;
    private JLabel l_armaRareza;
    private JLabel l_armaAtaqFis;
    private JLabel l_armaAtaqMag;
    private JLabel l_armaDefFis;
    private JLabel l_armaDefMag;
    private JLabel l_armaTipo;
    private JComboBox<Arma> comboBoxArmas;

    private JButton b_restarVit;
    private JButton b_restarFuerza;
    private JButton b_restarPoder;
    private JButton b_restarDestreza;
    private JButton b_restarAgilidad;
    private JButton b_restarDefFis;
    private JButton b_restarDefMag;

    private JButton b_sumarVit;
    private JButton b_sumarFuerza;
    private JButton b_sumarPoder;
    private JButton b_sumarDestreza;
    private JButton b_sumarAgilidad;
    private JButton b_sumarDefFis;
    private JButton b_sumarDefMag;
    private JButton b_cancelar;
    private JButton b_confirmar;

    private JSlider sliderVolumen;
    private JTextPane textPanePlaylists;
    private JButton b_nextSong;
    private JButton b_previousSong;
    private JLabel l_cancion;
    private JPanel panelJuego;
    private JLabel l_experiencia;
    private JLabel l_fotoJuego;

    // VARIABLES GENERALES
    private Grupo grupo;
    private Partida partida;
    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;
    private final String[] fotosPersonaje = AdministradorRutasArchivos.fotosPersonaje;
    private JSlider sliderVolumenInicio;
    private boolean recienAbierto = true;

    // VARIABLES TAB PERSONAJES
    private int puntosDisponibles = 0;
    private int vitalidad;
    private int fuerza;
    private int poder;
    private int destreza;
    private int agilidad;
    private int defensaFis;
    private int defensaMag;
    private ArrayList<Arma> armasDisponibles = new ArrayList<>();

    // VARIABLES TAB SONIDO
    private float volumen = -40;
    private PlaySound pm;
    private int indexCancion;
    private final String[] canciones = AdministradorRutasArchivos.canciones;
    private final String[] nombreCanciones = AdministradorRutasArchivos.nombreCanciones;

    private JButton b_siguienteRonda;
    private JButton b_salir;
    private JLabel l_rondasGanadas;

    private int index = 1;

    public VentanaPartida(JFrame ventanaPartida) {

        this.ventanaPartida = ventanaPartida;
        this.ventanaPartida.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (!recienAbierto) {
                    if (partida.getFinalizada()) {
                        tabbedPane1.removeTabAt(2);
                        tabbedPane1.removeTabAt(1);
                        b_siguienteRonda.setEnabled(false);
                        partida.setRonda(grupo.getRondasGanadas());
                        l_fotoJuego.setIcon(new ImageIcon("assets/fondo-2.png"));
                    } else {
                        pm.resumeSong();
                        l_rondasGanadas.setText("Rondas ganadas: " + grupo.getRondasGanadas());
                        partida.setRonda(grupo.getRondasGanadas() + 1);
                        index = 1;
                        cambiarDatos();
                        actualizarListaArmasDisponibles();
                        cambiarDatosArma();

                        boolean nivelesSubidos = false;

                        for (Personaje p: grupo.getPersonajes()) {
                            if (p.getNivelesSubidos() > 0)
                                nivelesSubidos = true;
                        }

                        if (nivelesSubidos) {
                            JButton okButton = new JButton("Ok");
                            JButton llevameButton = new JButton("¡Llévame!");
                            okButton.setFocusPainted(false);
                            Object[] options = {okButton, llevameButton};
                            final JOptionPane pane = new JOptionPane("¡Tienes puntos para subir!\n" +
                                    "¡Comprueba tu grupo en la pestaña personajes!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
                            JDialog dialog = pane.createDialog("Puntos Disponibles");
                            okButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    PlaySound ps = new PlaySound();
                                    ps.playSound(nombreSonidos[1], false, volumen);
                                    dialog.dispose();
                                }
                            });
                            llevameButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    PlaySound ps = new PlaySound();
                                    ps.playSound(nombreSonidos[1], false, volumen);
                                    tabbedPane1.setSelectedIndex(1);
                                    dialog.dispose();
                                }
                            });
                            dialog.setVisible(true);
                        }
                    }
                }
                else {
                    recienAbierto = false;
                }
            }
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                //System.out.println("Se ha ocultado la ventana partida!");
            }
        });
        this.ventanaPartida.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarPartida();
                e.getWindow().dispose();
            }
        });
        /*
        if (grupo == null) {
            ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();
            personajes.remove(3);
            personajes.remove(2);
            grupo = new Grupo(1, personajes, 1);
        }*/

        panelJuego.setLayout(null);

        Dimension dim = new Dimension();

        b_salir = new JButton("Salir al Menú");
        b_salir.setFont(b_confirmar.getFont());
        dim.setSize(200, 50);
        b_salir.setPreferredSize(dim);
        panelJuego.add(b_salir);
        b_salir.setBounds(770, 495, dim.width, dim.height);
        b_salir.setRequestFocusEnabled(false);
        b_salir.setFocusPainted(false);

        b_siguienteRonda = new JButton("Siguiente Ronda");
        b_siguienteRonda.setFont(b_confirmar.getFont());
        dim.setSize(200, 50);
        b_siguienteRonda.setPreferredSize(dim);
        panelJuego.add(b_siguienteRonda);
        b_siguienteRonda.setBounds(770, 430, dim.width, dim.height);
        b_siguienteRonda.setRequestFocusEnabled(false);
        b_siguienteRonda.setFocusPainted(false);

        l_rondasGanadas = new JLabel("Rondas ganadas: ");
        dim.setSize(200, 50);
        l_rondasGanadas.setPreferredSize(dim);
        l_rondasGanadas.setFont(l_nombre.getFont());
        panelJuego.add(l_rondasGanadas);
        l_rondasGanadas.setBounds(831, 380, dim.width, dim.height);


        l_fotoJuego = new JLabel("");
        l_fotoJuego.setIcon(new ImageIcon("assets/fondo-1.png"));
        panelJuego.add(l_fotoJuego);
        dim.setSize(995, 575);
        l_fotoJuego.setPreferredSize(dim);
        l_fotoJuego.setBounds(0, 0, dim.width, dim.height);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // LISTENERS JUEGO
        b_siguienteRonda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.stopSong();

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);

                VentanaRonda vr = new VentanaRonda();
                JFrame frame = new JFrame("Ronda " + partida.getRonda());
                frame.setContentPane(vr.getPanel());
                vr.setVentanaPartida(ventanaPartida);
                vr.setVolumen(volumen);
                vr.setVentanaRonda(frame);

                //vr.setGrupo(grupo);
                vr.setPartida(partida);

                vr.comenzarRonda();
                vr.setPmPartida(pm);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                ventanaPartida.dispose();
            }
        });
        b_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen);

                // TODO GUARDAR LA PARTIDA PARA CONTINUAR (?)
                guardarPartida();

                if (partida.getFinalizada()) {
                    pm.setIndexCancion(indexCancion);
                    pm.stopSong();
                    pm.playSound(canciones[indexCancion], true, volumen);
                }
                pm.setIndexCancion(indexCancion);
                ventanaInicio.setVisible(true);
                sliderVolumenInicio.setValue(sliderVolumen.getValue());
                ventanaPartida.dispose();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // LISTENERS PERSONAJES
        b_grupoPJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 1;
                cambiarDatos();
                actualizarListaArmasDisponibles();
                cambiarDatosArma();
            }
        });
        b_grupoPJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 2;
                cambiarDatos();
                actualizarListaArmasDisponibles();
                cambiarDatosArma();
            }
        });
        b_grupoPJ3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 3;
                cambiarDatos();
                actualizarListaArmasDisponibles();
                cambiarDatosArma();
            }
        });
        comboBoxArmas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatosArma();
            }
        });
        b_sumarVit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                vitalidad++;
                puntosDisponibles--;
                b_restarVit.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarVit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                vitalidad--;
                puntosDisponibles++;
                if (vitalidad == grupo.getPersonajes().get(index - 1).getAtributos().getVitalidad())
                    b_restarVit.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarFuerza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                fuerza++;
                puntosDisponibles--;
                b_restarFuerza.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarFuerza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                fuerza--;
                puntosDisponibles++;
                if (fuerza == grupo.getPersonajes().get(index - 1).getAtributos().getFuerza())
                    b_restarFuerza.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarPoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                poder++;
                puntosDisponibles--;
                b_restarPoder.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarPoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                poder--;
                puntosDisponibles++;
                if (poder == grupo.getPersonajes().get(index - 1).getAtributos().getPoderMagico())
                    b_restarPoder.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarDestreza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                destreza++;
                puntosDisponibles--;
                b_restarDestreza.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarDestreza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                destreza--;
                puntosDisponibles++;
                if (destreza == grupo.getPersonajes().get(index - 1).getAtributos().getDestreza())
                    b_restarDestreza.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarAgilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                agilidad++;
                puntosDisponibles--;
                b_restarAgilidad.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarAgilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                agilidad--;
                puntosDisponibles++;
                if (agilidad == grupo.getPersonajes().get(index - 1).getAtributos().getAgilidad())
                    b_restarAgilidad.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarDefFis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                defensaFis++;
                puntosDisponibles--;
                b_restarDefFis.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarDefFis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                defensaFis--;
                puntosDisponibles++;
                if (defensaFis == grupo.getPersonajes().get(index - 1).getAtributos().getDefensaFis())
                    b_restarDefFis.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_sumarDefMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[1], false, volumen);
                defensaMag++;
                puntosDisponibles--;
                b_restarDefMag.setEnabled(true);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_restarDefMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[3], false, volumen);
                defensaMag--;
                puntosDisponibles++;
                if (defensaMag == grupo.getPersonajes().get(index - 1).getAtributos().getDefensaMag())
                    b_restarDefMag.setEnabled(false);
                cambiarPuntosAtributos();
                habilitarDeshabilitarBotonesSumar();
            }
        });
        b_cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen);

                comboBoxArmas.setSelectedIndex(0);
                cambiarDatos();
            }
        });
        b_confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (puntosDisponibles > 0) { // Solo podremos confirmar cuando se hayan gastado todos los puntos disponibles
                    PlaySound ps = new PlaySound();
                    ps.playSound(nombreSonidos[3], false, volumen);

                    JOptionPane.showMessageDialog(null,
                            "Tienes que gastar todos los puntos disponibles antes de confirmar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    PlaySound ps = new PlaySound();
                    ps.playSound(nombreSonidos[0], false, volumen);

                    Personaje per = grupo.getPersonajes().get(index - 1);
                    grupo.getInventario().add(per.getArma());
                    per.setArma((Arma) comboBoxArmas.getSelectedItem());
                    grupo.getInventario().remove((Arma) comboBoxArmas.getSelectedItem());

                    per.getAtributos().setVitalidad(vitalidad);
                    per.getAtributos().setFuerza(fuerza);
                    per.getAtributos().setPoderMagico(poder);
                    per.getAtributos().setDestreza(destreza);
                    per.getAtributos().setAgilidad(agilidad);
                    per.getAtributos().setDefensaFis(defensaFis);
                    per.getAtributos().setDefensaMag(defensaMag);

                    actualizarListaArmasDisponibles();
                    actualizarNivelesSubidosPJ();
                    cambiarDatos();
                    cambiarDatosArma();
                }
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // LISTENERS SONIDO
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
    }

    public void guardarPartida() {
        partida.actualizarFecha();
        boolean anyadida = false;
        ArrayList<Partida> listaPartidas = new LeerDatosBase().leerListaPartidas().getLista();
        ArrayList<Partida> listaPartidasAGuardar = new ArrayList<>();

        for (Partida p: listaPartidas) {
            if (p.getId() == partida.getId()) { //Sustituimos la partida en cuestión
                listaPartidasAGuardar.add(partida);
                anyadida = true;
            } else {
                listaPartidasAGuardar.add(p);
            }
        }
        if (!anyadida)
            listaPartidasAGuardar.add(partida);

        ListaPartidas lp = new ListaPartidas(listaPartidasAGuardar);
        new EscribirDatosBase().escribirListaPartidas(lp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TAB JUEGO
    public void actualizarRondasGanadas() {
        l_rondasGanadas.setText("Rondas ganadas: " + grupo.getRondasGanadas());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TAB SONIDO
    public void cambiarCancion() {

        String cancion = nombreCanciones[indexCancion];
        l_cancion.setText(cancion);

        float volumen = (float) (int) sliderVolumen.getValue();
        volumen = (float) ((volumen - 100) * 0.80);
        if (pm != null)
            pm.stopSong();
        else
            pm = new PlaySound(indexCancion);
        pm.playSound(canciones[indexCancion], true, volumen);
        actualizarJTextPane();
    }

    public void actualizarJTextPane() {
        textPanePlaylists.setText("");

        StyledDocument doc = textPanePlaylists.getStyledDocument();

        Style style = textPanePlaylists.addStyle("PlaylistStyle", null);
        StyleConstants.setForeground(style, Color.black);

        for (int i = 0; i < nombreCanciones.length; i++) {
            if (i == indexCancion) {
                StyleConstants.setForeground(style, Color.blue);
                try {
                    doc.insertString(doc.getLength(), nombreCanciones[i], style);
                } catch (BadLocationException e) {}
                StyleConstants.setForeground(style, Color.black);
            } else {
                try {
                    doc.insertString(doc.getLength(), nombreCanciones[i], style);
                } catch (BadLocationException e) {}
            }
            if (i < nombreCanciones.length - 1) {
                try {
                    doc.insertString(doc.getLength(), "\n\n", style);
                } catch (BadLocationException e) {}
            }
        }

    }

    public void configurarSliderVolumen() {
        volumen = (float) ((volumen * 1.25) + 100);
        sliderVolumen.setValue((int) volumen);
    }

    public void setIndexCancion(int indexCancion) {
        this.indexCancion = indexCancion;

        String cancion = nombreCanciones[indexCancion];
        l_cancion.setText(cancion);
    }

    public void setPm(PlaySound pm) {
        this.pm = pm;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TAB PERSONAJES

    public void inicializarTabPersonajes() {
        iconosGrupo();
        cambiarDatos();
        actualizarListaArmasDisponibles();
        cambiarDatosArma();
    }

    public void datosPrueba() {
        if (grupo == null) {
            ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();
            grupo = new Grupo(1, personajes, 1);
            grupo.getPersonajes().get(0).setNivelesSubidos(2);
        }
    }

    public void actualizarNivelesSubidosPJ() {
        grupo.getPersonajes().get(index - 1).setNivelesSubidos(0);
    }

    public void actualizarListaArmasDisponibles() {

        // Vaciamos el combobox por si ha cambiado de personaje seleccionado
        comboBoxArmas.removeAllItems();

        // Reseteamos el Array
        armasDisponibles = new ArrayList<>();

        armasDisponibles.add(grupo.getPersonajes().get(index - 1).getArma());

        armasDisponibles.addAll(grupo.getInventario());

        // Añadimos las armas al modelo y se lo encajamos al combobox
        DefaultComboBoxModel<Arma> modelo = new DefaultComboBoxModel<>();
        for (Arma a: armasDisponibles) {
            modelo.addElement(a);
        }
        comboBoxArmas.setModel(modelo);

    }

    public void cambiarDatosArma() {
        Arma a;
        try {
            a = (Arma) comboBoxArmas.getSelectedItem();
        } catch (Exception e) {
            a = null;
        }

        if (a != null) {
            l_armaTipo.setText(a.getTipo());
            l_armaRareza.setText(Integer.toString(a.getRareza()));
            l_armaAtaqFis.setText(Integer.toString(a.getAtaqueFis()));
            l_armaAtaqMag.setText(Integer.toString(a.getAtaqueMag()));
            l_armaDefFis.setText(Integer.toString(a.getDefensaFis()));
            l_armaDefMag.setText(Integer.toString(a.getDefensaMag()));
        }
    }

    public void iconosGrupo() {
        b_grupoPJ1.setIcon(new ImageIcon(iconoPJ(grupo.getPersonajes().get(0))));
        b_grupoPJ2.setIcon(new ImageIcon(iconoPJ(grupo.getPersonajes().get(1))));
        b_grupoPJ3.setIcon(new ImageIcon(iconoPJ(grupo.getPersonajes().get(2))));
    }

    public String iconoPJ(Personaje p) {
        if (p.getId() == 1)
            return "assets/icono-1-guerrero.png";
        else if (p.getId() == 2)
            return "assets/icono-2-mago.png";
        else if (p.getId() == 3)
            return "assets/icono-3-arquero.png";
        else if (p.getId() == 4)
            return "assets/icono-4-guardian.png";
        else
            return "assets/icono-5-asesino.png";
    }

    public void cambiarDatos() {

        String nombre;
        String tipo;
        String arma;
        String descripcion;

        // Obtenemos los objetos que nos interesa
        Personaje per = grupo.getPersonajes().get(index - 1);
        Atributos atr = per.getAtributos();

        // Cargamos datos personaje
        nombre = per.getNombre();
        tipo = per.getTipo().name();
        arma = per.getArma().getNombre();
        descripcion = per.getDescripcion();

        if (tipo.equalsIgnoreCase("Guardian"))
            tipo = "Guardián";

        int longitudLinea = 53; // Esta cifra es porque a mi me cuadra bien, podría ser cualquier número que cuadrase
        int caracteresPorLinea = longitudLinea;
        int caracterInicioLinea = 0;
        StringBuilder descripcionReconstruida = new StringBuilder();
        try {
            while (true) {
                if (descripcion.length() > (caracterInicioLinea + caracteresPorLinea)) { // Leemos primeras líneas
                    if (descripcion.charAt(caracteresPorLinea + caracterInicioLinea) == ' ') {
                        descripcionReconstruida.append(descripcion.substring(caracterInicioLinea, (caracterInicioLinea + caracteresPorLinea))).append("\n");
                        caracterInicioLinea += caracteresPorLinea + 1; // Sin este +1, imprimirá al comienzo de la siguiente línea el espacio, y no queremos eso
                        caracteresPorLinea = longitudLinea;
                    } else {
                        caracteresPorLinea--;
                    }
                } else { // Última línea
                    descripcionReconstruida.append(descripcion.substring(caracterInicioLinea));
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Este try catch sobra porque en principio controlo cuándo llega a la última línea, peeero por si acaso
        }

        // Plasmamos datos personaje
        l_fotoPJ.setIcon(new ImageIcon("assets/" + fotosPersonaje[per.getId() - 1]));
        l_nombre.setText(nombre);
        l_tipo.setText(tipo);
        l_arma.setText(arma);
        l_experiencia.setText(per.getExperienciaConseguida() + "/" + per.getExperienciaNecesaria());
        l_descripcion.setText(descripcionReconstruida.toString());

        // Guardamos los atributos en las variables
        vitalidad = atr.getVitalidad();
        fuerza = atr.getFuerza();
        poder = atr.getPoderMagico();
        destreza = atr.getDestreza();
        agilidad = atr.getAgilidad();
        defensaFis = atr.getDefensaFis();
        defensaMag = atr.getDefensaMag();

        // Plasmamos datos pantalla Atributos
        calcularPuntosDisponiblesPJ();
        l_atrNivel.setText("Nivel: " + atr.getNivel());
        cambiarPuntosAtributos();

        // Habilitamos o deshabilitamos los botones de sumar en base a si tenemos puntos disponibles o no
        habilitarDeshabilitarBotonesSumar();

        // Deshabilitamos los botones de restar puesto que hemos recargado los atributos
        deshabilitarBotonesRestar();
    }

    public void habilitarDeshabilitarBotonesSumar() {
        if (puntosDisponibles > 0) {
            b_sumarVit.setEnabled(true);
            b_sumarFuerza.setEnabled(true);
            b_sumarPoder.setEnabled(true);
            b_sumarDestreza.setEnabled(true);
            b_sumarAgilidad.setEnabled(true);
            b_sumarDefFis.setEnabled(true);
            b_sumarDefMag.setEnabled(true);
        } else {
            b_sumarVit.setEnabled(false);
            b_sumarFuerza.setEnabled(false);
            b_sumarPoder.setEnabled(false);
            b_sumarDestreza.setEnabled(false);
            b_sumarAgilidad.setEnabled(false);
            b_sumarDefFis.setEnabled(false);
            b_sumarDefMag.setEnabled(false);
        }
    }

    public void calcularPuntosDisponiblesPJ() {
        Personaje per = grupo.getPersonajes().get(index - 1);
        puntosDisponibles = per.getNivelesSubidos() * 5; // Cada nivel te deja subir 5 puntos
    }

    public void deshabilitarBotonesRestar() {
        b_restarVit.setEnabled(false);
        b_restarFuerza.setEnabled(false);
        b_restarPoder.setEnabled(false);
        b_restarAgilidad.setEnabled(false);
        b_restarDestreza.setEnabled(false);
        b_restarDefFis.setEnabled(false);
        b_restarDefMag.setEnabled(false);
    }

    public void cambiarPuntosAtributos() {
        // Plasmamos datos pantalla Atributos
        l_atrPuntosDisponibles.setText("Puntos Disponibles: " + puntosDisponibles);
        l_atrVit.setText("Vitalidad: " + vitalidad);
        l_atrFuerza.setText("Fuerza: " + fuerza);
        l_atrPoder.setText("Poder Mágico: " + poder);
        l_atrDestreza.setText("Destreza: " + destreza);
        l_atrAgil.setText("Agilidad: " + agilidad);
        l_atrDefFis.setText("Defensa Física: " + defensaFis);
        l_atrDefMag.setText("Defensa Mágica: " + defensaMag);
    }

    public void setSliderVolumenInicio(JSlider sliderVolumenInicio) {
        this.sliderVolumenInicio = sliderVolumenInicio;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
        this.grupo = partida.getGrupo();
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;

        /*
        ListaPartidas listaPartidas = null;
        try {
            listaPartidas = new LeerDatosBase().leerListaPartidas();
        } catch (Exception e) {}
        int id = 1;
        if (listaPartidas != null)
            id = listaPartidas.getLista().size() + 1;
         */

        // Hacer setId cuando vayamos a guardarla
        partida = new Partida(1, false, this.grupo, new ArrayList<Enemigo>(), new ArrayList<Evento>());
                //int id, int ronda, boolean finalizada, Grupo grupo, ArrayList<Enemigo> enemigosDerrotados, ArrayList<Evento> eventosPasados
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setVentanaPartida(JFrame ventanaPartida) {
        this.ventanaPartida = ventanaPartida;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Partida");
        frame.setContentPane(new VentanaPartida(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
