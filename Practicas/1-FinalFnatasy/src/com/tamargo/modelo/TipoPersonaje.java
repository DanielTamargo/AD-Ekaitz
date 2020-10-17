package com.tamargo.modelo;

import java.io.Serializable;

public enum TipoPersonaje implements Serializable {
    Guerrero, Mago, Arquero, Guardian, Asesino, Desconocido
    // Pasivas:
    // Guardian = +%10 resistencias (duros como las piedras)
    // Guerrero = +15% daño físico (manejan la espada que no veas)
    // Mago = +15% daño mágico (dominan el arte de los elementos woooo)
    // Arquero = +20% probabilidad esquivar (estan lejos, es normal)
    // Asesino = +20% probabilidad de crítico (pinchan por detrás)
    // Desconocido = %50% experiencia (no hay personajes con este tipo)
}
