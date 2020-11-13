
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

    public static final String nombrefichero = "AleatorioEmple.dat"; //Si tuvi�ramos que usar un fichero
    static Scanner teclado = new Scanner(System.in);
    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/ColeccionPruebas"; //URI colecci�n   
    static String usu = "admin"; //Usuario
    static String usuPwd = "admin"; //Clave
    static Collection col = null;

    public static void main(String[] args) throws XMLDBException {

        pruebas();


    }//fin main
////////////////////////////////////////////////////////////////////////////////////

    public static void pruebas() {
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $partida in /listaPartidas return $partida");
                // recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println("--------------------------------------------");
                    System.out.println((String) r.getContent());
                }
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        }
    }

    private static void dibujamenu() {
        System.out.println("............................................................\n"
                + ".  1 Listar todos los departamentos. \n"
                + ".  2 Insertar un departamento (inserta 21, 'Departamento 21','Vitoria-Gasteiz'.  \n"
                + ".  3 Consultar un departamento.\n"
                + ".  4 Modificar un departamento (cambia el nombre por 'NUEVO NOMBRE').\n"
                + ".  5 Borrar un departamento.\n"
                + ".  6 Consultas de ejemplo.\n"
                + ".  0 SALIR.\n"
                + "............................................................\n");

    } // fin dibujamenu

    public static Collection conectar() {

        try {
            Class cl = Class.forName(driver); //Cargar del driver 
            Database database = (Database) cl.newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd);


            System.out.println("Antes de a�adir:");
            for (String colRe: col.listResources())
                System.out.println(colRe);
            System.out.println();

            // DOCUMENTACI�N: http://www.exist-db.org/exist/apps/doc/devguide_xmldb#use-xmldb
            System.out.println("Despu�s de a�adir:");
            XMLResource res = null; // INICIALIZAR RECURSO XML
            // creamos el recurso -> par�metros necesarios:
                                        // s: nombre.xml (si lo dejamos null, pondr� un nombre aleatorio)
                                        // s1: tipo recurso
            res = (XMLResource)col.createResource("partidas.xml", "XMLResource");
            File f = new File("./partidas.xml"); // elegimos el fichero .xml a a�adir
            res.setContent(f); // fijamos como contenido ese archivo .xml elegido
            col.storeResource(res); // lo a�adimos a la colecci�n
            for (String colRe: col.listResources())
                System.out.println(colRe);
            System.out.println();


            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            e.printStackTrace();
        }
        return null;
    }

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
                   // System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
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
            System.out.println("Error en la conexi�n. Comprueba datos.");
        }

    }

    private static void insertardep(int dep, String dnombre, String loc) {

        //Caso concreto: sabemos cu�les son los nodos
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
            System.out.println("Error en la conexi�n. Comprueba datos.");
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
                        System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
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
                System.out.println("Error en la conexi�n. Comprueba datos.");
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
                System.out.println("Error en la conexi�n. Comprueba datos.");
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
                System.out.println("Error en la conexi�n. Comprueba datos.");
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
                //Consulta para consultar la informaci�n de un departamento
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
            System.out.println("Error en la conexi�n. Comprueba datos.");
        }

        return false;

    }// comprobardep

    public static void consulta_ejemplo() throws XMLDBException  {
        String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
        Collection col = null; // Colecci�n
        String URI="xmldb:exist://localhost:8083/exist/xmlrpc/db/ColeccionPruebas"; //URI colecci�n
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
                System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
            while (i.hasMoreResources()) {
                Resource r = i.nextResource();
                System.out.println((String) r.getContent());}

            col.close(); //cerramos
        } catch (Exception e) {
            System.out.println("Error al inicializar la BD eXist");
            e.printStackTrace();    }
    }// FIN verempleados10
}// fin clase

