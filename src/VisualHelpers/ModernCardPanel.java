package VisualHelpers;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ModernCardPanel extends JPanel {

    private Color baseColor = new Color(10, 10, 10);
    private Color hoverColor = new Color(25, 25, 25);
    private int elevation = 0;
    private boolean hovering = false;

    // variables del destello
    private float shineX = -1f;
    private Timer shineTimer;
    private Timer elevationTimer;

    public ModernCardPanel() {
        setOpaque(false);
        setBackground(baseColor);
        setBorder(null);

        // animación del brillo diagonal
        shineTimer = new Timer(10, e -> animateShine());
        // animación de elevación
        elevationTimer = new Timer(10, e -> animateElevation());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                shineX = -0.3f; // posición inicial del destello
                elevationTimer.start();
                shineTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                elevationTimer.start();
            }
        });
    }

    private void animateElevation() {
        int target = hovering ? 10 : 0;
        if (elevation != target) {
            elevation += (target - elevation) / 2;
            repaint();
        } else {
            elevationTimer.stop();
        }
    }

    private void animateShine() {
        if (shineX < 1.3f) {
            shineX += 0.05f; // velocidad del destello
            repaint();
        } else {
            shineTimer.stop();
            shineX = -1f; // reinicia
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // sombra
        if (elevation > 0) {
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(0, elevation, width, height, 10, 10);
        }

        // fondo principal
        g2.setColor(hovering ? hoverColor : baseColor);
        g2.fillRoundRect(0, 0, width, height, 10, 10);

        // destello diagonal
        if (shineX >= 0 && shineX <= 1) {
            GradientPaint shine = new GradientPaint(
                    width * shineX - 100, 0, new Color(255, 255, 255, 0),
                    width * shineX, height, new Color(255, 255, 255, 80)
            );
            g2.setPaint(shine);
            g2.fillRoundRect(0, 0, width, height, 10, 10);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
