package com.tamargo;

import java.io.*;

public class VerInf {
    public static void main(String[] args) {
        System.out.println("INFORMACIÓN SOBRE EL FICHERO:");
        File f = new File(".\\src\\com\\tamargo\\VerInf.java");
        if (f.exists()) {
            System.out.println("Nombre del fichero: " + f.getName());
            System.out.println("Ruta: " + f.getPath());
            System.out.println("Ruta absoluta: " + f.getAbsolutePath());
            if (f.canRead())
                System.out.println("Se puede leer: Sí");
            else
                System.out.println("Se puede leer: No");
            if (f.canWrite())
                System.out.println("Se puede escribir: Sí");
            else
                System.out.println("Se puede escribir: No");
            System.out.println("Tamaño del archivo: " + f.length() + "bytes");
            if (f.isDirectory())
                System.out.println("Es un directorio: Sí");
            else
                System.out.println("Es un directorio: No");
            if (f.isFile())
                System.out.println("Es un fichero: Sí");
            else
                System.out.println("Es un fichero: No");
        }
    }
}

