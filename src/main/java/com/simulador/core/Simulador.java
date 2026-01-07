package com.simulador.core;

import com.simulador.model.EstadoProceso;
import com.simulador.model.Proceso;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulador {
    private Memoria memoria;
    private List<CPU> cpus;
    private List<Proceso> colaEntrada; // Waiting for memory
    private List<Proceso> procesosEnMemoria; // Active in memory (assigned to CPU or waiting)
    private List<Proceso> procesosFinalizados;
    private int cantidadCPUs;
    private Random random;
    private int nextId;

    public Simulador(int cantidadCPUs) {
        this.cantidadCPUs = cantidadCPUs;
        this.memoria = new Memoria();
        this.cpus = new ArrayList<>();
        for (int i = 0; i < cantidadCPUs; i++) {
            cpus.add(new CPU(i + 1));
        }
        this.colaEntrada = new ArrayList<>();
        this.procesosEnMemoria = new ArrayList<>();
        this.procesosFinalizados = new ArrayList<>();
        this.random = new Random();
        this.nextId = 256;
    }

    public void cargarProcesos(List<Proceso> procesos) {
        for (Proceso p : procesos) {
            p.setId(nextId++);
            p.setEstado(EstadoProceso.EN_ESPERA);
            colaEntrada.add(p);
        }
    }

    // Try to move processes from Entrance Queue -> Memory -> CPU
    public void asignarRecursos() {
        Iterator<Proceso> it = colaEntrada.iterator();
        while (it.hasNext()) {
            Proceso p = it.next();
            if (memoria.asignarMemoria(p)) {
                it.remove();
                procesosEnMemoria.add(p);
                asignarACPU(p);
            }
            // If doesn't fit, it stays in colaEntrada
        }
    }

    private void asignarACPU(Proceso p) {
        int cpuIndex = random.nextInt(cpus.size());
        CPU cpu = cpus.get(cpuIndex);
        cpu.asignarProceso(p);
    }

    public void unPasoSimulacion() {
        // 1. Execute CPUs
        for (CPU cpu : cpus) {
            cpu.ejecutar();
        }

        // 2. Check for finished processes
        Iterator<Proceso> it = procesosEnMemoria.iterator();
        while (it.hasNext()) {
            Proceso p = it.next();
            if (p.getEstado() == EstadoProceso.FINALIZADO) {
                memoria.liberarMemoria(p);
                procesosFinalizados.add(p);
                it.remove();
            }
        }

        // 3. Try to assign waiting processes to freed memory
        asignarRecursos();
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public List<CPU> getCpus() {
        return cpus;
    }

    public List<Proceso> getColaEntrada() {
        return colaEntrada;
    }

    public List<Proceso> getProcesosFinalizados() {
        return procesosFinalizados;
    }

    public List<Proceso> getProcesosEnMemoria() {
        return procesosEnMemoria;
    }

    // Helper to get ALL processes for the Table View
    public List<Proceso> getTodosLosProcesos() {
        List<Proceso> todos = new ArrayList<>();
        todos.addAll(procesosEnMemoria);
        todos.addAll(colaEntrada);
        todos.addAll(procesosFinalizados);
        return todos;
    }

    public void reiniciar() {
        this.memoria = new Memoria();
        this.cpus = new ArrayList<>();
        for (int i = 0; i < cantidadCPUs; i++) {
            cpus.add(new CPU(i + 1));
        }
        this.colaEntrada = new ArrayList<>();
        this.procesosEnMemoria = new ArrayList<>();
        this.procesosFinalizados = new ArrayList<>();
        this.nextId = 256;
    }

    public String getEstadisticas() {
        int total = procesosEnMemoria.size() + colaEntrada.size() + procesosFinalizados.size();
        int finalizados = procesosFinalizados.size();
        int memoriaUsada = memoria.getEspaciosOcupados();
        int memoriaTotal = Memoria.TAMANO_MEMORIA;

        StringBuilder sb = new StringBuilder();
        sb.append("Total Procesos: ").append(total).append(" | ");
        sb.append("Finalizados: ").append(finalizados).append(" | ");
        sb.append("Memoria: ").append(memoriaUsada).append("/").append(memoriaTotal).append(" (")
                .append(memoriaTotal > 0 ? (memoriaUsada * 100 / memoriaTotal) : 0).append("%)");

        return sb.toString();
    }
}
