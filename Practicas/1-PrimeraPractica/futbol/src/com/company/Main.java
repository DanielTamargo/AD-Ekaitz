package com.company;

import com.thoughtworks.xstream.XStream;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        escribirEquiposXML();

    }

    public static void escribirEquiposXML() throws IOException {

        ObjectInputStream fe = new ObjectInputStream(new FileInputStream("Equipos.dat"));

        ListaEquipos equipos = new ListaEquipos();

        try {
            while (true) {
                Equipo equipo = (Equipo) fe.readObject();
                equipos.add(equipo);
            }
        } catch (EOFException | ClassNotFoundException eo) {
            fe.close();
        }

        try {
            XStream xstream = new XStream();

            // Poner etiquetas personalizadas
            xstream.alias("ListaEquipos", ListaEquipos.class);
            xstream.alias("Equipo", Equipo.class);
            xstream.aliasField("cod", Equipo.class, "codigo");
            xstream.aliasField("golesMarcados", Equipo.class, "golesFavor");
            xstream.aliasField("golesRecibidos", Equipo.class, "golesContra");

            // Eliminar etiqueta general 'lista'
            xstream.addImplicitCollection(ListaEquipos.class, "lista");

            // Plasmar info en el XML
            xstream.toXML(equipos, new FileOutputStream("EquiposXML.xml"));
            System.out.println("¡Fichero XML creado!");
        } catch (FileNotFoundException e) {
            System.out.println("Error al crear el fichero XLM.");
        }


    }



}
