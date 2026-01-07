package com.simulador.files;

import com.simulador.model.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivos {

    public List<Proceso> cargarProcesos(File archivo) throws IOException {
        List<Proceso> procesos = new ArrayList<>();
        String nombreUsuario = archivo.getName().replace(".prs", ""); // Filename is username

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty())
                    continue;

                String[] partes = linea.split(",");
                for (int i = 0; i < partes.length; i++) {
                    partes[i] = partes[i].trim();
                }

                if (partes.length < 5)
                    continue; // Basic validation check

                String nombre = partes[0];
                int tamano = Integer.parseInt(partes[1]);
                int duracion = Integer.parseInt(partes[2]);
                String tipoStr = partes[3].toLowerCase();

                // Validation ranges
                if (tamano < 10)
                    tamano = 10;
                if (tamano > 20)
                    tamano = 20;
                if (duracion < 5)
                    duracion = 5;
                if (duracion > 20)
                    duracion = 20;

                Proceso proceso = null;

                switch (tipoStr) {
                    case "ejecutable":
                        if (partes.length >= 6) {
                            String tipoExe = partes[4]; // exe or bat
                            String esCoopStr = partes[5]; // Si or No

                            boolean esExe = "exe".equalsIgnoreCase(tipoExe);
                            boolean esCooperativo = "si".equalsIgnoreCase(esCoopStr);

                            proceso = new ProcesoEjecutable(nombre, tamano, duracion, nombreUsuario, esExe,
                                    esCooperativo);
                        }
                        break;
                    case "multimedia":
                        if (partes.length >= 5) {
                            String recursos = partes[4];
                            proceso = new ProcesoMultimedia(nombre, tamano, duracion, nombreUsuario, recursos);
                        }
                        break;
                    case "documento":
                        if (partes.length >= 5) {
                            String formato = partes[4];
                            proceso = new ProcesoDocumento(nombre, tamano, duracion, nombreUsuario, formato);
                        }
                        break;
                    default:
                        // Unknown type, skip or log
                        System.err.println("Tipo desconocido: " + tipoStr);
                        break;
                }

                if (proceso != null) {
                    procesos.add(proceso);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number in file: " + archivo.getName());
            throw e;
        }

        return procesos;
    }
}
