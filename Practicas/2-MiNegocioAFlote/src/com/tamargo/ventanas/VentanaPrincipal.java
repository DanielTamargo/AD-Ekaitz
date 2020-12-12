package com.tamargo.ventanas;

import com.tamargo.exist.Coleccion;
import com.tamargo.miscelanea.JTextFieldLimit;
import com.tamargo.miscelanea.PlayList;
import com.tamargo.modelo.*;
import org.xmldb.api.base.XMLDBException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class VentanaPrincipal {
    private JFrame ventana;
    private JPanel panel;
    private JPanel panelPlaylist;
    private JPanel panelRegistro;
    private JPanel panelPrincipal;

    // Datos
    private ArrayList<Dato> datos = new ArrayList<>();
    private ArrayList<Dato> datosNombresMasc = new ArrayList<>();
    private ArrayList<Dato> datosAvataresMasc = new ArrayList<>();
    private ArrayList<Dato> datosNombresFem = new ArrayList<>();
    private ArrayList<Dato> datosAvataresFem = new ArrayList<>();
    private ArrayList<Dato> datosApellidos = new ArrayList<>();
    private ArrayList<Dato> datosDnis = new ArrayList<>();
    private ArrayList<Dato> datosProfesiones = new ArrayList<>();

    // Inicio
    private ArrayList<Empresa> empresas = new ArrayList<>();
    private JPanel panelEmpresas;
    private JScrollPane scrollPaneInicio = null;

    // Registro
    private JTextPane registro;
    private ArrayList<String> lineasRegistro = new ArrayList<>();

    // Playlist
    private JLabel tituloPlaylist;
    private JLabel tituloCancion;
    private JTextPane cancionesPlaylist;
    private JSlider slider;

    private int indexPlaylist = 0;
    private int indexCancion = 0;
    private float volumen = -40;
    private PlayList pm;

    // Nueva empresa
    private int indexLogoEmpresa;
    private int idNuevaEmpresa;
    private String nombreNuevaEmpresa;
    private String ciudadNuevaEmpresa;

    // Partida
    private Empresa empresa;
    private ArrayList<EmpleadoContratado> empleadosEmpresa;
    private ArrayList<EmpleadoDisponible> empleadosDisponiblesEmpresa;
    private ArrayList<Departamento> departamentosEmpresa;

    private JLabel balanceEconomico;
    private JLabel iconoBalanceEconomico;
    private JLabel poblacionEmpleados;
    private JLabel iconoPoblacionEmpleados;
    private JLabel salarioTotal;

    private JPanel panelDepartamento;
    private JLabel dep_depNo;
    private JLabel dep_nombre;
    private JLabel dep_numEmps;
    private JLabel dep_salarioTotal;

    private int numEmpleados;
    private int numEmpleadosMax;

    private boolean mesPasado = false;
    
    // Dimensions
    private final Dimension dimPanelRegistro = new Dimension(250, 550);
    private final Dimension dimPanelPlaylist = new Dimension(250, 350);
    private final Dimension dimPanelPartida = new Dimension(900, 908);

    private final String fuenteMYHUI = "MicrosoftYaHeiUI";

    public VentanaPrincipal(JFrame ventana, PlayList pm) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("./ficheros/config/volumen.txt")));
            volumen = Float.parseFloat(br.readLine().replace(',', '.'));
        } catch (IOException ignored) {
            volumen = -40;
        }

        this.ventana = ventana;
        this.pm = pm;

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

        // CARGAMOS TODOS LOS DATOS QUE QUERAMOS MANEJAR
        empresas = new ArrayList<>();
        empresas = Coleccion.recogerEmpresas();
        if (datos.size() <= 0) {
            datos = Coleccion.recogerDatos();
            for (Dato dato : datos) {
                if (dato.getTipo().equalsIgnoreCase("nombreMasculino"))
                    datosNombresMasc.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("nombreFemenino"))
                    datosNombresFem.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("avatarMasculino"))
                    datosAvataresMasc.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("avatarFemenino"))
                    datosAvataresFem.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("dni"))
                    datosDnis.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("apellido"))
                    datosApellidos.add(dato);
                else if (dato.getTipo().equalsIgnoreCase("profesion"))
                    datosProfesiones.add(dato);
            }
        }


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

        int heighTotal = 350 + 350 * ((empresas.size()) / 4);


        panelEmpresas = new JPanel();

        panelEmpresas.setLayout(null);
        panelEmpresas.setPreferredSize(new Dimension(dimPanelPartida.width - 14, heighTotal));
        panelEmpresas.setMinimumSize(new Dimension(dimPanelPartida.width - 14, heighTotal));
        panelEmpresas.setMaximumSize(new Dimension(dimPanelPartida.width - 14, heighTotal));
        panelEmpresas.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        int anchuraPanelEmpresa = 200;
        int alturaPanelEmpresa = 350;
        int dimImagen = 128;
        int espacio = 20;
        int x = 10;
        int y = 0;
        int yPanel = 0;

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
        nuevaEmpresa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));
        panelNuevaEmpresa.add(nuevaEmpresa);
        nuevaEmpresa.setBounds((anchuraPanelEmpresa / 2) - (dimImagen / 2), 70, dimImagen, dimImagen);

        panelEmpresas.add(panelNuevaEmpresa);
        panelNuevaEmpresa.setBounds(x, 10, anchuraPanelEmpresa, alturaPanelEmpresa);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int indexEmpresa = 0;
        for (Empresa empresa : empresas) {

            x += anchuraPanelEmpresa;
            x += espacio;
            if (x > 720) {
                x = 10;
                yPanel += alturaPanelEmpresa + 10;
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
            b_empresa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));
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
            panelEmpresa.setBounds(x, 10 + yPanel, anchuraPanelEmpresa, alturaPanelEmpresa);


            int finalIndexEmpresa = indexEmpresa;
            b_empresa.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionarEmpresa(finalIndexEmpresa);
                }
            });
            indexEmpresa++;
        }

        if (scrollPaneInicio == null)
            scrollPaneInicio = new JScrollPane(panelEmpresas);
        panelPrincipal.add(scrollPaneInicio);
        scrollPaneInicio.setBounds(5, posY, dimPanelPartida.width - 10, dimPanelPartida.height - (posY + 5));

        nuevaEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaNuevaPartida();
            }
        });

    }
    private void seleccionarEmpresa(int index) {
        empresa = empresas.get(index);

        departamentosEmpresa = new ArrayList<>();
        ArrayList<Departamento> departamentos = Coleccion.recogerDepartamentos();
        for (Departamento departamento : departamentos) {
            if (departamento.getIdEmpresa() == empresa.getId())
                departamentosEmpresa.add(departamento);
        }


        empleadosEmpresa = new ArrayList<>();
        ArrayList<EmpleadoContratado> empleadosContratados = Coleccion.recogerEmpleadosContratados();
        for (Departamento departamento : departamentosEmpresa) {
            for (EmpleadoContratado empleadoContratado : empleadosContratados) {
                if (empleadoContratado.getDepNo() == departamento.getDepNo())
                    empleadosEmpresa.add(empleadoContratado);
            }
        }

        empleadosDisponiblesEmpresa = new ArrayList<>();
        ArrayList<EmpleadoDisponible> empleadosDisponibles = Coleccion.recogerEmpleadosDisponibles();
        for (EmpleadoDisponible empleadoDisponible : empleadosDisponibles) {
            if (empleadoDisponible.getIdEmpresa() == empresa.getId())
                empleadosDisponiblesEmpresa.add(empleadoDisponible);
        }

        cargarVentanaPartida();
    }

    // NUEVA PARTIDA
    private void cargarVentanaNuevaPartida() {
        try {
            panelPrincipal.removeAll();
            panelPrincipal.repaint();
        } catch (Exception ignored) {}

        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));

        int posY = 20;

        JLabel cabecera = new JLabel("COMENZANDO", SwingConstants.CENTER);
        configurarLabel(cabecera, fuenteMYHUI, Font.BOLD, 30);
        cabecera.setForeground(Color.GRAY);
        panelPrincipal.add(cabecera);
        cabecera.setBounds(0, posY, dimPanelPartida.width, 60);

        posY += 40;

        JLabel titulo = new JLabel("UN NUEVO IMPERIO", SwingConstants.CENTER);
        configurarLabel(titulo, fuenteMYHUI, Font.BOLD, 48);
        panelPrincipal.add(titulo);
        titulo.setBounds(0, posY, dimPanelPartida.width, 60);

        posY += 60;

        JPanel linea = new JPanel();
        linea.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea);
        linea.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 10;

        JLabel instruccion = new JLabel("RELLENA LOS DATOS Y COMIENZA UNA NUEVA EMPRESA, UNA NUEVA HISTORIA", SwingConstants.CENTER);
        configurarLabel(instruccion, fuenteMYHUI, Font.BOLD, 18);
        panelPrincipal.add(instruccion);
        instruccion.setBounds(0, posY, dimPanelPartida.width, 20);

        posY += 24;

        JPanel linea2 = new JPanel();
        linea2.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea2);
        linea2.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 100;

        int anchuraImagen = 128;
        int separacion = 100;
        int margX = 210;

        idNuevaEmpresa = Coleccion.idEmpresaMasAlto() + 1;

        indexLogoEmpresa = new Random().nextInt(6) + 1;
        JButton bIconoNuevaEmpresa = new JButton("");
        bIconoNuevaEmpresa.setIcon(new ImageIcon("./ficheros/logos/logo" + indexLogoEmpresa + ".png"));
        bIconoNuevaEmpresa.setFocusPainted(false);
        bIconoNuevaEmpresa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));
        panelPrincipal.add(bIconoNuevaEmpresa);
        bIconoNuevaEmpresa.setBounds(margX, posY + 25, anchuraImagen, anchuraImagen);

        JLabel cabeceraNombre = new JLabel("NOMBRE", SwingConstants.LEFT);
        configurarLabel(cabeceraNombre, fuenteMYHUI, Font.BOLD, 19);
        cabeceraNombre.setForeground(Color.GRAY);
        panelPrincipal.add(cabeceraNombre);
        cabeceraNombre.setBounds(margX + anchuraImagen + separacion, posY, 200, 20);
        
        JTextField tfNombre = new JTextField();
        configurarTextField(tfNombre, fuenteMYHUI, Font.BOLD, 22, 16);
        panelPrincipal.add(tfNombre);
        tfNombre.setBounds(margX + anchuraImagen + separacion, posY + 20, 260, 40);

        posY += 100;

        JLabel cabeceraCiudad = new JLabel("CIUDAD", SwingConstants.LEFT);
        configurarLabel(cabeceraCiudad, fuenteMYHUI, Font.BOLD, 19);
        cabeceraCiudad.setForeground(Color.GRAY);
        panelPrincipal.add(cabeceraCiudad);
        cabeceraCiudad.setBounds(margX + anchuraImagen + separacion, posY, 200, 20);

        JTextField tfCiudad = new JTextField();
        configurarTextField(tfCiudad, fuenteMYHUI, Font.BOLD, 22, 16);
        panelPrincipal.add(tfCiudad);
        tfCiudad.setBounds(margX + anchuraImagen + separacion, posY + 20, 260, 40);

        posY += 150;
        int margXtp = margX - 75;

        JTextPane tpFuncionamientoDelJuego = new JTextPane();
        tpFuncionamientoDelJuego.setEditable(false);
        tpFuncionamientoDelJuego.setOpaque(false);
        tpFuncionamientoDelJuego.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
        tpFuncionamientoDelJuego.setForeground(Color.DARK_GRAY);
        tpFuncionamientoDelJuego.setText("""
                Funcionamiento del juego:
                  - Empezarás desde lo más bajo, con solo los departamentos Ventas y RRHH
                  - Tu objetivo es fácil, hacer que tu empresa crezca tanto que desequilibre el Mundo
                  - Tendrás que ir contratando empleados y abriendo los departamentos que te faltan
                  - Cada departamento ofrece sus funciones, y cuantos más empleados tenga, mejor será
                
                Consejos:
                  - No te vayas a la quiebra""");
        panelPrincipal.add(tpFuncionamientoDelJuego);
        tpFuncionamientoDelJuego.setBounds(margXtp, posY, dimPanelPartida.width - (margXtp * 2), 170);

        posY += 250;

        JButton bVolver = new JButton("Volver");
        bVolver.setFocusPainted(false);
        configurarButton(bVolver, fuenteMYHUI, Font.BOLD, 20);
        panelPrincipal.add(bVolver);
        bVolver.setBounds(margXtp, posY, 200, 40);

        JButton bComenzar = new JButton("Comenzar");
        bComenzar.setFocusPainted(false);
        configurarButton(bComenzar, fuenteMYHUI, Font.BOLD, 20);
        panelPrincipal.add(bComenzar);
        bComenzar.setBounds(dimPanelPartida.width - margX - 200, posY, 200, 40);

        bIconoNuevaEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexLogoEmpresa++;
                if (indexLogoEmpresa > 6)
                    indexLogoEmpresa = 1;
                bIconoNuevaEmpresa.setIcon(new ImageIcon("./ficheros/logos/logo" + indexLogoEmpresa + ".png"));
            }
        });
        bComenzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comprobarDatos(tfNombre, tfCiudad)) {
                    nombreNuevaEmpresa = tfNombre.getText();
                    ciudadNuevaEmpresa = tfCiudad.getText();
                    LocalDate fechaCreacion = LocalDate.now();
                    LocalDate fechaActual = LocalDate.now();

                    empresa = new Empresa(idNuevaEmpresa, 5000, 2000, 2000,
                            nombreNuevaEmpresa, ciudadNuevaEmpresa, "logo" + indexLogoEmpresa + ".png", fechaActual, fechaCreacion);
                    empleadosEmpresa = new ArrayList<>();
                    departamentosEmpresa = new ArrayList<>();
                    empleadosDisponiblesEmpresa = new ArrayList<>();

                    int numDepRRHH = Coleccion.depNoDepartamentoMasAlto() + 1;
                    Departamento rrhh = new Departamento(idNuevaEmpresa, numDepRRHH, "RRHH", 1);
                    int numDepVentas = numDepRRHH + 1;
                    Departamento ventas = new Departamento(idNuevaEmpresa, numDepVentas, "Ventas", 1);
                    int numDepSalud = numDepVentas + 1;
                    Departamento salud = new Departamento(idNuevaEmpresa, numDepSalud, "Salud", 1);
                    int numDepMarketing = numDepSalud + 1;
                    Departamento marketing = new Departamento(idNuevaEmpresa, numDepMarketing, "Marketing", 1);
                    int numDepEstudioMercado = numDepMarketing + 1;
                    Departamento estudioMercado = new Departamento(idNuevaEmpresa, numDepEstudioMercado, "Estudio de Mercado", 1);

                    departamentosEmpresa.add(rrhh);
                    departamentosEmpresa.add(ventas);
                    departamentosEmpresa.add(salud);
                    departamentosEmpresa.add(marketing);
                    departamentosEmpresa.add(estudioMercado);

                    datos = Coleccion.recogerDatos();
                    String nombre = "Sin datos";
                    String apellido = "Sin datos";
                    String avatar = "avatar-masc1.png";
                    String dni = "SinDatosX";
                    int numEmp = 0;
                    int depNo = 0;
                    for (int i = 0; i < 2; i++) {
                        if (datos.size() > 0) {
                            int genero = new Random().nextInt(2); // 0 = hombre, 1 = mujer
                            try {
                                if (genero == 0) {
                                    nombre = datosNombresMasc.get(new Random().nextInt(datosNombresMasc.size())).getDato();
                                    avatar = datosAvataresMasc.get(new Random().nextInt(datosAvataresMasc.size())).getDato();
                                } else {
                                    nombre = datosNombresFem.get(new Random().nextInt(datosNombresFem.size())).getDato();
                                    avatar = datosAvataresFem.get(new Random().nextInt(datosAvataresFem.size())).getDato();
                                }
                                apellido = datosApellidos.get(new Random().nextInt(datosApellidos.size())).getDato();
                                dni = datosDnis.get(new Random().nextInt(datosDnis.size())).getDato();
                            } catch (IllegalArgumentException ignored) { }
                        }
                        if (i == 0) {
                            numEmp = Coleccion.empNoEmpleadoContratadoMasAlto() + 1;
                            depNo = rrhh.getDepNo();
                        } else {
                            numEmp += 1;
                            depNo = ventas.getDepNo();
                        }
                        EmpleadoContratado emp = new EmpleadoContratado(numEmp, dni, nombre, apellido,
                                LocalDate.now().minusMonths(new Random().nextInt(13)).minusDays(new Random().nextInt(32)).minusYears(new Random().nextInt(32) + 19), 1000,
                                depNo, avatar, LocalDate.now());
                        empleadosEmpresa.add(emp);
                    }

                    Coleccion.insertarEmpresa(empresa);

                    for (Departamento departamento : departamentosEmpresa) {
                        Coleccion.insertarDepartamento(departamento);
                    }
                    for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
                        Coleccion.insertarEmpleadoContratado(empleadoContratado);
                    }

                    mesPasado = true; // para que genere nuevos empleados disponibles
                    cargarVentanaPartida();

                }

            }
        });
        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanaInicio();
            }
        });

    }
    private boolean comprobarDatos(JTextField nombre, JTextField ciudad) {
        Pattern patronNombre = Pattern.compile("[a-z A-Z0-9'!¡¿?]{3,16}");
        Pattern patronCiudad = Pattern.compile("[a-zA-Z]{3,16}");
        if (patronNombre.matcher(nombre.getText()).matches() &&
        patronCiudad.matcher(ciudad.getText()).matches()) {
            return true;
        } else {
            mostrarJOptionPane("Datos no válidos", """
                    EH! Introduce unos datos válidos!
                    
                    Nombre: de 3 a 16 caracteres y/o números
                    Ciudad: de 3 a 16 caracteres""", 0);
            return false;
        }
    }

    // PARTIDA
    private void cargarVentanaPartida() {
        try {
            panelPrincipal.removeAll();
            panelPrincipal.repaint();
        } catch (Exception ignored) {}

        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, false));


        JLabel fechaActual = new JLabel();
        configurarLabel(fechaActual, fuenteMYHUI, Font.BOLD, 14);
        fechaActual.setForeground(Color.GRAY);
        panelPrincipal.add(fechaActual);
        fechaActual.setBounds(700, 10, 200, 20);
        fechaActual.setText("Fecha Actual: " + empresa.getFechaActual().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        int posY = 18;

        JLabel cabecera = new JLabel("Recursos", SwingConstants.LEFT);
        configurarLabel(cabecera, fuenteMYHUI, Font.BOLD, 18);
        cabecera.setForeground(Color.GRAY);
        panelPrincipal.add(cabecera);
        cabecera.setBounds(500, posY - 10, 300, 60);

        iconoBalanceEconomico = new JLabel("");
        iconoBalanceEconomico.setIcon(new ImageIcon("./ficheros/misc/monedas.png"));
        panelPrincipal.add(iconoBalanceEconomico);
        iconoBalanceEconomico.setBounds(500, posY + 48, 32, 32);

        balanceEconomico = new JLabel("1000", SwingConstants.LEFT);
        configurarLabel(balanceEconomico, fuenteMYHUI, Font.BOLD, 16);
        panelPrincipal.add(balanceEconomico);
        balanceEconomico.setBounds(500 + 32 + 5, posY + 29, 300, 32);

        salarioTotal = new JLabel("3000", SwingConstants.LEFT);
        configurarLabel(salarioTotal, fuenteMYHUI, Font.BOLD, 24);
        panelPrincipal.add(salarioTotal);
        salarioTotal.setBounds(500 + 32 + 5, posY + 48, 300, 32);


        iconoPoblacionEmpleados = new JLabel("");
        iconoPoblacionEmpleados.setIcon(new ImageIcon("./ficheros/misc/usuarios.png"));
        panelPrincipal.add(iconoPoblacionEmpleados);
        iconoPoblacionEmpleados.setBounds(500 + 150, posY + 48, 32, 32);

        poblacionEmpleados = new JLabel("2/5", SwingConstants.LEFT);
        configurarLabel(poblacionEmpleados, fuenteMYHUI, Font.BOLD, 24);
        panelPrincipal.add(poblacionEmpleados);
        poblacionEmpleados.setBounds(500 + 150 + 32 + 5, posY + 48, 300, 32);


        posY = 40;

        JLabel titulo = new JLabel(empresa.getNombre(), SwingConstants.LEFT);
        configurarLabel(titulo, fuenteMYHUI, Font.BOLD, 40);
        panelPrincipal.add(titulo);
        titulo.setBounds(40, posY, 400, 60);

        posY += 60;

        JPanel linea = new JPanel();
        linea.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea);
        linea.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 10;

        JLabel instruccion = new JLabel("ADMINISTRA TUS DEPARTAMENTOS Y TUS EMPLEADOS", SwingConstants.CENTER);
        configurarLabel(instruccion, fuenteMYHUI, Font.BOLD, 18);
        panelPrincipal.add(instruccion);
        instruccion.setBounds(0, posY, dimPanelPartida.width, 20);

        posY += 24;

        JPanel linea2 = new JPanel();
        linea2.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(linea2);
        linea2.setBounds(0, posY, dimPanelPartida.width, 2);

        posY += 10;

        JPanel panelDepartamentos = new JPanel();
        panelDepartamentos.setLayout(null);
        panelPrincipal.add(panelDepartamentos);
        panelDepartamentos.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDepartamentos.setBounds(10, posY, 300, 290);


        JButton b_DepRRHH = new JButton("Recursos Humanos");
        configurarButton(b_DepRRHH, fuenteMYHUI, Font.BOLD, 16);
        panelDepartamentos.add(b_DepRRHH);
        b_DepRRHH.setBounds(10, 10, 280, 50);

        JButton b_DepVENTAS = new JButton("Ventas");
        configurarButton(b_DepVENTAS, fuenteMYHUI, Font.BOLD, 16);
        panelDepartamentos.add(b_DepVENTAS);
        b_DepVENTAS.setBounds(10, 10 + 50 + 5, 280, 50);

        JButton b_DepSALUD = new JButton("Salud");
        configurarButton(b_DepSALUD, fuenteMYHUI, Font.BOLD, 16);
        panelDepartamentos.add(b_DepSALUD);
        b_DepSALUD.setBounds(10, 10 + 50 + 5 + 50 + 5, 280, 50);

        JButton b_DepMARKETING = new JButton("Marketing");
        configurarButton(b_DepMARKETING, fuenteMYHUI, Font.BOLD, 16);
        panelDepartamentos.add(b_DepMARKETING);
        b_DepMARKETING.setBounds(10, 10 + 50 + 5 + 50 + 5 + 50 + 5, 280, 50);

        JButton b_DepEstudioMercado = new JButton("Estudio de Mercado");
        configurarButton(b_DepEstudioMercado, fuenteMYHUI, Font.BOLD, 16);
        panelDepartamentos.add(b_DepEstudioMercado);
        b_DepEstudioMercado.setBounds(10, 10 + 50 + 5 + 50 + 5 + 50 + 5 + 50 + 5, 280, 50);

        cargarPanelDepartamento(departamentosEmpresa.get(0));
        cargarPanelEmpleadosDisponibles();

        JButton volverAlMenu = new JButton("Volver al Menú");
        configurarButton(volverAlMenu, fuenteMYHUI, Font.BOLD, 14);
        panelPrincipal.add(volverAlMenu);
        volverAlMenu.setBounds(720, 850, 165, 40);

        JButton siguienteMes = new JButton("Pasar un Mes");
        configurarButton(siguienteMes, fuenteMYHUI, Font.BOLD, 14);
        panelPrincipal.add(siguienteMes);
        siguienteMes.setBounds(720, 800, 165, 40);

        JButton iniciativa = new JButton("Nueva iniciativa");
        configurarButton(iniciativa, fuenteMYHUI, Font.BOLD, 14);
        panelPrincipal.add(iniciativa);
        iniciativa.setBounds(720, 750, 165, 40);
        iniciativa.setEnabled(false);

        JButton borrarEmpresa = new JButton("Borrar Empresa");
        configurarButton(borrarEmpresa, fuenteMYHUI, Font.BOLD, 14);
        panelPrincipal.add(borrarEmpresa);
        borrarEmpresa.setBounds(720, 700, 165, 40);

        JButton informe = new JButton("Imprimir Informe");
        configurarButton(informe, fuenteMYHUI, Font.BOLD, 14);
        panelPrincipal.add(informe);
        informe.setBounds(720, 450, 165, 40);

        borrarEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coleccion.borrarEmpresa(empresa.getId());
                JFrame frame = new JFrame("Inicio");
                //VentanaPrincipal vc = new VentanaPrincipal(frame);
                frame.setContentPane(new VentanaPrincipal(frame, pm).panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                ventana.dispose();
            }
        });
        informe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO sacar ventana que lea las querys insertadas y ya

            }
        });
        
        volverAlMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //empresas = Coleccion.recogerEmpresas();
                JFrame frame = new JFrame("Inicio");
                //VentanaPrincipal vc = new VentanaPrincipal(frame);
                frame.setContentPane(new VentanaPrincipal(frame, pm).panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                ventana.dispose();
            }
        });
        siguienteMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasarMes();
            }
        });

        b_DepRRHH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento rrhh = null;
                for (Departamento departamento : departamentosEmpresa) {
                    if (departamento.getNombre().equalsIgnoreCase("RRHH"))
                        rrhh = departamento;
                }
                if (rrhh != null) {
                    cargarPanelDepartamento(rrhh);
                }
            }
        });
        b_DepSALUD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento salud = null;
                for (Departamento departamento : departamentosEmpresa) {
                    if (departamento.getNombre().equalsIgnoreCase("Salud"))
                        salud = departamento;
                }
                if (salud != null) {
                    cargarPanelDepartamento(salud);
                }
            }
        });
        b_DepMARKETING.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento marketing = null;
                for (Departamento departamento : departamentosEmpresa) {
                    if (departamento.getNombre().equalsIgnoreCase("Marketing"))
                        marketing = departamento;
                }
                if (marketing != null) {
                    cargarPanelDepartamento(marketing);
                }
            }
        });
        b_DepEstudioMercado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento estudioMercado = null;
                for (Departamento departamento : departamentosEmpresa) {
                    if (departamento.getNombre().equalsIgnoreCase("Estudio de Mercado"))
                        estudioMercado = departamento;
                }
                if (estudioMercado != null) {
                    cargarPanelDepartamento(estudioMercado);
                }
            }
        });
        b_DepVENTAS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento ventas = null;
                for (Departamento departamento : departamentosEmpresa) {
                    if (departamento.getNombre().equalsIgnoreCase("Ventas"))
                        ventas = departamento;
                }
                if (ventas != null) {
                    cargarPanelDepartamento(ventas);
                }
            }
        });

        calcularGananciasEmpresa();
        calcularGastosMensualesEmpresa();
        cargarDatosRecursos();
    }
    private void cargarEmpleadosDisponiblesEmpresa() {
        ArrayList<EmpleadoDisponible> empleadosDisponibles = Coleccion.recogerEmpleadosDisponibles();
        empleadosDisponiblesEmpresa = new ArrayList<>();
        for (EmpleadoDisponible empleadoDisponible : empleadosDisponibles) {
            if (empleadoDisponible.getIdEmpresa() == empresa.getId())
                empleadosDisponiblesEmpresa.add(empleadoDisponible);
        }
        if (empleadosDisponiblesEmpresa.size() <= 0)
            generarEmpleadosDisponibles();
    }
    private void generarEmpleadosDisponibles() {
        if (mesPasado) {
            for (int i = 0; i < 4; i++) {
                EmpleadoDisponible emp;
                String nombre;
                String avatar;
                if (new Random().nextBoolean()) {
                    nombre = datosNombresMasc.get(new Random().nextInt(datosNombresMasc.size())).getDato();
                    avatar = datosAvataresMasc.get(new Random().nextInt(datosAvataresMasc.size())).getDato();
                } else {
                    nombre = datosNombresFem.get(new Random().nextInt(datosNombresFem.size())).getDato();
                    avatar = datosAvataresFem.get(new Random().nextInt(datosAvataresFem.size())).getDato();
                }

                emp = new EmpleadoDisponible(Coleccion.idEmpleadoDisponibleMasAlto() + 1,
                        datosDnis.get(new Random().nextInt(datosDnis.size())).getDato(),
                        nombre,
                        datosApellidos.get(new Random().nextInt(datosApellidos.size())).getDato(),
                        LocalDate.now().minusMonths(new Random().nextInt(12)).minusDays(new Random().nextInt(31)).minusYears(new Random().nextInt(32) + 19),
                        (new Random().nextInt(32) + 1) * 1000,
                        avatar,
                        datosProfesiones.get(new Random().nextInt(datosProfesiones.size())).getDato(),
                        empresa.getId(),
                        empresa.getFechaActual());
                Coleccion.insertarEmpleadoDisponible(emp);
                empleadosDisponiblesEmpresa.add(emp);
            }
            mesPasado = false;
        }
    }
    private void pasarMes() {
        mesPasado = true;

        try {
            Coleccion.borrarEmpleadosDisponiblesEmpresa(empresa.getId(), null);
        } catch (XMLDBException ignored) {}

        empresa.setFechaActual(empresa.getFechaActual().plusMonths(1));
        empresa.setSalarioDisponible(empresa.getSalarioDisponible() + (empresa.getGananciasMensuales() - empresa.getGastosMensuales()));
        Coleccion.editarEmpresa(empresa);

        for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
            if (empleadoContratado.haCumplidoAnyos(empresa.getFechaActual())) {
                lineasRegistro.add(0, empleadoContratado.getNombre() + " ¡Ha cumplido años!");
                Coleccion.editarEdadEmpleadoContratado(empleadoContratado);
            }
        }

        volcarDatosTextPaneRegistro();

        cargarVentanaPartida();
    }
    private void cargarPanelEmpleadosDisponibles() {
        cargarEmpleadosDisponiblesEmpresa();

        JPanel panelEmpleadosDisponibles = new JPanel();
        panelEmpleadosDisponibles.setLayout(null);
        panelPrincipal.add(panelEmpleadosDisponibles);
        panelEmpleadosDisponibles.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelEmpleadosDisponibles.setBounds(320, 144, 570, 290);

        JLabel cabecera = new JLabel("Empleados Disponibles", SwingConstants.CENTER);
        configurarLabel(cabecera, fuenteMYHUI, Font.BOLD, 16);
        cabecera.setForeground(Color.GRAY);
        panelEmpleadosDisponibles.add(cabecera);
        cabecera.setBounds(0, 10, 570, 20);

        int x = 12;
        int anchuraPanel = 130;
        int alturaPanel = 240;
        int posY = 40;
        for (int i = 0; i < 4; i++) {
            try {
                EmpleadoDisponible emp = empleadosDisponiblesEmpresa.get(i);
                // todo generar panel del empleado disponible
                JPanel panelEmpleado = new JPanel();
                panelEmpleado.setLayout(null);
                panelEmpleadosDisponibles.add(panelEmpleado);
                panelEmpleado.setBounds(x, posY, anchuraPanel, alturaPanel);
                panelEmpleado.setBorder(BorderFactory.createEtchedBorder());


                JLabel avatar = new JLabel();
                avatar.setIcon(new ImageIcon("./ficheros/avatares/" + emp.getAvatar()));
                panelEmpleado.add(avatar);
                avatar.setBounds(15, 5, 100, 100);
                avatar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

                JTextPane datos = new JTextPane();
                datos.setFont(new Font(fuenteMYHUI, Font.PLAIN, 12));
                panelEmpleado.add(datos);
                datos.setOpaque(false);
                datos.setEditable(false);
                datos.setBounds(5, 110, anchuraPanel - 10, 88);
                String[] listaDatos = { emp.getDni(), emp.getNombre(), emp.getApellido(), "Salario: " + String.valueOf(emp.getSalario()),
                            emp.getDepartamentoDeseado()
                };

                volcarDatosTextPaneInfoEmpleado(datos, listaDatos);

                JButton b_Contratar = new JButton("Contratar");
                configurarButton(b_Contratar, fuenteMYHUI, Font.BOLD, 12);
                panelEmpleado.add(b_Contratar);
                b_Contratar.setBounds(anchuraPanel / 2 - 80 / 2, 205, 80, 25);

                b_Contratar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO BORRAR DE EMPLEADOS DISPONIBLES Y AÑADIR A EMPLEADOS CONTRATADOS RECARGANDO LA VENTANA
                        if (numEmpleados >= numEmpleadosMax) {
                            mostrarJOptionPane("Máximo de Empleados",
                                    "No puedes contratar más empleados! \nNecesitarías tener más empleados en el departamento de recursos humanos..." +
                                            "\nEs posible que sea la hora de despedir a alguien, jefe", 0);
                        } else {
                            Coleccion.borrarEmpleadoDisponible(emp.getId(), null);
                            //int empNo, String dni, String nombre, String apellido, LocalDate fechaNac, int salario, int depNo, String avatar, LocalDate fechaActual
                            int depNo = 0;
                            for (Departamento departamento : departamentosEmpresa) {
                                if (departamento.getNombre().equalsIgnoreCase(emp.getDepartamentoDeseado()))
                                    depNo = departamento.getDepNo();
                            }

                            EmpleadoContratado empContr = new EmpleadoContratado(
                                    Coleccion.empNoEmpleadoContratadoMasAlto() + 1, emp.getDni(), emp.getNombre(), emp.getApellido(),
                                    emp.getFechaNac(), emp.getSalario(), depNo, emp.getAvatar(), empresa.getFechaActual()
                            );

                            empleadosEmpresa.add(empContr);
                            Coleccion.insertarEmpleadoContratado(empContr);
                            cargarPanelDepartamento(departamentosEmpresa.get(0));

                            calcularGananciasEmpresa();
                            calcularGastosMensualesEmpresa();
                            cargarDatosRecursos();

                            System.out.println(emp);
                            b_Contratar.setText("Contratado/a");
                            b_Contratar.setEnabled(false);
                        }
                    }
                });

                x += 10 + anchuraPanel;

            } catch (NullPointerException | IndexOutOfBoundsException ignored) {}
        }

    }
    private void cargarPanelDepartamento(Departamento departamento) {

        int posY = 450;
        panelDepartamento = new JPanel();
        panelDepartamento.setLayout(null);
        panelDepartamento.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelPrincipal.add(panelDepartamento);
        panelDepartamento.setBounds(10, posY, 700, 450);
        
        dep_depNo = new JLabel("Nº Dep: " + departamento.getDepNo());
        configurarLabel(dep_depNo, fuenteMYHUI, Font.BOLD, 14);
        panelDepartamento.add(dep_depNo);
        dep_depNo.setBounds(20, 10, 300, 20);

        dep_nombre = new JLabel(departamento.getNombre());
        configurarLabel(dep_nombre, fuenteMYHUI, Font.BOLD, 14);
        panelDepartamento.add(dep_nombre);
        dep_nombre.setBounds(20, 10 + 15, 300, 20);

        int numEmps = Coleccion.numEmpleadosDepartamento(departamento.getDepNo());

        dep_numEmps = new JLabel("Número empleados: " + numEmps);
        configurarLabel(dep_numEmps, fuenteMYHUI, Font.BOLD, 14);
        panelDepartamento.add(dep_numEmps);
        dep_numEmps.setBounds(40 + 150, 10, 300, 20);

        long salarioTotalDestinado = Coleccion.salarioTotalDepartamento(departamento.getDepNo());

        dep_salarioTotal = new JLabel("Salario total destinado: " + salarioTotalDestinado);
        configurarLabel(dep_salarioTotal, fuenteMYHUI, Font.BOLD, 14);
        panelDepartamento.add(dep_salarioTotal);
        dep_salarioTotal.setBounds(40 + 150, 10 + 15, 300, 20);

        JPanel panelEmpleadosDepartamento = new JPanel();
        panelEmpleadosDepartamento.setLayout(null);

        ArrayList<EmpleadoContratado> empleadosDep = new ArrayList<>();
        for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
            if (empleadoContratado.getDepNo() == departamento.getDepNo())
                empleadosDep.add(empleadoContratado);
        }

        int x = 10;
        int y = 10;
        int anchuraPanelEmpleado = 130;
        int alturaPanelEmpleado = 350;
        int totalX = 10 + ((10 + anchuraPanelEmpleado) * empleadosDep.size());
        panelEmpleadosDepartamento.setPreferredSize(new Dimension(totalX, alturaPanelEmpleado + 20));

        int indexEmpleado = 0;
        for (EmpleadoContratado empleadoContratado : empleadosDep) {
            JPanel panelEmpleado = new JPanel();
            panelEmpleado.setLayout(null);
            panelEmpleadosDepartamento.add(panelEmpleado);
            panelEmpleado.setBorder(BorderFactory.createEtchedBorder());
            panelEmpleado.setBounds(x, y, anchuraPanelEmpleado, alturaPanelEmpleado);

            JLabel fotoEmpleado = new JLabel();
            fotoEmpleado.setIcon(new ImageIcon("./ficheros/avatares/" + empleadoContratado.getAvatar()));
            panelEmpleado.add(fotoEmpleado);
            fotoEmpleado.setBounds(15, 60, 100, 100);
            fotoEmpleado.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            String[] listaDatos = {empleadoContratado.getDni(),
                    empleadoContratado.getNombre(), empleadoContratado.getApellido(),
                    empleadoContratado.getFechaNac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    String.valueOf(empleadoContratado.getEdad()) + " años"
            };


            JButton b_despedirEmpleado = new JButton("Despedir");
            configurarButton(b_despedirEmpleado, fuenteMYHUI, Font.BOLD, 14);
            panelEmpleado.add(b_despedirEmpleado);
            b_despedirEmpleado.setBounds(anchuraPanelEmpleado / 2 - 80 / 2, 290, 80, 40);

            JTextPane tp = new JTextPane();
            tp.setFont(new Font(fuenteMYHUI, Font.BOLD, 14));
            tp.setOpaque(false);
            tp.setEditable(false);
            panelEmpleado.add(tp);
            tp.setBounds(0, 180, anchuraPanelEmpleado, 100);

            volcarDatosTextPaneInfoEmpleado(tp, listaDatos);

            panelEmpleadosDepartamento.add(panelEmpleado);

            x += 10 + anchuraPanelEmpleado;

            b_despedirEmpleado.setEnabled(false);
            b_despedirEmpleado.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(empleadoContratado);
                    //Coleccion.borrarEmpleadoContratado(empleadoContratado.getEmpNo());
                    //empleadosEmpresa.remove(empleadoContratado);
                    //cargarVentanaPartida();
                    //b_despedirEmpleado.setVisible(false);
                }
            });

        }

        JScrollPane scrollPane = new JScrollPane(panelEmpleadosDepartamento);
        panelDepartamento.add(scrollPane);
        scrollPane.setBounds(5, 50, 690, 450 - 55);

    }
    private void volcarDatosTextPaneInfoEmpleado(JTextPane tp, String[] listaDatos) {
        tp.setText("");
        StyledDocument doc = tp.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        StyleConstants.setForeground(center, Color.DARK_GRAY);

        try {
            for (String str : listaDatos) {
                doc.insertString(doc.getLength(), str, center);
                doc.insertString(doc.getLength(), "\n", center);
            }
        } catch (BadLocationException | IndexOutOfBoundsException ignored) {}
    }
    private void cargarDatosRecursos() {
        long balanceRecursos = empresa.getGananciasMensuales() - empresa.getGastosMensuales();
        long salarioRecursos = empresa.getSalarioDisponible();

        if (balanceRecursos < 0) {
            balanceEconomico.setText(String.valueOf(balanceRecursos));
            balanceEconomico.setForeground(new Color(186, 45, 35));
        } else if (balanceRecursos == 0) {
            balanceEconomico.setText(String.valueOf(balanceRecursos));
            balanceEconomico.setForeground(Color.DARK_GRAY);
        } else {
            balanceEconomico.setText("+" + String.valueOf(balanceRecursos));
            balanceEconomico.setForeground(new Color(26, 156, 87));
        }

        salarioTotal.setText(String.valueOf(salarioRecursos));
        if (salarioRecursos < 0)
            salarioTotal.setForeground(new Color(186, 45, 35));
        else if (salarioRecursos == 0)
            salarioTotal.setForeground(Color.DARK_GRAY);
        else
            salarioTotal.setForeground(new Color(26, 156, 87));

        numEmpleados = empleadosEmpresa.size();
        numEmpleadosMax = 5;
        for (Departamento departamento : departamentosEmpresa) {
            if (departamento.getNombre().equalsIgnoreCase("RRHH")) {
                int n = 0;
                for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
                    if (empleadoContratado.getDepNo() == departamento.getDepNo())
                        n++;
                }

                int multiplicador = departamento.getNivel() + 2;
                numEmpleadosMax = 2 + (n * multiplicador);
            }
        }

        poblacionEmpleados.setText(numEmpleados + "/" + numEmpleadosMax);
        if (numEmpleados >= numEmpleadosMax) {
            poblacionEmpleados.setForeground(new Color(186, 45, 35));
        } else {
            poblacionEmpleados.setForeground(new Color(26, 156, 87));
        }

    }
    private void calcularNivelDep(Departamento departamento) {

        int nivelAntes = departamento.getNivel();
        long salarioTotalDep = 0;
        for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
            if (empleadoContratado.getDepNo() == departamento.getDepNo())
                salarioTotalDep += empleadoContratado.getSalario();
        }

        int nivel;
        if (salarioTotalDep < 4000)
            nivel = 1;
        else if (salarioTotalDep < 8000)
            nivel = 2;
        else if (salarioTotalDep < 12000)
            nivel = 3;
        else if (salarioTotalDep < 16000)
            nivel = 4;
        else
            nivel = 5;

        if (nivelAntes != nivel) {
            departamento.setNivel(nivel);
            Coleccion.editarNivelDepartamento(departamento);
        }

        if (nivelAntes > nivel) {
            lineasRegistro.add(0, departamento.getNombre() + " ha bajado al nivel " + nivel);
            volcarDatosTextPaneRegistro();
        } else if (nivelAntes < nivel) {
            lineasRegistro.add(0, departamento.getNombre() + " ha subido al nivel " + nivel);
            volcarDatosTextPaneRegistro();
        }
    }
    private void calcularGananciasEmpresa() {
        long gananciasTotales = 0;
        for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
            for (Departamento departamento : departamentosEmpresa) {
                if (empleadoContratado.getDepNo() == departamento.getDepNo()) {
                    if (departamento.getNombre().equalsIgnoreCase("Ventas")) {
                        gananciasTotales += empleadoContratado.getSalario() + (empleadoContratado.getSalario() * 0.8 * departamento.getNivel());
                    } else if (departamento.getNombre().equalsIgnoreCase("Marketing")) {
                        gananciasTotales += empleadoContratado.getSalario() + (empleadoContratado.getSalario() * 0.6 * departamento.getNivel());
                    } else if (departamento.getNombre().equalsIgnoreCase("Estudio de Mercado")) {
                        gananciasTotales += empleadoContratado.getSalario() + (empleadoContratado.getSalario() * 0.5 * departamento.getNivel());
                    } else if (departamento.getNombre().equalsIgnoreCase("Administrativo")) {
                        gananciasTotales += empleadoContratado.getSalario() + (empleadoContratado.getSalario() * 0.4 * departamento.getNivel());
                    } else if (departamento.getNombre().equalsIgnoreCase("Salud")) {
                        gananciasTotales += (empleadoContratado.getSalario() * 0.2 * departamento.getNivel());
                    }
                }
            }
        }
        empresa.setGananciasMensuales(gananciasTotales);
    }
    private void calcularGastosMensualesEmpresa() {
        long gastosMensuales = 0;
        for (EmpleadoContratado empleadoContratado : empleadosEmpresa) {
            gastosMensuales += empleadoContratado.getSalario();
        }
        empresa.setGastosMensuales(gastosMensuales);
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

        try {
            if (pm == null) {
                pm = new PlayList(indexPlaylist, indexCancion, volumen, tituloPlaylist, tituloCancion, cancionesPlaylist);
                //pm.setVolumen(volumen);
                pm.start();
            } else {
                pm.setDatos(tituloPlaylist, tituloCancion, cancionesPlaylist);
            }
        } catch (Exception ignored) {}

        //volcarDatosPlayList();

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
                pm.nextSong();
            }
        });
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.previousSong();
            }
        });
        nextPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.nextPlayList();
            }
        });
        prevPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.previousPlayList();
            }
        });

    }
    /* LO HACE LA PROPIA PLAYLIST
    private void volcarDatosPlayList() {
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
    */

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

        rellenarRegistroAlmacenado();
    }
    private void rellenarRegistroAlmacenado() {
        // TODO CARGAR REGISTRO ALMACENADO

        volcarDatosTextPaneRegistro();
    }
    private void volcarDatosTextPaneRegistro() {
        // Text pane
        registro.setText("");
        StyledDocument doc = registro.getStyledDocument();

        Style style = registro.addStyle("PlaylistStyle", null);
        StyleConstants.setForeground(style, Color.DARK_GRAY);

        try {
            boolean resetearColor = false;
            for (String str : lineasRegistro) {
                if (str.contains("nivel") && str.contains("bajado")) {
                    StyleConstants.setForeground(style, new Color(186, 45, 35));
                    resetearColor = true;
                } else if (str.contains("nivel") && str.contains("subido")) {
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


    public JPanel getPanel() {
        return panel;
    }

}
