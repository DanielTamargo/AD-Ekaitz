package com.tamargo;

import java.io.Serializable;

public class Libro implements Serializable {

    private String isbn;
    private String titulo;
    private int autor;
    private boolean prestado;
    private boolean libro; // true = libro, false = revista

    public Libro() {
    }

    public Libro(String isbn, String titulo, int autor, boolean prestado, boolean libro) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = prestado;
        this.libro = libro;
    }

    public void mostrar() {
        String libroStr;
        if (libro)
            libroStr = "Libro";
        else
            libroStr = "Revista";
        String prestadoStr;
        if (prestado)
            prestadoStr = "Sí";
        else
            prestadoStr = "No";
        System.out.format("%-8s | %-10s | %5d | %-7s | %s\n", isbn, titulo, autor, libroStr, prestadoStr);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public boolean isLibro() {
        return libro;
    }

    public void setLibro(boolean libro) {
        this.libro = libro;
    }
}
