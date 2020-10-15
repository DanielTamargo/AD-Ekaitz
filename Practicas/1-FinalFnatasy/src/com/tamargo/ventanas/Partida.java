package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.modelo.Arma;
import com.tamargo.modelo.Atributos;
import com.tamargo.modelo.Grupo;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.util.ArrayList;

public class Partida {

    private JFrame ventanaPartida;
    private JFrame ventanaInicio;

    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JPanel tabJuego;
    private JPanel tabPersonajes;
    private JPanel tabMisc;
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
    private JButton button1;
    private JLabel l_armaRareza;
    private JLabel l_armaAtaqFis;
    private JLabel l_armaAtaqMag;
    private JLabel l_armaDefFis;
    private JLabel l_armaDefMag;
    private JComboBox<Arma> comboBoxArmas;

    private Grupo grupo;

    private ArrayList<Arma> armasDisponibles = new ArrayList<>();

    private final String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;
    private float volumen = -40;

    private final String[] fotosPersonaje = AdministradorRutasArchivos.fotosPersonaje;
    private int index = 1;

    public Partida() {

        // TODO falta que el grupo tenga una serie de armas, cuando le pones un arma al personaje la eliminas y añades la que le has quitado al personaje
        // TODO en el listado de armas para elegir tenemos que sacar la primera opción el arma que lleva el personaje, y luego añadir las armas del inventario del grupo


        // Borrar
        ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();
        grupo = new Grupo(1, personajes, 1);

        cambiarDatos();
        actualizarListaArmasDisponibles();


    }

    public void actualizarListaArmasDisponibles() {
        armasDisponibles.add(grupo.getPersonajes().get(index - 1).getArma());
        //TODO aquí añadir las armas del inventario

        // borrar
        armasDisponibles.add(new LeerDatosBase().leerArmasBase().get(3));

        DefaultComboBoxModel<Arma> modelo = new DefaultComboBoxModel<>();

        for (Arma a: armasDisponibles) {
            modelo.addElement(a);
        }

        comboBoxArmas.setModel(modelo);

    }

    public void cambiarDatosArma() {

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
        l_atrPuntosDisponibles.setText("Atributos de " + per.getNombre());
        l_atrNivel.setText("Nivel: " + atr.getNivel());
        l_atrVit.setText("Vitalidad: " + atr.getVitalidad());
        l_atrFuerza.setText("Fuerza: " + atr.getFuerza());
        l_atrPoder.setText("Poder Mágico: " + atr.getPoderMagico());
        l_atrDestreza.setText("Destreza: " + atr.getDestreza());
        l_atrAgil.setText("Agilidad: " + atr.getAgilidad());
        l_atrDefFis.setText("Defensa Física: " + atr.getDefensaFis());
        l_atrDefMag.setText("Defensa Mágica: " + atr.getDefensaMag());

    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
        frame.setContentPane(new Partida().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
