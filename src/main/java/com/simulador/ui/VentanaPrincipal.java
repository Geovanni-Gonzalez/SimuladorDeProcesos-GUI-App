package com.simulador.ui;

import com.simulador.core.Simulador;
import com.simulador.files.LectorArchivos;
import com.simulador.model.Proceso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private Simulador simulador;
    private Timer timer;

    private ModeloTablaProcesos modeloTabla;
    private VistaMemoria vistaMemoria;
    private JPanel panelCPUs;
    private JSpinner spinnerCPUs;
    private JButton btnCargar;
    private JButton btnAsignar;
    private JButton btnEjecutar;

    public VentanaPrincipal() {
        super("Simulador de Procesos OS - Proyecto GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        setLayout(new BorderLayout(10, 10)); // Gap between regions
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10)); // Outer margin

        // Top Panel: Configuration & Controls
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelTop.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblCpu = new JLabel("CPUs (1-4):");
        lblCpu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelTop.add(lblCpu);

        spinnerCPUs = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
        spinnerCPUs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerCPUs.setPreferredSize(new Dimension(50, 30));
        panelTop.add(spinnerCPUs);

        btnCargar = styledButton("Cargar Archivos PRS", new Color(70, 130, 180));
        btnAsignar = styledButton("Asignar Recursos", new Color(255, 165, 0));
        btnEjecutar = styledButton("Ejecutar Simulación", new Color(50, 205, 50));

        btnAsignar.setEnabled(false);
        btnEjecutar.setEnabled(false);

        panelTop.add(btnCargar);
        panelTop.add(btnAsignar);
        panelTop.add(btnEjecutar);

        add(panelTop, BorderLayout.NORTH);

        // Center Panel: Split Pane (Table left, Memory right)
        modeloTabla = new ModeloTablaProcesos();
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Procesos"));

        vistaMemoria = new VistaMemoria();
        JPanel wrapperMemoria = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center memory view
        wrapperMemoria.add(vistaMemoria);
        JScrollPane scrollMemoria = new JScrollPane(wrapperMemoria);
        scrollMemoria.setBorder(BorderFactory.createEmptyBorder()); // Let VistaMemoria have the border

        JSplitPane splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTabla, scrollMemoria);
        splitCenter.setDividerLocation(700);
        splitCenter.setOneTouchExpandable(true);
        add(splitCenter, BorderLayout.CENTER);

        // Bottom Panel: CPUs
        panelCPUs = new JPanel(); // To be populated
        JScrollPane scrollCPUs = new JScrollPane(panelCPUs);
        scrollCPUs.setPreferredSize(new Dimension(1000, 220));
        scrollCPUs.setBorder(BorderFactory.createTitledBorder("Monitor de CPUs"));
        add(scrollCPUs, BorderLayout.SOUTH);

        // Actions
        btnCargar.addActionListener(e -> cargarArchivos());
        btnAsignar.addActionListener(e -> asignarRecursos());
        btnEjecutar.addActionListener(e -> iniciarSimulacion());
    }

    private JButton styledButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        return btn;
    }

    private void cargarArchivos() {
        JFileChooser fileChooser = new JFileChooser("."); // Start in current dir to find usuario1.prs easily
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".prs");
            }

            public String getDescription() {
                return "Archivos de Procesos (*.prs)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            try {
                int numCPUs = (Integer) spinnerCPUs.getValue();
                simulador = new Simulador(numCPUs);
                LectorArchivos lector = new LectorArchivos();

                for (File f : files) {
                    List<Proceso> procesos = lector.cargarProcesos(f);
                    simulador.cargarProcesos(procesos);
                }

                actualizarVistas();

                btnCargar.setEnabled(false);
                spinnerCPUs.setEnabled(false);
                btnAsignar.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Archivos cargados correctamente.");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error cargando archivos: " + ex.getMessage());
            }
        }
    }

    private void asignarRecursos() {
        panelCPUs.removeAll();
        int numCPUs = (Integer) spinnerCPUs.getValue();
        panelCPUs.setLayout(new GridLayout(1, numCPUs, 10, 10)); // Gap between CPUS
        panelCPUs.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (com.simulador.core.CPU cpu : simulador.getCpus()) {
            panelCPUs.add(new VistaCPU(cpu));
        }

        simulador.asignarRecursos();
        actualizarVistas();

        btnAsignar.setEnabled(false);
        btnEjecutar.setEnabled(true);
        panelCPUs.revalidate();
        panelCPUs.repaint();
    }

    private void iniciarSimulacion() {
        if (timer != null && timer.isRunning())
            return;

        timer = new Timer(1000, e -> {
            simulador.unPasoSimulacion();
            actualizarVistas();
        });
        timer.start();
        btnEjecutar.setText("Simulación en curso...");
        btnEjecutar.setEnabled(false);
    }

    private void actualizarVistas() {
        if (simulador == null)
            return;
        modeloTabla.setProcesos(simulador.getTodosLosProcesos());
        vistaMemoria.setMemoria(simulador.getMemoria());
        for (Component c : panelCPUs.getComponents()) {
            if (c instanceof VistaCPU) {
                ((VistaCPU) c).actualizar();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
