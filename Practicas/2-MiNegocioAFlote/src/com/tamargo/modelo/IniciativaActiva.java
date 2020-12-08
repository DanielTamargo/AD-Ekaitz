package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class IniciativaActiva implements Serializable {

    private int idIniciativa;
    private int idEmpresa;
    private LocalDate fechaInicio;

    public IniciativaActiva(int idIniciativa, int idEmpresa, LocalDate fechaInicio) {
        this.idIniciativa = idIniciativa;
        this.idEmpresa = idEmpresa;
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return "IniciativaActiva{" +
                "idIniciativa=" + idIniciativa +
                ", idEmpresa=" + idEmpresa +
                ", fechaInicio=" + fechaInicio +
                '}';
    }

    public int getIdIniciativa() {
        return idIniciativa;
    }

    public void setIdIniciativa(int idIniciativa) {
        this.idIniciativa = idIniciativa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
