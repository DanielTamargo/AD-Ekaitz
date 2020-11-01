package com.tamargo;

import java.io.*;

public class Ejercicio3 {

    public static void main(String[] args) {
        // GUARDAMOS LOS LIBROS AL MISMO TIEMPO QUE LOS MOSTRAMOS
        guardarSoloLibros();
    }

    public static void guardarSoloLibros() {

        File file = new File("RevistasLibros.dat");
        if (file.exists()) {
            try {
                FileInputStream fileIS = new FileInputStream(file);
                ObjectInputStream objIS = new ObjectInputStream(fileIS);

                ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(new File("Libros.dat")));

                // Vamos a ir leyendo del fichero RevistasLibros.dat a la vez que plasmamos en Libros.dat
                try {
                    while (true) {
                        Libro libro = (Libro) objIS.readObject();
                        if (libro.isLibro()) {
                            System.out.print("Libro a guardar: ");
                            libro.mostrar();
                            objOS.writeObject(libro);
                        }
                    }
                } catch (EOFException ignored) {
                    System.out.println("\nFin de la lectura y escritura\n");
                } catch (ClassNotFoundException ignored) {
                    System.out.println("\nError con la clase (la clase Libro no existe o no está bien formada)\n");
                }

                objIS.close();
                objOS.close();
            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + file.getName());
            }
        } else {
            System.out.println("Error, el fichero " + file.getName() + " no existe");
        }

    }

}
