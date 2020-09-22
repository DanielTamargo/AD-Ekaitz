package com.tamargo;

import java.io.*;

public class CrearDir {
    public static void main(String[] args) throws IOException {
        File directorio = new File(".\\src\\com\\tamargo\\NuevoDir");
        if (directorio.mkdir()){
            System.out.println("Directorio creado. Procediendo a crear los ficheros.");

            File fichero1 = new File(directorio, "fichero1.txt");
            File fichero2 = new File(directorio, "fichero2.txt");

            if (fichero1.createNewFile() && fichero2.createNewFile())
                System.out.println("Ficheros creados con éxito!");
            else
                System.out.println("Error al crear los ficheros!! No puede ser!!");

        }
        else {
            System.out.println("Error al crear el directorio. Es posible que ya exista, procederemos a eliminarlo.");

            try {
                for (File f : directorio.listFiles()) {
                    if (f.delete())
                        System.out.println("Fichero eliminado: " + f.getName());
                }
            } catch (NullPointerException e) {
                // No hacer nada
            }

            if (directorio.delete())
                System.out.println("Directorio eliminado.");
            else
                System.out.println("Error al eliminar el directorio!! No puede ser!!");
        }

    }
}
