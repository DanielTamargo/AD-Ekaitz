package com.tamargo;

import java.io.*;

public class LeerFichData {
    public static void main(String[] args) throws IOException {
        File f = new File(".\\src\\com\\tamargo\\FichData.dat");
        FileInputStream filein = new FileInputStream(f);
        DataInputStream dataIS = new DataInputStream(filein);

        String nombre;
        int edad;
        try {
            System.out.println("Personas registradas: ");
            while (true) {
                nombre = dataIS.readUTF();
                edad = dataIS.readInt();
                System.out.println("Nombre: " + nombre + ", edad: " + edad + ".");
            }
        } catch (IOException e) {
            System.out.println();
        }
        dataIS.close();

    }
}
