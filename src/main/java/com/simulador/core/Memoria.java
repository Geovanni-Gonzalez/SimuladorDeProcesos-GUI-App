package com.simulador.core;

import com.simulador.model.Proceso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Memoria {
    public static final int TAMANO_MEMORIA = 128; // 1 to 128
    private Proceso[] bloques;
    private int espaciosOcupados;

    public Memoria() {
        this.bloques = new Proceso[TAMANO_MEMORIA]; // 0-based index internally, mapped to 1-128 for UI
        this.espaciosOcupados = 0;
    }

    // Try to allocate process. Returns true if successful.
    // First-Fit approach
    public synchronized boolean asignarMemoria(Proceso proceso) {
        int tamanoRequerido = proceso.getTamano();
        if (espaciosOcupados + tamanoRequerido > TAMANO_MEMORIA) {
            return false; // Not enough total space, optimization check
        }

        int start = -1;
        int count = 0;

        for (int i = 0; i < TAMANO_MEMORIA; i++) {
            if (bloques[i] == null) {
                if (count == 0)
                    start = i;
                count++;
                if (count == tamanoRequerido) {
                    // Found a hole big enough
                    for (int j = start; j < start + tamanoRequerido; j++) {
                        bloques[j] = proceso;
                    }
                    espaciosOcupados += tamanoRequerido;
                    return true;
                }
            } else {
                count = 0;
                start = -1;
            }
        }
        return false; // Fragmentation prevents allocation
    }

    public synchronized void liberarMemoria(Proceso proceso) {
        for (int i = 0; i < TAMANO_MEMORIA; i++) {
            if (bloques[i] != null && bloques[i].equals(proceso)) {
                bloques[i] = null;
                espaciosOcupados--;
            }
        }
    }

    public boolean estaLleno() {
        return espaciosOcupados == TAMANO_MEMORIA;
    }

    public int getEspaciosOcupados() {
        return espaciosOcupados;
    }

    // For UI Visualization
    public Proceso getProcesoEn(int indice) {
        if (indice >= 0 && indice < TAMANO_MEMORIA) {
            return bloques[indice];
        }
        return null; // Empty
    }
}
