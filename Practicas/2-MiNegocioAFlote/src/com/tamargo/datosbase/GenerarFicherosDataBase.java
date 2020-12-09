package com.tamargo.datosbase;

import com.tamargo.modelo.Dato;
import com.tamargo.modelo.Iniciativa;
import net.xqj.exist.bin.F;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GenerarFicherosDataBase {

    private static final String nombre = "[Data] ";
    private static final String pathFicheros = "./ficheros/data";

    // Solo datos base e iniciativas base porque son los únicos datos fijos que necesitaremos

    public static void generarFicherosData() {
        // Crear la carpeta
        comprobarCarpetaData();

        // Datos
        ArrayList<Dato> listaDatos = listaDatos();
        try {
            ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(new File(pathFicheros, "datosbase.dat")));
            for (Dato dato : listaDatos) {
                objOS.writeObject(dato);
            }
            System.out.println(nombre + "Fichero datosbase.dat creado con éxito");
        } catch (IOException e) {
            System.out.println(nombre + "Error al generar/escribir en el fichero datosbase.dat");
        }

        // Iniciativas
        ArrayList<Iniciativa> listaIniciativas = listaIniciativas();
        try {
            ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(new File(pathFicheros, "iniciativasbase.dat")));
            for (Iniciativa iniciativa : listaIniciativas) {
                objOS.writeObject(iniciativa);
            }
            System.out.println(nombre + "Fichero iniciativasbase.dat creado con éxito");
        } catch (IOException e) {
            System.out.println(nombre + "Error al generar/escribir en el fichero iniciativasbase.dat");
        }

    }

    private static ArrayList<Iniciativa> listaIniciativas() {
        ArrayList<Iniciativa> iniciativasBase = new ArrayList<>();

        Iniciativa ini1 = new Iniciativa(1, "Captación de socios básica", 90, 1, 1000, 4000);
        Iniciativa ini2 = new Iniciativa(2, "Captación de socios intermedia", 60, 3, 5000, 9000);
        Iniciativa ini3 = new Iniciativa(3, "Captación de socios avanzada", 120, 5, 15000, 100000);

        Iniciativa ini4 = new Iniciativa(4, "Colaboración con Philips", 30, 1, 500, 1500);
        Iniciativa ini5 = new Iniciativa(5, "Colaboración con ASUS", 60, 2, 2000, 8000);
        Iniciativa ini6 = new Iniciativa(6, "Colaboración con CocaCola", 360, 5, 100000, 1000000);

        Iniciativa ini7 = new Iniciativa(7, "Cursos de formación de empleados básicos", 90, 2, 4000, 5000);
        Iniciativa ini8 = new Iniciativa(8, "Cursos de formación de empleados intermedios", 90, 3, 4000, 8000);
        Iniciativa ini9 = new Iniciativa(9, "Cursos de formación de empleados avanzados", 90, 4, 4000, 12000);

        Iniciativa ini10 = new Iniciativa(10, "Optimización de las oficinas", 30, 1, 200, 800);
        Iniciativa ini11 = new Iniciativa(11, "Optimización de los dispositivos", 30, 2, 9000, 20000);
        Iniciativa ini12 = new Iniciativa(12, "Optimización del sistema informático", 150, 3, 10000, 36000);

        Iniciativa ini13 = new Iniciativa(13, "Publicidad local", 30, 3, 30000, 60000);
        Iniciativa ini14 = new Iniciativa(14, "Publicidad nacional", 30, 4, 100000, 150000);
        Iniciativa ini15 = new Iniciativa(15, "Publicidad internacional", 30, 5, 1000000, 1800000);

        iniciativasBase.add(ini1);
        iniciativasBase.add(ini2);
        iniciativasBase.add(ini3);
        iniciativasBase.add(ini4);
        iniciativasBase.add(ini5);
        iniciativasBase.add(ini6);
        iniciativasBase.add(ini7);
        iniciativasBase.add(ini8);
        iniciativasBase.add(ini9);
        iniciativasBase.add(ini10);
        iniciativasBase.add(ini11);
        iniciativasBase.add(ini12);
        iniciativasBase.add(ini13);
        iniciativasBase.add(ini14);
        iniciativasBase.add(ini15);

        return iniciativasBase;
    }

    private static ArrayList<Dato> listaDatos() {
        ArrayList<Dato> listaDatos = new ArrayList<>();
        
        String[] nombresMasculinos = { "" +
                "Daniel", "Ander", "Mario", "Ramón", "Alejandro", "Aitor", "Diego", "Leo", "Hugo", "Martín",
                "Pablo", "Manuel", "Álvaro", "David", "Marcos", "Javier", "Alex", "Bruno", "Miguel", "Antonio",
                "Oliver", "Rodrigo", "Ekaitz", "Luis", "Rafael", "Pau", "Unai", "Joel", "Alberto", "Pedro",
                "Raúl", "Julen", "Rubén", "Aaron", "Jaime", "Jon", "Teo", "Cristian", "Leonardo", "Oscar",
                "Amir", "Iker", "Luca", "Saúl", "Víctor", "Francisco", "Cameron", "Phil", "Mitchel", "Manny", "Luke"
        };
        String[] nombresFemeninos = {
                "Lucian", "Sofia", "Martina", "María", "Julia", "Paula", "Emma", "Daniel", "Valeria",
                "Alba", "Sara", "Noa", "Carmen", "Carla", "Alma", "Claudia", "Valentina", "Vega", "Lara",
                "Olivia", "Mia", "Aitana", "Lola", "Chloe", "Ana", "Alejandra", "Abril", "Jimena", "Elena",
                "Laia", "Adriana", "Candela", "Marta", "Triana", "Irene", "Vera", "Inés", "Laura", "Carlota",
                "Mónica", "Svitlana", "Irune", "Claire", "Gloria", "Alex", "Haley"
        };
        String[] apellidos = {
                "García", "González", "Rodríguez", "Fernández", "López", "Martínez", "Sánchez", "Pérez", "Gómez",
                "Paredes", "Delgado", "Pritchet", "Tucker", "Dunphy", "Ruiz", "Hernández", "Diaz", "Moreno", "Muñoz",
                "Álvarez", "Romero", "Alonso", "Gutiérrez", "Navarro", "Torres", "Domínguez", "Vázquez",
                "Ramos", "Gil", "Ramírez", "Serrano", "Blanco", "Molina", "Morales", "Suárez", "Ortega", "Delgado",
                "Castro", "Ortiz", "Rubio", "Marín", "Sanz", "Núñez", "Iglesias", "Medina", "Garrido", "Cortés",
                "Castillo", "Santos", "Lozano", "Guerrero", "Cano", "Prieto", "Méndez", "Cruz", "Calvo", "Gallego",
                "Vidal", "León", "Márquez", "Herrera", "Peña", "Flores", "Cabrera", "Campos", "Vega", "Fuentes",
                "Carrasco", "Diez", "Caballero", "Reyes", "Nieto", "Aguilar", "Pascual", "Santana",
                "Herrero", "Lorenzo", "Montero", "Hidalgo", "Giménez", "Ibáñez", "Ferrer", "Duran", "Santiago",
                "Benítez", "Mora", "Vicente", "Vargas", "Arias", "Carmona", "Crespo", "Román", "Pastor", "Soto",
                "Sáez", "Velasco", "Moya", "Soler", "Parra"
        };
        String[] avataresMasculinos = {
                "avatar-masc1.png", "avatar-masc2.png", "avatar-masc3.png", "avatar-masc4.png", "avatar-masc5.png",
                "avatar-masc6.png", "avatar-masc7.png"
        };
        String[] avataresFemeninos = {
                "avatar-fem1.png", "avatar-fem2.png", "avatar-fem3.png", "avatar-fem4.png", "avatar-fem5.png",
                "avatar-fem6.png", "avatar-fem7.png"
        };
        String[] profesiones = {
                "RRHH", "Estudio de Mercado", "Ventas", "Administrativo", "Marketing", "Salud"
        };
        int id = 1;
        
        // Añadimos los nombres masculinos
        for (String nombreMasc : nombresMasculinos) {
            listaDatos.add(new Dato("nombreMasculino", nombreMasc, id));
            id++;
        }
        
        // Añadimos los nombres femeninos
        for (String nombreFem : nombresFemeninos) {
            listaDatos.add(new Dato("nombreFemulino", nombreFem, id));
            id++;
        }

        // Añadimos los apellidos
        for (String apellido : apellidos) {
            listaDatos.add(new Dato("apellido", apellido, id));
            id++;
        }

        // Añadimos los avatares masculinos
        for (String avatarMasc : avataresMasculinos) {
            listaDatos.add(new Dato("avatarMasculino", avatarMasc, id));
            id++;
        }

        // Añadimos los avatares femeninos
        for (String avatarFem : avataresFemeninos) {
            listaDatos.add(new Dato("avatarFemenino", avatarFem, id));
            id++;
        }
        
        // Añadimos las profesiones
        for (String profesion : profesiones) {
            listaDatos.add(new Dato("profesion", profesion, id));
            id++;
        }

        return listaDatos;
    }

    /**
     * Comprueba que la carpeta data existe, si no existe, la crea para poder generar dentro los logs
     */
    private static synchronized void comprobarCarpetaData() {
        try {
            File f = new File(pathFicheros);
            if (!f.exists()) {
                System.out.println("[Data] La carpeta data no existe");
                System.out.println("[Data] Creando la carpeta data...");
                boolean crearCarpeta = f.mkdirs();
                if (crearCarpeta)
                    System.out.println("[Data] Carpeta data creada");
                else
                    System.out.println("[Data] Error al crear la carpeta data");
            }
        } catch (Exception e) {
            System.out.println("[Data] Error al crear la carpeta data");
        }
    }
    
}
