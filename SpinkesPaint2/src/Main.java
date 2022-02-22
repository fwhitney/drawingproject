import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

// ferris

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.ImageIcon;
import java.awt.Graphics;

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


public class Main extends JPanel {
    String state = "circle";
    Color currentColor;

    int shapeWidth = 50;
    int shapeLength = 50;

    Graphics2D graphics;
    ArrayList<Shape> shapes;
    ArrayList<Color> colors;

    int clickX;
    int clickY;

    public Main() {
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

    public static void setup(){
        JFrame frame = new JFrame("Welcome!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        frame.pack();
        frame.setSize(475, 650);

        JLabel textLabel = new JLabel("Welcome to Sprinkles Paint!");
        textLabel.setBounds(150, 3, 1000, 15);
        panel.add(textLabel);

        JLabel sizeLabel = new JLabel("Please select a size below:");
        sizeLabel.setBounds(150, 330, 1000, 15);
        panel.add(sizeLabel);

        Icon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.add(logoLabel);
        logoLabel.setBounds(85, 20, 300, 300);

        Icon instagram = new ImageIcon("instagram.png");
        JLabel instagramLabel = new JLabel(instagram);
        instagramLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        panel.add(instagramLabel);
        instagramLabel.setBounds(20, 360, 120, 120);

        Icon paper = new ImageIcon("a4.png");
        JLabel paperLabel = new JLabel(paper);
        paperLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        panel.add(paperLabel);
        paperLabel.setBounds(180, 360, 100, 120);

        Icon widescreen = new ImageIcon("widescreen.png");
        JLabel widescreenLabel = new JLabel(widescreen);
        widescreenLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        panel.add(widescreenLabel);

        widescreenLabel.setBounds(330, 390, 100, 60);

        JButton instagrambutton = new JButton("Instagram");
        instagrambutton.setBounds(20, 520, 120, 45);
        panel.add(instagrambutton);
        instagrambutton.setBackground(Color.white);

        JButton paperbutton = new JButton("A4 Paper");
        paperbutton.setBounds(170, 520, 120, 45);
        panel.add(paperbutton);
        paperbutton.setBackground(Color.white);

        JButton widescreenbutton = new JButton("Widescreen");
        widescreenbutton.setBounds(320, 520, 120, 45);
        panel.add(widescreenbutton);
        widescreenbutton.setBackground(Color.white);

        instagrambutton.addActionListener(e -> {
            frame.setVisible(false);
        });

        paperbutton.addActionListener(e -> {
            frame.setVisible(false);
        });

        widescreenbutton.addActionListener(e -> {
            frame.setVisible(false);
        });

        frame.add(panel);
        frame.setVisible(true);
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

    public void updateState(String newState) {
        this.state = newState;
    }

    public void updateColor(Color newColor) {
        this.currentColor = newColor;
    }

    public static void main(String[] args) {
        // Title Button X Offset.
        int cpx = 410;
        setup();
        // Brush State for applying pixels.

        // JFrame and JPanel stuff.
        JFrame frame = new JFrame("Sprinkles Paint Demo");
        JPanel panel = new JPanel();
        Main drawingPanel = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layouts to null, allows for virtually any button location placement.
        panel.setLayout(null);
        panel.setBackground(new Color(220, 220, 220));
        drawingPanel.setLayout(null);


        frame.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        drawingPanel.setBorder(BorderFactory.createBevelBorder(0, Color.darkGray, Color.lightGray));
        drawingPanel.setSize(new Dimension(875, 580));
        drawingPanel.setVisible(true);
        drawingPanel.setLocation(110,100);
        drawingPanel.setBackground(Color.white);

        TextPanel toolStateMessage = new TextPanel();
        toolStateMessage.setLayout(null);
        toolStateMessage.setVisible(true);
        toolStateMessage.setLocation(110,685);
        toolStateMessage.setBackground(Color.white);
        toolStateMessage.setSize(new Dimension(400, 15));
        //toolStateMessage.setHorizontalTextPosition(100);

        frame.add(toolStateMessage);
        frame.add(drawingPanel);
        frame.add(panel);

        ImageIcon icon = new ImageIcon("assets/paint.png");
        frame.setIconImage(icon.getImage());

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu filemenu = new JMenu("Sprinkles Paint Version 1.0");
        menuBar.add(filemenu);

        JMenuItem saveAsItem = new JMenuItem("Credits - Alex Wills, Philip Gray, Ferris Whitney", KeyEvent.VK_T);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.ALT_MASK));
        filemenu.add(saveAsItem);

        // All Buttons
        Icon pencil = new ImageIcon("assets/pencil.png");
        JButton toolbutton = new JButton(pencil);
        toolbutton.setBounds(35, 30, 60, 45);
        panel.add(toolbutton);
        toolbutton.setBackground(Color.white);

        Icon zoomin = new ImageIcon("assets/zoomin.png");
        JButton zoominbutton = new JButton(zoomin);
        zoominbutton.setBounds(55, 600, 30, 25);
        panel.add(zoominbutton);
        zoominbutton.setBackground(Color.white);

        Icon zoomout = new ImageIcon("assets/zoomout.png");
        JButton zoomoutbutton = new JButton(zoomout);
        zoomoutbutton.setBounds(55, 630, 30, 25);
        panel.add(zoomoutbutton);
        zoomoutbutton.setBackground(Color.white);

        Icon brush = new ImageIcon("assets/brush.png");
        JButton brushbutton = new JButton(brush);
        brushbutton.setBounds(110, 30, 60, 45);
        panel.add(brushbutton);
        brushbutton.setBackground(Color.white);

        Icon spray = new ImageIcon("assets/spray.png");
        JButton spraybutton = new JButton(spray);
        spraybutton.setBounds(185, 30, 60, 45);
        panel.add(spraybutton);
        spraybutton.setBackground(Color.white);

        Icon square = new ImageIcon("assets/square.png");
        JButton squarebutton = new JButton(square);
        squarebutton.setBounds(260, 30, 60, 45);
        panel.add(squarebutton);
        squarebutton.setBackground(Color.white);

        Icon circle = new ImageIcon("assets/circle.png");
        JButton circlebutton = new JButton(circle);
        circlebutton.setBounds(335, 30, 60, 45);
        panel.add(circlebutton);
        circlebutton.setBackground(Color.white);

        Icon text = new ImageIcon("assets/text.png");
        JButton textbutton = new JButton(text);
        textbutton.setBounds(565, 30, 60, 45);
        panel.add(textbutton);
        textbutton.setBackground(Color.white);

        Icon save = new ImageIcon("assets/save.png");
        JButton savebutton = new JButton(save);
        savebutton.setBounds(35, 150, 60, 45);
        panel.add(savebutton);
        savebutton.setBackground(Color.white);

        Icon wand = new ImageIcon("assets/wand.png");
        JButton wandbutton = new JButton(wand);
        wandbutton.setBounds(35, 90, 60, 45);
        panel.add(wandbutton);
        wandbutton.setBackground(Color.white);

        Icon open = new ImageIcon("assets/open.png");
        JButton openbutton = new JButton(open);
        openbutton.setBounds(35, 210, 60, 45);
        panel.add(openbutton);
        openbutton.setBackground(Color.white);

        JButton redButton = new JButton("");
        redButton.setBounds(cpx, 30, 20, 20);
        panel.add(redButton);
        redButton.setBackground(Color.RED);

        redButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Red");
            drawingPanel.updateColor(Color.RED);
        });

        savebutton.addActionListener(e -> {
            JFrame saveframe = new JFrame("Save As...");
            saveframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            JPanel savepanel = new JPanel();

            JTextField savepath = new JTextField(1);
            savepath.setBounds(10, 30,300,20);

            savepanel.setLayout(null);
            savepanel.setBackground(new Color(230, 230, 230));
            savepanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            savepanel.add(savepath);

            JLabel textLabel = new JLabel("Type the name of the file you want to save:");
            textLabel.setBounds(10, 7, 1000, 15);
            savepanel.add(textLabel);

            saveframe.pack();
            saveframe.setSize(350, 135);

            JButton savePathButton = new JButton("Save!");
            savePathButton.setBounds(10, 55, 100, 20);
            savepanel.add(savePathButton);
            savePathButton.setBackground(Color.white);

            savePathButton.addActionListener(a -> {
                // Filename below:
                String filename = savepath.getText();
                saveframe.setVisible(false);
            });
            saveframe.add(savepanel);
            saveframe.setVisible(true);
        });

        openbutton.addActionListener(e -> {
            JFrame saveframe = new JFrame("Load File...");
            saveframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            JPanel savepanel = new JPanel();

            JTextField savepath = new JTextField(1);
            savepath.setBounds(10, 30,300,20);

            savepanel.setLayout(null);
            savepanel.setBackground(new Color(230, 230, 230));
            savepanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            savepanel.add(savepath);

            JLabel textLabel = new JLabel("Type the full path of the file you want to open:");
            textLabel.setBounds(10, 7, 1000, 15);
            savepanel.add(textLabel);

            saveframe.pack();
            saveframe.setSize(350, 135);

            JButton savePathButton = new JButton("Open File...");
            savePathButton.setBounds(10, 55, 100, 20);
            savepanel.add(savePathButton);
            savePathButton.setBackground(Color.white);

            savePathButton.addActionListener(a -> {
                // Filename below:
                String filename = savepath.getText();
                saveframe.setVisible(false);
            });
            saveframe.add(savepanel);
            saveframe.setVisible(true);
        });

        JButton orangeButton = new JButton("");
        orangeButton.setBounds(cpx + 25, 30, 20, 20);
        panel.add(orangeButton);
        orangeButton.setBackground(Color.ORANGE);

        orangeButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Orange");
            drawingPanel.updateColor(Color.ORANGE);
        });

        JButton yellowButton = new JButton("");
        yellowButton.setBounds(cpx + 50, 30, 20, 20);
        panel.add(yellowButton);
        yellowButton.setBackground(Color.YELLOW);

        yellowButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Yellow");
            drawingPanel.updateColor(Color.YELLOW);
        });

        JButton greenButton = new JButton("");
        greenButton.setBounds(cpx + 75, 30, 20, 20);
        panel.add(greenButton);
        greenButton.setBackground(Color.GREEN);

        greenButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Green");
            drawingPanel.updateColor(Color.GREEN);
        });

        JButton blueButton = new JButton("");
        blueButton.setBounds(cpx + 100, 30, 20, 20);
        panel.add(blueButton);
        blueButton.setBackground(Color.BLUE);

        blueButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Blue");
            drawingPanel.updateColor(Color.BLUE);
        });

        JButton magentaButton = new JButton("");
        magentaButton.setBounds(cpx + 125, 30, 20, 20);
        panel.add(magentaButton);
        magentaButton.setBackground(Color.MAGENTA);

        magentaButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Magenta");
            drawingPanel.updateColor(Color.MAGENTA);
        });

        JButton pinkButton = new JButton("");
        pinkButton.setBounds(cpx, 55, 20, 20);
        panel.add(pinkButton);
        pinkButton.setBackground(Color.PINK);

        pinkButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Pink");
            drawingPanel.updateColor(Color.PINK);
        });

        JButton whiteButton = new JButton("");
        whiteButton.setBounds(cpx+25, 55, 20, 20);
        panel.add(whiteButton);
        whiteButton.setBackground(Color.WHITE);

        whiteButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: White");
            drawingPanel.updateColor(Color.WHITE);
        });

        JButton blackButton = new JButton("");
        blackButton.setBounds(cpx+50, 55, 20, 20);
        panel.add(blackButton);
        blackButton.setBackground(Color.BLACK);

        blackButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Black");
            drawingPanel.updateColor(Color.BLACK);
        });

        JButton grayButton = new JButton("");
        grayButton.setBounds(cpx+75, 55, 20, 20);
        panel.add(grayButton);
        grayButton.setBackground(Color.GRAY);

        grayButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Gray");
            drawingPanel.updateColor(Color.GRAY);
        });

        JButton cyanButton = new JButton("");
        cyanButton.setBounds(cpx+100, 55, 20, 20);
        panel.add(cyanButton);
        cyanButton.setBackground(Color.CYAN);

        cyanButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Cyan");
            drawingPanel.updateColor(Color.CYAN);
        });

        JButton lightGrayButton = new JButton("");
        lightGrayButton.setBounds(cpx+125, 55, 20, 20);
        panel.add(lightGrayButton);
        lightGrayButton.setBackground(Color.LIGHT_GRAY);

        lightGrayButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Light Gray");
            drawingPanel.updateColor(Color.LIGHT_GRAY);
        });


        /**
         * BUTTONS
         * BUTTON_NAME.addActionListener(new ActionListener() {
         *             public void actionPerformed(ActionEvent e) {
         *                 toolStateMessage.updateText("Tool Selected: Pencil Tool");
         *             }
         *         });
         */

        /**
         * IntelliJ thought we could do better, so we present to you the lambda!
         * https://www.w3schools.com/java/java_lambda.asp
         *
         */

        toolbutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Pencil Tool"));

        brushbutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Brush Tool"));

        spraybutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Spray Tool"));

        textbutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Text Tool"));

        squarebutton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Rectangle Tool");
            drawingPanel.updateState("rect");
        });

        circlebutton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Circle Tool");
            drawingPanel.updateState("circle");
        });

        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1024, 768);
        frame.setVisible(true);

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
    } // MouseHandler

    private static class TextPanel extends JPanel {
        private Graphics graphics;

        public TextPanel() {
        }

        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
        }

        public void updateText(String text){
            graphics = getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0,0,700,20);
            graphics.setColor(Color.black);
            graphics.drawString(text, 12, 12);

        }
    }
}


