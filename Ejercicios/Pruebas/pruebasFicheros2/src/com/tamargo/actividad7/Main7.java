package com.tamargo.actividad7;

import java.io.*;
import java.util.ArrayList;

public class Main7 {

    public static void main(String[] args) throws IOException {

        escribirFichObject();

        leerFichObject();

    }

    public static void escribirFichObject() throws IOException {

        File f = new File("FichPersona.dat");
        FileOutputStream fileout = new FileOutputStream(f);
        ObjectOutputStream objectOS = new ObjectOutputStream(fileout);

        // Nuevos datos
        Persona p1 = new Persona("Dani", 25);
        Persona p2 = new Persona("Irune", 23);
        Persona p3 = new Persona("Cris", 119);
        Persona p4 = new Persona("Ander", 29);
        Persona p5 = new Persona("Santi", 47);
        Persona p6 = new Persona("Sveta", 96);

        // Lista + añadir los datos
        ArrayList<Persona> personas = new ArrayList<>();
        personas.add(p1);
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);
        personas.add(p5);
        personas.add(p6);


        System.out.println("Escribiendo los datos...\n");
        // Foreach de la lista
        try {
            for (Persona p: personas) {
                objectOS.writeObject(p);
            }
            System.out.println("Datos escritos correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir los datos.");
        }

        objectOS.close();

    }

    public static void leerFichObject() throws IOException {

        System.out.println("\nLeyendo los datos...\n");

        File f = new File("FichPersona.dat");
        FileInputStream filein = new FileInputStream(f);
        ObjectInputStream objectIS = new ObjectInputStream(filein);

        try {
            while (true) {
                Persona p = (Persona) objectIS.readObject();
                System.out.println("- " + p.getNombre() + ", " + p.getEdad());
            }
        } catch (IOException e) {
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer los datos");
        }

        objectIS.close();

    }

}
