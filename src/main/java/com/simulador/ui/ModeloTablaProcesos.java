package com.simulador.ui;

import com.simulador.model.Proceso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaProcesos extends AbstractTableModel {
    private List<Proceso> procesos;
    private final String[] columnas = { "ID", "Nombre", "Tipo", "Tamaño", "Duración", "Estado", "CPU" };

    public ModeloTablaProcesos() {
        this.procesos = new ArrayList<>();
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return procesos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proceso p = procesos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getNombre();
            case 2:
                return p.getTipo();
            case 3:
                return p.getTamano();
            case 4:
                return p.getDuracion();
            case 5:
                return p.getEstado();
            case 6:
                return (p.getCpuAsignado() > 0) ? "CPU " + p.getCpuAsignado() : "-";
            default:
                return null;
        }
    }
}
