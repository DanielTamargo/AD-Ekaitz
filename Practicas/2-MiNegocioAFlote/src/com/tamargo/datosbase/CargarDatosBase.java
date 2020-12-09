package com.tamargo.datosbase;

import com.tamargo.modelo.Dato;
import com.tamargo.modelo.Iniciativa;
import com.tamargo.modelolistas.Datos;
import com.tamargo.modelolistas.Iniciativas;

import java.io.*;

public class CargarDatosBase {

    private static final String nombre = "[Data] ";
    private static final String pathFicheros = "./ficheros/data";

    public static Datos cargarDatos() {
        Datos datos = new Datos();

        try {
            ObjectInputStream objIS = new ObjectInputStream(new FileInputStream(new File(pathFicheros, "datosbase.dat")));
            
            try {
                while (true) {
                    datos.addDato((Dato) objIS.readObject());
                }
            } catch (EOFException ignored) { } 

            System.out.println(nombre + "Fichero datosbase.dat leído con éxito");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(nombre + "Error al leer en el fichero datosbase.dat");
        }
        
        return datos;
    }

    public static Iniciativas cargarIniciativas() {
        Iniciativas iniciativas = new Iniciativas();

        try {
            ObjectInputStream objIS = new ObjectInputStream(new FileInputStream(new File(pathFicheros, "iniciativasbase.dat")));

            try {
                while (true) {
                    iniciativas.addIniciativa((Iniciativa) objIS.readObject());
                }
            } catch (EOFException ignored) { }

            System.out.println(nombre + "Fichero iniciativasbase.dat leído con éxito");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(nombre + "Error al leer en el fichero iniciativasbase.dat");
        }

        return iniciativas;
    }

}
