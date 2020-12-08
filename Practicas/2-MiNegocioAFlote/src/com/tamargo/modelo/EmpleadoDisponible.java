package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class EmpleadoDisponible implements Serializable {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNac;
    private int salario;
    private String departamentoDeseado;

    public EmpleadoDisponible(int id, String dni, String nombre, String apellido, int edad, LocalDate fechaNac, int salario, String departamentoDeseado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.departamentoDeseado = departamentoDeseado;
    }

    @Override
    public String toString() {
        return "EmpleadoDisponible{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", fechaNac=" + fechaNac +
                ", salario=" + salario +
                ", departamentoDeseado='" + departamentoDeseado + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getDepartamentoDeseado() {
        return departamentoDeseado;
    }

    public void setDepartamentoDeseado(String departamentoDeseado) {
        this.departamentoDeseado = departamentoDeseado;
    }
}
