package com.tamargo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Empresa implements Serializable {

    private int id;
    private long salarioDisponible;
    private long gastosMensuales;
    private long gananciasMensuales;
    private String nombre;
    private String ciudad;
    private String logo;
    private LocalDate fechaActual;
    private LocalDate fechaCreacion;

    /**
     * Constructor para nuevas empresas en el juego
     */
    public Empresa(int id, String nombre, String ciudad, String logo) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.logo = logo;

        this.salarioDisponible = 5000;

        if (id != -1) {
            this.fechaCreacion = LocalDate.now();
            this.fechaActual = LocalDate.now();
        }
    }

    public Empresa(int id, long salarioDisponible, long gastosMensuales, long gananciasMensuales, String nombre, String ciudad, String logo, LocalDate fechaActual, LocalDate fechaCreacion) {
        this.id = id;
        this.salarioDisponible = salarioDisponible;
        this.gastosMensuales = gastosMensuales;
        this.gananciasMensuales = gananciasMensuales;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.logo = logo;
        this.fechaActual = fechaActual;
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", salarioDisponible=" + salarioDisponible +
                ", gastosMensuales=" + gastosMensuales +
                ", gananciasMensuales=" + gananciasMensuales +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", logo='" + logo + '\'' +
                ", fechaActual=" + fechaActual +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    public String queryUpdateInsert() {
        if (fechaActual == null)
            fechaActual = LocalDate.now();
        if (fechaCreacion == null)
            fechaCreacion = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaActualStr = fechaActual.format(formatter);
        String fechaCreacionStr = fechaCreacion.format(formatter);

        return "update insert " +
                "<empresa>" +
                    "<id>" + id + "</id>" +
                    "<salarioDisponible>" + salarioDisponible + "</salarioDisponible>" +
                    "<gastosMensuales>" + gastosMensuales + "</gastosMensuales>" +
                    "<gananciasMensuales>" + gananciasMensuales + "</gananciasMensuales>" +
                    "<nombre>" + nombre + "</nombre>" +
                    "<ciudad>" + ciudad + "</ciudad>" +
                    "<logo>" + logo + "</logo>" +
                    "<fechaActual>" + fechaActualStr + "</fechaActual>" +
                    "<fechaCreacion>" + fechaCreacionStr + "</fechaCreacion>" +
                "</empresa>" +
                " into /empresas";
    }

    public String queryUpdateReplaceFecha() {
        if (fechaActual == null)
            fechaActual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaActualStr = fechaActual.format(formatter);

        return "update replace " +
                "/empresas/empresa[id=" + id + "]/fechaActual " +
                "with " +
                "<fechaActual>" + fechaActualStr + "</fechaActual>";
    }

    public String queryUpdateReplace() {
        if (fechaActual == null)
            fechaActual = LocalDate.now();
        if (fechaCreacion == null)
            fechaCreacion = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaActualStr = fechaActual.format(formatter);
        String fechaCreacionStr = fechaCreacion.format(formatter);

        return "update replace " +
                "/empresas/empresa[id=" + id + "] " +
                "with " +
                "<empresa>" +
                    "<id>" + id + "</id>" +
                    "<salarioDisponible>" + salarioDisponible + "</salarioDisponible>" +
                    "<gastosMensuales>" + gastosMensuales + "</gastosMensuales>" +
                    "<gananciasMensuales>" + gananciasMensuales + "</gananciasMensuales>" +
                    "<nombre>" + nombre + "</nombre>" +
                    "<ciudad>" + ciudad + "</ciudad>" +
                    "<logo>" + logo + "</logo>" +
                    "<fechaActual>" + fechaActualStr + "</fechaActual>" +
                    "<fechaCreacion>" + fechaCreacionStr + "</fechaCreacion>" +
                "</empresa>";
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getSalarioDisponible() {
        return salarioDisponible;
    }

    public void setSalarioDisponible(long salarioDisponible) {
        this.salarioDisponible = salarioDisponible;
    }

    public long getGastosMensuales() {
        return gastosMensuales;
    }

    public void setGastosMensuales(long gastosMensuales) {
        this.gastosMensuales = gastosMensuales;
    }

    public long getGananciasMensuales() {
        return gananciasMensuales;
    }

    public void setGananciasMensuales(long gananciasMensuales) {
        this.gananciasMensuales = gananciasMensuales;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(LocalDate fechaActual) {
        this.fechaActual = fechaActual;
    }
}
