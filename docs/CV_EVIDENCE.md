# CV_EVIDENCE — SimuladorDeProcesos-GUI-App

Mostly **reinforces** Java OO skills evidenced elsewhere (compiler projects). Unique angle: operating-systems concepts.

## Unique evidence

| Item | Evidence |
|---|---|
| OS concepts: process lifecycle (waiting → in-memory → CPU → finished), memory-admission control, multi-CPU assignment | `core/Simulador.java`, `Memoria.java`, `model/EstadoProceso.java` |
| Polymorphic domain hierarchy with purpose | `Proceso` (abstract) → `ProcesoDocumento`/`Ejecutable`/`Multimedia` |
| Swing MVC (custom TableModel, separate views) | `ui/ModeloTablaProcesos.java`, `VistaCPU`, `VistaMemoria` |

## Optional resume bullet

- Built a multi-CPU process-scheduling simulator in Java Swing modeling the full process lifecycle (admission queue, memory allocation, CPU dispatch, termination) with a polymorphic process-type hierarchy and file-based (.prs) workload loading.

## ATS keywords (incremental)

Operating systems, process scheduling, process states, memory management, Java Swing, MVC, polymorphism, inheritance.
