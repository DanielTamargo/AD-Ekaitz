package com.tamargo;

import java.io.*;
public class VerDir {
    public static void main(String[] args) {
        System.out.println("Archivos en el directorio actual:");
        File f = new File(".\\src\\com\\tamargo");
        String[] archivos = f.list();
        for (int i = 0; i < archivos.length; i++) {
            System.out.println(archivos[i]);
        }
    }
}