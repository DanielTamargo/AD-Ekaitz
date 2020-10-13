package com.tamargo.modelo;

import java.io.Serializable;

public enum TipoPersonaje implements Serializable {
    Guerrero, Mago, Arquero, Guardian, Asesino, Desconocido
    // Pasivas:
    // Guardian = +%10 resistencias, 20% provocar al enemigo
    // Guerrero = +20% daño físico
    // Mago = +20% daño mágico
    // Arquero = +20% probabilidad crítico
    // Desconocido = %50% experiencia
}
