package com.tamargo.datosbase;

import com.tamargo.miscelanea.GuardarLogs;
import com.tamargo.modelo.*;
import com.tamargo.modelolistas.*;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.logging.Level;

public class GenerarFicherosXMLBase {

    private static final String nombre = "[XML] ";
    private static final String pathFicheros = "./ficheros/xml";

    public static void generarFicherosXML(Datos datos, Departamentos departamentos, EmpleadosContratados empleadosContratados,
                                          EmpleadosDisponibles empleadosDisponibles, Empresas empresas, Iniciativas iniciativas,
                                          IniciativasActivas iniciativasActivas) {
        // Crear la carpeta
        comprobarCarpetaXML();

        // Generar ficheros
        generarXMLDatos(datos);
        generarXMLDepartamentos(departamentos);
        generarXMLEmpleadosContratados(empleadosContratados);
        generarXMLEmpleadosDisponibles(empleadosDisponibles);
        generarXMLEmpresas(empresas);
        generarXMLIniciativas(iniciativas);
        generarXMLIniciativasActivas(iniciativasActivas);

    }

    private static void generarXMLIniciativasActivas(IniciativasActivas iniciativasActivas) {

        IniciativaActiva iniciativaActiva = new IniciativaActiva(-1, -1, null);
        iniciativasActivas.addIniciativaActiva(iniciativaActiva);

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("iniciativasActivas", IniciativasActivas.class);
            xstream.alias("iniciativaActiva", IniciativaActiva.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(IniciativasActivas.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "iniciativasActivas.xml");
            xstream.toXML(iniciativasActivas, new FileOutputStream(f));
            System.out.println(nombre + "Fichero iniciativasActivas.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero iniciativasActivas.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero iniciativasActivas.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero iniciativasActivas.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLIniciativas(Iniciativas iniciativas) {

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("iniciativas", Iniciativas.class);
            xstream.alias("iniciativa", Iniciativa.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(Iniciativas.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "iniciativas.xml");
            xstream.toXML(iniciativas, new FileOutputStream(f));
            System.out.println(nombre + "Fichero iniciativas.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero iniciativas.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero iniciativas.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero iniciativas.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLEmpresas(Empresas empresas) {

        Empresa empresa = new Empresa(-1, "base", "base", "logo1.png");
        empresas.addEmpresa(empresa);

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("empresas", Empresas.class);
            xstream.alias("empresa", Empresa.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(Empresas.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "empresas.xml");
            xstream.toXML(empresas, new FileOutputStream(f));
            System.out.println(nombre + "Fichero empresas.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero empresas.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero empresas.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero empresas.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLEmpleadosDisponibles(EmpleadosDisponibles empleadosDisponibles) {

        EmpleadoDisponible empleadoDisponible = new EmpleadoDisponible(-1, "base", "base", "base",
                null, -1, "base", "base", -1, LocalDate.now());
        empleadosDisponibles.addEmpleadoDisponible(empleadoDisponible);

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("empleadosDisponibles", EmpleadosDisponibles.class);
            xstream.alias("empleadoDisponible", EmpleadoDisponible.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(EmpleadosDisponibles.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "empleadosDisponibles.xml");
            xstream.toXML(empleadosDisponibles, new FileOutputStream(f));
            System.out.println(nombre + "Fichero empleadosDisponibles.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero empleadosDisponibles.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero empleadosDisponibles.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero empleadosDisponibles.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLEmpleadosContratados(EmpleadosContratados empleadosContratados) {

        EmpleadoContratado empleadoContratado = new EmpleadoContratado(-1, "base", "base", "base",
                null, -1, -1, "base", LocalDate.now());
        empleadosContratados.addEmpleadoContratado(empleadoContratado);

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("empleadosContratados", EmpleadosContratados.class);
            xstream.alias("empleadoContratado", EmpleadoContratado.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(EmpleadosContratados.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "empleadosContratados.xml");
            xstream.toXML(empleadosContratados, new FileOutputStream(f));
            System.out.println(nombre + "Fichero empleadosContratados.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero empleadosContratados.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero empleadosContratados.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero empleadosContratados.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLDepartamentos(Departamentos departamentos) {

        Departamento departamento = new Departamento(-1, -1, "base", -1);
        departamentos.addDepartamento(departamento);

        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("departamentos", Departamentos.class);
            xstream.alias("departamento", Departamento.class);
            xstream.aliasAttribute(Departamento.class, "idEmpresa", "idEmpresa");

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(Departamentos.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "departamentos.xml");
            xstream.toXML(departamentos, new FileOutputStream(f));
            System.out.println(nombre + "Fichero departamentos.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero departamentos.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero departamentos.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero departamentos.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    private static void generarXMLDatos(Datos datos) {
        
        try {
            // Preparamos el proceso
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            xstream.alias("datos", Datos.class);
            xstream.alias("dato", Dato.class);

            // Quitamos etiqueta lista (atributo de la clase ListaPartidas)
            xstream.addImplicitCollection(Datos.class, "lista");

            // Insertamos los objetos en el XML
            File f = new File(pathFicheros, "datos.xml");
            xstream.toXML(datos, new FileOutputStream(f));
            System.out.println(nombre + "Fichero datos.xml creado con éxito");
            GuardarLogs.logger.log(Level.FINE, "Fichero datos.xml generado con éxito");
        } catch (Exception e) {
            System.out.println(nombre + "Error al generar el fichero datos.xml. " + e.getLocalizedMessage());
            GuardarLogs.logger.log(Level.SEVERE, "Error al generar el fichero datos.xml. Error: " + e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Comprueba que la carpeta xml existe, si no existe, la crea para poder generar dentro los logs
     */
    private static synchronized void comprobarCarpetaXML() {
        try {
            File f = new File(pathFicheros);
            if (!f.exists()) {
                System.out.println(nombre + "La carpeta xml no existe");
                System.out.println(nombre + "Creando la carpeta xml...");
                boolean crearCarpeta = f.mkdirs();
                if (crearCarpeta)
                    System.out.println(nombre + "Carpeta xml creada");
                else
                    System.out.println(nombre + "Error al crear la carpeta xml");
            }
        } catch (Exception e) {
            System.out.println(nombre + "Error al crear la carpeta xml");
        }
    }

}
