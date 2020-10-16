package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.Atributos;
import com.tamargo.modelo.Grupo;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NuevaPartida {
    private JFrame ventanaNuevaPartida;
    private JFrame ventanaInicio;

    private JPanel panel;
    private JLabel l_creaTuGrupo;
    private JLabel l_eligePersonajes;
    private JButton b_pj1;
    private JButton b_pj2;
    private JButton b_pj3;
    private JButton b_pj4;
    private JButton b_pj5;
    private JLabel l_fotoPJ;
    private JLabel l_nombre;
    private JLabel l_tipo;
    private JLabel l_arma;
    private JTextArea l_descripcion;
    private JButton b_elegirPersonaje;
    private JButton b_grupoPJ1;
    private JButton b_grupoPJ2;
    private JButton b_grupoPJ3;
    private JButton b_salir;
    private JButton b_comenzarPartida;
    private JLabel l_atrNivel;
    private JLabel l_atrTitulo;
    private JLabel l_atrVit;
    private JLabel l_atrFuerza;
    private JLabel l_atrPoder;
    private JLabel l_atrDestreza;
    private JLabel l_atrAgil;
    private JLabel l_atrDefFis;
    private JLabel l_atrDefMag;

    private int index = 1;
    private ArrayList<Personaje> personajesElegidos = new ArrayList<>();
    private ArrayList<Personaje> personajes = new ArrayList<>();

    private PlaySound pm;
    private int indexCancion;

    private final String[] canciones = AdministradorRutasArchivos.canciones;
    private final String[] nombreCanciones = AdministradorRutasArchivos.nombreCanciones;
    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;
    private float volumen = -40;

    private final String[] fotosPersonaje = AdministradorRutasArchivos.fotosPersonaje;

    public NuevaPartida() {

        // Iconos grupo vacíos (SELECT YOUR TEAM)
        b_grupoPJ1.setIcon(new ImageIcon("assets/icono-0-vacio.png"));
        b_grupoPJ2.setIcon(new ImageIcon("assets/icono-0-vacio.png"));
        b_grupoPJ3.setIcon(new ImageIcon("assets/icono-0-vacio.png"));

        // Cargamos los personajes
        personajes = new LeerDatosBase().leerPersonajesBase();

        // Iconos botones
        b_pj1.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_pj2.setIcon(new ImageIcon("assets/icono-2-mago.png"));
        b_pj3.setIcon(new ImageIcon("assets/icono-3-arquero.png"));
        b_pj4.setIcon(new ImageIcon("assets/icono-4-guardian.png"));
        b_pj5.setIcon(new ImageIcon("assets/icono-5-asesino.png"));

        // Preparamos los datos del primer personaje (PJ seleccionado de forma predeterminada)
        cambiarDatos();

        // LISTENERS
        b_pj1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 1;
                b_elegirPersonaje.setText("Elegir Personaje");
                cambiarDatos();
            }
        });
        b_pj2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 2;
                b_elegirPersonaje.setText("Elegir Personaje");
                cambiarDatos();
            }
        });
        b_pj3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 3;
                b_elegirPersonaje.setText("Elegir Personaje");
                cambiarDatos();
            }
        });
        b_pj4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 4;
                b_elegirPersonaje.setText("Elegir Personaje");
                cambiarDatos();
            }
        });
        b_pj5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 5;
                b_elegirPersonaje.setText("Elegir Personaje");
                cambiarDatos();
            }
        });
        b_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaInicio.setVisible(true);
                ventanaNuevaPartida.dispose();

                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen + 20);

            }
        });
        b_elegirPersonaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b_elegirPersonaje.getText().equalsIgnoreCase("Elegir Personaje")) {
                    // Añadir personaje elegido
                    if (personajesElegidos.size() < 3) {
                        personajesElegidos.add(personajes.get(index - 1));
                        actualizarGrupo();
                        deshabilitarPJ();
                        cambiarDatosTrasElegirPersonaje();

                        PlaySound ps = new PlaySound();
                        ps.playSound(nombreSonidos[1], false, volumen);

                        if (personajesElegidos.size() == 3)
                            b_comenzarPartida.setEnabled(true);

                    } else {
                        //System.out.println("No se pueden añadir más de 3 personajes al grupo.");

                        PlaySound ps = new PlaySound();
                        ps.playSound(nombreSonidos[3], false, volumen);

                        /*
                        JOptionPane.showMessageDialog(null,
                                "No se pueden seleccionar más de 3 personajes.",
                                "Error en la selección",
                                JOptionPane.ERROR_MESSAGE);
                        */

                    }
                } else {
                    // Descartar personaje elegido
                    descartarPersonaje();
                    habilitarPJ();
                }
            }
        });
        b_grupoPJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (personajesElegidos.size() > 0)
                    elegirPJgrupo(1);
            }
        });
        b_grupoPJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (personajesElegidos.size() > 1)
                    elegirPJgrupo(2);
            }
        });
        b_grupoPJ3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (personajesElegidos.size() > 2)
                    elegirPJgrupo(3);
            }
        });
        b_comenzarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // En principio el botón sólo está habilitado cuando haya elegido sí o sí 3 personajes
                // Pero como los bugs pueden pasar, y es difícil tener tod@ en cuenta, vamos a evitar que pueda comenzar la partida sin tener 3 personajes
                if (personajesElegidos.size() == 3) {
                    // Nota: el id del grupo no es útil en estos momentos, siempre será 1, lo añadí por si en algún momento almacenaba los grupos en un fichero
                    Grupo grupo = new Grupo(1, personajesElegidos, 1);

                    PlaySound ps = new PlaySound();
                    ps.playSound(nombreSonidos[0], false, volumen);

                    JFrame frame = new JFrame("Partida");

                    Partida p = new Partida();
                    p.setPm(pm);
                    p.setIndexCancion(indexCancion);
                    p.setVolumen(volumen);
                    p.configurarSliderVolumen();
                    p.setVentanaInicio(ventanaInicio);
                    p.setVentanaPartida(frame);
                    p.setGrupo(grupo);
                    p.inicializarTabPersonajes();

                    frame.setContentPane(p.getPanel());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    ventanaNuevaPartida.dispose();
                } else {
                    PlaySound ps = new PlaySound();
                    ps.playSound(nombreSonidos[3], false, volumen);
                }
            }
        });
    }

    public void descartarPersonaje() {
        Personaje per = null;
        for (Personaje p: personajesElegidos) {
            if (index == p.getId()) {
                per = p;
            }
        }
        if (per != null) {
            personajesElegidos.remove(per);
        }

        PlaySound ps = new PlaySound();
        ps.playSound(nombreSonidos[1], false, volumen);

        b_comenzarPartida.setEnabled(false);
        b_elegirPersonaje.setText("Elegir Personaje");
        actualizarGrupo();

    }

    public void cambiarDatosTrasElegirPersonaje() {
        ArrayList<Integer> indexElegidos = new ArrayList<Integer>();
        for (Personaje p: personajesElegidos) {
            indexElegidos.add(p.getId());
        }

        if (!indexElegidos.contains(1))
            index = 1;
        else if (!indexElegidos.contains(2))
            index = 2;
        else if (!indexElegidos.contains(3))
            index = 3;
        else if (!indexElegidos.contains(4))
            index = 4;
        else if (!indexElegidos.contains(5))
            index = 5;

        cambiarDatos();
    }

    public void elegirPJgrupo(int indexPJGrupo) {
        index = personajesElegidos.get(indexPJGrupo - 1).getId();
        b_elegirPersonaje.setText("Descartar");
        cambiarDatos();
    }

    public void actualizarGrupo() {
        if (personajesElegidos.size() > 0)
            b_grupoPJ1.setIcon(new ImageIcon(iconoPJ(personajesElegidos.get(0))));
        else
            b_grupoPJ1.setIcon(new ImageIcon("assets/icono-0-vacio.png"));
        if (personajesElegidos.size() > 1)
            b_grupoPJ2.setIcon(new ImageIcon(iconoPJ(personajesElegidos.get(1))));
        else
            b_grupoPJ2.setIcon(new ImageIcon("assets/icono-0-vacio.png"));
        if (personajesElegidos.size() > 2)
            b_grupoPJ3.setIcon(new ImageIcon(iconoPJ(personajesElegidos.get(2))));
        else
            b_grupoPJ3.setIcon(new ImageIcon("assets/icono-0-vacio.png"));
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

    public void habilitarPJ() {
        if (index == 1)
            b_pj1.setEnabled(true);
        else if (index == 2)
            b_pj2.setEnabled(true);
        else if (index == 3)
            b_pj3.setEnabled(true);
        else if (index == 4)
            b_pj4.setEnabled(true);
        else
            b_pj5.setEnabled(true);
    }

    public void deshabilitarPJ() {
        if (index == 1)
            b_pj1.setEnabled(false);
        else if (index == 2)
            b_pj2.setEnabled(false);
        else if (index == 3)
            b_pj3.setEnabled(false);
        else if (index == 4)
            b_pj4.setEnabled(false);
        else
            b_pj5.setEnabled(false);
    }

    public void cambiarDatos() {
        String nombre;
        String tipo;
        String arma;
        String descripcion;

        // Obtenemos los objetos que nos interesa
        Personaje per = personajes.get(index - 1);
        Atributos atr = per.getAtributos();

        // Cargamos datos personaje
        nombre = per.getNombre();
        tipo = per.getTipo().name();
        arma = per.getArma().getNombre();
        descripcion = per.getDescripcion();

        if (tipo.equalsIgnoreCase("Guardian"))
            tipo = "Guardián";

        int longitudLinea = 47; // Esta cifra es porque a mi me cuadra bien, podría ser cualquier número que cuadrase
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
        l_fotoPJ.setIcon(new ImageIcon("assets/" + fotosPersonaje[(index - 1)]));
        l_nombre.setText(nombre);
        l_tipo.setText(tipo);
        l_arma.setText(arma);
        l_descripcion.setText(descripcionReconstruida.toString());

        // Plasmamos datos pantalla Atributos
        l_atrTitulo.setText("Atributos de " + per.getNombre());
        l_atrNivel.setText("Nivel: " + atr.getNivel());
        l_atrVit.setText("Vitalidad: " + atr.getVitalidad());
        l_atrFuerza.setText("Fuerza: " + atr.getFuerza());
        l_atrPoder.setText("Poder Mágico: " + atr.getPoderMagico());
        l_atrDestreza.setText("Destreza: " + atr.getDestreza());
        l_atrAgil.setText("Agilidad: " + atr.getAgilidad());
        l_atrDefFis.setText("Defensa Física: " + atr.getDefensaFis());
        l_atrDefMag.setText("Defensa Mágica: " + atr.getDefensaMag());

    }

    public void setIndexCancion(int indexCancion) {
        this.indexCancion = indexCancion;
    }

    public void setPm(PlaySound pm) {
        this.pm = pm;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setVentanaInicio(JFrame ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public void setVentanaNuevaPartida(JFrame ventanaNuevaPartida) {
        this.ventanaNuevaPartida = ventanaNuevaPartida;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("NuevaPartida");
        frame.setContentPane(new NuevaPartida().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
