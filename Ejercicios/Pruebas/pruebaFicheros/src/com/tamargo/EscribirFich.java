package com.tamargo;

import java.io.*;

public class EscribirFich {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Escribe algo a añadir en el fichero 'FichText.txt': ");
        String cadena = br.readLine();

        char[] c = cadena.toCharArray();

        File f = new File(".\\src\\com\\tamargo\\FichText.txt");
        FileWriter fw = new FileWriter(f, true);
        try {
            fw.write(c);
            fw.write("\n");
            System.out.println("¡Documento escrito!");
        } catch (IOException e) {
            System.out.println("Error al escribir en el documento.");
        }

        fw.close();

        System.out.println("\n\nResultado final del fichero:");
        FileReader fr = new FileReader(f);
        int i;
        while ((i = fr.read()) != -1)
            System.out.print((char) i);
        fr.close();


    }

}
