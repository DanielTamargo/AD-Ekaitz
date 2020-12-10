package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class EmpleadoContratado implements Serializable {

    private int empNo;
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;
    private LocalDate fechaNac;
    private int salario;
    private int depNo;
    private String avatar;

    public EmpleadoContratado(int empNo, String dni, String nombre, String apellido, LocalDate fechaNac, int salario, int depNo, String avatar, LocalDate fechaActual) {
        this.empNo = empNo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.depNo = depNo;
        this.avatar = avatar;

        try {
            this.edad = calcularEdad(fechaActual);
        } catch (NullPointerException ignored) {
            this.edad = -1;
        }
    }

    public EmpleadoContratado(int empNo, String dni, String nombre, String apellido, int edad, LocalDate fechaNac, int salario, int depNo, String avatar) {
        this.empNo = empNo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.depNo = depNo;
        this.avatar = avatar;
    }

    public int calcularEdad(LocalDate fechaActual) {
        return Period.between(fechaNac, fechaActual).getYears();
    }

    public boolean haCumplidoAnyos(LocalDate fechaActual) {
        int edadActual = calcularEdad(fechaActual);
        if (edad < edadActual) {
            edad = edadActual;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "EmpleadoContratado{" +
                "empNo=" + empNo +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", fechaNac=" + fechaNac +
                ", salario=" + salario +
                ", depNo=" + depNo +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String queryUpdateInsert() {
        if (fechaNac == null)
            fechaNac = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaNacStr = fechaNac.format(formatter);

        return "update insert " +
                "<empleadoContratado>" +
                "<empNo>" + empNo + "</empNo>" +
                "<dni>" + dni + "</dni>" +
                "<nombre>" + nombre + "</nombre>" +
                "<apellido>" + apellido + "</apellido>" +
                "<edad>" + edad + "</edad>" +
                "<fechaNac>" + fechaNacStr + "</fechaNac>" +
                "<salario>" + salario + "</salario>" +
                "<depNo>" + depNo + "</depNo>" +
                "<avatar>" + avatar + "</avatar>" +
                "</empleadoContratado>" +
                " into /empleadosContratados";

    }

    public String queryUpdateReplaceEdad() {
        return "update replace " +
                "/empleadosContratados/empleadoContratado[empNo=" + empNo + "]/edad " +
                "with " +
                "<edad>" + edad + "</edad>";
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
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

    public int getDepNo() {
        return depNo;
    }

    public void setDepNo(int depNo) {
        this.depNo = depNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
