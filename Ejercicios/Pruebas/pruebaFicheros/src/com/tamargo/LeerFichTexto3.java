package com.tamargo;

import java.io.*;

public class LeerFichTexto3 {

    public static void main(String[] args) throws IOException {

        try {
            File f = new File(".\\src\\com\\tamargo\\LeerFichTexto3.java");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while((linea = br.readLine())!= null)
                System.out.println(linea);
            br.close();
        }
        catch (FileNotFoundException fn) {
            System.out.println("Error de lectura");
        }
        catch (IOException io){
            System.out.println("Error de E/S");
        }

    }
}
