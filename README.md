# Simulador de Procesos OS - GUI App

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Java](https://img.shields.io/badge/Java-11%2B-orange)
![License](https://img.shields.io/badge/license-MIT-green)

Aplicación gráfica desarrollada en Java (Swing) para simular y visualizar la gestión de procesos, planificación de CPU y asignación de memoria en un sistema operativo.

## 📋 Descripción

Este proyecto es una herramienta educativa y de simulación que permite observar cómo un sistema operativo gestiona los recursos del sistema. Implementa algoritmos clásicos como **Round Robin** para la planificación de CPU y **First-Fit** para la gestión de memoria, todo visualizado a través de una interfaz gráfica interactiva.

El simulador permite configurar el entorno (número de CPUs), cargar lotes de procesos desde archivos y observar su ciclo de vida completo: desde la carga en memoria hasta su finalización.

## ✨ Características Principales

- **Gestión de Memoria First-Fit**: Simulación de una memoria de 128 bloques donde los procesos se asignan en el primer hueco disponible.
- **Planificación Round Robin**: Algoritmo de planificación con soporte para ejecución en múltiples núcleos (1 a 4 CPUs simultáneos).
- **Tipos de Procesos**:
  - 🟢 **Proceso Ejecutable**: Prioridad estándar.
  - 🟣 **Proceso Multimedia**: Simulación de tareas intensivas.
  - 🔵 **Proceso Documento**: Tareas ligeras.
- **Interfaz Gráfica Interactiva**:
  - Panel de control para cargar, iniciar, pausar y reiniciar.
  - Visualización de la tabla de procesos con estados en tiempo real.
  - Mapa de memoria gráfico.
  - Monitores individuales por CPU.
- **Carga de Archivos**: Importación de procesos mediante archivos `.prs` personalizados.

## 🚀 Instalación y Ejecución

### Prerrequisitos

- **Java Development Kit (JDK)** 11 o superior.
- **Maven** (opcional, para gestión de dependencias si se integra en el futuro).

### Pasos para Ejecutar

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/tu-usuario/SimuladorDeProcesos-GUI-App.git
   cd SimuladorDeProcesos-GUI-App
   ```

2. **Compilar el proyecto**:

   ```bash
   javac -d bin -sourcepath src/main/java src/main/java/com/simulador/ui/VentanaPrincipal.java
   ```

3. **Ejecutar la aplicación**:

   ```bash
   java -cp bin com.simulador.ui.VentanaPrincipal
   ```

## 📖 Uso del Simulador

1. **Configuración Inicial**:
   - Seleccione el número de CPUs deseados (1-4) en el control superior.
   - Haga clic en **"Cargar Archivos PRS"** y seleccione uno o varios archivos `.prs` que contengan la definición de los procesos.

2. **Asignación**:
   - Una vez cargados, presione **"Asignar Recursos"**. El sistema intentará cargar los procesos en memoria (First-Fit) y prepararlos para ejecución.

3. **Simulación**:
   - Presione **"Ejecutar Simulación"** para iniciar.
   - Observe cómo los procesos cambian de estado, consumen tiempo de CPU y liberan memoria al finalizar.
   - Use **"Pausar"** para detener momentáneamente y analizar el estado.
   - Use **"Reiniciar"** para limpiar el sistema y comenzar de nuevo.

## 📂 Estructura del Proyecto

```
src/main/java/com/simulador/
├── core/           # Lógica central del sistema
│   ├── CPU.java        # Simulación de núcleo y cola de ejecución
│   ├── Memoria.java    # Gestión de bloques de memoria
│   └── Simulador.java  # Clase principal que orquesta el sistema
├── model/          # Modelos de datos
│   ├── Proceso.java    # Definición abstracta de proceso
│   └── ...             # Subtipos: Ejecutable, Multimedia, Documento
├── ui/             # Interfaz Gráfica (Swing)
│   ├── VentanaPrincipal.java # Ventana main
│   ├── VistaMemoria.java     # Componente visual de memoria
│   └── ...
└── files/          # Gestión de archivos
    └── LectorArchivos.java # Parser de archivos .prs
```

## 📄 Formato de Archivo (.prs)

Los archivos de entrada deben seguir el siguiente formato línea por línea:

```text
NombreProceso,Tipo,Tamano,Duracion,TiempoLlegada
```

Ejemplo:

```text
ProcesoA,Ejecutable,10,5,0
Video1,Multimedia,25,12,2
Doc1,Documento,5,3,4
```

## 🛠️ Tecnologías

- **Lenguaje**: Java 17
- **GUI**: Java Swing
- **Arquitectura**: MVC (Modelo-Vista-Controlador)

---
Desarrollado como parte del proyecto de Sistemas Operativos.
