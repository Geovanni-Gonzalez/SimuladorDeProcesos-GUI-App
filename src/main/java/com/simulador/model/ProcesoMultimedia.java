package com.simulador.model;

public class ProcesoMultimedia extends Proceso {
    private String recursos; // "video", "sonido", "ambos"

    public ProcesoMultimedia(String nombre, int tamano, int duracion, String usuario, String recursos) {
        super(nombre, TipoProceso.MULTIMEDIA, tamano, duracion, usuario);
        this.recursos = recursos;
    }

    public String getRecursos() {
        return recursos;
    }

    @Override
    public String toString() {
        return super.toString() + " [Multimedia: " + recursos + "]";
    }
}
