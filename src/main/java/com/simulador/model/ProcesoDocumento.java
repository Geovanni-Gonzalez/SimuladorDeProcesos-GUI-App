package com.simulador.model;

public class ProcesoDocumento extends Proceso {
    private String formato; // "texto plano", "cifrado"

    public ProcesoDocumento(String nombre, int tamano, int duracion, String usuario, String formato) {
        super(nombre, TipoProceso.DOCUMENTO, tamano, duracion, usuario);
        this.formato = formato;
    }

    public String getFormato() {
        return formato;
    }

    @Override
    public String toString() {
        return super.toString() + " [Documento: " + formato + "]";
    }
}
