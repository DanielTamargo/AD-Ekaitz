package com.tamargo.ventanas;

import com.tamargo.miscelanea.JTextFieldLimit;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panel;
    private JPanel panelPlaylist;
    private JPanel panelRegistro;
    private JPanel panelPrincipal;

    private JTextPane registro;

    private Dimension dimPanelRegistro = new Dimension(250, 550);

    private final String fuenteMYHUI = "MicrosoftYaHeiUI";

    public VentanaPrincipal() {
        cargarVentanaInicio();
        cargarPanelPlaylist();
        cargarPanelRegistro();
    }

    /*public void volcarDatosTextPane(JTextPane textPane, ArrayList<String> listaStrings, int tipo) {
        textPane.setText("");

        StyledDocument doc = textPane.getStyledDocument();

        Style style = textPane.addStyle("PlaylistStyle", null);
        StyleConstants.setForeground(style, Color.DARK_GRAY);

        String nums = "1234567890";

        try {
            boolean resetearColor = false;
            for (String str : listaStrings) {
                if (str.contains(".")) {
                    if ((tipo == 2 && str.substring(0, str.indexOf('.')).equalsIgnoreCase(nickJugador))) {
                        StyleConstants.setForeground(style, Color.BLUE);
                        resetearColor = true;
                    }
                }
                if ((tipo == 2 && str.contains("Tu puntuación"))
                        || (tipo == 1 && nums.contains(str.substring(0, 1)))) {
                    StyleConstants.setForeground(style, Color.BLUE);
                    resetearColor = true;
                }
                doc.insertString(doc.getLength(), str, style);

                if (resetearColor) {
                    StyleConstants.setForeground(style, Color.DARK_GRAY);
                    resetearColor = false;
                }
            }
        } catch (BadLocationException ignored) {}
    }*/

    private void cargarVentanaInicio() {
        try {
            panelPrincipal.removeAll();
            panelPrincipal.repaint();
        } catch (Exception ignored) {}

        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
    }

    private void cargarPanelPlaylist() {
        try {
            panelPlaylist.removeAll();
            panelPlaylist.repaint();
        } catch (Exception ignored) {}

        panelPlaylist.setLayout(null);
        panelPlaylist.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));
    }

    private void cargarPanelRegistro() {
        try {
            panelRegistro.removeAll();
            panelRegistro.repaint();
        } catch (Exception ignored) {}

        panelRegistro.setLayout(null);
        panelRegistro.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));

        int posY = 10;

        JLabel titulo = new JLabel("REGISTRO", SwingConstants.CENTER);
        configurarLabel(titulo, fuenteMYHUI, Font.BOLD, 24);
        panelRegistro.add(titulo);
        titulo.setBounds(0, posY, dimPanelRegistro.width, 40);

        posY += 40;

        JPanel linea = new JPanel();
        linea.setBackground(Color.DARK_GRAY);
        panelRegistro.add(linea);
        linea.setBounds(0, posY, dimPanelRegistro.width, 2);

        registro = new JTextPane();
        registro.setEditable(false);
        registro.setOpaque(false);
        registro.setFont(new Font(fuenteMYHUI, Font.BOLD, 12));
        registro.setText("Inicia una partida y avanza un mes para actualizar el registro");

        posY += 10;
        int margX = 4;

        JScrollPane panelInfoRegistro = new JScrollPane(registro);
        panelInfoRegistro.setBorder(BorderFactory.createEmptyBorder());
        panelRegistro.add(panelInfoRegistro);
        panelInfoRegistro.setBounds(margX, posY, dimPanelRegistro.width - (margX*2), dimPanelRegistro.height - (posY + 10));

    }

    private void rellenarRegistro() {

    }

    private void volcarDatosTextPaneRegistro() {

    }

    // JOPTIONPANES
    public void mostrarJOptionPane(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("Ok");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // CONFIGURAR ELEMENTOS
    public void configurarLabel(JLabel label, String fuente, int tipo, int size) {
        label.setFont(new Font(fuente, tipo, size));
        label.setForeground(Color.DARK_GRAY);
    }
    public void configurarTextField(JTextField tField, String fuente, int tipo, int size, int maxCaracteres) {
        tField.setFont(new Font(fuente, tipo, size));
        tField.setForeground(Color.DARK_GRAY);
        tField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tField.setDocument(new JTextFieldLimit(maxCaracteres));
    }
    public void configurarButton(JButton boton, String fuente, int tipo, int size) {
        boton.setFont(new Font(fuente, tipo, size));
        boton.setForeground(Color.DARK_GRAY);
        boton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        boton.setFocusPainted(false);
    }

    // LANZAR VENTANA
    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VentanaPrincipal().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
