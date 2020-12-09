package com.tamargo.exist;

import com.tamargo.miscelanea.GuardarLogs;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

public class Coleccion {

    private final static String nombre = "[Colección] ";

    private final static String nombreColeccion = "Practica2DanielTamargo";

    private final static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist

    //todo fichero que controle que es la primera vez que se inicia? o que ponga un 0 si da error de intentar conectar con servicio exist
    //todo ventana personalizada para que ponga su puerto, usuario y contraseña y que se almacenen en un fichero?
    private final static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/" + nombreColeccion; //URI colección
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

            } catch (XMLDBException | ClassNotFoundException e) {
                System.out.println("ERROR AL CONSULTAR DOCUMENTO.");
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear la colección. Error: " + e.getLocalizedMessage());
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
    // BORRAR EMPRESA + BORRAR LO QUE LE CORRESPONDE (SIMULANDO DELETE CASCADE)
    public static boolean borrarEmpresa(int id) {
        boolean eliminado = false;

        if (comprobarEmpresa(id)) {
            if (conectar() != null) {
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

                    System.out.println(nombre + "Empresa eliminada.");
                    coleccion.close();
                } catch (Exception e) {
                    System.out.println(nombre + "Error al eliminar la empresa.");
                    e.printStackTrace();
                }
            } else {
                System.out.println(nombre + "Error al conectar con la BBDD.");
            }
        } else {
            System.out.println(nombre + "La empresa no existe.");
        }

        return eliminado;
    }

    // Borramos las iniciativas activas de una empresa
    private static void borrarIniciativasActivasEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null)
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

        String query = "update delete /iniciativasActivas/iniciativaActiva[idEmpresa=" + idEmpresa + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "IniciativasActivas de la empresa " + idEmpresa + " eliminados");
    }

    // Borramos los empleados disponibles de una empresa
    private static void borrarEmpleadosDisponiblesEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null)
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

        String query = "update delete /empleadosDisponibles/empleadoDisponible[idEmpresa=" + idEmpresa + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "EmpleadosDisponibles de la empresa " + idEmpresa + " eliminados");
    }

    // Borramos los departamentos de una empresa
    private static void borrarDepartamentosEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null)
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

        String query = "update delete /departamentos/departamento[idEmpresa=" + idEmpresa + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "Departamentos de la empresa " + idEmpresa + " eliminados");
    }

    // Borramos los empleados de los departamentos de una empresa
    private static void borrarEmpleadosDepartamentosEmpresa(int idEmpresa, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null)
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

        // Sacar el id de cada departamento de una empresa
        String query = "for $dep in /departamentos/departamento[idEmpresa=" + idEmpresa + "] let $depNo:=$dep/depNo return $depNo/text()"; // TODO GUARDAR QUERY

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

    // Borramos los empleados de un departamento (se usará al eliminar los departamentos de una empresa o al eliminar un departamento concreto)
    private static void borrarEmpleadosDepartamento(int depNo, XPathQueryService servicio) throws XMLDBException {
        if (servicio == null)
            servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
        String query = "update delete /empleadosContratados/empleadoContratado[depNo=" + depNo + "]"; // TODO GUARDAR QUERY
        servicio.query(query);
        System.out.println(nombre + "Empleados del departamento " + depNo + " eliminados");
    }






    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // COMPROBACIONES DE QUE EXISTEN LOS ELEMENTOS A ELIMINAR
    private static boolean comprobarEmpresa(int id) {
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                String query = "/empresas/empresa[id=" + id + "]";
                ResourceSet result = servicio.query(query);
                ResourceIterator i;
                i = result.getIterator();
                coleccion.close();

                // TODO GUARDAR QUERY
                return i.hasMoreResources();
            } catch (Exception e) {
                System.out.println(nombre + "Error al consultar. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al consultar. Error: " + e.getLocalizedMessage());
                // e.printStackTrace();
            }
        } else {
            System.out.println(nombre + "Error al conectar con la BBDD.");
        }

        return false;
    }

}