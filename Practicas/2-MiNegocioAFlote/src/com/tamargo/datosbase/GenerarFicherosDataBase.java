package com.tamargo.datosbase;

import java.io.File;

public class GenerarFicherosDataBase {

    public static String pathFicheros = "./ficheros/data";

    // Solo datos base e iniciativas base porque son los únicos datos fijos que necesitaremos

    public static void generarDatosBase() {
        //todo
    }



    public static void generarIniciativasBase() {
        //todo

    }


    /**
     * Comprueba que la carpeta data existe, si no existe, la crea para poder generar dentro los logs
     */
    public static synchronized void comprobarCarpetaData() {
        File f = new File(pathFicheros);
        if (!f.exists()) {
            System.out.println("[Log] La carpeta data no existe");
            System.out.println("[Log] Creando la carpeta data...");
            boolean crearCarpeta = f.mkdirs();
            if (crearCarpeta)
                System.out.println("[Log] Carpeta data creada");
            else
                System.out.println("[Log] Error al crear la carpeta data");
        }
    }
    
}
