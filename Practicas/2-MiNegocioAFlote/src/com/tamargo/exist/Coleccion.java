package com.tamargo.exist;

import com.tamargo.datosbase.GenerarDatosBase;
import com.tamargo.miscelanea.GuardarLogs;
import com.tamargo.modelo.*;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.PatternSyntaxException;

public class Coleccion {

    private final static String nombre = "[Colección] ";

    private final static String nombreColeccion = "Practica2DanielTamargo";

    private final static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist

    //todo fichero que controle que es la primera vez que se inicia? o que ponga un 0 si da error de intentar conectar con servicio exist
    //todo ventana personalizada para que ponga su puerto, usuario y contraseña y que se almacenen en un fichero?
    private final static String puerto = "8083";
    private final static String URI = "xmldb:exist://localhost:" + puerto + "/exist/xmlrpc/db/" + nombreColeccion; //URI colección
    private final static String usu = "admin"; //Usuario
    private final static String usuPwd = "admin"; //Clave

    /**
     * Accederemos a esta variable estática
     *
     * A través del método conectar intentará conectar con la colección,
     */
    public static Collection coleccion = null;

    /**
     * Conecta con la coleccion y la devuelve
     */
    public static Collection conectar() {

        try {
            // Preparamos, conectamos y cargamos la colección que queramos utilizar
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instanciar la BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            coleccion = DatabaseManager.getCollection(URI, usu, usuPwd); // Cargar la colección en cuestión


            // Devolvemos la conexión
            return coleccion;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist. Error: " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al inicializar la BD eXist. Error: " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver. Error: " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error en el driver. Error: " + e.getLocalizedMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error al instanciar la BD. Error: " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al instanciar la BD. Error: " + e.getLocalizedMessage());
        }

        return null;
    }

    public static void comprobarColeccion() {
        if (conectar() == null) {
            try {
                System.out.println(nombre + "La colección '" + nombreColeccion + "' no existe");

                // DOCUMENTACIÓN: https://www.exist-db.org/exist/apps/doc/xmldb
                // Accedemos a la colección general donde crearemos la colección a usar en la práctica
                String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db";
                Class cl = Class.forName(driver); // Cargar del driver
                Database database = (Database) cl.newInstance(); // Instanciar la BD
                DatabaseManager.registerDatabase(database); // Registro del driver
                coleccion = DatabaseManager.getCollection(URI, usu, usuPwd); // Cargar la colección en cuestión

                XPathQueryService servicio;
                servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                //Preparamos la creación
                String crearColeccion = "declare namespace rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\";\n" +
                        "\n" +
                        "import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\";\n" +
                        "\n" +
                        "let $log-in := xmldb:login(\"/db\", \"" + usu + "\", \"" + usuPwd + "\")\n" +
                        "let $create-collection := xmldb:create-collection(\"/db\", \"" + nombreColeccion + "\")\n" +
                        "for $record in doc('/db/" + nombreColeccion + ".rdf')/rdf:RDF/*\n" +
                        "let $split-record :=\n" +
                        "    <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
                        "        {$record}\n" +
                        "    </rdf:RDF>\n" +
                        "let $about := $record/@rdf:about\n" +
                        "let $filename := util:hash($record/@rdf:about/string(), \"md5\") || \".xml\"\n" +
                        "return\n" +
                        "    xmldb:store(\"/db/" + nombreColeccion + "\", $filename, $split-record)";

                // Ejecutamos la creación de la colección
                servicio.query(crearColeccion);
                coleccion.close();
                System.out.println(nombre + "Colección '" + nombreColeccion + "' creada");

                GenerarDatosBase.generarDatosBase();

            } catch (XMLDBException | ClassNotFoundException e) {
                System.out.println("Error al conectar con la BBDD.");
                mostrarJOptionPane("Error con eXist", String.format("""
                        Imposible conectar con eXist.
                        
                        ¿Te faltan librerías?
                        ¿Has arrancado el servidor?
                        
                        O puede que tus credenciales no sean:
                        - Puerto: %s
                        - Admin: %s
                        - Password: %s
                        
                        Comprueba lo mencionado y vuelve a arrancar la aplicación
                        """, puerto, usu, usuPwd), 0);
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear la colección. Error: " + e.getLocalizedMessage());
                System.exit(0);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear la colección. Error: " + e.getLocalizedMessage());
            }
        } else {
            try {
                coleccion.close();
            } catch (XMLDBException ignored) { }
        }
    }

    private static void mostrarJOptionPane(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("Ok");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    /**
     * Insertará un fichero XML recibido en la colección
     * @param ficheroXML fichero a insertar
     */
    public static void insertarXML(File ficheroXML) {
        if (conectar() != null) {
            try {

                // DOCUMENTACIÓN: http://www.exist-db.org/exist/apps/doc/devguide_xmldb#use-xmldb (storeResource)

                // Inicializamos el recurso
                XMLResource res = null;

                // Creamos el recurso -> recibe 2 parámetros tipo String:
                //                          s: nombre.xml (si lo dejamos null, pondrá un nombre aleatorio)
                //                          s1: tipo recurso (en este caso, siempre será XMLResource)
                res = (XMLResource) coleccion.createResource(ficheroXML.getName(), "XMLResource");

                // Fijamos como contenido ese archivo .xml elegido
                res.setContent(ficheroXML);
                coleccion.storeResource(res); // Lo añadimos a la colección

                System.out.println(nombre + "Recurso XML añadido a la colección: " + ficheroXML.getName());

            } catch (XMLDBException e) {
                System.out.println(nombre + "Error al crear el recurso. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear el recurso. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
    }

    /**
     * Devuelve una lista de nombres de los recursos (los nombres de los xml) de la colección
     */
    public static ArrayList<String> nombresRecursosColeccion() {
        ArrayList<String> nombres = new ArrayList<>();
        if (conectar() != null) {
            try {
                // Listamos la colección para ver que en efecto se ha añadido
                for (String colRe : coleccion.listResources())
                    nombres.add(colRe);
            } catch (XMLDBException e) {
                System.out.println(nombre + "Error al generar el listado de nombres de los recursos de la colección. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al generar el listado de nombres de los recursos de la colección. Error: " + e.getLocalizedMessage());
            }

            try {
                coleccion.close();
            } catch (XMLDBException ignored) { }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return nombres;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // RETRIEVES PARTICULARES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> informe(int idEmpresa) {
        ArrayList<String> informe = new ArrayList<>();

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                // TODO numero de empleados totales
                //  numero de empleados por departamento
                //  empleado con mayor salario + departamento al que pertenece
                //  departamento que mas salario genera
                //  empresa que mas empleados tiene, que mas salario genera, etc..... <- no hacer
                String queryNumEmpleadosEmpresa = "for $cuenta in /empleadosContratados/count(empleadoContratado[depNo=(for $depNo in /departamentos/departamento[@idEmpresa=\"" + idEmpresa + "\"" +
                        "]/depNo return $depNo)]) return $cuenta"; // TODO GUARDAR QUERY
                String queryEmpMaxSalario = "for $maxSalario in /empleadosContratados/max(empleadoContratado[depNo=(for $depNo in /departamentos/departamento[@idEmpresa=\"" + idEmpresa +
                        "\"]/depNo return $depNo)]/salario) return $maxSalario";




                coleccion.close();
                System.out.println("Informe de la empresa " + idEmpresa + " generado");
                GuardarLogs.logger.log(Level.INFO,  "Informe de la empresa " + idEmpresa + " generado");
            } catch (Exception e) {
                System.out.println(nombre + "Error al generar el informe. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer generar el informe. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return informe;
    }

    public static int idEmpleadoDisponibleMasAlto() {
        int idEmpleadoDisponible = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando el id de empleadoDisponible más alto");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $id in /empleadosDisponibles/max(empleadoDisponible/id) return $id"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            idEmpleadoDisponible = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Id de empleadoDisponible más alto: " + idEmpleadoDisponible);
                GuardarLogs.logger.log(Level.INFO,  "Id de empleadoDisponible más alto: " + idEmpleadoDisponible);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el id de empleadoDisponible más alto. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el id de empleadoDisponible más alto. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return idEmpleadoDisponible;
    }
    public static int idEmpresaMasAlto() {
        int idEmpresa = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando el id de empresa más alto");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $id in /empresas/max(empresa/id) return $id"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            idEmpresa = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Id de empresa más alto: " + idEmpresa);
                GuardarLogs.logger.log(Level.INFO,  "Id de empresa más alto: " + idEmpresa);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el id de empresa más alto. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el id de empresa más alto. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return idEmpresa;
    }
    public static int depNoDepartamentoMasAlto() {
        int depNoDepartamento = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando el depNo de departamento más alto");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $depNo in /departamentos/max(departamento/depNo) return $depNo"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            depNoDepartamento = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "DepNo de departamento más alto: " + depNoDepartamento);
                GuardarLogs.logger.log(Level.INFO,  "DepNo de departamento más alto: " + depNoDepartamento);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el depNo de departamento más alto. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el depNo de departamento más alto. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return depNoDepartamento;
    }
    public static int empNoEmpleadoContratadoMasAlto() {
        int empNoEmpleadoContratado = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando el empNo de empleadoContratado más alto");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $empNo in /empleadosContratados/max(empleadoContratado/empNo) return $empNo"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            empNoEmpleadoContratado = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "EmpNo de empleadoContratado más alto: " + empNoEmpleadoContratado);
                GuardarLogs.logger.log(Level.INFO,  "EmpNo de empleadoContratado más alto: " + empNoEmpleadoContratado);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el empNo de empleadoContratado más alto. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el empNo de empleadoContratado más alto. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return empNoEmpleadoContratado;
    }
    public static int numDepartamentos() {
        int numDepartamentos = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando número de departamentos");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $cuenta in /departamentos/count(departamento) " +
                        " return $cuenta"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            numDepartamentos = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Número de departamentos: " + numDepartamentos);
                GuardarLogs.logger.log(Level.INFO,  "Número de departamentos: " + numDepartamentos);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el número de departamentos. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el número de departamentos. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return numDepartamentos;
    }
    public static int numEmpleadosDepartamento(int depNo) {
        int numEmps = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando número de empleados del departamento " + depNo);
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $cuenta in /empleadosContratados/count(empleadoContratado[depNo=" + depNo + "]) " +
                        " return $cuenta"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            numEmps = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Número de empleados del departamento " + depNo + ": " + numEmps);
                GuardarLogs.logger.log(Level.INFO,  "Número de empleados del departamento " + depNo + ": " + numEmps);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el número de empleados del departamento " + depNo + ". Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el número de empleados del departamento " + depNo + ". Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return numEmps;
    }
    public static long salarioTotalDepartamento(int depNo) {
        long salarioTotal = 0;
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Sacando salario total del departamento " + depNo);
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $cuenta in /empleadosContratados/sum(empleadoContratado[depNo=" + depNo + "]/salario) " +
                        " return $cuenta"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            salarioTotal = Integer.parseInt(resultado);

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Salario total del departamento " + depNo + ": " + salarioTotal);
                GuardarLogs.logger.log(Level.INFO,  "Número de empleados del departamento " + depNo + ": " + salarioTotal);
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer el salario total del departamento " + depNo + ". Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer el salario total del departamento " + depNo + ". Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return salarioTotal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // RETRIEVES LISTAS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<Empresa> recogerEmpresas() {
        ArrayList<Empresa> empresas = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo las empresas de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $empr in /empresas/empresa " +
                        " let $id:=$empr/id, " +
                        " $salario:=$empr/salarioDisponible, " +
                        " $gastos:=$empr/gastosMensuales, " +
                        " $ganancias:=$empr/gananciasMensuales, " +
                        " $nombre:=$empr/nombre, " +
                        " $ciudad:=$empr/ciudad, " +
                        " $logo:=$empr/logo, " +
                        " $fechaActual:=$empr/fechaActual, " +
                        " $fechaCreacion:=$empr/fechaCreacion " +
                        " return concat($id, '|', $salario, '|', $gastos, '|', $ganancias, '|', $nombre, '|', " +
                        "$ciudad, '|', $logo, '|', $fechaActual, '|', $fechaCreacion)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                            int id = Integer.parseInt(datos[0]);
                            int salario = Integer.parseInt(datos[1]);
                            int gastosMensuales = Integer.parseInt(datos[2]);
                            int gananciasMensuales = Integer.parseInt(datos[3]);
                            String nombre = datos[4];
                            String ciudad = datos[5];
                            String logo = datos[6];
                            LocalDate fechaActual = LocalDate.parse(datos[7], formatter);
                            LocalDate fechaCreacion = LocalDate.parse(datos[8], formatter);

                            empresas.add(new Empresa(id, salario, gastosMensuales, gananciasMensuales, nombre,
                                    ciudad, logo, fechaActual, fechaCreacion));

                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Empresas leídas: " + empresas.size());
                GuardarLogs.logger.log(Level.INFO,  "Empresas leídas: " + empresas.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer las empresas. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer las empresas. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return empresas;
    }

    public static ArrayList<Dato> recogerDatos() {
        ArrayList<Dato> datos = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo las datos de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $dato in /datos/dato " +
                        " let $tipo:=$dato/tipo, " +
                        " $dato:=$dato/dato " +
                        " return concat($tipo, '|', $dato)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] data = resultado.split("\\|");

                            String tipoDato = data[0];
                            String dato = data[1];
                            datos.add(new Dato(tipoDato, dato, 1));
                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Datos leídos: " + datos.size());
                GuardarLogs.logger.log(Level.INFO,  "Datos leídos: " + datos.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer los datos. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer los datos. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return datos;
    }
    
    public static ArrayList<Iniciativa> recogerIniciativas() {
        ArrayList<Iniciativa> iniciativas = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo las iniciativas de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $ini in /iniciativas/iniciativa " +
                        " let $id:=$ini/id, " +
                        " $nombre:=$ini/nombre, " +
                        " $duracion:=$ini/duracion, " +
                        " $nivelNecesario:=$ini/nivelNecesario, " +
                        " $coste:=$ini/coste, " +
                        " $ganancias:=$ini/ganancias " +
                        " return concat($id, '|', $nombre, '|', $duracion, '|', " +
                        "$nivelNecesario, '|', $coste, '|', $ganancias)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            int id = Integer.parseInt(datos[0]);
                            String nombre = datos[1];
                            int duracion = Integer.parseInt(datos[2]);
                            int nivelNecesario = Integer.parseInt(datos[3]);
                            int coste = Integer.parseInt(datos[4]);
                            int ganancias = Integer.parseInt(datos[5]);

                            iniciativas.add(new Iniciativa(id, nombre, duracion, nivelNecesario, coste, ganancias));
                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Iniciativas leídas: " + iniciativas.size());
                GuardarLogs.logger.log(Level.INFO,  "Iniciativas leídas: " + iniciativas.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer las iniciativas. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer las iniciativas. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return iniciativas;
    }
    
    public static ArrayList<IniciativaActiva> recogerIniciativasActivas() {
        ArrayList<IniciativaActiva> iniciativasActivas = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo las iniciativasActivas de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $ini in /iniciativasActivas/iniciativaActiva " +
                        " let $idIniciativa:=$ini/idIniciativa, " +
                        " $idEmpresa:=$ini/idEmpresa, " +
                        " $fechaInicio:=$ini/fechaInicio " +
                        " return concat($idIniciativa, '|', $idEmpresa, '|', $fechaInicio)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                            int idIniciativa = Integer.parseInt(datos[0]);
                            int idEmpresa = Integer.parseInt(datos[1]);
                            LocalDate fechaInicio = LocalDate.parse(datos[2], formatter);

                            iniciativasActivas.add(new IniciativaActiva(idIniciativa, idEmpresa, fechaInicio));
                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Iniciativas activas leídas: " + iniciativasActivas.size());
                GuardarLogs.logger.log(Level.INFO,  "Iniciativas activas leídas: " + iniciativasActivas.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer las iniciativas activas. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer las iniciativas activas. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return iniciativasActivas;
    }

    public static ArrayList<Departamento> recogerDepartamentos() {
        ArrayList<Departamento> departamentos = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo los departamentos de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $dep in /departamentos/departamento " +
                        " let $idEmpresa:=$dep/@idEmpresa, " +
                        " $depNo:=$dep/depNo, " +
                        " $nombre:=$dep/nombre, " +
                        " $nivel:=$dep/nivel " +
                        " return concat($idEmpresa, '|', $depNo, '|', $nombre, '|', $nivel)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada departamento borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            int idEmpresa = Integer.parseInt(datos[0]);
                            int depNo = Integer.parseInt(datos[1]);
                            String nombre = datos[2];
                            int nivel = Integer.parseInt(datos[3]);

                            departamentos.add(new Departamento(idEmpresa, depNo, nombre, nivel));
                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Departamentos leídos: " + departamentos.size());
                GuardarLogs.logger.log(Level.INFO,  "Departamentos leídos: " + departamentos.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer los departamentos. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer los departamentos. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return departamentos;
    }

    public static ArrayList<EmpleadoContratado> recogerEmpleadosContratados() {
        ArrayList<EmpleadoContratado> empleadosContratados = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo los empleados contratados de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $emp in /empleadosContratados/empleadoContratado " +
                        " let "+
                        " $empNo:=$emp/empNo, " +
                        " $dni:=$emp/dni, " +
                        " $nombre:=$emp/nombre, " +
                        " $apellido:=$emp/apellido, " +
                        " $edad:=$emp/edad, " +
                        " $fechaNac:=$emp/fechaNac, " +
                        " $salario:=$emp/salario, " +
                        " $depNo:=$emp/depNo, " +
                        " $avatar:=$emp/avatar " +
                        " return concat($empNo, '|', $dni, '|', $nombre, '|', $apellido, '|', $edad, '|', $fechaNac, '|', $salario, '|', $depNo, '|', $avatar)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada empleadoContratado borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                            int empNo = Integer.parseInt(datos[0]);
                            String dni = datos[1];
                            String nombre = datos[2];
                            String apellido = datos[3];
                            int edad = Integer.parseInt(datos[4]);
                            LocalDate fechaNac = LocalDate.parse(datos[5], formatter);
                            int salario = Integer.parseInt(datos[6]);
                            int depNo = Integer.parseInt(datos[7]);
                            String avatar = datos[8];

                            empleadosContratados.add(new EmpleadoContratado(empNo, dni, nombre, apellido, edad, fechaNac, salario, depNo, avatar));
                        } catch (NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                            ignored.printStackTrace();
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Empleados contratados leídos: " + empleadosContratados.size());
                GuardarLogs.logger.log(Level.INFO,  "Empleados contratados leídos: " + empleadosContratados.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer los empleados contratados. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer los empleados contratados. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return empleadosContratados;
    }

    public static ArrayList<EmpleadoDisponible> recogerEmpleadosDisponibles() {
        ArrayList<EmpleadoDisponible> empleadosDisponibles = new ArrayList<>();
        if (conectar() != null) {
            try {
                System.out.println(nombre + "Leyendo los empleados disponibles de la BBDD");
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                String query = "" +
                        "for $emp in /empleadosDisponibles/empleadoDisponible " +
                        " let "+
                        " $id:=$emp/id, " +
                        " $dni:=$emp/dni, " +
                        " $nombre:=$emp/nombre, " +
                        " $apellido:=$emp/apellido, " +
                        " $edad:=$emp/edad, " +
                        " $fechaNac:=$emp/fechaNac, " +
                        " $salario:=$emp/salario, " +
                        " $avatar:=$emp/avatar, " +
                        " $departamentoDeseado:=$emp/departamentoDeseado, " +
                        " $idEmpresa:=$emp/idEmpresa " +
                        " return concat($id, '|', $dni, '|', $nombre, '|', $apellido, '|', $edad, '|', $fechaNac, '|', $salario, '|', $avatar, '|', $departamentoDeseado, '|', $idEmpresa)"; // TODO GUARDAR QUERY

                ResourceSet result = servicio.query(query);
                ResourceIterator i = result.getIterator();
                if (i.hasMoreResources()) {
                    while (i.hasMoreResources()) {
                        try {
                            // Por cada empleadoDisponible borrar los empleados
                            Resource r = i.nextResource();
                            String resultado = (String) r.getContent();
                            String[] datos = resultado.split("\\|");

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                            int id = Integer.parseInt(datos[0]);
                            String dni = datos[1];
                            String nombre = datos[2];
                            String apellido = datos[3];
                            int edad = Integer.parseInt(datos[4]);
                            LocalDate fechaNac = LocalDate.parse(datos[5], formatter);
                            int salario = Integer.parseInt(datos[6]);
                            String departamentoDeseado = datos[7];
                            String avatar = datos[8];
                            int idEmpersa = Integer.parseInt(datos[9]);

                            empleadosDisponibles.add(new EmpleadoDisponible(id, dni, nombre, apellido, edad, fechaNac, salario, departamentoDeseado, avatar, idEmpersa));
                        } catch (IndexOutOfBoundsException | NumberFormatException | PatternSyntaxException | ClassCastException ignored) {
                        }
                    }
                }
                coleccion.close();
                System.out.println(nombre + "Empleados disponibles leídos: " + empleadosDisponibles.size());
                GuardarLogs.logger.log(Level.INFO,  "Empleados disponibles leídos: " + empleadosDisponibles.size());
            } catch (Exception e) {
                System.out.println(nombre + "Error al leer los empleados disponibles. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE,  "Error al leer los empleados disponibles. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }
        return empleadosDisponibles;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INSERTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INSERTAR EMPLEADO CONTRATADO
    public static boolean insertarEmpleadoContratado(EmpleadoContratado empleado) {
        boolean insertado = false;

        if (conectar() != null) {
            int empNo = empleado.getEmpNo();
            if (!comprobarEmpleadoContratado(empNo, coleccion)) {
                try {
                    System.out.println(nombre + "Insertando nuevo empleado contratado: " + empNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empleado.queryUpdateInsert(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    insertado = true;
                    System.out.println(nombre + "Nuevo empleado contratado " + empNo + " insertado");
                    GuardarLogs.logger.log(Level.FINEST,  "Nuevo empleado contratado " + empNo + " insertado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al insertar el empleado contratado " + empNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al insertar el empleado contratado " + empNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "Se ha intentado insertar el empleado contratado " + empNo + " pero ya existe un empleado con ese empNo");
                GuardarLogs.logger.log(Level.INFO,  "Se ha intentado insertar el empleado contratado " + empNo + " pero ya existe un empleado con ese empNo");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return insertado;
    }

    // INSERTAR EMPLEADO DISPONIBLE
    public static boolean insertarEmpleadoDisponible(EmpleadoDisponible empleado) {
        boolean insertado = false;

        if (conectar() != null) {
            int id = empleado.getId();
            if (!comprobarEmpleadoDisponible(id, coleccion)) {
                try {
                    System.out.println(nombre + "Insertando nuevo empleado disponible: " + id);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empleado.queryUpdateInsert(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    insertado = true;
                    System.out.println(nombre + "Nuevo empleado disponible " + id + " insertado");
                    GuardarLogs.logger.log(Level.FINEST,  "Nuevo empleado disponible " + id + " insertado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al insertar el empleado disponible " + id + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al insertar el empleado disponible " + id + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "Se ha intentado insertar el empleado disponible " + id + " pero ya existe un empleado con ese id");
                GuardarLogs.logger.log(Level.INFO,  "Se ha intentado insertar el empleado disponible " + id + " pero ya existe un empleado con ese id");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return insertado;
    }

    // INSERTAR DEPARTAMENTO
    public static boolean insertarDepartamento(Departamento departamento) {
        boolean insertado = false;

        if (conectar() != null) {
            int depNo = departamento.getDepNo();
            if (!comprobarDepartamento(depNo, coleccion)) {
                try {
                    System.out.println(nombre + "Insertando nuevo departamento: " + depNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = departamento.queryUpdateInsert(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    insertado = true;
                    System.out.println(nombre + "Nuevo departamento " + depNo + " insertado");
                    GuardarLogs.logger.log(Level.FINEST,  "Nuevo departamento  " + depNo + " insertado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al insertar el departamento " + depNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al insertar el departamento  " + depNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "Se ha intentado insertar el departamento " + depNo + " pero ya existe un departamento con ese id");
                GuardarLogs.logger.log(Level.INFO,  "Se ha intentado insertar el departamento " + depNo + " pero ya existe un departamento con ese id");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return insertado;
    }

    // INSERTAR EMPRESA
    public static boolean insertarEmpresa(Empresa empresa) {
        boolean insertado = false;

        if (conectar() != null) {
            int id = empresa.getId();
            if (!comprobarEmpresa(id, coleccion)) {
                try {
                    System.out.println(nombre + "Insertando nuevo empresa disponible: " + id);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empresa.queryUpdateInsert(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    insertado = true;
                    System.out.println(nombre + "Nueva empresa " + id + " insertada");
                    GuardarLogs.logger.log(Level.FINEST,  "Nueva empresa " + id + " insertada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al insertar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al insertar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "Se ha intentado insertar la empresa " + id + " pero ya existe una empresa con ese id");
                GuardarLogs.logger.log(Level.INFO,  "Se ha intentado insertar la empresa " + id + " pero ya existe una empresa con ese id");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return insertado;
    }

    // INSERTAR INICIATIVA ACTIVA
    public static boolean insertarIniciativaActiva(IniciativaActiva iniciativaActiva) {
        boolean insertado = false;

        if (conectar() != null) {
            int idIniciativa = iniciativaActiva.getIdIniciativa();
            int idEmpresa = iniciativaActiva.getIdEmpresa();
            if (!comprobarIniciativaActiva(idIniciativa, idEmpresa, coleccion)) {
                try {
                    System.out.println(nombre + "Insertando nuevo iniciativaActiva disponible.");
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = iniciativaActiva.queryUpdateInsert(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    insertado = true;
                    System.out.println(nombre + "Nueva iniciativaActiva insertada");
                    GuardarLogs.logger.log(Level.FINEST,  "Nueva iniciativaActiva insertada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al insertar la iniciativaActiva. Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al insertar la iniciativaActiva. Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "Se ha intentado insertar la iniciativa activa pero ya existe esa iniciativa en esa empresa");
                GuardarLogs.logger.log(Level.INFO,  "Se ha intentado insertar la iniciativa activa pero ya existe esa iniciativa en esa empresa");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return insertado;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REPLACES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EDITAR EDAD EMPLEADO CONTRATADO (ha cumplido años)
    public static boolean editarEdadEmpleadoContratado(EmpleadoContratado empleado) {
        boolean editado = false;

        if (conectar() != null) {
            int empNo = empleado.getEmpNo();
            if (comprobarEmpleadoContratado(empNo, coleccion)) {
                try {
                    System.out.println(nombre + "Editando la edad del empleado: " + empNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empleado.queryUpdateReplaceEdad(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    editado = true;
                    System.out.println(nombre + "Edad del empleado " + empNo + " editada");
                    GuardarLogs.logger.log(Level.FINEST,  "Edad del empleado " + empNo + " editada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al editar la edad del empleado " + empNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al edad del empleado " + empNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "El empleado " + empNo + " se intenta editar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "El empleado " + empNo + " se intenta editar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return editado;
    }

    // EDITAR NIVEL DEPARTAMENTO
    public static boolean editarNivelDepartamento(Departamento departamento) {
        boolean editado = false;

        if (conectar() != null) {
            int empNo = departamento.getDepNo();
            if (comprobarDepartamento(empNo, coleccion)) {
                try {
                    System.out.println(nombre + "Editando la edad del departamento: " + empNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = departamento.queryUpdateReplaceNivel(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    editado = true;
                    System.out.println(nombre + "Nivel del departamento " + empNo + " editado");
                    GuardarLogs.logger.log(Level.FINEST,  "Nivel del departamento " + empNo + " editado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al editar el nivel del departamento " + empNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error el nivel del departamento " + empNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "El departamento " + empNo + " se intenta editar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "El departamento " + empNo + " se intenta editar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return editado;
    }

    // EDITAR EMPRESA ENTERA (cambiar nombre / ciudad por ejemplo)
    public static boolean editarEmpresa(Empresa empresa) {
        boolean editado = false;

        if (conectar() != null) {
            int id = empresa.getId();

            if (comprobarEmpresa(id, coleccion)) {
                try {
                    System.out.println(nombre + "Editando la empresa cuyo id es: " + id);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empresa.queryUpdateReplace(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    editado = true;
                    System.out.println(nombre + "Empresa " + id + " editada");
                    GuardarLogs.logger.log(Level.FINEST,  "Empresa " + id + " editada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al editar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al editar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "La empresa " + id + " se intenta editar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "La empresa " + id + " se intenta editar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return editado;
    }

    // EDITAR EMPRESA SOLO LA FECHA
    public static boolean editarFechaEmpresa(Empresa empresa) {
        boolean editado = false;

        if (conectar() != null) {
            int id = empresa.getId();

            if (comprobarEmpresa(id, coleccion)) {
                try {
                    System.out.println(nombre + "Editando la fecha actual de la empresa cuyo id es: " + id);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = empresa.queryUpdateReplaceFecha(); // TODO GUARDAR QUERY
                    servicio.query(query);

                    editado = true;
                    System.out.println(nombre + "Fecha actual de la empresa " + id + " editada");
                    GuardarLogs.logger.log(Level.FINEST,  "Fecha actual de la empresa " + id + " editada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al editar la fecha actual de la empresa " + id + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al editar la fecha actual de la empresa " + id + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "La empresa " + id + " se intenta editar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "La empresa " + id + " se intenta editar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return editado;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DELETES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BORRAR EMPRESA + BORRAR LO QUE LE CORRESPONDE (on delete cascade)
    public static boolean borrarEmpresa(int id) {
        boolean eliminado = false;

        if (conectar() != null) {
            if (comprobarEmpresa(id, coleccion)) {
                try {
                    System.out.println(nombre + "Eliminando la empresa cuyo id es: " + id);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = "update delete /empresas/empresa[id=" + id + "]";  // TODO GUARDAR QUERY
                    servicio.query(query);

                    eliminado = true;

                    // Borramos aquello que va relacionado con la empresa para que no queden datos colgantes que nunca se usarían
                    borrarEmpleadosDepartamentosEmpresa(id, servicio);
                    borrarDepartamentosEmpresa(id, servicio);
                    borrarEmpleadosDisponiblesEmpresa(id, servicio);
                    borrarIniciativasActivasEmpresa(id, servicio);

                    System.out.println(nombre + "Empresa " + id + " eliminada");
                    GuardarLogs.logger.log(Level.FINEST,  "Empresa " + id + " eliminada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al eliminar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al eliminar la empresa " + id + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "La empresa " + id + " se intenta eliminar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "La empresa " + id + " se intenta eliminar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return eliminado;
    }

    // BORRAR LAS INICIATIVAS DE UNA EMPRESA
    private static void borrarIniciativasActivasEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null) {
            conectar();
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        }
        String query = "update delete /iniciativasActivas/iniciativaActiva[idEmpresa=" + idEmpresa + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "IniciativasActivas de la empresa " + idEmpresa + " eliminados");
    }

    // BORRAR LOS EMPLEADOS DISPONIBLES DE UNA EMPRESA
    public static void borrarEmpleadosDisponiblesEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null) {
            conectar();
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        }
        String query = "update delete /empleadosDisponibles/empleadoDisponible[idEmpresa=" + idEmpresa + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "EmpleadosDisponibles de la empresa " + idEmpresa + " eliminados");
    }

    // BORRAR EMPLEADO DISPONIBLE DIRECTAMENTE
    public static void borrarEmpleadoDisponible(int idEmpleado, XPathQueryService servicio) {
        try {
            if (servicio == null) {
                conectar();
                servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
            }
            String query = "update delete /empleadosDisponibles/empleadoDisponible[id=" + idEmpleado + "]"; // TODO GUARDAR QUERY
            servicio.query(query);
            System.out.println(nombre + "Empleado Disponible " + idEmpleado + " eliminado");
        } catch (XMLDBException ignored) {}
    }

    // BORRAR LOS DEPARTAMENTOS DE UNA EMPRESA
    private static void borrarDepartamentosEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null) {
            conectar();
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        }
        String query = "update delete /departamentos/departamento[@idEmpresa=\"" + idEmpresa + "\"]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "Departamentos de la empresa " + idEmpresa + " eliminados");
    }

    // BORRAR LOS EMPLEADOS DE CADA DEPARTAMENTO DE UNA EMPRESA
    private static void borrarEmpleadosDepartamentosEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null) {
            conectar();
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        }
        // Sacar el id de cada departamento de una empresa
        String query = "for $dep in /departamentos/departamento[@idEmpresa=\"" + idEmpresa + "\"] let $depNo:=$dep/depNo return $depNo/text()"; // TODO GUARDAR QUERY

        ResourceSet result = servicio.query(query);
        ResourceIterator i = result.getIterator();
        if (i.hasMoreResources()) {
            while (i.hasMoreResources()) {
                try {
                    // Por cada departamento borrar los empleados
                    Resource r = i.nextResource();
                    String depNo = (String) r.getContent();
                    borrarEmpleadosDepartamento(Integer.parseInt(depNo), servicio);
                } catch (NumberFormatException ignored) { }
            }
        }
    }

    // BORRAR LOS EMPLEADOS DE UN DEPARTAMENTO
    public static void borrarEmpleadosDepartamento(int depNo, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null) {
            conectar();
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        }
        String query = "update delete /empleadosContratados/empleadoContratado[depNo=" + depNo + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "Empleados del departamento " + depNo + " eliminados");
    }

    // BORRAR UN DEPARTAMENTO + BORRAR SUS EMPLEADOS (on delete cascade)
    public static boolean borrarDepartamento(int depNo) {
        boolean eliminado = false;

        if (conectar() != null) {
            if (comprobarDepartamento(depNo, coleccion)) {
                try {
                    System.out.println(nombre + "Eliminando el departamento cuyo depNo es: " + depNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = "update delete /departamentos/departamento[depNo=" + depNo + "]";  // TODO GUARDAR QUERY
                    servicio.query(query);
                    
                    eliminado = true;

                    // Borramos aquello que va relacionado con el departamento para que no queden datos colgantes que nunca se usarían
                    borrarEmpleadosDepartamento(depNo, servicio);

                    System.out.println(nombre + "Departamento " + depNo + " eliminado");
                    GuardarLogs.logger.log(Level.FINEST,  "Departamento " + depNo + " eliminado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al eliminar el departamento " + depNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al eliminar el departamento " + depNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "El departamento " + depNo + " se intenta eliminar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "El departamento " + depNo + " se intenta eliminar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return eliminado;
    }

    // BORRAR EMPLEADO CONTRATADO (despedir)
    public static boolean borrarEmpleadoContratado(int empNo) {
        boolean eliminado = false;

        if (conectar() != null) {
            if (comprobarEmpleadoContratado(empNo, coleccion)) {
                try {
                    System.out.println(nombre + "Eliminando el empleadoContratado cuyo empNo es: " + empNo);
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = "update delete /empleadosContratados/empleadoContratado[empNo=" + empNo + "]";  // TODO GUARDAR QUERY
                    servicio.query(query);

                    eliminado = true;

                    System.out.println(nombre + "EmpleadoContratado " + empNo + " eliminado");
                    GuardarLogs.logger.log(Level.FINEST,  "EmpleadoContratado " + empNo + " eliminado");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al eliminar el empleadoContratado " + empNo + ". Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al eliminar el empleadoContratado " + empNo + ". Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "El empleadoContratado " + empNo + " se intenta eliminar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "El empleadoContratado " + empNo + " se intenta eliminar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return eliminado;
    }
    
    public static boolean borrarIniciativaActiva(int idIniciativa, int idEmpresa) {
        boolean eliminado = false;

        if (conectar() != null) {
            if (comprobarIniciativaActiva(idIniciativa, idEmpresa, coleccion)) {
                try {
                    System.out.println(nombre + "Eliminando la iniciativa activa");
                    XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

                    String query = "update delete /iniciativasActivas/iniciativaActiva[idIniciativa=" + idIniciativa + " and idEmpresa=" + idEmpresa + "]";  // TODO GUARDAR QUERY
                    servicio.query(query);

                    eliminado = true;

                    System.out.println(nombre + "IniciativaActiva eliminada");
                    GuardarLogs.logger.log(Level.FINEST,  "IniciativaActiva eliminada");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al eliminar la iniciativa activa. Error: " + e.getLocalizedMessage());
                    GuardarLogs.logger.log(Level.SEVERE,  "Error al eliminar la iniciativa activa. Error: " + e.getLocalizedMessage());
                }
            } else {
                System.out.println(nombre + "La iniciativa activa se intenta eliminar pero no existe no existe");
                GuardarLogs.logger.log(Level.INFO,  "La iniciativa activa se intenta eliminar pero no existe no existe");
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return eliminado;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // COMPROBACIONES DE QUE EXISTEN LOS ELEMENTOS SOBRE LOS QUE SE DESEA ACTUAR
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static boolean comprobarEmpresa(int id, Collection col) {
        boolean cerrar = false;
        if (col == null) {
            conectar();
            col = coleccion;
            cerrar = true;
        }

        if (col != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                String query = "/empresas/empresa[id=" + id + "]"; // TODO GUARDAR QUERY
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();

                if (cerrar)
                    col.close();

                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return false;
    }

    private static boolean comprobarDepartamento(int depNo, Collection col) {
        boolean cerrar = false;
        if (col == null) {
            conectar();
            col = coleccion;
            cerrar = true;
        }

        if (col != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                String query = "/departamentos/departamento[depNo=" + depNo + "]"; // TODO GUARDAR QUERY
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();

                if (cerrar)
                    col.close();

                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return false;
    }

    private static boolean comprobarEmpleadoContratado(int empNo, Collection col) {
        boolean cerrar = false;
        if (col == null) {
            conectar();
            col = coleccion;
            cerrar = true;
        }

        if (col != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un empleadoContratado
                String query = "/empleadosContratados/empleadoContratado[empNo=" + empNo + "]"; // TODO GUARDAR QUERY
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();

                if (cerrar)
                    col.close();

                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return false;
    }

    private static boolean comprobarEmpleadoDisponible(int id, Collection col) {
        boolean cerrar = false;
        if (col == null) {
            conectar();
            col = coleccion;
            cerrar = true;
        }

        if (col != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un empleadoDisponible
                String query = "/empleadosDisponibles/empleadoDisponible[id=" + id + "]"; // TODO GUARDAR QUERY
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();

                if (cerrar)
                    col.close();

                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return false;
    }

    private static boolean comprobarIniciativaActiva(int idIniciativa, int idEmpresa, Collection col) {
        boolean cerrar = false;
        if (col == null) {
            conectar();
            col = coleccion;
            cerrar = true;
        }

        if (col != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un iniciativaActiva
                String query = "/iniciativasActivas/iniciativaActiva[idIniciativa=" + idIniciativa + " and idEmpresa=" + idEmpresa +"]"; // TODO GUARDAR QUERY
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();

                if (cerrar)
                    col.close();

                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD");
        }

        return false;
    }

}