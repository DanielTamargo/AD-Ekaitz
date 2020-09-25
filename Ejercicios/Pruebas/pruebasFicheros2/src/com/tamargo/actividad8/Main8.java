package com.tamargo.actividad8;

import com.tamargo.actividad7.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main8 {
    public static void main(String[] args) throws IOException {

        escribirFichObject();

        leerFichObject();

    }

    public static void escribirFichObject() throws IOException {

        File f = new File("FichDepartamento.dat");
        FileOutputStream fileout = new FileOutputStream(f);
        ObjectOutputStream objectOS = new ObjectOutputStream(fileout);

        // Nuevos datos
        Departamento d1 = new Departamento("Prueba 1".toCharArray(), "Vitoria".toCharArray(), 1);
        Departamento d2 = new Departamento("Prueba 2".toCharArray(), "Vitoria".toCharArray(), 2);
        Departamento d3 = new Departamento("Prueba 3".toCharArray(), "Vitoria".toCharArray(), 3);

        // Lista + añadir los datos
        ArrayList<Departamento> departamentos = new ArrayList<>();
        departamentos.add(d1);
        departamentos.add(d2);
        departamentos.add(d3);


        System.out.println("Escribiendo los datos...\n");
        // Foreach de la lista
        try {
            for (Departamento d : departamentos) {
                objectOS.writeObject(d);
            }
            System.out.println("Datos escritos correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir los datos.");
        }

        objectOS.close();

    }

    public static void leerFichObject() throws IOException {

        System.out.println("\nLeyendo los datos...\n");

        File f = new File("FichDepartamento.dat");
        FileInputStream filein = new FileInputStream(f);
        ObjectInputStream objectIS = new ObjectInputStream(filein);

        try {
            while (true) {
                Departamento d = (Departamento) objectIS.readObject();
                System.out.println("- " + new String(d.getNombre()) + ", " + new String(d.getLoc()) +  ", " + d.getDep());
            }
        } catch (IOException e) {
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer los datos");
        }

        objectIS.close();

    }

}
