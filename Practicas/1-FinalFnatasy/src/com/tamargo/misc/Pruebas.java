package com.tamargo.misc;

public class Pruebas {

    public static void main(String[] args) {

        String linea = "hola,que,tal,estas,8";
        String[] datos = linea.split(",");
        System.out.println(datos[0]);
        System.out.println(Integer.parseInt(datos[datos.length - 1]) * 4);

        System.out.println();
        String substringg = "probando substring";
        System.out.println(substringg.substring(0, 5));
        System.out.println(substringg.substring(5, 10));

    }

}
