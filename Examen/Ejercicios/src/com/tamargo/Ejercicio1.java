package com.tamargo;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Ejercicio1 {

    public static void main(String[] args) {
        // Estructura datos: int (4), string (20), string (20), int (4), double (8) = total 56
        File fichero = new File("Jugadores.dat");

        //MOSTRARLOS
        mostrarJugadores(fichero, "Lista original:");

        //AÑADIR AL FINAL
        addJugadorAlFinal(fichero);

        //MOSTRAR OTRA VEZ
        mostrarJugadores(fichero, "Lista después de añadir un jugador:");
    }

    public static void addJugadorAlFinal(File fichero) {

        if (fichero.exists()) {
            try {
                RandomAccessFile raf = new RandomAccessFile(fichero, "rw");

                long posicionFinal = raf.length();
                raf.seek(posicionFinal);

                int id = (int) (posicionFinal / 56) + 1;
                StringBuffer nombre;
                nombre = new StringBuffer("NOMBRE" + id);
                nombre.setLength(10);
                StringBuffer apellido;
                apellido = new StringBuffer("APELLIDO" + id);
                apellido.setLength(10);
                int juego = (new Random().nextInt(3) + 1) * 10;
                double puntos = 1500.35;

                raf.writeInt(id);
                raf.writeChars(nombre.toString());
                raf.writeChars(apellido.toString());
                raf.writeInt(juego);
                raf.writeDouble(puntos);
                raf.close();
                System.out.println("Jugador con el ID " + id + " añadido al final de la lista\n");

            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + fichero.getName());
            }
        } else
            System.out.println("El fichero no existe");
    }

    public static void mostrarJugadores(File fichero, String cabecera) {

        if (fichero.exists()) {
            try {
                RandomAccessFile raf = new RandomAccessFile(fichero, "r");
                raf.seek(0); //Empezamos por el principio

                int id;
                String nombre;
                String apellido;
                int juego;
                double puntos;

                try {
                    System.out.println(cabecera);
                    while (true) {
                        id = raf.readInt();

                        char[] charNombre = new char[10];
                        for (int i = 0; i < 10; i++) {
                            charNombre[i] = raf.readChar();
                        }
                        nombre = new String(charNombre);

                        char[] charApellido = new char[10];
                        for (int i = 0; i < 10; i++) {
                            charApellido[i] = raf.readChar();
                        }
                        apellido = new String(charApellido);

                        nombre = nombre.trim();
                        apellido = apellido.trim();

                        juego = raf.readInt();
                        puntos = raf.readDouble();

                        System.out.format("\tID: %3d | %-12s | %-12s | %3d | %.2f\n", id, nombre, apellido, juego, puntos);
                    }

                } catch (EOFException ignored) {
                    System.out.println("\nFin de la lectura\n");
                }
                raf.close();
            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + fichero.getName());
            }
        } else
            System.out.println("El fichero no existe");
    }

}
