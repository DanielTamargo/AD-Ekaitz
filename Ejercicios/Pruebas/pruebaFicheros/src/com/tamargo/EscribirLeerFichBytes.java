package com.tamargo;

import java.io.*;
public class EscribirLeerFichBytes {
    public static void main(String[] args) throws IOException {

        File fichero = new File(".\\src\\com\\tamargo\\FichBytes.dat");
        // Crea flujo de salida hacia el fichero
        FileOutputStream fileout = new FileOutputStream(fichero);
        // Crea flujo de entrada
        FileInputStream filein = new FileInputStream(fichero);
        int i;
        // Escribir los datos del fichero
        for (i=1; i<100; i++)
            fileout.write(i); //escribe datos en el flujo de salida
        fileout.close(); //cerrar stream de salida
        // Visualizar los datos del fichero
        while ((i = filein.read()) != -1) //lee datos del flujo de entrada
            System.out.print(i + " ");
        filein.close(); //cerrar stream de entrada
    }
}
