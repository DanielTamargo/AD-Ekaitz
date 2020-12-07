package com.tamargo.playlist;

import com.tamargo.misc.AdministradorRutasArchivos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPlaylist {
    private JButton b_nextSong;
    private JButton b_previousSoung;
    private JPanel panel;
    private JButton buttonParar;
    private JLabel l_cancion;

    private String[] playlistRPG = AdministradorRutasArchivos.playlistRPG;
    private String[] playlistAgresivo = AdministradorRutasArchivos.playlistAgresivo;
    private PlayPlaylistManager playlist;

    public TestPlaylist() {


        playlist = new PlayPlaylistManager(playlistRPG, 0, -40);
        playlist.start();

        //JOptionPane.showMessageDialog(null, "Vas a parar la playlist y el hilo", "Escuchando", JOptionPane.INFORMATION_MESSAGE);
        //playlist.stop(); //<- deprecado pero para este caso en concreto funciona

        b_previousSoung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playlist.stop();
                playlist = new PlayPlaylistManager(playlistAgresivo, 0, -40);
                playlist.start();
            }
        });
        b_nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonParar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playlist.stop();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TestPlaylist");
        frame.setContentPane(new TestPlaylist().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
