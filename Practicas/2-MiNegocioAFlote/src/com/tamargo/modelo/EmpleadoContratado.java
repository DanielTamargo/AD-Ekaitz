package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

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

    public EmpleadoContratado(int empNo, String dni, String nombre, String apellido, LocalDate fechaNac, int salario, int depNo, String avatar) {
        this.empNo = empNo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.depNo = depNo;
        this.avatar = avatar;

        try {
            this.edad = Period.between(fechaNac, LocalDate.now()).getYears();
        } catch (NullPointerException ignored) {
            this.edad = -1;
        }
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
