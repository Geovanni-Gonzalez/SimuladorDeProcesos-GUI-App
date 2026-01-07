package com.simulador.core;

import com.simulador.model.EstadoProceso;
import com.simulador.model.Proceso;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class CPU {
    private int id;
    private LinkedList<Proceso> colaEjecucion; // Max 5 active running RR
    private Queue<Proceso> colaEspera; // Assigned but waiting for slot
    private Proceso procesoActual; // Only useful for visualization/current tick logic if needed, but RR rotates
                                   // explicitly

    private static final int MAX_EJECUCION = 5;

    public CPU(int id) {
        this.id = id;
        this.colaEjecucion = new LinkedList<>();
        this.colaEspera = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public synchronized void asignarProceso(Proceso p) {
        if (colaEjecucion.size() < MAX_EJECUCION) {
            p.setEstado(EstadoProceso.EN_ESPERA); // Ready for execution
            colaEjecucion.add(p);
        } else {
            p.setEstado(EstadoProceso.EN_ESPERA); // Waiting to enter execution ring
            colaEspera.add(p);
        }
        p.setCpuAsignado(this.id);
    }

    // Executes 1 second of simulation
    public synchronized void ejecutar() {
        if (colaEjecucion.isEmpty()) {
            refillColaEjecucion();
            return;
        }

        // Round Robin: Take head, execute 1s, put back to tail if not finished
        Proceso p = colaEjecucion.poll();
        if (p == null)
            return;

        // Update state to executing just for this moment (conceptually)
        // Note: Spec says "alternará al siguiente proceso".
        // We assume we process 'p', decrement 1s.

        p.setEstado(EstadoProceso.EJECUCION);
        p.decrementarDuracion();

        if (p.getDuracion() <= 0) {
            p.setEstado(EstadoProceso.FINALIZADO);
            p.setHoraFinalizacion(java.time.LocalTime.now()); // Or simulated time
            // Don't add back to queue
            // Process finished, free memory happens in Simulador or here?
            // Better to let Simulador check for finished processes to free memory,
            // or return a list of finished processes.
        } else {
            p.setEstado(EstadoProceso.EN_ESPERA); // Back to ready
            colaEjecucion.add(p); // Add to end
        }

        // Ensure we keep the pipe full if something finished
        refillColaEjecucion();
    }

    private void refillColaEjecucion() {
        while (colaEjecucion.size() < MAX_EJECUCION && !colaEspera.isEmpty()) {
            Proceso next = colaEspera.poll();
            colaEjecucion.add(next);
        }
    }

    public List<Proceso> getColaEjecucion() {
        return new ArrayList<>(colaEjecucion);
    }

    public List<Proceso> getColaEspera() {
        return new ArrayList<>(colaEspera);
    }
}
