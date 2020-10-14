package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Atributos;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

    private final String[] fotosPersonaje = { "pjs-1-guerrero.png", "pjs-2-mago.png", "pjs-3-arquero.png",
            "pjs-4-guardian.png", "pjs-5-asesino.png" };

    public NuevaPartida() {

        // TODO necesitamos un icono vacío (también abajo en el método actualizarGrupo())
        // Borrar
        /*
        b_grupoPJ1.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_grupoPJ2.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_grupoPJ3.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));*/

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
                    } else {
                        System.out.println("No se pueden añadir más de 3 personajes al grupo.");
                        JOptionPane.showMessageDialog(null,
                                "No se pueden seleccionar más de 3 personajes.",
                                "Error en la selección",
                                JOptionPane.ERROR_MESSAGE);
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
            b_grupoPJ1.setIcon(new ImageIcon("assets/icono-4-guardian.png")); // TODO necesitamos un icono vacío
        if (personajesElegidos.size() > 1)
            b_grupoPJ2.setIcon(new ImageIcon(iconoPJ(personajesElegidos.get(1))));
        else
            b_grupoPJ2.setIcon(new ImageIcon("assets/icono-4-guardian.png"));
        if (personajesElegidos.size() > 2)
            b_grupoPJ3.setIcon(new ImageIcon(iconoPJ(personajesElegidos.get(2))));
        else
            b_grupoPJ3.setIcon(new ImageIcon("assets/icono-4-guardian.png"));
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
