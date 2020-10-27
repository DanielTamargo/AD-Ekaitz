import java.io.*;
import java.util.Random;

public class Ficheros {

    public static void main(String[] args) {
        randomAccessFile();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void xstream() {

        // ESCRIBIR
        /*
        try {
            XStream xstream = new XStream();

            xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);
            xstream.alias("DatosPersona", Persona.class);

            //Quitar etiqueta lista (atributo de la clase ListaPersonas)
            xstream.addImplicitCollection(ListaPersonas.class, "lista");

            xstream.toXML(listaper, new FileOutputStream("Personas.xml"));
            System.out.println("Creado fichero XML....");
        } catch (Exception ignored) { }
        */

        // LEER
        /*
        XStream xstream = new XStream();
        xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);
        xstream.alias("DatosPersona", Persona.class);
        xstream.addImplicitCollection(ListaPersonas.class,"lista");
        FileInputStream fichero = new FileInputStream("Personas.xml");
        ListaPersonas listadoTodas = (ListaPersonas) xstream.fromXML(fichero);
        System.out.println("Número de personas: " + listadoTodas.getListaPersonas().size());
        List<Persona> listaPersonas = new ArrayList<Persona>();
        listaPersonas = listadoTodas.getListaPersonas();
        Iterator iterador = listaPersonas.listIterator();
        while(iterador.hasNext()){
            Persona p = (Persona) iterador.next();
            System.out.printf("Nombre: %s, edad: %d %n", p.getNombre(), p.getEdad());
        }
        System.out.println("Fin de listado ....");
        */
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void randomAccessFile() {
        File fichero = new File("./prueba/fichero.dat");

        // ESCRIBIR DATOS
        escribirRandomAccessFile(fichero);

        // LEER DATOS
        leerRandomAccessFile(fichero, "Lista de datos original: ");

        //AÑADIR DATOS AL FINAL
        addAlFinalRandomAccessFile(fichero);

        //REVISAMOS QUE ESTÁ AÑADIDO
        leerRandomAccessFile(fichero, "Lista de datos tras añadir un dato al final: ");

        //MODIFICAR UN DATO CONCRETO
        modificarDatoConcretoRandomAccesFile(fichero);

        //REVISAMOS QUE ESTÁ MODIFICADO
        leerRandomAccessFile(fichero, "Lista de datos tras modificar un dato concreto: ");

        //LEER UN DATO CONCRETO
        leerDatoConcretoRandomAccesFile(fichero);
    }

    public static void leerDatoConcretoRandomAccesFile(File fichero) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "r");
            int datoALeer = new Random().nextInt(8) + 1;
            System.out.println("Toca leer el id: " + datoALeer);
            long posicionALeer = (datoALeer - 1) * 36;
            raf.seek(posicionALeer);

            int id, departamento;
            double salario;
            char[] apellido = new char[10];

            id = raf.readInt();

            for (int i = 0; i < apellido.length; i++)
                apellido[i] = raf.readChar();

            String apellidoStr = new String(apellido);
            departamento = raf.readInt();
            salario = raf.readDouble();

            if (id > 0) {
                System.out.printf("\tID: %2d, Apellido: %-12s Departamento: %3d, Salario: %.2f %n",
                        id, apellidoStr.trim(), departamento, salario);
            }

        } catch (IOException ignored) { }
    }

    public static void modificarDatoConcretoRandomAccesFile(File fichero) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
            int idDatoAModificar = new Random().nextInt(8) + 1;
            System.out.println("Modificamos el dato con el id: " + idDatoAModificar);
            long posicionAModiciar = (idDatoAModificar - 1) * 36;
            raf.seek(posicionAModiciar);

            StringBuffer bufferUltimoDato;
            bufferUltimoDato = new StringBuffer("ZUGASTI");
            bufferUltimoDato.setLength(10);

            raf.writeInt(idDatoAModificar); //Insertamos id
            raf.writeChars(bufferUltimoDato.toString()); //Insertamos apellido
            raf.writeInt(40); //Insertamos departamento
            raf.writeDouble(700.00); //Insertamos salario
        } catch (IOException ignored) { }
    }

    public static void addAlFinalRandomAccessFile(File fichero) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
            long posicionFinal = raf.length();
            raf.seek(posicionFinal);

            StringBuffer bufferUltimoDato;
            bufferUltimoDato = new StringBuffer("TAMARGO");
            bufferUltimoDato.setLength(10);
            int idUltimoDato = (int) (posicionFinal / 36) + 1;

            raf.writeInt(idUltimoDato); //Insertamos id
            raf.writeChars(bufferUltimoDato.toString()); //Insertamos apellido
            raf.writeInt(10); //Insertamos departamento
            raf.writeDouble(3500.00); //Insertamos salario

            raf.close();
        } catch (IOException ignored) {}
    }

    public static void leerRandomAccessFile(File fichero, String cabeceraLista) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "r");
            int id, departamento;
            double salario;
            char[] apellido = new char[10];

            try {
                System.out.println(cabeceraLista);
                while (true) {
                    id = raf.readInt(); //Leemos id

                    for (int i = 0; i < apellido.length; i++)
                        apellido[i] = raf.readChar(); //Leemos caracter a caracter el apellido

                    String apellidoStr = new String(apellido); //Guardamos en String el apellido
                    departamento = raf.readInt(); //Leemos departamento
                    salario = raf.readDouble(); //Leemos salario

                    if (id > 0) {
                        System.out.printf("\tID: %2d, Apellido: %-12s Departamento: %3d, Salario: %.2f %n",
                                id, apellidoStr.trim(), departamento, salario);
                    }

                    if (raf.getFilePointer() == raf.length())
                        break;
                }
            } catch (EOFException ignored) { }
            raf.close();
            System.out.println();
        } catch (IOException ignored) {}
    }

    public static void escribirRandomAccessFile(File fichero) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
            raf.setLength(0);
            String[] apellidos = {"MENDEZ", "LOPEZ", "ETXEBERRIA", "CASTILLO", "AGIRRE", "PEREZ", "ALVAREZ"};
            int[] departamentos = {10, 20, 10, 10, 30, 30, 20};
            Double[] salarios = {1000.45, 2400.60, 3000.0, 1500.50, 2200.0, 1400.65, 2000.0};

            StringBuffer buffer; //buffer para almacenar el apellido
            int n = apellidos.length; //número de elementos del array (por ej. elementos del apellido)

            for (int i = 0; i < n; i++) {
                raf.writeInt(i + 1); //Para identificar al empleado
                buffer = new StringBuffer(apellidos[i]);
                buffer.setLength(10); //Dejamos 10 caracteres para el apellido
                raf.writeChars(buffer.toString()); //Insertamos apellido
                raf.writeInt(departamentos[i]); //Insertamos departamento
                raf.writeDouble(salarios[i]); //Insertamos salario
            }
            raf.close();
        } catch (IOException ignored) { }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void dataInputOutputStream() {
        try {
            File f = new File("prueba/fichero.dat");

            FileOutputStream fileOS = new FileOutputStream(f);
            DataOutputStream dataOS = new DataOutputStream(fileOS);

            FileInputStream fileIS = new FileInputStream(f);
            DataInputStream dataIS = new DataInputStream(fileIS);

            dataOS.writeInt(1);
            dataOS.writeUTF("Tú, probando");
            dataOS.writeInt(2);
            dataOS.writeUTF("Prueba 2");
            dataOS.flush();

            try {
                while (true) {
                    System.out.println(dataIS.readInt());
                    System.out.println(dataIS.readUTF());
                    System.out.println();
                }
            } catch (EOFException ignored) {
            }

            dataOS.close();
            dataIS.close();
        } catch (IOException ex) {
            System.out.println("¡Error! No se puede crear el archivo, o se ha producido un error con los métodos");
        }
    }

    public static void fileInputOutputStream() {
        try {
            File f = new File("prueba/fichero.dat");

            FileOutputStream fileOS = new FileOutputStream(f);
            fileOS.write("Hola que tal\nBien gracias".getBytes());
            fileOS.close();

            FileInputStream fileIS = new FileInputStream(f);
            int i;
            while ((i = fileIS.read()) != -1)
                System.out.print((char) i); //Si hemos escrito números, el (char) sobra
            fileIS.close();

        } catch (IOException ex) {
            System.out.println("¡El fichero binario no existe y no puede crearse, o no tenemos permisos de escritura!");

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void printWriter() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("./prueba/fichero.txt"));
            pw.println("Prueba1");
            pw.println("Prueba2");
            pw.flush();
            pw.close();
        } catch (IOException io) {
            System.out.println("¡El fichero no existe y no puede crearse, o no tenemos permisos de escritura!");
        }
    }

    public static void bufferedWriter() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("./prueba/fichero1.txt"));
            for (int i = 1; i < 11; i++) {
                bw.write("Fila numero: " + i);
                bw.newLine(); // <- tenemos salto de línea en un nuevo método
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter("./prueba/fichero.txt"));
            bw.append('a');
            bw.close();

        } catch (IOException io) {
            System.out.println("¡El fichero no existe y no puede crearse, o no tenemos permisos de escritura!");
        }
    }

    public static void fileWriter() {
        String[] mensajes = {"Prueba1", "Prrrrrueba2", "Pruebaaaa3"};
        try {
            FileWriter fw = new FileWriter(new File("./prueba/fichero.txt"), true);
            for (String mensaje : mensajes)
                fw.write(mensaje + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("¡El fichero no existe y no puede crearse, o no tenemos permisos de escritura!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void bufferedReader() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("./src/Ficheros.java")));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El fichero no existe!");
        } catch (IOException ex) {
            System.out.println("Error al leer (¿el sistema tiene permisos de lectura?)");
        }
    }

    public static void fileReaderleerConBufferEspecifico() {
        try {
            File f = new File("./src/Ficheros.java");
            FileReader fr = new FileReader(f);
            char[] buffer = new char[10];
            int i = fr.read(buffer, 2, 5);
            System.out.println("Caracteres leídos: " + i);
            for (char c : buffer) {
                if (((int) c) == 0)
                    c = ' ';
                System.out.print(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileReaderleerConBuffer() {
        try {
            File f = new File("./src/Ficheros.java");
            FileReader fr = new FileReader(f);
            char[] buffer = new char[20];
            while ((fr.read(buffer)) != -1) {
                System.out.print(buffer);
                buffer = new char[20];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileReaderleerByteAByte() {
        try {
            File f = new File("./src/Ficheros.java");
            FileReader fr = new FileReader(f);
            int i;
            while ((i = fr.read()) != -1) //se va leyendo un carácter
                System.out.print((char) i);
            fr.close(); //cerrar flujo de entrada
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void directoriosYficheros() {
        File f = new File(".");

        if (f.exists()) {
            if (f.isDirectory()) {
                System.out.println("Es un directorio\n");

                File crearCarpetas = new File("./prueba/subprueba");
                if (crearCarpetas.mkdirs())
                    System.out.println("[Directorio] Carpetas creadas\n");
                else
                    System.out.println("[Directorio] Carpetas ya existentes\n");

                File carpetaFichero = new File("./prueba/subprueba");
                if (carpetaFichero.exists()) {
                    File crearFichero = new File(carpetaFichero, "fichero1.txt");
                    try {
                        if (crearFichero.createNewFile())
                            System.out.println("[Fichero] Fichero creado\n");
                        else
                            System.out.println("[Fichero] Fichero ya existente\n");
                    } catch (IOException e) {
                        System.out.println("[Fichero] Error al crear el fichero\n");
                    }
                } else
                    System.out.println("[Fichero] No existe la carpeta donde quiero crearme");

                String parent;
                if (f.getParent() == null)
                    parent = "No hay carpetas superiores en el proyecto";
                else
                    parent = f.getParent();

                System.out.println("[Directorio] Está en      : " + parent);
                System.out.println("[Directorio] Nombre       : " + f.getName());
                System.out.println("[Directorio] Ruta absoluta: " + f.getAbsolutePath());
                System.out.println("[Directorio] Lista de archivos:");
                mostrarListaFicheros(f, 1);

            } else {
                System.out.println("No es un directorio");
            }
        } else {
            System.out.println("El directorio no existe");
        }
    }

    public static void mostrarListaFicheros(File f, int tabulaciones) {
        File[] listaFicheros = f.listFiles();
        if (listaFicheros != null) {
            for (File file : listaFicheros) {
                for (int i = 0; i < tabulaciones; i++) {
                    System.out.print("\t");
                }
                System.out.println("- " + file.getName());
                if (file.isDirectory())
                    mostrarListaFicheros(file, tabulaciones + 1);
            }
        }
    }

}
