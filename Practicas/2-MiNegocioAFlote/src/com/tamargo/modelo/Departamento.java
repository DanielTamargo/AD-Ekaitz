package com.tamargo.modelo;

import java.io.Serializable;

public class Departamento implements Serializable {

    private int idEmpresa;
    private int depNo;
    private String nombre;
    private int nivel;

    /**
     * Constructor con todos los datos
     */
    public Departamento(int idEmpresa, int depNo, String nombre, int nivel) {
        this.idEmpresa = idEmpresa;
        this.depNo = depNo;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    /**
     * Constructor sin el idEmpresa por si fuese necesario
     */
    public Departamento(int depNo, String nombre, int nivel) {
        this.depNo = depNo;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "idEmpresa=" + idEmpresa +
                ", depNo=" + depNo +
                ", nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                '}';
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getDepNo() {
        return depNo;
    }

    public void setDepNo(int depNo) {
        this.depNo = depNo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
