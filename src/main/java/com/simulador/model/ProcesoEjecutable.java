package com.simulador.model;

public class ProcesoEjecutable extends Proceso {
    private boolean esExe; // true = exe, false = bat
    private boolean esCooperativo;

    public ProcesoEjecutable(String nombre, int tamano, int duracion, String usuario, boolean esExe,
            boolean esCooperativo) {
        super(nombre, TipoProceso.EJECUTABLE, tamano, duracion, usuario);
        this.esExe = esExe;
        this.esCooperativo = esCooperativo;
    }

    public boolean isEsExe() {
        return esExe;
    }

    public boolean isEsCooperativo() {
        return esCooperativo;
    }

    @Override
    public String toString() {
        return super.toString() + " [Ejecutable: " + (esExe ? "EXE" : "BAT") + ", Cooperativo: " + esCooperativo + "]";
    }
}
