package com.simulador.ui;

import com.simulador.core.CPU;
import com.simulador.model.Proceso;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaCPU extends JPanel {
    private CPU cpu;
    private JLabel lblEstado;
    private JLabel lblStatusIcon;
    private JList<String> listCola;
    private DefaultListModel<String> listModel;

    public VistaCPU(CPU cpu) {
        this.cpu = cpu;
        this.setLayout(new BorderLayout(5, 5));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("CPU " + cpu.getId()),
                new EmptyBorder(5, 5, 5, 5)));

        // Status Panel
        JPanel panelStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblStatusIcon = new JLabel("\u25CF"); // Unicode bullet
        lblStatusIcon.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblStatusIcon.setForeground(Color.GRAY);

        lblEstado = new JLabel("Inactivo");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));

        panelStatus.add(lblStatusIcon);
        panelStatus.add(lblEstado);
        add(panelStatus, BorderLayout.NORTH);

        // Queue Panel
        listModel = new DefaultListModel<>();
        listCola = new JList<>(listModel);
        listCola.setBackground(new Color(245, 245, 245));
        listCola.setFont(new Font("Consolas", Font.PLAIN, 11));

        JScrollPane scroll = new JScrollPane(listCola);
        scroll.setPreferredSize(new Dimension(150, 80));
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de Procesos"));
        add(scroll, BorderLayout.CENTER);
    }

    public void actualizar() {
        java.util.List<Proceso> cola = cpu.getColaEjecucion();

        if (!cola.isEmpty()) {
            Proceso active = cola.get(0);
            lblEstado.setText("<html>EJECUTANDO<br>" + active.getNombre() + "<br>T. Restante: " + active.getDuracion()
                    + "s</html>");
            lblStatusIcon.setForeground(new Color(0, 180, 0)); // Green
        } else {
            lblEstado.setText("Inactivo");
            lblStatusIcon.setForeground(Color.LIGHT_GRAY);
        }

        listModel.clear();

        // Round Robin Queue (excluding current usually, but here we show "Next")
        if (cola.size() > 1) {
            for (int i = 1; i < cola.size(); i++) {
                Proceso p = cola.get(i);
                listModel.addElement(" [RR] " + p.getNombre());
            }
        }

        // Waiting Queue
        for (Proceso p : cpu.getColaEspera()) {
            listModel.addElement(" [ESPERA] " + p.getNombre());
        }
    }
}
