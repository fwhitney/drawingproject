import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.WindowConstants;

/*
Graphics JPanel Dummy, To be replaced with better drawing JPanel
 */
public class DrawingPanel extends JPanel {
    private mouseMovement movement;
    private Graphics graphics;
    int x1, y1, x2, y2;

    public DrawingPanel() {
        movement = new mouseMovement();
        this.addMouseListener(movement);
        this.addMouseMotionListener(movement);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    // Draws lines on screen, nothing more.
    private class mouseMovement extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
            graphics = getGraphics();
            x2 = x1;
            y2 = y1;
        }

        public void mouseDragged(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
            graphics.drawLine(x1, y1, x2, y2);
            x2 = x1;
            y2 = y1;
        }
    }
}
