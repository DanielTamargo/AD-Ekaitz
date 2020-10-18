package com.tamargo.misc;

import com.tamargo.LeerDatosBase;
import com.tamargo.modelo.Arma;
import com.tamargo.modelo.Grupo;
import com.tamargo.modelo.Partida;
import com.tamargo.modelo.Personaje;
import com.tamargo.playlist.PlayPlaylist;
import com.tamargo.playlist.PlayPlaylistManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Pruebas {

    public static void main(String[] args) {

        ArrayList<Partida> listaPartidas1 = new LeerDatosBase().leerListaPartidas().getLista();
        System.out.println(listaPartidas1.size());
        Map<LocalDateTime, Partida> listaOrdenada = new TreeMap<LocalDateTime, Partida>();
        for (Partida p: listaPartidas1) {
            System.out.println(p);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(p.getFecha(), formatter);
            listaOrdenada.put(dateTime, p);
        }

        System.out.println();

        listaPartidas1.clear();
        listaPartidas1.addAll(listaOrdenada.values());
        Collections.reverse(listaPartidas1);

        for (Partida p: listaPartidas1) {
            System.out.println(p);
        }


/*
        ArrayList<Personaje> personajes = new LeerDatosBase().leerPersonajesBase();
        personajes.remove(0); personajes.remove(1);

        Grupo grupo = new Grupo(1, personajes, 1, new ArrayList<Arma>());
        Partida partida = new Partida(1, 155, true, grupo, new ArrayList<>(), new ArrayList<>());
        boolean anyadida = false;
        ArrayList<Partida> listaPartidas1 = new LeerDatosBase().leerListaPartidas().getLista();
        ArrayList<Partida> listaPartidas2 = new ArrayList<>();

        for (Partida p: listaPartidas1) {
            System.out.println(p);
            if (p.getId() == partida.getId()) {
                listaPartidas2.add(partida);
            } else {
                listaPartidas2.add(p);
            }
        }
        System.out.println();
        for (Partida p: listaPartidas2) {
            System.out.println(p);
        }
*/
/*
        JOptionPane jop = new JOptionPane();
        JLabel label = new JLabel("¡Tienes puntos para subir! ¡Comprueba tu grupo en la pestaña personajes!");
        label.setRequestFocusEnabled(true);
        jop.setRequestFocusEnabled(false);
        jop.showMessageDialog(null, label, "Puntos Disponibles",
                JOptionPane.INFORMATION_MESSAGE);

 */
/*
        JButton okButton = new JButton("Entendido");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane("¡Tienes puntos para subir!\n" +
                "¡Comprueba tu grupo en la pestaña personajes!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog("Puntos Disponibles");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.setVisible(true);

 */



        /*
        JOptionPane jop = new JOptionPane();
                            jop.setRequestFocusEnabled(false);
                            jop.showMessageDialog(null, "¡Tienes puntos para subir!\n" +
                                    "¡Comprueba tu grupo en la pestaña personajes!", "Puntos Disponibles",
                                    JOptionPane.INFORMATION_MESSAGE);
         */
/*
        System.out.println();
        String danyo = "Daño a Dios del Cristal Olvidado: 99999";
        System.out.println(danyo.length());

 */

        /*
        String[] canciones = AdministradorRutasArchivos.canciones;

        PlaySound pm = new PlaySound();
        pm.playSound(canciones[0], true, -40);
        JOptionPane.showMessageDialog(null, "Parar", "Parar", JOptionPane.INFORMATION_MESSAGE);
        pm.stopSong();
        JOptionPane.showMessageDialog(null, "Reanudar", "Reanudar", JOptionPane.INFORMATION_MESSAGE);
        pm.resumeSong();
        JOptionPane.showMessageDialog(null, "Terminar", "Terminar", JOptionPane.INFORMATION_MESSAGE);
*/

/*
        String[] playlistLista = AdministradorRutasArchivos.playlistRPG;

        PlayPlaylistManager playlist = new PlayPlaylistManager(playlistLista, 1, -40);
        playlist.start();

        JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);

        playlist.stop(); //<- deprecado pero para este caso en concreto funciona*/

    }

}
