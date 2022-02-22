import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Credit to David J. Eck for creating, "Introduction to Computer Graphics"
 * https://math.hws.edu/graphicsbook/index.html
 *
 * This book has been fundamental in helping me start building my understanding of
 * how to use graphics2D, java.awt, and swing.
 * https://math.hws.edu/graphicsbook/c2/s5.html
 *
 *
 */


public class Drawing extends JPanel {
    String state = "circle";
    Color currentColor;

    int shapeWidth = 50;
    int shapeLength = 50;

    Graphics2D graphics;
    ArrayList<Shape> shapes;
    ArrayList<Color> colors;

    int clickX;
    int clickY;

    public Drawing() {
        setPreferredSize(new Dimension(500, 500)); // Apparently JPanel uses a layout manager and this let's the manager handle things?
        this.shapes = new ArrayList<>();
        this.colors = new ArrayList<>();
        // setSize(); // Which is why you don't want to use this.
        addMouseListener(new MouseHandler()); // This confuses me slightly.

        this.currentColor = Color.RED;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // wish someone explained why we do this
        graphics = (Graphics2D) g;

        for (int i = 0; i < shapes.size(); i++) {
            // pain
            graphics.setColor(colors.get(i)); // set border color
            graphics.fill(shapes.get(i)); // fills the shape
        }
    }


    public void createShape(Graphics2D g, String shape, int x, int y) {
        int x1 = x - (shapeWidth / 2);
        int y1 = y - (shapeLength / 2);

        if (shape.equals("rect")) {
            Shape rect = new Rectangle(x1, y1, shapeWidth, shapeLength);
            shapes.add(rect);
            colors.add(currentColor);

        } else if (shape.equals("circle")) {
            Shape circle = new Ellipse2D.Double(x1, y1, shapeWidth, shapeLength);
            shapes.add(circle);
            colors.add(currentColor);
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame paintWindow = new JFrame("PaintBySprinkles"); // Creating a new Window
        Drawing drawingPanel = new Drawing();

        paintWindow.setContentPane(drawingPanel);
        paintWindow.setResizable(false);
        paintWindow.pack();

        paintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the program when you hit the X.
        paintWindow.setVisible(true);
    }

    /**
     * We use MouseAdapter over MouseListener, so we can only implement the
     * things that are relevant to us as opposed to implementing everything
     * needed in MouseListener.
     *
     * private class MouseHandler implements MouseListener
     *
     * https://docs.oracle.com/javase/8/docs/api/java/awt/event/MouseAdapter.html
     *
     * ------------------------------------------------------------------------------------
     *
     * There are three different actions that we're looking for:
     * mousePressed - Invoked when a mouse button has been pressed on a component.
     * mouseDragged - Invoked when a mouse button is pressed on a component and then dragged.
     * mouseReleased - Invoked when a mouse button has been released on a component.
     */
    private class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            clickX = event.getX();
            clickY = event.getY();
            // Adds a MouseMotionListener, so we can track where we are going.

            if (state.equals("rect") || state.equals("circle")) {
                createShape(graphics, state, clickX, clickY);
            } else {
                addMouseMotionListener(this);
            }
        }

        /**
         * mouseDragged might seem unimportant when we have mouseReleased,
         * however if we're trying to freeform draw or have some sort of
         * shape to visualize what size shape / item you're building, we
         * want to keep track of the X and Y constantly.
         *
         *
         public void mouseDragged(MouseEvent event) {
         endX = event.getX(); // pass to drawing
         endY = event.getY();
         }

         public void mouseReleased(MouseEvent event) {
         removeMouseMotionListener(this);

         if (state.equals("rect")) {
         // createShape(g, state,endX, endY, shapeWidth, shapeLength);
         }
         }
         **/
    }
}
