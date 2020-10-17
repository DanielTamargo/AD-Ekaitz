package com.tamargo;

import com.tamargo.modelo.*;

import java.util.ArrayList;
import java.io.*;

import com.thoughtworks.xstream.XStream;

public class EscribirDatosBase {

    public ArrayList<Atributos> prepararAtributosBase() {
        ArrayList<Atributos> atributosBase = new ArrayList<>();

        // Personajes base
        Atributos guerrero = new Atributos(1, 1,
                3, 3, 0, 0, 0, 3, 1);
        Atributos mago = new Atributos(2, 1,
                2, 0, 4, 0, 0, 2, 2);
        Atributos arquero = new Atributos(3, 1,
                2, 2, 0, 2, 2, 2, 0);
        Atributos guardian = new Atributos(4, 1,
                4, 1, 1, 0, 0, 3, 2);
        Atributos asesino = new Atributos(5, 1,
                2, 4, 0, 2, 0, 2, 0);


        // Enemigos base
        Atributos atributosLobo = new Atributos(6, 1,
                1, 3, 0, 0, 0, 1, 0);
        Atributos atributosDuende = new Atributos(7, 1,
                1, 2, 0, 1, 1, 1, 0);
        Atributos atributosBoom = new Atributos(8, 2,
                4, 1, 1, 0, 0, 2, 2);
        Atributos atributosFlan = new Atributos(9, 2,
                4, 0, 1, 0, 0, 4, 0);
        Atributos atributosAvispon = new Atributos(10, 3,
                2, 2, 1, 0, 3, 1, 0);
        Atributos atributosBrocoliMalvado = new Atributos(11, 4,
                6, 1, 3, 0, 0, 3, 3);
        Atributos atributosEsqueleto = new Atributos(12, 4,
                3, 5, 0, 0, 0, 2, 2);
        Atributos atributosDuendePerdido = new Atributos(13, 5,
                4, 5, 0, 2, 3, 2, 1);
        Atributos atributosBoomTanque = new Atributos(14, 6,
                10, 0, 3, 0, 0, 12, 0);
        Atributos atributosGuardianMaldito = new Atributos(15, 8,
                12, 10, 0, 0, 0, 8, 6);

        // Jefes (?)
        Atributos atributosDiosDelCristalPerdido = new Atributos(16, 20,
                50, 20, 20, 0, 0, 20, 12);

        // Añadimos todos a la lista
        atributosBase.add(guerrero);
        atributosBase.add(mago);
        atributosBase.add(arquero);
        atributosBase.add(guardian);
        atributosBase.add(asesino);

        atributosBase.add(atributosLobo);
        atributosBase.add(atributosDuende);
        atributosBase.add(atributosBoom);
        atributosBase.add(atributosFlan);
        atributosBase.add(atributosAvispon);
        atributosBase.add(atributosBrocoliMalvado);
        atributosBase.add(atributosEsqueleto);
        atributosBase.add(atributosDuendePerdido);
        atributosBase.add(atributosBoomTanque);
        atributosBase.add(atributosGuardianMaldito);

        atributosBase.add(atributosDiosDelCristalPerdido);

        return atributosBase;
    }

    public ArrayList<Enemigo> prepararEnemigosBase() {
        ArrayList<Enemigo> enemigos = new ArrayList<>();

        ArrayList<Atributos> atributos = prepararAtributosBase();

        Enemigo lobo = new Enemigo(1, "Lobo", 20,
                "Lobo que ha mutado debido a las pésimas circustancias del mundo, ahora es agresivo y peligroso.",
                atributos.get(1 + 4), "FFIX-1-Lobo.png");
        Enemigo duende = new Enemigo(2, "Duende", 20,
                "El enemigo más pesado y utilizado al inicio en la saga de los Final Fnatasy. Siempre estará ahí para luchar contigo.",
                atributos.get(2 + 4), "FFIX-2-Duende.png");
        Enemigo boom = new Enemigo(3, "Boom", 40,
                "En los Final Fnatasy 'reales', tiende a explotar tras recibir daño unas cuantas veces. Aquí no.",
                atributos.get(3 + 4), "FFIX-3-Boom.png");
        Enemigo flan = new Enemigo(4, "Flan", 50,
                "Su forma gelatinosa hace que derrotarlo a base de espadazos y flechazos sea un coñazo. Mejor utilizar daño mágico...",
                atributos.get(4 + 4), "FFIX-4-Flan.png");
        Enemigo avispon = new Enemigo(5, "Avispón", 70,
                "Dan miedo en la vida real y en los juegos. Al volar tiene bastantes probabilidad de esquivarte.",
                atributos.get(5 + 4), "FFIX-5-Avispon.png");
        Enemigo brocoliMalvado = new Enemigo(6, "Brocoli Malvado", 100,
                "Por su forma no sabrías decir si es un brócoli o un virus masivo. Por su aguante sabes que es una molestia.",
                atributos.get(6 +4), "FFIX-6-BrocoliMalvado.png");
        Enemigo esqueleto = new Enemigo(7, "Esqueleto", 110,
                "Esqueleto con espada equivale a pelea intensa. Hace bastante daño para no tener músculo en el que basar sus movimientos.",
                atributos.get(7 + 4), "FFIX-7-Esqueleto.png");
        Enemigo duendePerdido = new Enemigo(8, "Duende Perdido", 160,
                "El enemigo pesado, el duende, ha conseguido hacerse más fuerte al ser influenciado por el cristal perdido. Cuidado con sus movimientos ágiles y su gran daño",
                atributos.get(8 + 4), "FFIX-8-DuendePerdido.png");
        Enemigo flanTanque = new Enemigo(9, "Boom Tanque", 200,
                "Los Boom también han mejorado, ahora tienen mucho más aguante y están dispuestos a molestar mucho más tiempo.",
                atributos.get(9 + 4), "FFIX-9-BoomTanque.png");
        Enemigo guardianMaldito = new Enemigo(10, "Guardián Maldito", 400,
                "Un guardián tan grande y fuerte que rezas para esquivar sus ataques. Tiene tanto aguante que hará de la pelea una pesadilla.",
                atributos.get(10 + 4), "FFIX-10-GuardianMaldito.png");
        Enemigo diosDelCristalPerdido = new Enemigo(11, "Dios del Cristal Perdido", 9999,
                "Aquel Dios que un día protegía el planeta, ahora está obcecado con el poder del Cristal Perdido y ansía más y más poder. Si lo derrotas, salvarás el Mundo.",
                atributos.get(11 + 4), "FFIX-11-DiosDelCristalPerdido.png");

        enemigos.add(lobo);
        enemigos.add(duende);
        enemigos.add(boom);
        enemigos.add(flan);
        enemigos.add(avispon);
        enemigos.add(brocoliMalvado);
        enemigos.add(esqueleto);
        enemigos.add(duendePerdido);
        enemigos.add(flanTanque);
        enemigos.add(guardianMaldito);
        enemigos.add(diosDelCristalPerdido);

        return enemigos;
    }

    public ArrayList<Arma> prepararArmasBase() {
        ArrayList<Arma> armas = new ArrayList<>();

        Arma espada = new Arma(1, "Espada Corta", "Espada", 5, 0, 0, 0, "espada-1.png", 1);
        Arma espadaLarga = new Arma(6, "Espada Larga", "Espada", 7, 0, 2, 0, "espada-2.png", 2);
        Arma espadon = new Arma(11, "Espadón", "Espada", 12, 0, 3, 0, "espada-3.png", 3);
        Arma excalibur = new Arma(16, "Excalibur", "Espada", 18, 5, 5, 5, "espada-4.png", 4);

        Arma baston = new Arma(2, "Bastón Mágico", "Bastón", 0, 5, 0, 0, "baston-1.png", 1);
        Arma bastonArcangel = new Arma(7, "Bastón Arcangel", "Bastón", 0, 8, 0, 0, "baston-2.png", 2);
        Arma bastonPoderoso = new Arma(12, "Bastón Poderoso", "Bastón", 0, 12, 0, 0, "baston-3.png", 3);
        Arma morellonomicom = new Arma(17, "Morellonomicom", "Bastón", 0, 24, 0, 0, "baston-4.png", 4);

        Arma arco = new Arma(3, "Arco Ligero", "Arco", 5, 0, 0, 0, "arco-1.png", 1);
        Arma arcoPesado = new Arma(8, "Arco Pesado", "Arco", 8, 0, 0, 0, "arco-2.png", 2);
        Arma arcoPreciso = new Arma(13, "Arco Preciso", "Arco", 11, 0, 0, 0, "arco-3.png", 3);
        Arma arcoDeArtemisa = new Arma(18, "Arco de Artemisa", "Arco", 20, 8, 0, 0, "arco-4.png", 4);

        Arma escudo = new Arma(4, "Escudo Redondo", "Escudo", 0, 0, 4, 2, "escudo-1.png", 1);
        Arma escudoPesado = new Arma(9, "Escudo Pesado", "Escudo", 0, 0, 6, 4, "escudo-2.png", 2);
        Arma escudoMagico = new Arma(14, "Escudo Mágico", "Escudo", 2, 2, 8, 8, "escudo-3.png", 3);
        Arma escudoLegendario = new Arma(19, "Escudo Legendario", "Escudo", 6, 4, 20, 16, "escudo-4.png", 4);

        Arma dagas = new Arma(5, "Dagas", "Cuchillas", 6, 0 ,0, 0, "cuchillas-1.png", 1);
        Arma cuchillas = new Arma(10, "Cuchillas", "Cuchillas", 9, 0,0, 0, "cuchillas-2.png", 2);
        Arma cuchillasVenenosas = new Arma(15, "Cuchillas Venenosas", "Cuchillas", 13, 2,0, 0, "cuchillas-3.png", 3);
        Arma masamune = new Arma(20, "Masamune", "Cuchillas", 25, 5,0, 0, "cuchillas-4.png", 4);

        armas.add(espada); armas.add(arco); armas.add(baston); armas.add(escudo); armas.add(dagas);
        armas.add(espadaLarga); armas.add(arcoPesado); armas.add(bastonArcangel);  armas.add(escudoPesado); armas.add(cuchillas);
        armas.add(espadon); armas.add(arcoPreciso); armas.add(bastonPoderoso); armas.add(escudoMagico); armas.add(cuchillasVenenosas);
        armas.add(excalibur); armas.add(arcoDeArtemisa); armas.add(morellonomicom); armas.add(escudoLegendario); armas.add(masamune);
        return armas;
    }

    public ArrayList<Personaje> prepararPersonajesBase() {
        ArrayList<Personaje> personajes = new ArrayList<>();

        ArrayList<Atributos> atributos = prepararAtributosBase();
        ArrayList<Arma> armas = prepararArmasBase();

        Personaje guerrero = new Personaje(1, "Lord Kaplan",
                "Un guerrero dispuesto a luchar hasta la muerte. Cuando masacraron su aldea y perdió a su familia, nacieron sus ganas de purgar el mal.",
                armas.get(0), TipoPersonaje.Guerrero, atributos.get(0), "icono-1-guerrero.png");
        Personaje mago = new Personaje(2, "Lady Sophie",
                "Su misión consistía en ser la princesa del reino Dartnor, pero una vez descubrió su inmenso poder mágico no quiso quedarse encerrada sin hacer nada.",
                armas.get(1), TipoPersonaje.Mago, atributos.get(1), "icono-2-mago.png");
        Personaje arquero = new Personaje(3, "Legomas",
                "Su increíble precisión, sus rasgos élficos y su nombre te recuerdan a un arquero de alguna saga... Tendrás que confiar en lo que vean sus ojos de elfo.",
                armas.get(2), TipoPersonaje.Arquero, atributos.get(2), "icono-3-arquero.png");
        Personaje guardian = new Personaje(4, "Brimstone",
                "Escuchas cómo sus pasos avisan de su llegada desde una larga distancia. Su tenacidad y caracter le protegen del daño mejor que a ningún otro. Es un guardián de confianza.",
                armas.get(3), TipoPersonaje.Guardian, atributos.get(3), "icono-4-guardian.png");
        Personaje asesino = new Personaje(5, "KillJoy",
                "A esta chica nadie le entiende y su lenguaje asiático confunde a todos, pero en el combate sabes que aportará un daño inmenso. Aunque sus pintas de hipster te molestan un poco.",
                armas.get(4), TipoPersonaje.Asesino, atributos.get(4), "icono-5-asesino.png");

        personajes.add(guerrero); personajes.add(mago); personajes.add(arquero); personajes.add(guardian); personajes.add(asesino);

        return personajes;
    }

    public ArrayList<Evento> prepararEventosBase() {
        ArrayList<Evento> eventos = new ArrayList<>();

        Evento nuevaArma = new Evento(1, "¿Nueva arma? ¡Nueva arma! Espero que te sirva de algo, si no... sh*t happens :/", 1);
        Evento puzleOportuno = new Evento(2, "¿Qué es esto? ¿Una sala secreta? Parece que hay varias pruebas... espero que dispongas de la inteligencia necesaria para resolverlas.", 2);
        Evento subidaNivel = new Evento(3, "¡Reflexionar tras los combates también es importante! ¡Tu equipo ha asimilado conocimientos nuevos y ha subido un nivel!", 3);

        eventos.add(nuevaArma); eventos.add(puzleOportuno); eventos.add(subidaNivel);

        return eventos;
    }


    /**
     * Utilizando ObjectOutputStream (serializable) guardaré en el fichero 'ficheros/atributos.dat' los atributos base
     */
    public void escribirAtributosBase() {

        ArrayList<Atributos> atributos = prepararAtributosBase();

        try {
            // Se puede preparar en una sola línea
            ObjectOutputStream dataOS = new ObjectOutputStream(new FileOutputStream(new File(".\\ficheros\\atributos.dat")));

            for (Atributos a: atributos) { // Escribimos todos los objetos de la lista
                dataOS.writeObject(a);
            }

            System.out.println("¡Atributos base escritos!");
            dataOS.close(); // Cerramos el fichero

        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero de los atributos.");
            System.out.println(ex);
        }
    }

    /**
     * Utilizando DataOutputStream guardaré en el fichero 'ficheros/enemigos.dat' los enemigos base
     */
    public void escribirEnemigosBase() {

        ArrayList<Enemigo> enemigos = prepararEnemigosBase();

        try {
            File fichero = new File(".\\ficheros\\enemigos.dat"); // Preparamos el fichero
            FileOutputStream fileout = new FileOutputStream(fichero); // Definimos el tipo paso 1
            DataOutputStream dataOS = new DataOutputStream(fileout); // Definimos el tipo paso 2

            // Escribimos todos los datos de todos los enemigos (menos los atributos, esos van guardados en otro fichero)
            // int, String, int, String, atributos, String
            for (Enemigo e: enemigos) {
                dataOS.writeInt(e.getId());
                dataOS.writeUTF(e.getNombre());
                dataOS.writeInt(e.getExperiencia());
                dataOS.writeUTF(e.getDescripcion());
                dataOS.writeInt(e.getAtributos().getId()); // <- Guardamos el id para luego cargarlo
                dataOS.writeUTF(e.getImagen());
            }

            System.out.println("¡Enemigos base escritos!");
            dataOS.close(); // Cerramos el fichero! Aquí daría igual, pero si no lo cerramos no podría usarse en otro método!

        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero de los enemigos.");
            System.out.println(ex);
        }
    }

    /**
     * Utilizando ObjectOutputStream (serializable) guardaré en el fichero 'ficheros/armas.dat' las armas base
     */
    public void escribirArmasBase() {

        ArrayList<Arma> armas = prepararArmasBase();

        try {
            // Se puede preparar en una sola línea
            ObjectOutputStream dataOS = new ObjectOutputStream(new FileOutputStream(new File(".\\ficheros\\armas.dat")));

            for (Arma a: armas) { // Escribimos todos los objetos de la lista
                dataOS.writeObject(a);
            }

            System.out.println("¡Armas base escritas!");
            dataOS.close(); // Cerramos el fichero

        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero de las armas.");
            System.out.println(ex);
        }
    }

    /**
     * Utilizando PrintWriter guardaré en el fichero 'ficheros/persnajes.txt' los personajes base
     */
    public void escribirPersonajesBase() {
        ArrayList<Personaje> personajes = prepararPersonajesBase();

        try {
            File fichero = new File(".\\ficheros\\personajes.txt");
            FileWriter fw = new FileWriter(fichero);
            PrintWriter pw = new PrintWriter(fw);

            for (Personaje p : personajes) {
                String linea = "" + p.getId() + "/" + p.getNombre() + "/"
                        + p.getDescripcion() + "/" + p.getArma().getId() + "/"
                        + p.getTipo().name() + "/" + p.getAtributos().getId() + "/" + p.getImagen();
                pw.println(linea);
            }
            System.out.println("¡Personajes base escritos!");
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero de los personajes.");
        }
    }

    /**
     * Utilizando DataOutputStream guardaré en el fichero 'ficheros/eventos.dat' los eventos base
     */
    public void escribirEventosBase() {

        ArrayList<Evento> eventos = prepararEventosBase();

        try {
            File fichero = new File(".\\ficheros\\eventos.dat"); // Preparamos el fichero
            FileOutputStream fileout = new FileOutputStream(fichero); // Definimos el tipo paso 1
            DataOutputStream dataOS = new DataOutputStream(fileout); // Definimos el tipo paso 2

            // Escribimos todos los datos de todos los eventos
            // int, String, int
            for (Evento e: eventos) {
                dataOS.writeInt(e.getId());
                dataOS.writeUTF(e.getDescripcion());
                dataOS.writeInt(e.getEfecto());
            }

            System.out.println("¡Eventos base escritos!");
            dataOS.close(); // Cerramos el fichero! Aquí daría igual, pero si no lo cerramos no podría usarse en otro método!

        } catch (IOException ex) {
            System.out.println("Error al escribir el fichero de los eventos.");
            System.out.println(ex);
        }
    }


    /**
     * Utilizando XStream guardaré en el fichero 'ficheros/partidas.xml' la lista de partidas jugadas
     */
    public void escribirListaPartidas(ListaPartidas listaPartidas) {

        // Vamos a plasmar la lista de partidas en un fichero XML
        try {
            // Preparamos el proceso
            XStream xstream = new XStream();

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

            // Insertamos los objetos en el XML
            File f = new File(".\\ficheros\\partidas.xml");
            xstream.toXML(listaPartidas, new FileOutputStream(f));
            System.out.println("¡Lista de Partidas actualizada!");
        } catch (Exception e) {
            System.out.println("Error al actualizar la lista de partidas.");
            e.printStackTrace();
        }
    }

}
