package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.Arma;
import com.tamargo.modelo.Atributos;
import com.tamargo.modelo.Grupo;
import com.tamargo.modelo.Personaje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private int vitalidad;
    private int fuerza;
    private int poder;
    private int destreza;
    private int agilidad;
    private int defensaFis;
    private int defensaMag;

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

        iconosGrupo();

        cambiarDatos();
        actualizarListaArmasDisponibles();
        cambiarDatosArma();

        b_grupoPJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 1;
                cambiarDatos();
                actualizarListaArmasDisponibles();
            }
        });
        b_grupoPJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 2;
                cambiarDatos();
                actualizarListaArmasDisponibles();
            }
        });
        b_grupoPJ3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = 3;
                cambiarDatos();
                actualizarListaArmasDisponibles();
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
                vitalidad++;
                b_restarVit.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarVit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vitalidad--;
                if (vitalidad == grupo.getPersonajes().get(index - 1).getAtributos().getVitalidad())
                    b_restarVit.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarFuerza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fuerza++;
                b_restarFuerza.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarFuerza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fuerza--;
                if (fuerza == grupo.getPersonajes().get(index - 1).getAtributos().getFuerza())
                    b_restarFuerza.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarPoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poder++;
                b_restarPoder.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarPoder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poder--;
                if (poder == grupo.getPersonajes().get(index - 1).getAtributos().getPoderMagico())
                    b_restarPoder.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarDestreza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destreza++;
                b_restarDestreza.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarDestreza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destreza--;
                if (destreza == grupo.getPersonajes().get(index - 1).getAtributos().getDestreza())
                    b_restarDestreza.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarAgilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agilidad++;
                b_restarAgilidad.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarAgilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agilidad--;
                if (agilidad == grupo.getPersonajes().get(index - 1).getAtributos().getAgilidad())
                    b_restarAgilidad.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarDefFis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defensaFis++;
                b_restarDefFis.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarDefFis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defensaFis--;
                if (defensaFis == grupo.getPersonajes().get(index - 1).getAtributos().getDefensaFis())
                    b_restarDefFis.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_sumarDefMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defensaMag++;
                b_restarDefMag.setEnabled(true);
                cambiarPuntosAtributos();
            }
        });
        b_restarDefMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defensaMag--;
                if (defensaMag == grupo.getPersonajes().get(index - 1).getAtributos().getDefensaMag())
                    b_restarDefMag.setEnabled(false);
                cambiarPuntosAtributos();
            }
        });
        b_cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[2], false, volumen + 20);

                comboBoxArmas.setSelectedIndex(0);
                cambiarDatos();
            }
        });
        b_confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySound ps = new PlaySound();
                ps.playSound(nombreSonidos[0], false, volumen);

                Personaje per = grupo.getPersonajes().get(index - 1);
                per.setArma((Arma) comboBoxArmas.getSelectedItem());

                per.getAtributos().setVitalidad(vitalidad);
                per.getAtributos().setFuerza(fuerza);
                per.getAtributos().setPoderMagico(poder);
                per.getAtributos().setDestreza(destreza);
                per.getAtributos().setAgilidad(agilidad);
                per.getAtributos().setDefensaFis(defensaFis);
                per.getAtributos().setDefensaMag(defensaMag);

                cambiarDatos();
                cambiarDatosArma();
                actualizarListaArmasDisponibles();
            }
        });
    }

    public void actualizarListaArmasDisponibles() {

        // Vaciamos el combobox por si ha cambiado de personaje seleccionado
        comboBoxArmas.removeAllItems();

        // Reseteamos el Array
        armasDisponibles = new ArrayList<>();

        armasDisponibles.add(grupo.getPersonajes().get(index - 1).getArma());

        // TODO borrar esta línea y decomentar la de abajo cuando los grupos ya tengan inventario
        armasDisponibles.add(new LeerDatosBase().leerArmasBase().get(new LeerDatosBase().leerArmasBase().size() - 1));
        armasDisponibles.add(new LeerDatosBase().leerArmasBase().get(7));
        armasDisponibles.add(new LeerDatosBase().leerArmasBase().get(15));
        //armasDisponibles.addAll(grupo.getInventario());

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

        // Guardamos los atributos en las variables
        vitalidad = atr.getVitalidad();
        fuerza = atr.getFuerza();
        poder = atr.getPoderMagico();
        destreza = atr.getDestreza();
        agilidad = atr.getAgilidad();
        defensaFis = atr.getDefensaFis();
        defensaMag = atr.getDefensaMag();

        // TODO borrar esta línea después de implementar los puntos disponibles
        l_atrPuntosDisponibles.setText("Atributos de " + per.getNombre());
        l_atrNivel.setText("Nivel: " + atr.getNivel());

        // Plasmamos datos pantalla Atributos
        cambiarPuntosAtributos();

        // Deshabilitamos los botones de restar puesto que hemos recargado los atributos
        deshabilitarBotonesRestar();
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
        // TODO aún queda implementar esta línea con los puntos disponibles
        // l_atrPuntosDisponibles.setText("Atributos de " + per.getNombre());
        l_atrVit.setText("Vitalidad: " + vitalidad);
        l_atrFuerza.setText("Fuerza: " + fuerza);
        l_atrPoder.setText("Poder Mágico: " + poder);
        l_atrDestreza.setText("Destreza: " + destreza);
        l_atrAgil.setText("Agilidad: " + agilidad);
        l_atrDefFis.setText("Defensa Física: " + defensaFis);
        l_atrDefMag.setText("Defensa Mágica: " + defensaMag);

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
