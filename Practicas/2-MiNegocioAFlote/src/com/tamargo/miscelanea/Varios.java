package com.tamargo.miscelanea;

import com.tamargo.datosbase.GenerarDatosBase;
import com.tamargo.exist.Coleccion;
import com.tamargo.modelo.*;
import org.xmldb.api.base.XMLDBException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Varios {

    private final static Empresa empresa = new Empresa(0, "To the Sky", "Vitoria", "logo1.png");
    private final static IniciativaActiva iniciativaActiva = new IniciativaActiva(1, 0, LocalDate.now());
    private final static Departamento departamento = new Departamento(0, 0, "PruebaDep", 1);
    private final static EmpleadoContratado emp1 = new EmpleadoContratado(0, "dni", "Nombre", "Ape", LocalDate.now().minusYears(19), 1000, 0, "avatar", LocalDate.now());
    private final static EmpleadoDisponible emp2 = new EmpleadoDisponible(0, "dni", "nombre", "ape", LocalDate.now().minusYears(19), 1000, "avatar", "RRHH", 0, LocalDate.now());


    public static void main(String[] args) throws XMLDBException {

        GenerarDatosBase.generarDatosBase();
        Coleccion.borrarEmpresa(-1);

        Scanner teclado = new Scanner(System.in);

        System.out.println("Inserciones:");
        insertarDatos();
        System.out.println();

        System.out.println("Aprieta Enter para mostrar los datos");
        teclado.nextLine();
        System.out.println();

        System.out.println("Datos:");
        mostrarDatos();
        System.out.println();

        System.out.println("Aprieta Enter para seguir con los updates");
        teclado.nextLine();
        System.out.println();

        System.out.println("Ediciones:");
        editarDatos();
        System.out.println();

        System.out.println("Aprieta Enter para seguir con los deletes");
        teclado.nextLine();
        System.out.println();

        System.out.println("Eliminaciones");
        borrarDatos();
        System.out.println();

        //Coleccion.borrarDepartamento(-1);

        /*
        Coleccion.borrarEmpleadosDepartamento(1, null);

        EmpleadoContratado emp = new EmpleadoContratado(1, "prueba", "prueba", "prueba",
                LocalDate.now(), 1000, 1, "avatar-masc1.png", LocalDate.now());
        Coleccion.insertarEmpleadoContratado(emp);
        */
    }

    private static void mostrarDatos() {
        System.out.println("EMPRESAS");
        ArrayList<Empresa> empresas = Coleccion.recogerEmpresas();
        for (Empresa empr : empresas) {
            System.out.println(empr);
        }
        System.out.println();

        System.out.println("INICIATIVAS");
        ArrayList<Iniciativa> iniciativas = Coleccion.recogerIniciativas();
        for (Iniciativa ini : iniciativas) {
            System.out.println(ini);
        }
        System.out.println();

        System.out.println("INICIATIVAS ACTIVAS");
        ArrayList<IniciativaActiva> iniciativasActivas = Coleccion.recogerIniciativasActivas();
        for (IniciativaActiva ini : iniciativasActivas) {
            System.out.println(ini);
        }
        System.out.println();

        System.out.println("DEPARTAMENTOS");
        ArrayList<Departamento> departamentos = Coleccion.recogerDepartamentos();
        for (Departamento dep : departamentos) {
            System.out.println(dep);
        }
        System.out.println();

        System.out.println("EMPLEADOS CONTRATADOS");
        ArrayList<EmpleadoContratado> empleadosContratados = Coleccion.recogerEmpleadosContratados();
        for (EmpleadoContratado empcontr : empleadosContratados) {
            System.out.println(empcontr);
        }
        System.out.println();

        System.out.println("EMPLEADOS DISPONIBLES");
        ArrayList<EmpleadoDisponible> empleadosDisponibles = Coleccion.recogerEmpleadosDisponibles();
        for (EmpleadoDisponible empdisp : empleadosDisponibles) {
            System.out.println(empdisp);
        }
        System.out.println();

        System.out.println("DATOS");
        ArrayList<Dato> datos = Coleccion.recogerDatos();
    }

    private static void borrarDatos() {
        //Coleccion.borrarDepartamento(0);
        //Coleccion.borrarEmpleadoContratado(0);
        //Coleccion.borrarIniciativaActiva(1, 0);
        Coleccion.borrarEmpresa(empresa.getId());
    }

    private static void editarDatos() {
        empresa.setCiudad("Vitoria");
        empresa.setNombre("Shark Business");
        Coleccion.editarEmpresa(empresa);

        empresa.setFechaActual(LocalDate.now().plusDays(30));
        Coleccion.editarFechaEmpresa(empresa);


        departamento.setNivel(3);
        Coleccion.editarNivelDepartamento(departamento);

        emp1.setEdad(21);
        Coleccion.editarEdadEmpleadoContratado(emp1);
    }

    private static void insertarDatos() {
        Coleccion.insertarEmpresa(empresa);
        Coleccion.insertarDepartamento(departamento);
        Coleccion.insertarIniciativaActiva(iniciativaActiva);
        Coleccion.insertarEmpleadoContratado(emp1);
        Coleccion.insertarEmpleadoDisponible(emp2);
    }

}
