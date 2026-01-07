package com.simulador.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Proceso {
    private int id;
    private String nombre;
    private TipoProceso tipo;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFinalizacion;
    private int tamano;   // 10 - 20
    private int duracion; // 5 - 20 (seconds)
    private String usuario;
    private EstadoProceso estado;
    private int cpuAsignado; // 1 - 4

    // Constructor without ID/Dates (to be set when loading/running)
    public Proceso(String nombre, TipoProceso tipo, int tamano, int duracion, String usuario) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamano = tamano;
        this.duracion = duracion;
        this.usuario = usuario;
        this.estado = EstadoProceso.EN_ESPERA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoProceso getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(LocalTime horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public int getTamano() {
        return tamano;
    }

    public int getDuracion() {
        return duracion;
    }
    
    public void decrementarDuracion() {
        if (this.duracion > 0) {
            this.duracion--;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    public int getCpuAsignado() {
        return cpuAsignado;
    }

    public void setCpuAsignado(int cpuAsignado) {
        this.cpuAsignado = cpuAsignado;
    }

    @Override
    public String toString() {
        return "Proceso{" + "id=" + id + ", nombre=" + nombre + ", estado=" + estado + '}';
    }
}
