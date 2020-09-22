package com.tamargo;

import java.io.*;
public class LeerFichTexto {
    public static void main(String[] args) throws IOException {

        File fichero = new File(".\\src\\com\\tamargo\\LeerFichTexto.java");
        //Declarar fichero
        FileReader fic = new FileReader(fichero); //crear el flujo de entrada

        int i;
        while ((i = fic.read()) != -1) //Se va leyendo un carácter
            System.out.print((char) i);
        fic.close(); //Cerrar flujo de entrada
    }
}

