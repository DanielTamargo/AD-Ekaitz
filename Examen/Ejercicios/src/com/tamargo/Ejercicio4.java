package com.tamargo;

import java.io.*;

public class Ejercicio4 {

    public static void main(String[] args) {

        String nombreFichero = "Productos.dat";
        String nombreFicheroAuxiliar = "ProductosAux.dat";

        // GENERAMOS EL FICHERO Y RELLENAMOS CON LOS DATOS BASE
        generarFicheroProductos(nombreFichero);

        // MOSTRAMOS EL FICHERO
        mostrarProductos(nombreFichero, false);

        // MODIFICAR LOS PRECIOS
        modificarPrecios(nombreFichero, nombreFicheroAuxiliar);

    }

    public static void modificarPrecios(String nombreFichero, String nombreFicheroAuxiliar) {

        File ficheroAux = new File(nombreFicheroAuxiliar);
        File fichero = new File(nombreFichero);
        if (fichero.exists()) {
            try {
                DataInputStream dataIS = new DataInputStream(new FileInputStream(fichero));
                DataOutputStream dataOS = new DataOutputStream(new FileOutputStream(ficheroAux));
                try {
                    while (true) {
                        dataOS.writeUTF(dataIS.readUTF());
                        dataOS.writeUTF(dataIS.readUTF());

                        double precio = dataIS.readDouble();
                        dataOS.writeDouble(precio); // Precio antiguo
                        precio += 1;
                        dataOS.writeDouble(precio); // Precio nuevo
                        dataOS.writeBoolean(dataIS.readBoolean());
                    }
                } catch (EOFException ignored) {
                    System.out.println("\nModificaciones a los precios realizadas en " + nombreFichero + "\n");
                }

                dataIS.close();
                dataOS.close();

                // VOLCAMOS LOS DATOS DEL FICHERO AUXILIAR EN EL ORIGINAL
                volcarFicheroAuxiliarEnOriginal(nombreFichero, nombreFicheroAuxiliar);

                // MOSTRAMOS LOS DATOS DE NUEVO DIFERENCIANDO ENTRE PRECIOS ANTIGUOS Y PRECIOS NUEVOS
                mostrarProductos(nombreFichero, true);
            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + nombreFichero);
            }
        } else
            System.out.println("No existe el fichero " + nombreFichero);

    }

    public static void volcarFicheroAuxiliarEnOriginal(String nombreFichero, String nombreFicheroAuxiliar) {

        File ficheroAux = new File(nombreFicheroAuxiliar);
        File fichero = new File(nombreFichero);
        try {
            DataInputStream dataIS = new DataInputStream(new FileInputStream(ficheroAux));
            DataOutputStream dataOS = new DataOutputStream(new FileOutputStream(fichero));
            try {
                while (true) {
                    dataOS.writeUTF(dataIS.readUTF());
                    dataOS.writeUTF(dataIS.readUTF());
                    dataOS.writeDouble(dataIS.readDouble());
                    dataOS.writeDouble(dataIS.readDouble());
                    dataOS.writeBoolean(dataIS.readBoolean());
                }
            } catch (EOFException ignored) {
                System.out.println("\nFin del volcado de datos del fichero auxiliar en " + nombreFichero + "\n");
            }

            dataIS.close();
            dataOS.close();

        } catch (IOException ignored) {
            System.out.println("\nError al trabajar con el fichero " + nombreFichero + " o el fichero auxiliar " + nombreFicheroAuxiliar + "\n");
        }
    }

        public static void mostrarProductos (String nombreFichero, boolean preciosAntiguos){

            File fichero = new File(nombreFichero);
            if (fichero.exists()) {

                try {
                    DataInputStream dataIS = new DataInputStream(new FileInputStream(fichero));
                    System.out.println("Lista de productos:");
                    while (true) {
                        String producto = dataIS.readUTF();
                        String comercio = dataIS.readUTF();

                        double precioAntiguo = 0;
                        if (preciosAntiguos)
                            precioAntiguo = dataIS.readDouble();

                        double precio = dataIS.readDouble();
                        boolean alimento = dataIS.readBoolean();
                        String alimnetoStr;
                        if (alimento) alimnetoStr = "Sí";
                        else alimnetoStr = "No";
                        if (!preciosAntiguos)
                            System.out.format("\t%-13s | %-11s | Precio: %6.2f | Alimento: %s\n", producto, comercio, precio, alimnetoStr);
                        else
                            System.out.format("\t%-13s | %-11s | Precio antiguo: %6.2f | Precio nuevo: %6.2f | Alimento: %s\n",
                                    producto, comercio, precioAntiguo, precio, alimnetoStr);
                    }
                } catch (EOFException ignored) {
                    System.out.println("\nFin de la lectura de " + nombreFichero + "\n");
                } catch (IOException ignored) {
                    System.out.println("\nError al trabajar con el fichero " + nombreFichero + "\n");
                }

            } else
                System.out.println("No existe el fichero " + nombreFichero);
        }

        public static void generarFicheroProductos (String nombreFichero){

            String[] productos = {"Fruta", "Lavavajillas", "Pescado", "Leche", "Colonia"};
            String[] comercios = {"Carrefour", "Hipercor", "Eroski", "Dia", "Mercadona"};
            double[] precios = {20.3, 10.7, 23.4, 16.5, 21.4};
            boolean[] alimentos = {true, false, true, true, false};

            try {
                DataOutputStream dataOS = new DataOutputStream(new FileOutputStream(new File(nombreFichero)));

                // Como todos los arrays tienen la misma longitud, no hay necesidad de partirse la cabeza
                for (int i = 0; i < productos.length; i++) {
                    dataOS.writeUTF(productos[i]);
                    dataOS.writeUTF(comercios[i]);
                    dataOS.writeDouble(precios[i]);
                    dataOS.writeBoolean(alimentos[i]);
                }
                System.out.println("Datos base insertados en el fichero " + nombreFichero + "\n");

            } catch (IOException ignored) {
                System.out.println("Error al trabajar con el fichero " + nombreFichero + "\n");
            }
        }

    }
