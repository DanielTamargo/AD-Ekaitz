package com.tamargo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EscribirFicheroLibros {
    public static void main (String[] args) throws IOException {
        //Objeto File
        File fichero = new File ("RevistasLibros.dat");
        //Flujo de salida
        FileOutputStream fileout = new FileOutputStream(fichero);
        //Conecta el flujo de bytes al flujo de datos
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

        //ISBN
        String isbn1[] = {"ES0001", "IA1235", "OE99812", "LK53123", "OE87231", "ES0002",
                "BV1324", "JH4421", "CX3311", "OE8723","KS0042", "DS7643",
                "MN4452", "CX5532", "PO8709", "BC0313","LL2222", "WE9931",
                "AS0021", "ZX2294", "FD1234", "ER5544","YT4132", "UT9999",};
        //Título
        String titulo1[] = {"Titulo1", "Titulo2", "Titulo3", "Titulo4", "Titulo5",
                "Titulo6", "Titulo7", "Titulo8", "Titulo9", "Titulo10",
                "Titulo11", "Titulo12", "Titulo13", "Titulo14", "Titulo15",
                "Titulo16", "Titulo17", "Titulo18", "Titulo19", "Titulo20",
                "Titulo21", "Titulo22", "Titulo23", "Titulo24"};
        //Autor
        int autor1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        //Prestado
        boolean prestado1[] = {true, false, true, true, false, true,
                false, false, true, false, true, true,
                true, true, true, false, false, false,
                false, true, false, true, true, false};
        //Es libro o revista
        boolean eslibro1[] = {true, false, true, true, false, true,
                false, false, true, false, true, true,
                true, true, true, true, false, false,
                true, true, false, true, true, false};


        for (int i = 0; i<autor1.length; i++) {

            Libro libro = new Libro(isbn1[i], titulo1[i], autor1[i], prestado1[i], eslibro1[i]);
            dataOS.writeObject(libro);


        }
        dataOS.close();
    }
}
