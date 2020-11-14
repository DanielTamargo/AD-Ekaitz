
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

public class PruebasEmpleYDepart {

    static String nombreColeccion = "Practica2DanielTamargo";

    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/" + nombreColeccion; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = "admin"; //Clave
    static Collection col = null;

    public static void main(String[] args) throws XMLDBException {

        crearColeccion();
        System.out.println();

        System.out.println("Antes de insertar los XML:");
        mostrarRecursosColeccion();

        System.out.println();
        insertarXMLPartidas();
        System.out.println();

        System.out.println("Después de insertar los XML:");
        mostrarRecursosColeccion();

    }

    public static void mostrarRecursosColeccion() {
        if (conectar() != null) {
            try {
                // Listamos la colección para ver que en efecto se ha añadido
                for (String colRe : col.listResources())
                    System.out.println(colRe);
            } catch (XMLDBException ignored) {
                System.out.println("Error al mostrar el listado");
            }
        } else {
            System.out.println("Error al conectar con la BBDD");
        }
    }

    public static void insertarXMLPartidas() {
        if (conectar() != null) {
            try {
                int recursosAntes = col.listResources().length;
                int recursosDespues;

                // DOCUMENTACIÓN: http://www.exist-db.org/exist/apps/doc/devguide_xmldb#use-xmldb (storeResource)

                // Inicializamos el recurso
                XMLResource res = null;

                // Creamos el recurso -> recibe 2 parámetros tipo String:
                //                          s: nombre.xml (si lo dejamos null, pondrá un nombre aleatorio)
                //                          s1: tipo recurso (en este caso, siempre será XMLResource)
                res = (XMLResource) col.createResource("partidas.xml", "XMLResource");

                // Elegimos el fichero .xml que queremos añadir a la colección
                File f = new File("./partidas.xml");

                // Fijamos como contenido ese archivo .xml elegido
                res.setContent(f);
                col.storeResource(res); // Lo añadimos a la colección

                recursosDespues = col.listResources().length;

                System.out.println("Recursos XML añadidos a la colección: " + (recursosDespues - recursosAntes));

            } catch (XMLDBException ignored) {
                System.out.println("Error al crear el recurso");
            }
        } else {
            System.out.println("Error al conectar con la BBDD");
        }
    }

    public static Collection conectar() {

        try {
            // Preparamos, conectamos y cargamos la colección que queramos utilizar
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instanciar la BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd); // Cargar la colección en cuestión

            // Devolvemos la conexión
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            e.printStackTrace();
        }
        return null;
    }

    public static void crearColeccion() {
        if (conectar() == null) {
            try {
                System.out.println("La colección '" + nombreColeccion + "' no existe");

                // DOCUMENTACIÓN: https://www.exist-db.org/exist/apps/doc/xmldb
                // Accedemos a la colección general donde crearemos la colección a usar en la práctica
                String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db";
                Class cl = Class.forName(driver); // Cargar del driver
                Database database = (Database) cl.newInstance(); // Instanciar la BD
                DatabaseManager.registerDatabase(database); // Registro del driver
                col = DatabaseManager.getCollection(URI, usu, usuPwd); // Cargar la colección en cuestión

                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
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
                col.close();
                System.out.println("Colección '" + nombreColeccion + "' creada");

                //TODO generarDatosBase();
            } catch (XMLDBException | ClassNotFoundException e) { System.out.println("ERROR AL CONSULTAR DOCUMENTO.");
            } catch (IllegalAccessException | InstantiationException e) { e.printStackTrace(); }
        }
    }

    /*
    declare namespace rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#";

import module namespace xmldb="http://exist-db.org/xquery/xmldb";

let $log-in := xmldb:login("/db", "admin", "")
let $create-collection := xmldb:create-collection("/db", "output")
for $record in doc('/db/records.rdf')/rdf:RDF/*
let $split-record :=
    <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
        {$record}
    </rdf:RDF>
let $about := $record/@rdf:about
let $filename := util:hash($record/@rdf:about/string(), "md5") || ".xml"
return
    xmldb:store("/db/output", $filename, $split-record)
     */
    private static void listardep() {
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $dep in /departamentos/DEP_ROW return $dep");
                // recorrer los datos del recurso.  
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                   // System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    //System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    private static void insertardep(int dep, String dnombre, String loc) {

        //Caso concreto: sabemos cuáles son los nodos
        String nuevodep = "<DEP_ROW><DEPT_NO>" + dep + "</DEPT_NO>"
                + "<DNOMBRE>" + dnombre + "</DNOMBRE><LOC>" + loc + "</LOC>"
                + "</DEP_ROW>";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                System.out.printf("Inserto: %s \n", nuevodep);
                //Consulta para insertar --> update insert ... into
                ResourceSet result = servicio.query("update insert " + nuevodep + " into /departamentos");
                col.close(); //borramos 
                System.out.println("Dep insertado.");
            } catch (Exception e) {
                System.out.println("Error al insertar empleado.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }
    }

    private static void consultardep(int dep) {
        if (comprobardep(dep)) {
            if (conectar() != null) {
                try {
                    XPathQueryService servicio;
                    servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Preparamos la consulta
                    ResourceSet result = servicio.query("/departamentos/DEP_ROW[DEPT_NO=" + dep + "]");
                    // recorrer los datos del recurso.  
                    ResourceIterator i;
                    i = result.getIterator();
                    if (!i.hasMoreResources()) {
                        System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                    } else {
                        Resource r = i.nextResource();
                        System.out.println("--------------------------------------------");
                        System.out.println((String) r.getContent());
                    }
                    col.close();
                } catch (XMLDBException e) {
                    System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("El departamento NO EXISTE.");
        }

    }

    private static void modificardep(int dep) {

        if (comprobardep(dep)) {

            if (conectar() != null) {
                try {
                    System.out.printf("Actualizo el departamento: %s\n", dep);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para modificar/actualizar un valor --> update value
                    ResourceSet result = servicio.query(
                            "update value /departamentos/DEP_ROW[DEPT_NO=" + dep + "]/DNOMBRE with data('NUEVO NOMBRE') ");

                    col.close();
                    System.out.println("Dep actualizado.");
                } catch (Exception e) {
                    System.out.println("Error al actualizar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("El departamento NO EXISTE.");
        }
    }

    private static void borrarregistrodep(int dep) {
        if (comprobardep(dep)) {
            if (conectar() != null) {
                try {
                    System.out.printf("Borro el departamento: %s\n", dep);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para borrar un departamento --> update delete
                    ResourceSet result = servicio.query(
                            "update delete /departamentos/DEP_ROW[DEPT_NO=" + dep + "]");
                    col.close();
                    System.out.println("Dep borrado.");
                } catch (Exception e) {
                    System.out.println("Error al borrar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("El departamento NO EXISTE.");
        }

    }

    private static boolean comprobardep(int dep) {
        //Devuelve true si el dep existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/departamentos/DEP_ROW[DEPT_NO=" + dep + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }// comprobardep

    public static void consulta_ejemplo() throws XMLDBException  {
        String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
        Collection col = null; // Colección
        String URI="xmldb:exist://localhost:8083/exist/xmlrpc/db/ColeccionPruebas"; //URI colección
        String usu="admin"; //Usuario
        String usuPwd="admin"; //Clave
        try {
            Class cl = Class.forName(driver); //Cargar del driver
            Database database = (Database) cl.newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver

            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if(col == null)
                System.out.println(" *** LA COLECCION NO EXISTE. ***");

            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            //ResourceSet result = servicio.query ("for $em in /EMPLEADOS/EMP_ROW[DEPT_NO=10] return $em");
            //query="for $em in doc("file:///C:/users/usuario/Desktop/empleados.xml")/EMPLEADOS/EMP_ROW[DEPT_NO=10] return $em";
            String query = "for $emp in /EMPLEADOS/EMP_ROW let $nom:=$emp/APELLIDO, $ofi:=$emp/OFICIO order by $emp/OFICIO return <APE_OFI> {($nom, ' ', $ofi)} </APE_OFI>";
            ResourceSet result = servicio.query (query);
            System.out.println("Se han obtenido " + result.getSize() + " elementos.");
            // recorrer los datos del recurso.
            ResourceIterator i;
            i = result.getIterator();
            if (!i.hasMoreResources())
                System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
            while (i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println((String) r.getContent());}

            col.close(); //cerramos
        } catch (Exception e) {
            System.out.println("Error al inicializar la BD eXist");
            e.printStackTrace();    }
    }// FIN verempleados10
}// fin clase

