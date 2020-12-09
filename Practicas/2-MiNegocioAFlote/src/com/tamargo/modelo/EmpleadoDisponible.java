package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class EmpleadoDisponible implements Serializable {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNac;
    private int salario;
    private String avatar;
    private String departamentoDeseado;
    private int idEmpresa;

    public EmpleadoDisponible(int id, String dni, String nombre, String apellido, LocalDate fechaNac, int salario, String avatar, String departamentoDeseado, int idEmpresa) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.avatar = avatar;
        this.departamentoDeseado = departamentoDeseado;
        this.idEmpresa = idEmpresa;

        try {
            this.edad = Period.between(fechaNac, LocalDate.now()).getYears();
        } catch (NullPointerException ignored) {
            this.edad = -1;
        }
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
                ", avatar='" + avatar + '\'' +
                ", departamentoDeseado='" + departamentoDeseado + '\'' +
                ", idEmpresa=" + idEmpresa +
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
