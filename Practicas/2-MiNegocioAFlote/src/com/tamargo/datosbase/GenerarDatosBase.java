package com.tamargo.datosbase;

import com.tamargo.exist.Coleccion;
import com.tamargo.modelolistas.*;
import org.xmldb.api.base.Collection;

import java.io.File;
import java.util.ArrayList;

public class GenerarDatosBase {

    private final static String pathFicherosXML = "./ficheros/xml";

    public static void generarDatosBase() {
        // Generamos los ficheros .dat con los datos e iniciativas base almacenados en binario
        GenerarFicherosDataBase.generarFicherosData();

        // Leemos dichos datos e iniciativas para traspasarlos a xml
        Datos datos = CargarDatosBase.cargarDatos();
        Iniciativas iniciativas = CargarDatosBase.cargarIniciativas();

        // Generamos los ficheros xml, algunos vacíos puesto que se rellenarán según se dé uso a la aplicación
        //     y otros con los datos e iniciativas base cargados anteriormente
        GenerarFicherosXMLBase.generarFicherosXML(datos, new Departamentos(), new EmpleadosContratados(), new EmpleadosDisponibles(),
                new Empresas(), iniciativas, new IniciativasActivas());

        // Comprobamos que la colección existe, si no existe, la creará
        Coleccion.comprobarColeccion();

        // Añadimos los que no existan en la colección a la misma
        ArrayList<String> nombresRecursosColeccion = Coleccion.nombresRecursosColeccion();
        ArrayList<String> nombresRecursosObligatorios = nombresRecursosObligatorios();

        for (String nombre : nombresRecursosObligatorios) {
            if (!nombresRecursosColeccion.contains(nombre)) {
                Coleccion.insertarXML(new File(pathFicherosXML, nombre));
            }
        }

    }

    private static ArrayList<String> nombresRecursosObligatorios() {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("datos.xml");
        nombres.add("departamentos.xml");
        nombres.add("empleadosContratados.xml");
        nombres.add("empleadosDisponibles.xml");
        nombres.add("empresas.xml");
        nombres.add("iniciativas.xml");
        nombres.add("iniciativasActivas.xml");
        return nombres;
    }




}
