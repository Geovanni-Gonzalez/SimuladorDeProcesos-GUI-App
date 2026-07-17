# TECHNICAL_REVIEW — SimuladorDeProcesos-GUI-App

Fecha de revisión: 2026-07-16
Método: análisis estático, CI y git. Sin enunciado en `docs/` (validación contra código y README). Sin ejecución en esta pasada (app Swing interactiva); CI compila.

## 1. Comprensión del proyecto

Simulador gráfico de planificación de procesos en **Java Swing** (~1,100 LOC): múltiples CPUs, memoria con asignación/rechazo (cola de entrada → memoria → CPU), estados de proceso (`EstadoProceso`), jerarquía polimórfica de tipos de proceso (`Proceso` → `ProcesoDocumento`/`ProcesoEjecutable`/`ProcesoMultimedia`), carga desde archivos `.prs` y vistas de inspección de CPU/memoria. Temática de Sistemas Operativos.

## 2. Arquitectura

| Capa | Evidencia |
|---|---|
| `core/` — motor de simulación | `Simulador` (colas entrada/memoria/finalizados, asignación de recursos), `CPU`, `Memoria` |
| `model/` — dominio polimórfico | `Proceso` abstracto + 3 subclases con atributos propios; enums `EstadoProceso`, `TipoProceso` |
| `files/` — parsing de `.prs` | `LectorArchivos` |
| `ui/` — Swing | `VentanaPrincipal`, `VistaCPU`, `VistaMemoria`, `ModeloTablaProcesos` (TableModel propio) |

Ciclo de vida correcto: cola de entrada → admisión por memoria disponible → asignación aleatoria a CPU → finalización y liberación (patrón admission control simplificado).

## 3. Fortalezas

1. Modelado OO limpio de un dominio de SO: herencia con propósito real (atributos distintos por tipo de proceso), enums de estado.
2. Separación core/UI: el motor no depende de Swing.
3. `ModeloTablaProcesos` extiende el patrón MVC de Swing correctamente en vez de manipular la tabla a mano.

## 4. Debilidades y riesgos

| Hallazgo | Severidad | Nota |
|---|---|---|
| ~~15 archivos `.class` trackeados junto a las fuentes~~ | — | Corregido: `git rm --cached` (el patrón `*.class` ya estaba en `.gitignore`) |
| Sin tests; el motor (`Simulador.asignarRecursos`) es puro y fácil de testear | Media | |
| Asignación de CPU aleatoria sin planificador real (FIFO/RR/SJF) | Baja-Media | Limita el claim de "scheduling" |
| Sin enunciado en `docs/` — cumplimiento no verificable | Baja | |
| ~~Link de imagen roto en README~~ | — | Corregido |

## 5. Evaluación profesional

- Nivel demostrado: **Junior+**. OO correcto y dominio de SO básico; sin algoritmos de planificación reales ni tests.
- Rol en el portafolio: **refuerza** Java OO y Swing; su valor único es la temática de sistemas operativos (estados de proceso, admisión por memoria).

## 6. Recomendaciones

Ver `IMPROVEMENT_ROADMAP.md`. El mayor salto de valor: implementar 2-3 planificadores reales seleccionables.
