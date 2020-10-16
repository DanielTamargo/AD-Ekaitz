package com.tamargo;

import com.tamargo.modelo.*;
import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;

public class LeerDatosBase {

    /**
     * Utilizando ObjectInputStream (serializable) leeré del fichero 'ficheros/atributos.dat' los atributos base
     */
    public ArrayList<Atributos> leerAtributosBase() {

        ArrayList<Atributos> atributos = new ArrayList<>();

        try {
            // Se puede instanciar y definir el tipo en una sola línea
            ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(new File(".\\ficheros\\atributos.dat")));

            try {
                while (true) { // Forzamos un bucle infinito para leer hasta que salte el error de leer 'nada' para finalizar
                    Atributos a = (Atributos) dataIS.readObject();
                    atributos.add(a);
                }
            } catch (EOFException eo) {
                // Ya hemos acabado de leer
            } catch (ClassNotFoundException e) {
                System.out.println("¡Error con el casting!");
            }

            dataIS.close(); //Cerramos el flujo de entrada

        } catch (IOException ex) {
            System.out.println("Error al leer los atributos base.");
            System.out.println(ex);
        }

        return atributos;
    }

    /**
     * Utilizando DataInputStream leeré del fichero 'ficheros/enemigos.dat' los datos base de los enemigos
     */
    public ArrayList<Enemigo> leerEnemigosBase() {

        ArrayList<Enemigo> enemigos = new ArrayList<>();

        ArrayList<Atributos> atributos = leerAtributosBase();

        try {
            File fichero = new File(".\\ficheros\\enemigos.dat"); // Preparamos el fichero
            FileInputStream filein = new FileInputStream(fichero); // Definimos el fichero paso 1
            DataInputStream dataIS = new DataInputStream(filein); // Definimos el fichero paso 2
            try {
                while (true) { // Recorremos infinito hasta acabar, forzando un error al intentar leer 'nada' para salir
                    enemigos.add(new Enemigo(dataIS.readInt(), dataIS.readUTF(), dataIS.readInt(), dataIS.readUTF(), atributos.get(dataIS.readInt() - 1), dataIS.readUTF()));
                }
            } catch (EOFException eo) {
                // Hemos acabado de recorrer el fichero
            }
            dataIS.close();  // Como siempre, cerrar el fichero al acabar con el
        } catch (IOException ex) {
            System.out.println("Error al leer los enemigos base.");
        }

        return enemigos;
    }

    /**
     * Utilizando ObjectInputStream (serializable) leeré del fichero 'ficheros/armas.dat' las armas base
     */
    public ArrayList<Arma> leerArmasBase() {

        ArrayList<Arma> armas = new ArrayList<>();

        try {
            // Se puede instanciar y definir el tipo en una sola línea
            ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(new File(".\\ficheros\\armas.dat")));

            try {
                while (true) { // Forzamos un bucle infinito para leer hasta que salte el error de leer 'nada' para finalizar
                    Arma a = (Arma) dataIS.readObject();
                    armas.add(a);
                }
            } catch (EOFException eo) {
                // Ya hemos acabado de leer
            } catch (ClassNotFoundException e) {
                System.out.println("¡Error con el casting!");
            }

            dataIS.close(); // Cerramos el fichero

        } catch (IOException ex) {
            System.out.println("Error al leer las armas base.");
            System.out.println(ex);
        }

        return armas;
    }


    /**
     * Utilizando BufferedReader leeré del fichero 'ficheros/personajes.txt' los personajes base
     * Tendré que construir el objeto a raíz de la línea que definí en el método 'escribirPersonajesBase()'
     */
    public ArrayList<Personaje> leerPersonajesBase() {

        ArrayList<Personaje> personajes = new ArrayList<>();

        ArrayList<Atributos> atributos = leerAtributosBase();
        ArrayList<Arma> armas = leerArmasBase();

        try {
            BufferedReader fichero = new BufferedReader(new FileReader(".\\ficheros\\personajes.txt"));
            String linea;
            while ((linea = fichero.readLine()) != null) {
                if (!linea.equalsIgnoreCase("")) {
                    // Construimos el objeto a partir de la línea
                    String[] datos = linea.split("/");
                    int id = Integer.parseInt(datos[0]);
                    int idArma = Integer.parseInt(datos[3]);
                    int idAtributos = Integer.parseInt(datos[5]);
                    TipoPersonaje tipoPersonaje;
                    if (datos[4].equalsIgnoreCase("Guerrero"))
                        tipoPersonaje = TipoPersonaje.Guerrero;
                    else if (datos[4].equalsIgnoreCase("Mago"))
                        tipoPersonaje = TipoPersonaje.Mago;
                    else if (datos[4].equalsIgnoreCase("Arquero"))
                        tipoPersonaje = TipoPersonaje.Arquero;
                    else if (datos[4].equalsIgnoreCase("Guardian"))
                        tipoPersonaje = TipoPersonaje.Guardian;
                    else if (datos[4].equalsIgnoreCase("Asesino"))
                        tipoPersonaje = TipoPersonaje.Asesino;
                    else
                        tipoPersonaje = TipoPersonaje.Desconocido;
                    personajes.add(new Personaje(id, datos[1], datos[2], armas.get(idArma - 1), tipoPersonaje, atributos.get(idAtributos - 1), datos[6]));
                }
            }
            fichero.close();
        } catch (FileNotFoundException fn) {
            System.out.println("No se encuentra el fichero personajes.txt");
        } catch (IOException io) {
            System.out.println("Error al leer los personajes base.");
        }

        return personajes;
    }

    /**
     * Utilizando DataInputStream leeré del fichero 'ficheros/eventos.dat' los datos base de los eventos
     */
    public ArrayList<Evento> leerEventosBase() {
        ArrayList<Evento> eventos = new ArrayList<>();

        try {
            File fichero = new File(".\\ficheros\\eventos.dat"); // Preparamos el fichero
            FileInputStream filein = new FileInputStream(fichero); // Definimos el fichero paso 1
            DataInputStream dataIS = new DataInputStream(filein); // Definimos el fichero paso 2
            try {
                while (true) { // Recorremos infinito hasta acabar, forzando un error al intentar leer 'nada' para salir
                    eventos.add(new Evento(dataIS.readInt(), dataIS.readUTF(), dataIS.readInt()));
                }
            } catch (EOFException eo) {
                // Hemos acabado de recorrer el fichero
            }
            dataIS.close();  // Como siempre, cerrar el fichero al acabar con el
        } catch (IOException ex) {
            System.out.println("Error al leer los eventos base.");
        }

        return eventos;
    }

    /**
     * W.I.P
     * Utilizando XStream leeré del fichero 'ficheros/partidas.xml' las partidas guardadas
     */
    public ListaPartidas leerListaPartidas() {
        ListaPartidas listaPartidas = new ListaPartidas();

        try {
            //Objeto xstream
            XStream xstream = new XStream();
            //Etiquetas de ListaPersonas y de Persona --> para leerlas como están en el XML
            xstream.alias("listaPartidas", ListaPartidas.class);
            xstream.alias("partida", Partida.class);
            xstream.alias("grupo", Grupo.class);
            xstream.alias("enemigo", Enemigo.class);
            xstream.alias("atributos", Atributos.class);
            xstream.alias("evento", Evento.class);
            xstream.alias("personaje", Personaje.class);

            xstream.aliasAttribute(Partida.class, "fecha", "fecha");

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(ListaPartidas.class, "lista");

            File f = new File(".\\ficheros\\partidas.xml");
            FileInputStream fis = new FileInputStream(f);

            //En este caso no cargaremos objetos, sino la lista entera de las personas (objetos)
            listaPartidas = (ListaPartidas) xstream.fromXML(fis);

            System.out.println("Número de partidas cargadas: " + listaPartidas.getLista().size());

            /*
            //Iremos persona a persona usando "iterator"
            Iterator iterador = listaPartidas.getLista().listIterator();
            while(iterador.hasNext()){
                Partida p = (Partida) iterador.next();
                System.out.println(p);
            }
            */
        } catch (Exception e) {
            System.out.println("Error al cargar la lista de partidas.");
            e.printStackTrace();
        }

        return listaPartidas;
    }



}
