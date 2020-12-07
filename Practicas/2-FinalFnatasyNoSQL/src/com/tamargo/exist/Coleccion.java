package com.tamargo.exist;

import com.tamargo.GuardarLogs;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import java.io.File;
import java.util.logging.Level;

public class Coleccion {

    private static final String nombreColeccion = "Practica2DanielTamargo";

    private final static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist

    private final static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/" + nombreColeccion; //URI colección
    private final static String usu = "admin"; //Usuario
    private final static String usuPwd = "admin"; //Clave

    public static Collection coleccion = conectar();

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

            if (coleccion == null)
                crearColeccion();

            System.out.println(coleccion.getName());

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

        return crearColeccion();
    }

    public static Collection crearColeccion() {
        Collection col = null;

        if (coleccion == null) {
            try {
                System.out.println("La colección '" + nombreColeccion + "' no existe");

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
                System.out.println("Colección '" + nombreColeccion + "' creada");

                //TODO generarDatosBase();

                col = coleccion;
            } catch (XMLDBException | ClassNotFoundException e) {
                System.out.println("ERROR AL CONSULTAR DOCUMENTO.");
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear la colección. Error: " + e.getLocalizedMessage());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear la colección. Error: " + e.getLocalizedMessage());
            }
        } else {
            col = coleccion;
        }

        return col;
    }


    // TODO RECIBIR NOMBRE DEL XML + ARRAYLIST DE OBJETOS A GUARDAR
    //  falta por tejerlo bien
    public static void insertarXML() {
        if (conectar() != null) {
            try {
                int recursosAntes = coleccion.listResources().length;
                int recursosDespues;

                // DOCUMENTACIÓN: http://www.exist-db.org/exist/apps/doc/devguide_xmldb#use-xmldb (storeResource)

                // Inicializamos el recurso
                XMLResource res = null;

                // Creamos el recurso -> recibe 2 parámetros tipo String:
                //                          s: nombre.xml (si lo dejamos null, pondrá un nombre aleatorio)
                //                          s1: tipo recurso (en este caso, siempre será XMLResource)
                res = (XMLResource) coleccion.createResource("partidas.xml", "XMLResource");

                // Elegimos el fichero .xml que queremos añadir a la colección
                File f = new File("./partidas.xml");

                // Fijamos como contenido ese archivo .xml elegido
                res.setContent(f);
                coleccion.storeResource(res); // Lo añadimos a la colección

                recursosDespues = coleccion.listResources().length;

                System.out.println("Recursos XML añadidos a la colección: " + (recursosDespues - recursosAntes));

            } catch (XMLDBException e) {
                System.out.println("Error al crear el recurso. Error: " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al crear el recurso. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println("Error al conectar con la BBDD");
        }
    }

    /**
     * Muestra los recursos (los nombres de los xml) de la colección
     */
    public static void mostrarRecursosColeccion() {
        if (conectar() != null) {
            try {
                // Listamos la colección para ver que en efecto se ha añadido
                for (String colRe : coleccion.listResources())
                    System.out.println(colRe);
            } catch (XMLDBException e) {
                System.out.println("Error al mostrar el listado. " + e.getLocalizedMessage());
                GuardarLogs.logger.log(Level.SEVERE, "Error al mostrar el listado. Error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println("Error al conectar con la BBDD");
        }
    }

}
