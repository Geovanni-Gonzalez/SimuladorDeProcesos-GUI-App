# IMPROVEMENT_ROADMAP — SimuladorDeProcesos-GUI-App

Backlog priorizado. Impacto/Esfuerzo: Alto/Medio/Bajo.

## Quick Wins

| # | Mejora | Impacto | Esfuerzo | Prioridad |
|---|---|---|---|---|
| 1 | Commitear el untracking de los 15 `.class` y el fix de imagen (aplicados en esta revisión) | Medio | Bajo | P0 |
| 2 | GitHub Topics: `java`, `swing`, `operating-systems`, `process-scheduling`, `simulator` + descripción | Medio | Bajo | P1 |
| 3 | Ejemplo de archivo `.prs` documentado en el README (formato de entrada) | Bajo | Bajo | P2 |

## Mejoras técnicas

| # | Mejora | Impacto | Esfuerzo | Prioridad |
|---|---|---|---|---|
| 4 | Planificadores reales seleccionables (FIFO, Round-Robin, SJF) en vez de asignación aleatoria — convierte el proyecto en evidencia de scheduling de verdad | Alto | Medio | P1 |
| 5 | Tests JUnit del motor (`Simulador`, `Memoria` — son puros, sin Swing) + ejecutarlos en CI | Medio | Bajo | P1 |
| 6 | Build Maven/Gradle en vez de `javac` manual (consistencia con los otros repos Java) | Medio | Bajo | P2 |

## Mejoras arquitectónicas

| # | Mejora | Impacto | Esfuerzo | Prioridad |
|---|---|---|---|---|
| 7 | Interfaz `Planificador` (Strategy) para enchufar los algoritmos del item 4 | Medio | Bajo | P1 (junto al 4) |

## Mejoras de GitHub

Ya presentes: badge CI, LICENSE, `.gitignore`. Faltan: Topics (item 2), enunciado o descripción del problema en `docs/`.
