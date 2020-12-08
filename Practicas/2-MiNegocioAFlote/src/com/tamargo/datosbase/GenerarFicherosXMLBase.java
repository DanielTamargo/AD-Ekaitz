package com.tamargo.datosbase;

import com.tamargo.miscelanea.GuardarLogs;
import com.tamargo.modelo.Dato;
import com.tamargo.modelolistas.Datos;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;

public class GenerarFicherosXMLBase {

    public static String pathFicheros = "./ficheros/xml";

    // todo generar un xml de cada, datos e iniciativas cargarán los datos e iniciativas base guardadas en ficheros .dat
    //  los demás simplemente tendrán una lista vacía e inicializarán el xml donde posteriormente iremos insertando datos

    public static void insertarDatosBase() {

        Datos datos = new Datos();
        //TODO cargar datos base


        // Vamos a plasmar la lista de partidas en un fichero XML
        try {

            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("datos", Datos.class);
            xstream.alias("dato", Dato.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(Datos.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "datos.xml");
            xstream.toXML(datos, new FileOutputStream(f));
            System.out.println("¡Datos base generados!");
            GuardarLogs.logger.log(Level.FINE, "Datos base generados");
        } catch (Exception e) {
            System.out.println("Error al generar los datos base. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar los datos base. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }

    }

    /**
     * Comprueba que la carpeta xml existe, si no existe, la crea para poder generar dentro los logs
     */
    public static synchronized void comprobarCarpetaXML() {
        File f = new File(pathFicheros);
        if (!f.exists()) {
            System.out.println("[Log] La carpeta xml no existe");
            System.out.println("[Log] Creando la carpeta xml...");
            boolean crearCarpeta = f.mkdirs();
            if (crearCarpeta)
                System.out.println("[Log] Carpeta xml creada");
            else
                System.out.println("[Log] Error al crear la carpeta xml");
        }
    }

}
