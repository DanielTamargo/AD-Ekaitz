package com.tamargo.ventanas;

import com.tamargo.LeerDatosBase;
import com.tamargo.misc.AdministradorRutasArchivos;
import com.tamargo.misc.PlaySound;
import com.tamargo.modelo.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
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
    private JButton b_siguienteTurno;
    private JButton b_objetos;
    private JTextPane textPaneRegistro;
    private JLabel l_fotoVS;

    private Grupo grupo;
    private PlaySound pm;
    private PlaySound pmPartida;
    private float volumen;
    String[] cancionesBatalla = AdministradorRutasArchivos.cancionesBatallas;
    String[] nombreSonidos = AdministradorRutasArchivos.nombreSonidos;
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

    private int turnos = 1;
    private int personajesVivosAlComienzoTurno = 0;
    private StringBuilder sbTextoRegistro = new StringBuilder();
    private ArrayList<String> registroNombres = new ArrayList<>();
    private ArrayList<String> registroDanyos = new ArrayList<>();
    private ArrayList<String> registroDerrotados = new ArrayList<>();
    private ArrayList<String> registroNivelesSubidos = new ArrayList<>();
    /*
    private ArrayList<String> registroPJ1 = new ArrayList<>();
    private ArrayList<String> registroPJ2 = new ArrayList<>();
    private ArrayList<String> registroPJ3 = new ArrayList<>();
    private ArrayList<String> registroEnem1 = new ArrayList<>();
    private ArrayList<String> registroEnem2 = new ArrayList<>();
    private ArrayList<String> registroEnem3 = new ArrayList<>();
    */

    private String nombreDatos;
    private String iconoDatos;
    private Atributos atributosDatos;
    private String armaDatos;

    private int controlarRegistroDerrotados = 1;

    public VentanaRonda() {

        l_fotoVS.setIcon(new ImageIcon("assets/icono-vs-1.png"));
        //generarGrupoPruebas();

        textPaneRegistro.setText("¡Comienza el primer turno!");


        b_siguienteTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b_siguienteTurno.getText().equalsIgnoreCase("Finalizar Ronda")) {
                    if (!partida.getFinalizada())
                        pm.stopSong();
                    ventanaPartida.setVisible(true);
                    ventanaRonda.dispose();
                } else {
                    turno();
                    actualizarDatos();
                    comprobarResultado();
                    plasmarRegistroTurno();
                    turnos++;
                }
            }
        });
        b_grupoPJ1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosPersonaje(0);
            }
        });
        b_grupoPJ2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosPersonaje(1);
            }
        });
        b_grupoPJ3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosPersonaje(2);
            }
        });
        b_enemigo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosEnemigo(0);
            }
        });
        b_enemigo2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosEnemigo(1);
            }
        });
        b_enemigo3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararVentanaDatosEnemigo(2);
            }
        });
    }

    public void lanzarVentanaDatos() {
        JFrame frame = new JFrame("Datos " + nombreDatos);
        VentanaRondaDatos vrd = new VentanaRondaDatos(nombreDatos, iconoDatos, atributosDatos, frame, armaDatos);
        frame.setContentPane(vrd.getPanel());
        vrd.setVentanaRonda(ventanaRonda);
        vrd.setVentanaRondaDatos(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        ventanaRonda.setEnabled(false);
    }

    public void prepararVentanaDatosPersonaje(int i) {
        nombreDatos = grupo.getPersonajes().get(i).getNombre();
        iconoDatos = grupo.getPersonajes().get(i).getImagen();
        atributosDatos = grupo.getPersonajes().get(i).getAtributos();
        armaDatos = grupo.getPersonajes().get(i).getArma().getNombre();
        lanzarVentanaDatos();
    }

    public void prepararVentanaDatosEnemigo(int i) {
        nombreDatos = enemigos.get(i).getNombre();
        iconoDatos = enemigos.get(i).getImagen();
        atributosDatos = enemigos.get(i).getAtributos();
        armaDatos = "";
        lanzarVentanaDatos();
    }

    public void comprobarResultado() {

        if (enemigosVivos.size() <= 0) {
            pm.stopSong();
            //pm = new PlaySound();
            pm.playSound(cancionesBatalla[2], true, volumen);

            grupo.rondaGanada();
            droppearObjeto();

            // TODO POSIBILIDAD DE DROPPEAR OBJETOS

            b_siguienteTurno.setText("Finalizar Ronda");
        } else if (personajesVivos.size() <= 0) {
            pm.stopSong();
            //pm = new PlaySound();
            pmPartida.playSound(cancionesBatalla[3], true, volumen);
            partida.setFinalizada(true);
            b_siguienteTurno.setText("Finalizar Ronda");
        }
    }

    public void plasmarRegistroTurno() {
        if (turnos > 0) {
            sbTextoRegistro.append(String.format("%0120d", 0).replace('0', '-'));
            sbTextoRegistro.append("\n");
        }
        sbTextoRegistro.append("TURNO ").append(turnos).append("\n\n");

        // Guardamos los turnos de los Personajes
        for (int j = 0; j < personajesVivosAlComienzoTurno; j++) {
            try {
                String dato = registroNombres.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");
        for (int j = 0; j < personajesVivosAlComienzoTurno; j++) {
            try {
                String dato = registroDanyos.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");
        for (int j = 0; j < personajesVivosAlComienzoTurno; j++) {
            try {
                String dato = registroDerrotados.get(j);
                if (!dato.equalsIgnoreCase("b"))
                    sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");
        for (int j = 0; j < personajesVivosAlComienzoTurno; j++) {
            try {
                String dato = registroNivelesSubidos.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }

        sbTextoRegistro.append("\n\n");

        // Guardamos los turnos del enemigo
        for (int j = personajesVivosAlComienzoTurno; j < registroNombres.size(); j++) {
            try {
                String dato = registroNombres.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");
        for (int j = personajesVivosAlComienzoTurno; j < registroDanyos.size(); j++) {
            try {
                String dato = registroDanyos.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");
        for (int j = personajesVivosAlComienzoTurno; j < registroDerrotados.size(); j++) {
            try {
                String dato = registroDerrotados.get(j);
                sbTextoRegistro.append(String.format("%-46s", dato));
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        sbTextoRegistro.append("\n");

        textPaneRegistro.setText(String.valueOf(sbTextoRegistro));

        // Reseteamos para poder plasmar el siguiente turno
        registroNombres.clear();
        registroDanyos.clear();
        registroDerrotados.clear();
        registroNivelesSubidos.clear();
        personajesVivosAlComienzoTurno = 0;
    }

    public void comenzarRonda() {
        generarEnemigos();
        cargarVidaPJsYPersonajesVivos();

        cargarDatosIniciales();

        actualizarIconos();
        actualizarDatos();
    }

    public void droppearObjeto() {
        // Calcular la probabilidad de que caiga un objeto, y en caso de caer, calcular cual podria ser
        int x = 20 + (grupo.getRondasGanadas() / 4); // <- probabilidad de que caiga
        if (x >= new Random().nextInt(100) + 1) {
            int rarezaArma = 1;
            int probArmaLegendaria = 1 + grupo.getRondasGanadas();
            int probArmaEpica = 5 + grupo.getRondasGanadas();
            int probArmaFuerte = 15 + grupo.getRondasGanadas();
            if (probArmaLegendaria >= new Random().nextInt(100) + 1)
                rarezaArma = 4;
            if (probArmaEpica >= new Random().nextInt(100) + 1 && rarezaArma == 1) {
                rarezaArma = 3;
            }
            if (probArmaFuerte >= new Random().nextInt(100) + 1 && rarezaArma == 1) {
                rarezaArma = 2;
            }

            ArrayList<Arma> armasDisponibles = new LeerDatosBase().leerArmasBase();
            ArrayList<Arma> armasDroppeables = new ArrayList<>();
            for (Arma a: armasDisponibles) {
                if (a.getRareza() == rarezaArma)
                    armasDroppeables.add(a);
            }
            Arma armaDroppeada = armasDroppeables.get(new Random().nextInt(armasDroppeables.size()));
            String rarezaNombre = switch (rarezaArma) {
                case 2 -> "rara";
                case 3 -> "épica";
                case 4 -> "legendaria";
                default -> "normal";
            };
            grupo.addArmaAlInventario(armaDroppeada);
            JButton okButton = new JButton("Ok");
            okButton.setFocusPainted(false);
            Object[] options = {okButton};
            final JOptionPane pane = new JOptionPane("¡Has encontrado " + armaDroppeada.getNombre() + "!\n¡Un arma de rareza " + rarezaNombre + "!\n" +
                    "Se ha añadido a tu inventario.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
            JDialog dialog = pane.createDialog("Nueva arma encontrada");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlaySound ps = new PlaySound();
                    ps.playSound(nombreSonidos[1], false, volumen);
                    dialog.dispose();
                }
            });
            dialog.setVisible(true);
        }

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
        controlarRegistroDerrotados = 1;
        if (enemigosVivos.size() > 0) {
            personajesVivosAlComienzoTurno = personajesVivos.size();
            for (Personaje p : personajesVivos) {
                System.out.println("Turno de " + p.getNombre());
                registroNombres.add("Turno de " + p.getNombre());
                turnoPersonaje(p);
                System.out.println();
            }
        }
        if (personajesVivos.size() > 0) {
            for (Enemigo e : enemigosVivos) {
                System.out.println("Turno de " + e.getNombre());
                registroNombres.add("Turno de " + e.getNombre());
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
            if (danyo <= -99999) {
                registroDanyos.add(per.getNombre() + " ha esquivado el ataque");
                System.out.println(per.getNombre() + " ha esquivado el ataque");
                danyo = 0;
            } else {
                if (danyo <= 0)
                    danyo = 1;
                registroDanyos.add("Daño a " + per.getNombre() + ": " + danyo);
                System.out.println("Daño a " + per.getNombre() + ": " + danyo);
            }
            vidaPersonajes.put(per, vidaPersonajes.get(per) - danyo);
            if (vidaPersonajes.get(per) < 0)
                vidaPersonajes.put(per, 0);

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
                if (controlarRegistroDerrotados > registroDerrotados.size() + 1) {
                    int j = registroDerrotados.size();
                    if (j < personajesVivosAlComienzoTurno) {
                        for (int i = 0; i < personajesVivosAlComienzoTurno - j; i++) {
                            registroDerrotados.add("b");
                        }
                    }
                    j = registroDerrotados.size();
                    for (int i = 0; i < (controlarRegistroDerrotados - j) - 1; i++) {
                        registroDerrotados.add("");
                    }
                }
                registroDerrotados.add("Ha derrotado a " + per.getNombre());
                System.out.println("Ha derrotado a " + per.getNombre());
            }
            controlarRegistroDerrotados++;

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
            if (danyo <= -99999) {
                registroDanyos.add(enem.getNombre() + " ha esquivado el ataque");
                System.out.println(enem.getNombre() + " ha esquivado el ataque");
                danyo = 0;
            } else {
                if (danyo <= 0)
                    danyo = 1;
                registroDanyos.add("Daño a " + enem.getNombre() + ": " + danyo);
                System.out.println("Daño a " + enem.getNombre() + ": " + danyo);
            }


            vidaEnemigos.put(enem, vidaEnemigos.get(enem) - danyo);
            if (vidaEnemigos.get(enem) < 0)
                vidaEnemigos.put(enem, 0);


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
                if (controlarRegistroDerrotados > registroDerrotados.size() + 1) {
                    int j = registroDerrotados.size();
                    for (int i = 0; i < (controlarRegistroDerrotados - j) - 1; i++) {
                        registroDerrotados.add("");
                    }
                }
                registroDerrotados.add("Ha derrotado a " + enem.getNombre());
                System.out.println("Ha derrotado a " + enem.getNombre());
                int nivelesSubidos;
                int k = 1;
                for (Personaje p: personajesVivos) {
                    nivelesSubidos = p.getNivelesSubidos();
                    p.addExperienciaConseguida(enem.getExperiencia());
                    //System.out.println(p.getExperienciaConseguida());
                    if (p.getNivelesSubidos() > nivelesSubidos) {
                        if (k > registroNivelesSubidos.size() + 1) {
                            int j = registroNivelesSubidos.size();
                            for (int i = 0; i < (k - j) - 1; i++) {
                                registroNivelesSubidos.add("");
                            }
                        }
                        registroNivelesSubidos.add("¡" + p.getNombre() + " ha subido al nivel " + p.getAtributos().getNivel() + "!");
                        System.out.println("\t¡" + p.getNombre() + " ha subido al nivel " + p.getAtributos().getNivel() + "!");
                    }
                    k++;
                }
            }
            controlarRegistroDerrotados++;
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
        while (enemigos.size() < 3) {
            ArrayList<Enemigo> enemigosDisponibles =  new LeerDatosBase().leerEnemigosBase();

            Enemigo enem = enemigosDisponibles.get(new Random().nextInt(enemigosDisponibles.size()));
            if (enem.getAtributos().getNivel() <= rondas + 1) {
                enemigos.add(enem);
            }
        }

        int vit;
        int fuerza;
        int poderMag;
        int destreza;
        int agilidad;
        int defFis;
        int defMag;
        int exp;

        // Balanceamos los enemigos que sean demasiado débiles para la ronda en la que estamos
        for (Enemigo enem: enemigos) {
            Atributos atr = enem.getAtributos();
            if (atr.getNivel() < rondas) {
                vit = atr.getVitalidad();
                fuerza = atr.getFuerza();
                poderMag = atr.getPoderMagico();
                destreza = atr.getDestreza();
                agilidad = atr.getAgilidad();
                defFis = atr.getDefensaFis();
                defMag = atr.getDefensaMag();
                exp = enem.getExperiencia();

                for (int i = 0; i < (rondas - atr.getNivel()); i++) {

                    atr.setNivel(atr.getNivel() + 1);
                    atr.setVitalidad(atr.getVitalidad() + vit);
                    atr.setFuerza(atr.getFuerza() + fuerza);
                    atr.setPoderMagico(atr.getPoderMagico() + poderMag);
                    atr.setDestreza(atr.getDestreza() + destreza);
                    atr.setAgilidad(atr.getAgilidad() + agilidad);
                    atr.setDefensaFis(atr.getDefensaFis() + defFis);
                    atr.setDefensaMag(atr.getDefensaMag() + defMag);
                    enem.setExperiencia(enem.getExperiencia() + (exp / 2));
                }
            }
        }

        enemigosVivos.addAll(enemigos);
        for (Enemigo e: enemigos) {
            vidaEnemigos.put(e, e.calcularVida());
        }

        int indexCancionBatalla = 0; // 0 = normal, 1 = boss
        int diosEncontrado = 0;
        for (Enemigo e: enemigosVivos) {
            if (e.getId() == 11)
                diosEncontrado++;
        }
        if (diosEncontrado > 0) {
            if (diosEncontrado == 1)
                textPaneRegistro.setText("¡¡Pero qué!!\n¡Te has encontrado con un Dios del Cristal Perdido!\nTen cuidado...\n\n¡Comienza el primer turno!");
            else if (diosEncontrado == 2)
                textPaneRegistro.setText("¡¡Pero qué!!\n¡¡No puede ser!\n¡Te has encontrado con dos Dioses del Cristal Perdido!\nTen cuidado... Mucho cuidado...\n\n¡Comienza el primer turno!");
            else
                textPaneRegistro.setText("¡¡Pero qué!!\n¡¡No puede ser!\n¡¡¡ESTO NO TIENE NINGÚN SENTIDO!!!\n¡Te has encontrado con TRES MALDITOS Dioses del Cristal Perdido!\nIntentas huir pero no puedes...\nTendrás que luchar.\n\n¡Comienza el primer turno!");
            indexCancionBatalla = 1;
        }

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

    public void setPmPartida(PlaySound pmPartida) {
        this.pmPartida = pmPartida;
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
