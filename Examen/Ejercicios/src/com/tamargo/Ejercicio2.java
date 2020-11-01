package com.tamargo;

import java.io.*;
import java.util.Random;

public class Ejercicio2 {

    public static void main(String[] args) {

        // COGEMOS EL ID DE UN JUGADOR ALEATORIAMENTE PARA MODIFICARLO Y MOSTRARLO
        int idJugador = -1;
        File fichero = new File("Jugadores.dat");
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
            while (idJugador < 0 || (idJugador - 1) * 56 > raf.length()) {
                idJugador = new Random().nextInt((int) (raf.length() / 56)) + 1;
            }
        } catch (IOException ignored) { }

        // MODIFICAR
        modificarDatosJugadorConcreto(idJugador, fichero);

        // MOSTRAR
        mostrarDatosJugadorConcreto(idJugador, fichero);

    }

    public static void mostrarDatosJugadorConcreto(int idJugador, File fichero) {
        if (fichero.exists()) {
            try {
                RandomAccessFile raf = new RandomAccessFile(fichero, "r");

                long posicionInicial = (idJugador - 1) * 56;

                raf.seek(posicionInicial);

                int id = raf.readInt();

                String nombre;
                String apellido;

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

                int juego = raf.readInt();
                double puntos = raf.readDouble();

                System.out.format("\tID: %3d | %-12s | %-12s | %3d | %.2f\n", id, nombre, apellido, juego, puntos);

                raf.close();

            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + fichero.getName());
            }
        } else
            System.out.println("El fichero no existe");
    }

    public static void modificarDatosJugadorConcreto(int idJugador, File fichero) {

        if (fichero.exists()) {
            try {
                RandomAccessFile raf = new RandomAccessFile(fichero, "rw");

                long posicionInicial = (idJugador - 1) * 56;
                posicionInicial += 4 + 20 + 20; // No queremos modificar el ID ni el NOMBRE ni el APELLIDO
                raf.seek(posicionInicial);
                raf.writeInt(90);
                raf.writeDouble(9999.9);


                raf.close();
                System.out.println("Jugador con el ID " + idJugador +  " modificado correctamente.");

            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + fichero.getName());
            }
        } else
            System.out.println("El fichero no existe");
    }

}
