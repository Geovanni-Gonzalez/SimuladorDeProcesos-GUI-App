package com.simulador.ui;

import com.simulador.core.Memoria;
import com.simulador.model.Proceso;
import javax.swing.*;
import java.awt.*;

public class VistaMemoria extends JPanel {
    private Memoria memoria;
    private final int ROWS = 8;
    private final int COLS = 16; // 8 * 16 = 128
    private final int CELL_SIZE = 30;

    public VistaMemoria() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE + 20, ROWS * CELL_SIZE + 20));
        setBorder(BorderFactory.createTitledBorder("Memoria Principal (128 bloques)"));
        ToolTipManager.sharedInstance().setInitialDelay(200);
        setToolTipText(""); // Enable tooltips
    }

    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
        repaint();
    }

    @Override
    public String getToolTipText(java.awt.event.MouseEvent event) {
        if (memoria == null)
            return null;
        int x = event.getX();
        int y = event.getY();

        // Ensure we are inside the rendered area bounds roughly
        // Ideally we should use Insets, but standard paint is at 0,0 relative?
        // Actually borders are painted. Let's rely on simple math, usually Close
        // Enough.

        // Correct approach would be:
        Insets insets = getInsets();
        int drawX = x - insets.left;
        int drawY = y - insets.top;
        if (drawX < 0 || drawY < 0)
            return null;

        int col = drawX / CELL_SIZE;
        int row = drawY / CELL_SIZE;

        if (col < 0 || col >= COLS || row < 0 || row >= ROWS)
            return null;

        int index = row * COLS + col;
        Proceso p = memoria.getProcesoEn(index);

        if (p != null) {
            return "<html><b>ID:</b> " + p.getId() + "<br><b>Nombre:</b> " + p.getNombre() + "<br><b>Usuario:</b> "
                    + p.getUsuario() + "<br><b>Tipo:</b> " + p.getTipo() + "</html>";
        }
        return "Espacio Libre";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (memoria == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int baseX = insets.left;
        int baseY = insets.top;

        for (int i = 0; i < Memoria.TAMANO_MEMORIA; i++) {
            int row = i / COLS;
            int col = i % COLS;
            int x = baseX + col * CELL_SIZE;
            int y = baseY + row * CELL_SIZE;

            Proceso p = memoria.getProcesoEn(i);

            if (p != null) {
                Color bg = getColorForId(p.getId());
                g2.setColor(bg);
                // Draw with small margin
                g2.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);

                g2.setColor(bg.darker());
                g2.drawRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);

                // Draw ID centered
                g2.setColor(getContrastColor(bg));
                String s = String.valueOf(p.getId());
                FontMetrics fm = g2.getFontMetrics();
                int tx = x + (CELL_SIZE - fm.stringWidth(s)) / 2;
                int ty = y + ((CELL_SIZE - fm.getHeight()) / 2) + fm.getAscent();
                // Check if font fits
                if (fm.stringWidth(s) < CELL_SIZE - 4) {
                    g2.drawString(s, tx, ty - 2);
                }
            } else {
                g2.setColor(new Color(245, 245, 245));
                g2.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                g2.setColor(new Color(220, 220, 220));
                g2.drawRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
            }
        }
    }

    private Color getColorForId(int id) {
        // HSB for vibrant but readable colors
        float hue = (id * 0.618033988749895f) % 1.0f; // Golden ratio
        return Color.getHSBColor(hue, 0.65f, 0.95f);
    }

    private Color getContrastColor(Color color) {
        // Luminance formula
        double y = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }
}
