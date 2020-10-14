package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NuevaPartida {
    private JFrame ventanaNuevaPartida;

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
    private JButton b_comenzarPartida;


    private ArrayList<Personaje> personajes = new ArrayList<>();


    public NuevaPartida() {

        // Borrar
        b_grupoPJ1.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_grupoPJ2.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_grupoPJ3.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));

        // Cargamos los personajes
        personajes = new LeerDatosBase().leerPersonajesBase();

        // Iconos botones
        b_pj1.setIcon(new ImageIcon("assets/icono-1-guerrero.png"));
        b_pj2.setIcon(new ImageIcon("assets/icono-2-mago.png"));
        b_pj3.setIcon(new ImageIcon("assets/icono-3-arquero.png"));
        b_pj4.setIcon(new ImageIcon("assets/icono-4-guardian.png"));
        b_pj5.setIcon(new ImageIcon("assets/icono-5-asesino.png"));

        // Preparamos los datos del primer personaje (PJ seleccionado de forma predeterminada)
        cambiarDatos(1);

        b_pj1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(1);
            }
        });
        b_pj2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(2);
            }
        });
        b_pj3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(3);
            }
        });
        b_pj4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(4);
            }
        });
        b_pj5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos(5);
            }
        });
    }

    public void cambiarDatos(int index) {
        String foto;
        String nombre;
        String tipo;
        String arma;
        String descripcion;

        if (index == 2) { // PJ 2 - Mago
            foto = "assets/pjs-2-mago.png";
            nombre = personajes.get(1).getNombre();
            tipo = personajes.get(1).getTipo().name();
            arma = personajes.get(1).getArma().getNombre();
            descripcion = personajes.get(1).getDescripcion();
        }
        else if (index == 3) { // PJ 3 - Arquero
            foto = "assets/pjs-3-arquero.png";
            nombre = personajes.get(2).getNombre();
            tipo = personajes.get(2).getTipo().name();
            arma = personajes.get(2).getArma().getNombre();
            descripcion = personajes.get(2).getDescripcion();
        }
        else if (index == 4) { // PJ 4 - Guardian
            foto = "assets/pjs-4-guardian.png";
            nombre = personajes.get(3).getNombre();
            tipo = personajes.get(3).getTipo().name();
            arma = personajes.get(3).getArma().getNombre();
            descripcion = personajes.get(3).getDescripcion();
        }
        else if (index == 5) { // PJ 5 - Asesino
            foto = "assets/pjs-5-asesino.png";
            nombre = personajes.get(4).getNombre();
            tipo = personajes.get(4).getTipo().name();
            arma = personajes.get(4).getArma().getNombre();
            descripcion = personajes.get(4).getDescripcion();
        } else { // PJ 1 - Guerrero
            foto = "assets/pjs-1-guerrero.png";
            nombre = personajes.get(0).getNombre();
            tipo = personajes.get(0).getTipo().name();
            arma = personajes.get(0).getArma().getNombre();
            descripcion = personajes.get(0).getDescripcion();
        }

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

        l_fotoPJ.setIcon(new ImageIcon(foto));
        l_nombre.setText(nombre);
        l_tipo.setText(tipo);
        l_arma.setText(arma);
        l_descripcion.setText(descripcionReconstruida.toString());

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
