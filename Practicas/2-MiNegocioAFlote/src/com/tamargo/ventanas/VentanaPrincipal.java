package com.tamargo.ventanas;

import com.tamargo.exist.Coleccion;
import com.tamargo.miscelanea.AdministradorRutasArchivos;
import com.tamargo.miscelanea.JTextFieldLimit;
import com.tamargo.miscelanea.PlayList;
import com.tamargo.modelo.Empresa;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VentanaPrincipal {
    private JFrame ventana;
    private JPanel panel;
    private JPanel panelPlaylist;
    private JPanel panelRegistro;
    private JPanel panelPrincipal;

    // Inicio
    private ArrayList<Empresa> empresas = new ArrayList<>();

    // Registro
    private JTextPane registro;

    // Playlist
    private JLabel tituloPlaylist;
    private JLabel tituloCancion;
    private JTextPane cancionesPlaylist;
    private JSlider slider;

    int indexPlaylist = 0;
    int indexCancion = 0;
    private float volumen = -40;
    private PlayList pm;

    private final Dimension dimPanelRegistro = new Dimension(250, 550);
    private final Dimension dimPanelPlaylist = new Dimension(250, 350);
    private final Dimension dimPanelPartida = new Dimension(900, 908);

    private final String fuenteMYHUI = "MicrosoftYaHeiUI";

    public VentanaPrincipal(JFrame ventana) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("./ficheros/config/volumen.txt")));
            volumen = Float.parseFloat(br.readLine().replace(',', '.'));
        } catch (IOException ignored) {
            volumen = -40;
        }

        this.ventana = ventana;
        try {
            pm = new PlayList(indexPlaylist, indexCancion, volumen);
            pm.setVolumen(volumen);
            pm.start();
        } catch (Exception ignored) {}
        cargarVentanaInicio();
        cargarPanelPlaylist();
        cargarPanelRegistro();

        this.ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./ficheros/config/volumen.txt")));
                    bw.write(String.valueOf(volumen));
                    bw.close();
                } catch (IOException ignored) { }
                ventana.dispose();
            }
        });
    }



    // INICIO
    private void cargarVentanaInicio() {
        try {
            panelPrincipal.removeAll();
            panelPrincipal.repaint();
        } catch (Exception ignored) {}

        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));

        int posY = 20;

        JLabel cabecera = new JLabel("BIENVENIDO A", SwingConstants.CENTER);
        configurarLabel(cabecera, fuenteMYHUI, Font.BOLD, 30);
        cabecera.setForeground(Color.GRAY);
        panelPrincipal.add(cabecera);
        cabecera.setBounds(0, posY, dimPanelPartida.width, 60);

        posY += 40;

        JLabel titulo = new JLabel("TU EMPRESA A FLOTE", SwingConstants.CENTER);
        configurarLabel(titulo, fuenteMYHUI, Font.BOLD, 48);
        panelPrincipal.add(titulo);
        titulo.setBounds(0, posY, dimPanelPartida.width, 60);

        posY += 60;

        JPanel linea = new JPanel();
        linea.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea);
        linea.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 10;

        JLabel instruccion = new JLabel("CREA UNA NUEVA EMPRESA O SELECCIONA UNA DE LAS EXISTENTES PARA COMENZAR", SwingConstants.CENTER);
        configurarLabel(instruccion, fuenteMYHUI, Font.BOLD, 18);
        panelPrincipal.add(instruccion);
        instruccion.setBounds(0, posY, dimPanelPartida.width, 20);

        posY += 24;

        JPanel linea2 = new JPanel();
        linea2.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea2);
        linea2.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 20;

        empresas = Coleccion.recogerEmpresas();

        int heighTotal = 350 + 350 * ((empresas.size() - 1) / 4);

        JPanel panelEmpresas = new JPanel();
        panelEmpresas.setLayout(null);
        panelEmpresas.setPreferredSize(new Dimension(dimPanelPartida.width - 14, heighTotal));

        JScrollPane scrollPaneEmpresas = new JScrollPane(panelEmpresas);
        scrollPaneEmpresas.setBorder(BorderFactory.createEmptyBorder());

        int anchuraPanelEmpresa = 200;
        int alturaPanelEmpresa = 350;
        int dimImagen = 128;
        int espacio = 20;
        int x = 10;
        int y = 0;

        JPanel panelNuevaEmpresa = new JPanel();
        panelNuevaEmpresa.setLayout(null);
        panelNuevaEmpresa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel tituloNuevaEmpresa = new JLabel("Nueva Empresa", SwingConstants.CENTER);
        configurarLabel(tituloNuevaEmpresa, fuenteMYHUI, Font.BOLD, 16);
        panelNuevaEmpresa.add(tituloNuevaEmpresa);
        tituloNuevaEmpresa.setBounds(0, 40, anchuraPanelEmpresa, 25);

        JButton nuevaEmpresa = new JButton("");
        nuevaEmpresa.setIcon(new ImageIcon("./ficheros/logos/logo0.png"));
        nuevaEmpresa.setFocusPainted(false);
        nuevaEmpresa.setBorderPainted(false);
        panelNuevaEmpresa.add(nuevaEmpresa);
        nuevaEmpresa.setBounds((anchuraPanelEmpresa / 2) - (dimImagen / 2), 70, dimImagen, dimImagen);

        panelEmpresas.add(panelNuevaEmpresa);
        panelNuevaEmpresa.setBounds(x, 10, anchuraPanelEmpresa, alturaPanelEmpresa);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Empresa empresa : empresas) {
            for (int i = 0; i < 10; i++) {


            x += anchuraPanelEmpresa;
            x += espacio;
            if (x > 720) {
                x = 10;
                y += alturaPanelEmpresa + 10;
            }

            JPanel panelEmpresa = new JPanel();
            panelEmpresa.setLayout(null);
            panelEmpresa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            JLabel nombreEmpresa = new JLabel(empresa.getNombre(), SwingConstants.CENTER);
            configurarLabel(nombreEmpresa, fuenteMYHUI, Font.BOLD, 16);
            panelEmpresa.add(nombreEmpresa);
            nombreEmpresa.setBounds(0, 40 + y, anchuraPanelEmpresa, 25);

            JButton b_empresa = new JButton("");
            b_empresa.setIcon(new ImageIcon("./ficheros/logos/" + empresa.getLogo()));
            b_empresa.setFocusPainted(false);
            b_empresa.setBorderPainted(false);
            panelEmpresa.add(b_empresa);
            b_empresa.setBounds((anchuraPanelEmpresa / 2) - (dimImagen / 2), 70 + y, dimImagen, dimImagen);

            JLabel ciudadEmpresa = new JLabel(empresa.getCiudad(), SwingConstants.CENTER);
            configurarLabel(ciudadEmpresa, fuenteMYHUI, Font.BOLD, 14);
            panelEmpresa.add(ciudadEmpresa);
            ciudadEmpresa.setBounds(0, 210 + y, anchuraPanelEmpresa, 25);

            JLabel cabeceraFechaCreacionEmpresa = new JLabel("Fecha de Creación", SwingConstants.CENTER);
            configurarLabel(cabeceraFechaCreacionEmpresa, fuenteMYHUI, Font.BOLD, 12);
            panelEmpresa.add(cabeceraFechaCreacionEmpresa);
            cabeceraFechaCreacionEmpresa.setForeground(Color.GRAY);
            cabeceraFechaCreacionEmpresa.setBounds(0, 240 + y, anchuraPanelEmpresa, 25);

            String fechaCreacionStr = empresa.getFechaCreacion().format(formatter);
            JLabel fechaCreacionEmpresa = new JLabel(fechaCreacionStr, SwingConstants.CENTER);
            configurarLabel(fechaCreacionEmpresa, fuenteMYHUI, Font.BOLD, 14);
            panelEmpresa.add(fechaCreacionEmpresa);
            fechaCreacionEmpresa.setBounds(0, 255 + y, anchuraPanelEmpresa, 25);

            JLabel cabeceraFechaActualEmpresa = new JLabel("Fecha en Partida", SwingConstants.CENTER);
            configurarLabel(cabeceraFechaActualEmpresa, fuenteMYHUI, Font.BOLD, 12);
            panelEmpresa.add(cabeceraFechaActualEmpresa);
            cabeceraFechaActualEmpresa.setForeground(Color.GRAY);
            cabeceraFechaActualEmpresa.setBounds(0, 280 + y, anchuraPanelEmpresa, 25);

            String fechaActualStr = empresa.getFechaActual().format(formatter);
            JLabel fechaActualEmpresa = new JLabel(fechaActualStr, SwingConstants.CENTER);
            configurarLabel(fechaActualEmpresa, fuenteMYHUI, Font.BOLD, 14);
            panelEmpresa.add(fechaActualEmpresa);
            fechaActualEmpresa.setBounds(0, 295 + y, anchuraPanelEmpresa, 25);
            
            panelEmpresas.add(panelEmpresa);
            panelEmpresa.setBounds(x, 10 + y, anchuraPanelEmpresa, alturaPanelEmpresa);


            b_empresa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO CARGAR VENTANA PARTIDA CON LOS DATOS DE ESTA EMPRESA
                    //  cargar todos los datos e ir rellenando (plantear como recuperar el registro)
                    //  poder clicar cada departamento y ver sus empleados
                }
            });
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelEmpresas);
        panelPrincipal.add(scrollPane);
        scrollPane.setBounds(5, posY, dimPanelPartida.width - 10, dimPanelPartida.height - (posY + 5));

        nuevaEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ABRIR VENTANA NUEVA PARTIDA
                //  una ventana donde podamos poner el nombre, ciudad y elegir el logo
            }
        });

    }



    // PLAYLIST
    private void cargarPanelPlaylist() {
        try {
            panelPlaylist.removeAll();
            panelPlaylist.repaint();
        } catch (Exception ignored) {}

        panelPlaylist.setLayout(null);
        panelPlaylist.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));

        int posY = 10;

        JLabel titulo = new JLabel("SONIDO", SwingConstants.CENTER);
        configurarLabel(titulo, fuenteMYHUI, Font.BOLD, 24);
        panelPlaylist.add(titulo);
        titulo.setBounds(0, posY, dimPanelRegistro.width, 40);

        posY += 40;

        JPanel linea = new JPanel();
        linea.setBackground(Color.DARK_GRAY);
        panelPlaylist.add(linea);
        linea.setBounds(0, posY, dimPanelRegistro.width, 2);

        posY = 60;

        tituloPlaylist = new JLabel("Mentalidad Calmada", SwingConstants.CENTER);
        configurarLabel(tituloPlaylist, fuenteMYHUI, Font.BOLD, 16);
        panelPlaylist.add(tituloPlaylist);
        tituloPlaylist.setForeground(Color.GRAY);
        tituloPlaylist.setBounds(0, posY, dimPanelPlaylist.width, 40);

        posY = 80;

        tituloCancion = new JLabel("Ayasake no Starmine", SwingConstants.CENTER);
        configurarLabel(tituloCancion, fuenteMYHUI, Font.BOLD, 20);
        panelPlaylist.add(tituloCancion);
        tituloCancion.setBounds(0, posY, dimPanelPlaylist.width, 40);

        posY = 130;

        int sliderValue = (int)((volumen / 0.8) + 100);

        slider = new JSlider(JSlider.VERTICAL, 0, 100, sliderValue);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(25);

        panelPlaylist.add(slider);
        slider.setBounds(dimPanelPlaylist.width - 70, posY, 50, dimPanelPlaylist.height - (posY + 20));

        posY = 250;

        cancionesPlaylist = new JTextPane();
        cancionesPlaylist.setOpaque(false);
        cancionesPlaylist.setEditable(false);
        panelPlaylist.add(cancionesPlaylist);
        cancionesPlaylist.setBounds(10, posY, dimPanelPlaylist.width - (10 + 60), dimPanelPlaylist.height - (posY + 20));
        cancionesPlaylist.setFont(new Font(fuenteMYHUI, Font.BOLD, 13));
        cancionesPlaylist.setText("Ayasake no Starmine\nEmpty Crown\nButtercup (mix)");

        posY = 160;
        int margX = 20;
        int separacion = 5;
        int anchoBoton = 70;
        int altoBoton = 20;

        JLabel lNextSong = new JLabel("Next Song", SwingConstants.RIGHT);
        configurarLabel(lNextSong, fuenteMYHUI, Font.BOLD, 10);
        lNextSong.setForeground(Color.GRAY);
        panelPlaylist.add(lNextSong);
        lNextSong.setBounds(margX + anchoBoton + separacion, posY - 18, anchoBoton, altoBoton);

        JLabel lPrevSong = new JLabel("Prev Song", SwingConstants.LEFT);
        configurarLabel(lPrevSong, fuenteMYHUI, Font.BOLD, 10);
        lPrevSong.setForeground(Color.GRAY);
        panelPlaylist.add(lPrevSong);
        lPrevSong.setBounds(margX, posY - 18, anchoBoton, altoBoton);

        JButton nextSong = new JButton(">");
        nextSong.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
        panelPlaylist.add(nextSong);
        nextSong.setFocusPainted(false);
        nextSong.setBounds(margX + anchoBoton + separacion, posY, anchoBoton, altoBoton);

        JButton prevSong = new JButton("<");
        prevSong.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
        panelPlaylist.add(prevSong);
        prevSong.setFocusPainted(false);
        prevSong.setBounds(margX, posY, anchoBoton, altoBoton);

        posY = 185;

        JLabel lNextPlaylist = new JLabel("Next Playlist", SwingConstants.RIGHT);
        configurarLabel(lNextPlaylist, fuenteMYHUI, Font.BOLD, 10);
        lNextPlaylist.setForeground(Color.GRAY);
        panelPlaylist.add(lNextPlaylist);
        lNextPlaylist.setBounds(margX + anchoBoton + separacion, posY + 18, anchoBoton, altoBoton);

        JLabel lPrevPlaylist = new JLabel("Prev Playlist", SwingConstants.LEFT);
        configurarLabel(lPrevPlaylist, fuenteMYHUI, Font.BOLD, 10);
        lPrevPlaylist.setForeground(Color.GRAY);
        panelPlaylist.add(lPrevPlaylist);
        lPrevPlaylist.setBounds(margX, posY + 18, anchoBoton, altoBoton);
        
        JButton nextPlaylist = new JButton(">>");
        nextPlaylist.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
        panelPlaylist.add(nextPlaylist);
        nextPlaylist.setFocusPainted(false);
        nextPlaylist.setBounds(margX + anchoBoton + separacion, posY, anchoBoton, altoBoton);

        JButton prevPlaylist = new JButton("<<");
        prevPlaylist.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
        panelPlaylist.add(prevPlaylist);
        prevPlaylist.setFocusPainted(false);
        prevPlaylist.setBounds(margX, posY, anchoBoton, altoBoton);


        volcarDatosPlayList();

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) slider.getValue() > 100)
                    slider.setValue(100);
                else if ((int) slider.getValue() < 0)
                    slider.setValue(0);

                volumen = (float) (int) slider.getValue();
                volumen = (float) ((volumen - 100) * 0.80);
                //System.out.println(volumen);
                pm.setVolumen(volumen);
            }
        });
        nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion++;
                int numCanciones;
                if (indexPlaylist == 0)
                    numCanciones = AdministradorRutasArchivos.cancionesPlaylist1.length;
                else
                    numCanciones = AdministradorRutasArchivos.cancionesPlaylist2.length;
                if (indexCancion >= numCanciones) {
                    indexCancion = 0;
                }

                pm.nextSong();
                volcarDatosPlayList();
            }
        });
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexCancion--;
                int numCanciones;
                if (indexPlaylist == 0)
                    numCanciones = AdministradorRutasArchivos.cancionesPlaylist1.length;
                else
                    numCanciones = AdministradorRutasArchivos.cancionesPlaylist2.length;
                if (indexCancion < 0) {
                    indexCancion = numCanciones - 1;
                }

                pm.previousSong();
                volcarDatosPlayList();
            }
        });
        nextPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexPlaylist++;
                indexCancion = 0;
                int numPlaylists = AdministradorRutasArchivos.titulosPlaylists.length;

                if (indexPlaylist >= numPlaylists) {
                    indexPlaylist = 0;
                }

                pm.nextPlayList();
                volcarDatosPlayList();
            }
        });
        prevPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexPlaylist--;
                indexCancion = 0;
                int numPlaylists = AdministradorRutasArchivos.titulosPlaylists.length;

                if (indexPlaylist < 0) {
                    indexPlaylist = numPlaylists - 1;
                }

                pm.previousPlayList();
                volcarDatosPlayList();
            }
        });

    }
    public void volcarDatosPlayList() {
        // Nombres
        String[] listaNombres;
        if (indexPlaylist == 0)
            listaNombres = AdministradorRutasArchivos.nombresPlaylist1;
        else
            listaNombres = AdministradorRutasArchivos.nombresPlaylist2;
        String[] listaTitulosPlaylist = AdministradorRutasArchivos.titulosPlaylists;

        tituloPlaylist.setText(listaTitulosPlaylist[indexPlaylist]);
        tituloCancion.setText(listaNombres[indexCancion]);

        // Text pane
        cancionesPlaylist.setText("");
        StyledDocument doc = cancionesPlaylist.getStyledDocument();

        Style style = cancionesPlaylist.addStyle("PlaylistStyle", null);
        StyleConstants.setForeground(style, Color.DARK_GRAY);

        try {
            boolean resetearColor = false;
            for (String str : listaNombres) {
                if (str.equalsIgnoreCase(listaNombres[indexCancion])) {
                    StyleConstants.setForeground(style, Color.BLUE);
                    resetearColor = true;
                }

                doc.insertString(doc.getLength(), str, style);
                doc.insertString(doc.getLength(), "\n", style);

                if (resetearColor) {
                    StyleConstants.setForeground(style, Color.DARK_GRAY);
                    resetearColor = false;
                }
            }
        } catch (BadLocationException | IndexOutOfBoundsException ignored) {}
    }



    // REGISTRO
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
        JFrame frame = new JFrame("Inicio");
        //VentanaPrincipal vc = new VentanaPrincipal(frame);
        frame.setContentPane(new VentanaPrincipal(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
