package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VentanaRonda {

    private JFrame ventanaRonda;
    private JFrame ventanaPartida;

    private JPanel panel;
    private JButton b_grupoPJ1;
    private JButton b_grupoPJ2;
    private JButton b_grupoPJ3;
    private JButton b_enemigo1;
    private JButton b_enemigo2;
    private JButton b_enemigo3;
    private JLabel l_pj1nombre;
    private JLabel l_pj1damage;
    private JLabel l_pj1vida;
    private JLabel l_pj2nombre;
    private JLabel l_pj2damage;
    private JLabel l_pj2vida;
    private JLabel l_pj3damage;
    private JLabel l_pj3nombre;
    private JLabel l_pj3vida;
    private JLabel l_enem1nombre;
    private JLabel l_enem1damage;
    private JLabel l_enem1vida;
    private JLabel l_enem2nombre;
    private JLabel l_enem2damage;
    private JLabel l_enem2vida;
    private JLabel l_enem3damage;
    private JLabel l_enem3nombre;
    private JLabel l_enem3vida;
    private JTextPane textPaneRegistro;
    private JButton b_siguienteTurno;
    private JButton b_objetos;

    private Grupo grupo;
    private PlaySound pm;
    private float volumen;
    private Partida partida;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Enemigo> enemigosVivos = new ArrayList<>();
    private Map<Enemigo, Integer> vidaEnemigos = new HashMap<Enemigo, Integer>();
    private ArrayList<Personaje> personajesVivos = new ArrayList<>();
    private Map<Personaje, Integer> vidaPersonajes = new HashMap<Personaje, Integer>();

    private int vidaMaxPj1 = 0;
    private int vidaMaxPj2 = 0;
    private int vidaMaxPj3 = 0;
    private int vidaPj1 = 0;
    private int vidaPj2 = 0;
    private int vidaPj3 = 0;

    private int danyoPj1 = 0;
    private int danyoPj2 = 0;
    private int danyoPj3 = 0;

    private int vidaMaxEnem1 = 0;
    private int vidaMaxEnem2 = 0;
    private int vidaMaxEnem3 = 0;
    private int vidaEnem1 = 0;
    private int vidaEnem2 = 0;
    private int vidaEnem3 = 0;

    private int danyoEnem1 = 0;
    private int danyoEnem2 = 0;
    private int danyoEnem3 = 0;

    public VentanaRonda() {

        // TODO borrar esto ya que el grupo ya vendrá cargado desde la ventana anterior
        //generarGrupoPruebas();

        b_siguienteTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turno();
                actualizarDatos();
                comprobarResultado();
            }
        });
    }

    public void comprobarResultado() {
        // TODO COMPROBAR RESULTADO
    }

    public void comenzarRonda() {
        generarEnemigos();
        cargarVidaPJsYPersonajesVivos();

        cargarDatosIniciales();

        actualizarIconos();
        actualizarDatos();
    }

    public void cargarDatosIniciales() {
        vidaPj1 = grupo.getPersonajes().get(0).calcularVida();
        vidaPj2 = grupo.getPersonajes().get(1).calcularVida();
        vidaPj3 = grupo.getPersonajes().get(2).calcularVida();

        vidaMaxPj1 = vidaPj1;
        vidaMaxPj2 = vidaPj2;
        vidaMaxPj3 = vidaPj3;

        vidaEnem1 = enemigos.get(0).calcularVida();
        vidaEnem2 = enemigos.get(1).calcularVida();
        vidaEnem3 = enemigos.get(2).calcularVida();

        vidaMaxEnem1 = vidaEnem1;
        vidaMaxEnem2 = vidaEnem2;
        vidaMaxEnem3 = vidaEnem3;
    }

    public void actualizarDatos() {
        int num = 0;
        for (Personaje p: grupo.getPersonajes()) {
            if (num == 0) {
                l_pj1nombre.setText(p.getNombre());
                l_pj1vida.setText("Vida: " + vidaPj1 + "/" + vidaMaxPj1);
                l_pj1damage.setText("Daño total: " + danyoPj1);
            } else if (num == 1) {
                l_pj2nombre.setText(p.getNombre());
                l_pj2vida.setText("Vida: " + vidaPj2 + "/" + vidaMaxPj2);
                l_pj2damage.setText("Daño total: " + danyoPj2);
            } else {
                l_pj3nombre.setText(p.getNombre());
                l_pj3vida.setText("Vida: " + vidaPj3 + "/" + vidaMaxPj3);
                l_pj3damage.setText("Daño total: " + danyoPj3);
            }
            num++;
        }

        num = 0;
        for (Enemigo e: enemigos) {
            if (num == 0) {
                l_enem1nombre.setText(e.getNombre());
                l_enem1vida.setText("Vida: " + vidaEnem1 + "/" + vidaMaxEnem1);
                l_enem1damage.setText("Daño total: " + danyoEnem1);
            } else if (num == 1) {
                l_enem2nombre.setText(e.getNombre());
                l_enem2vida.setText("Vida: " + vidaEnem2 + "/" + vidaMaxEnem2);
                l_enem2damage.setText("Daño total: " + danyoEnem2);
            } else {
                l_enem3nombre.setText(e.getNombre());
                l_enem3vida.setText("Vida: " + vidaEnem3 + "/" + vidaMaxEnem3);
                l_enem3damage.setText("Daño total: " + danyoEnem3);
            }
            num++;
        }
    }

    public void turno() {
        // TODO HACER ESTO CON TODOS LOS PERSONAJES VIVOS Y LUEGO TURNOENEMIGO CON TODOS LOS ENEMIGOS VIVOS
        // TODO AÑADIR ARRAYLIST PERSONAJESVIVOS Y METER AHI LOS PERSONAJES
        if (enemigosVivos.size() > 0) {
            for (Personaje p : personajesVivos) {
                System.out.println("Turno de " + p.getNombre());
                turnoPersonaje(p);
                System.out.println();
            }
        }
        if (personajesVivos.size() > 0) {
            for (Enemigo e : enemigosVivos) {
                System.out.println("Turno de " + e.getNombre());
                turnoEnemigo(e);
                System.out.println();
            }
        }

    }

    public void turnoEnemigo(Enemigo enem) {
        if (personajesVivos.size() > 0) {

            // Los enemigos golpean random
            Personaje per = personajesVivos.get(new Random().nextInt(personajesVivos.size()));

            int danyo = per.danyoTotalRecibido(enem.calcularDanyoFis(), enem.calcularDanyoMag());
            if (danyo <= 0)
                danyo = 1;

            System.out.println("Daño a " + per.getNombre() + ": " + danyo);

            vidaPersonajes.put(per, vidaPersonajes.get(per) - danyo);
            if (vidaPersonajes.get(per) < 0)
                vidaPersonajes.put(per, 0);

            // TODO REGISTRO DEL DAÑO Y DE LA VIDA QUE TIENE EL PERSONAJE

            // Sumamos el daño realizado
            for (int i = 0; i < enemigos.size(); i++) {
                if (enemigos.get(i) == enem) {
                    if (i == 0)
                        danyoEnem1 += danyo;
                    else if (i == 1)
                        danyoEnem2 += danyo;
                    else if (i == 2)
                        danyoEnem3 += danyo;
                }
            }

            // Bajamos la vida al jugador herido
            for (int i = 0; i < grupo.getPersonajes().size(); i++) {
                if (grupo.getPersonajes().get(i) == per) {
                    if (i == 0)
                        vidaPj1 = vidaPersonajes.get(grupo.getPersonajes().get(0));
                    else if (i == 1)
                        vidaPj2 = vidaPersonajes.get(grupo.getPersonajes().get(1));
                    else if (i == 2)
                        vidaPj3 = vidaPersonajes.get(grupo.getPersonajes().get(2));
                }
            }

            // Comprobamos si el jugador herido ha muerto
            if (vidaPersonajes.get(per) <= 0) {
                for (int i = 0; i < grupo.getPersonajes().size(); i++) {
                    if (grupo.getPersonajes().get(i) == per) {
                        if (i == 0)
                            b_grupoPJ1.setEnabled(false);
                        else if (i == 1)
                            b_grupoPJ2.setEnabled(false);
                        else if (i == 2)
                            b_grupoPJ3.setEnabled(false);
                    }
                }
                personajesVivos.remove(per);
                System.out.println("Ha derrotado a " + per.getNombre());
            }

        } else {
            System.out.println("Ya no quedan personajes vivos!");
        }
    }

    public void turnoPersonaje(Personaje per) {

        if (enemigosVivos.size() > 0) {
            Enemigo enem = enemigosVivos.get(0);

            if (per.getAtributos().getFuerza() + per.getArma().getAtaqueFis() >= per.getAtributos().getPoderMagico() + per.getArma().getAtaqueMag()) {
                int defFisMin = 11111;
                for (Enemigo e : enemigosVivos) {
                    if (e.getAtributos().getDefensaFis() < defFisMin) {
                        defFisMin = e.getAtributos().getDefensaFis();
                        enem = e;
                    }
                }
            } else {
                int defMagMin = 11111;
                for (Enemigo e : enemigosVivos) {
                    if (e.getAtributos().getDefensaMag() < defMagMin) {
                        defMagMin = e.getAtributos().getDefensaMag();
                        enem = e;
                    }
                }
            }

            int danyo = enem.danyoTotalRecibido(per.calcularDanyoFis(), per.calcularDanyoMag());
            if (danyo <= 0)
                danyo = 1;

            System.out.println("Daño a " + enem.getNombre() + ": " + danyo);

            vidaEnemigos.put(enem, vidaEnemigos.get(enem) - danyo);
            if (vidaEnemigos.get(enem) < 0)
                vidaEnemigos.put(enem, 0);

            // TODO REGISTRO DEL DAÑO Y DE LA VIDA QUE TIENE EL ENEMIGO

            // Sumamos el daño realizado
            for (int i = 0; i < grupo.getPersonajes().size(); i++) {
                if (grupo.getPersonajes().get(i) == per) {
                    if (i == 0)
                        danyoPj1 += danyo;
                    else if (i == 1)
                        danyoPj2 += danyo;
                    else if (i == 2)
                        danyoPj3 += danyo;
                }
            }

            // Bajamos vida al enemigo herido
            for (int i = 0; i < enemigos.size(); i++) {
                if (enemigos.get(i) == enem) {
                    if (i == 0)
                        vidaEnem1 = vidaEnemigos.get(enemigos.get(0));
                    else if (i == 1)
                        vidaEnem2 = vidaEnemigos.get(enemigos.get(1));
                    else if (i == 2)
                        vidaEnem3 = vidaEnemigos.get(enemigos.get(2));
                }
            }

            // Comprobamos si el enemigo herido ha muerto
            if (vidaEnemigos.get(enem) <= 0) {
                for (int i = 0; i < enemigos.size(); i++) {
                    if (enemigos.get(i) == enem) {
                        if (i == 0)
                            b_enemigo1.setEnabled(false);
                        else if (i == 1)
                            b_enemigo2.setEnabled(false);
                        else if (i == 2)
                            b_enemigo3.setEnabled(false);
                    }
                }
                enemigosVivos.remove(enem);
                partida.addEnemigoDerrotado(enem);
                System.out.println("Ha derrotado a " + enem.getNombre());
            }

        } else {
            System.out.println("Ya no quedan enemigos vivos!");
        }
    }


    public void actualizarIconos() {
        b_grupoPJ1.setIcon(new ImageIcon("assets/" + grupo.getPersonajes().get(0).getImagen()));
        b_grupoPJ2.setIcon(new ImageIcon("assets/" + grupo.getPersonajes().get(1).getImagen()));
        b_grupoPJ3.setIcon(new ImageIcon("assets/" + grupo.getPersonajes().get(2).getImagen()));

        b_enemigo1.setIcon(new ImageIcon("assets/" + enemigos.get(0).getImagen()));
        b_enemigo2.setIcon(new ImageIcon("assets/" + enemigos.get(1).getImagen()));
        b_enemigo3.setIcon(new ImageIcon("assets/" + enemigos.get(2).getImagen()));
    }

    public void generarEnemigos() {
        //TODO importante calcular decentemente los enemigos

        /*
        int nivelPJ1 = 1;
        int nivelPJ2 = 1;
        int nivelPJ3 = 1;

        nivelPJ1 = grupo.getPersonajes().get(0).getAtributos().getNivel();
        nivelPJ2 = grupo.getPersonajes().get(1).getAtributos().getNivel();
        nivelPJ3 = grupo.getPersonajes().get(2).getAtributos().getNivel();
        int nivelGrupo = (nivelPJ1 + nivelPJ2 + nivelPJ3) / 3;
         */

        // Recogemos los enemigos en base a la ronda en la que estamos y el nivel del enemigo
        int rondas = grupo.getRondasGanadas() + 1;
        ArrayList<Enemigo> enemigosDisponibles =  new LeerDatosBase().leerEnemigosBase();
        while (enemigos.size() < 3) {
            Enemigo enem = enemigosDisponibles.get(new Random().nextInt(enemigosDisponibles.size()));
            if (enem.getAtributos().getNivel() <= rondas + 1) {
                enemigos.add(enem);
            }
        }

        // Balanceamos los enemigos que sean demasiado débiles para la ronda en la que estamos
        for (Enemigo enem: enemigos) {
            if (enem.getAtributos().getNivel() < rondas) {
                for (int i = 0; i < (rondas - enem.getAtributos().getNivel()); i++) {
                    Atributos atr = enem.getAtributos();
                    atr.setNivel(atr.getNivel() + 1);
                    atr.setVitalidad(atr.getVitalidad() * 2);
                    atr.setFuerza(atr.getFuerza() * 2);
                    atr.setPoderMagico(atr.getPoderMagico() * 2);
                    atr.setDestreza(atr.getDestreza() * 2);
                    atr.setAgilidad(atr.getAgilidad() * 2);
                    atr.setDefensaFis(atr.getDefensaFis() * 2);
                    atr.setDefensaMag(atr.getDefensaMag() * 2);
                }
            }
        }

        enemigosVivos.addAll(enemigos);
        for (Enemigo e: enemigos) {
            vidaEnemigos.put(e, e.calcularVida());
        }

        int indexCancionBatalla = 0; // 0 = normal, 1 = boss
        if (enemigosVivos.contains(enemigosDisponibles.get(enemigosDisponibles.size() - 1))) {
            indexCancionBatalla = 1;
        }
        String[] cancionesBatalla = AdministradorRutasArchivos.cancionesBatallas;
        pm = new PlaySound();
        pm.playSound(cancionesBatalla[indexCancionBatalla], true, volumen);

    }

    public void cargarVidaPJsYPersonajesVivos() {
        personajesVivos.addAll(grupo.getPersonajes());
        for (Personaje p: personajesVivos) {
            vidaPersonajes.put(p, p.calcularVida());
        }
    }

    public void generarGrupoPruebas() {
        ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();
        ArrayList<Personaje> personajesGrupo = new ArrayList<>();

        personajesGrupo.add(personajes.get(0)); personajesGrupo.add(personajes.get(1)); personajesGrupo.add(personajes.get(3));
        grupo = new Grupo(1, personajesGrupo, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaRonda");
        frame.setContentPane(new VentanaRonda().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
        grupo = partida.getGrupo();
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setVentanaRonda(JFrame ventanaRonda) {
        this.ventanaRonda = ventanaRonda;
    }

    public void setVentanaPartida(JFrame ventanaPartida) {
        this.ventanaPartida = ventanaPartida;
    }
}
