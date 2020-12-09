package com.tamargo;

import com.tamargo.datosbase.GenerarDatosBase;
import com.tamargo.exist.Coleccion;

public class Main {

    public static void main(String[] args) {

        GenerarDatosBase.generarDatosBase();


        Coleccion.borrarEmpresa(-1);

    }
}
