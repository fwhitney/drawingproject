import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
 * TODO: REMOVE THE COLOREDSHAPEWRAPPER
 * TODO: ACTUAL MAGIC WAND
 *
 */


public class D3 extends JPanel {
    String state = "pen";
    Color currentColor;

    int shapeWidth = 50;
    int shapeLength = 50;
    int strokeWidth = 10;

    BufferedImage bufferedImage;

    Graphics2D graphics;
    ArrayList<ColoredShapeWrapper> shapes;
    ArrayList<ColoredShapeWrapper> selection;
    ArrayList<Line2D> lines;
    Dimension selectedDimension;

    int clickX;
    int clickY;

    int clickX2; // used for drawing and dragging movements
    int clickY2; // ^

    public D3(int x, int y) {
        setPreferredSize(null); // Apparently JPanel uses a layout manager and this let's the manager handle things?
        this.shapes = new ArrayList<>();
        this.selection = new ArrayList<>();
        this.lines = new ArrayList<>();

        this.selectedDimension = new Dimension(x, y);
        // setSize(); // Which is why you don't want to use this.
        addMouseListener(new MouseHandler()); // This confuses me slightly.

        this.currentColor = Color.RED;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // wish someone explained why we do this
        graphics = (Graphics2D) g;

        if (bufferedImage != null) {
            graphics.drawImage(bufferedImage, 100, 100, null);
        }

        for (Line2D line : lines) {
            graphics.setColor(Color.BLACK);
            graphics.setStroke(new BasicStroke(strokeWidth));
            graphics.draw(line);
        }

        for (ColoredShapeWrapper shape : shapes) {
            graphics.setColor(shape.getColor());
            graphics.fill(shape.getShape());
        }

        for (ColoredShapeWrapper shape : selection) {
            graphics.setColor(shape.getColor());
            graphics.fill(shape.getShape());
        }
    }

    public void moveShapes(int x, int y) {
        for (ColoredShapeWrapper wrapped : selection) {
            wrapped.getShape().moveShape(x, y);
        }
        repaint();
    }

    public void loadImage(String fileName) {
        try {
            bufferedImage = ImageIO.read(new File(fileName));
            graphics = bufferedImage.createGraphics();
            repaint();
        } catch (IOException e) {
            System.out.println("File does not exist!!");
        }
    }

    /**
     * https://danceswithcode.net/engineeringnotes/rotations_in_2d/rotations_in_2d.htm
     *
     * Used for rotating shapes, calls the rotateShape method in each individual shape.
     */
    public void rotateShapes() {
        MovableShape currentShape;
        for (ColoredShapeWrapper wrapped : selection) {
            currentShape = wrapped.getShape();

            currentShape.rotateShape();
            System.out.println(currentShape.getX());
            System.out.println(currentShape.getY());
            /**
            double x = currentShape.getX();
            double y = currentShape.getY();
            System.out.println(x);
            System.out.println(y);
            currentShape.moveShape((x*Math.cos(theta) - y*Math.sin(theta)),(x*Math.sin(theta) +y*Math.cos(theta)));
            System.out.println(currentShape.getX());
            System.out.println(currentShape.getY());
             **/
        }
        repaint();
    }

    /**
     * DUPLICATE SHAPES
     *
     * CALLS THE DUPLICATESHAPE METHOD INSIDE EACH OF THE MOVEABLESHAPES
     */
    public void duplicateShapes() {
        MovableShape newShape;
        for (ColoredShapeWrapper wrapped : selection) {
            newShape = wrapped.getShape().duplicate();
            shapes.add(new ColoredShapeWrapper(newShape.getColor(), newShape));
        }
        repaint();
    }

    /**
     * SELECTION TOOL
     * SELECTS OBJECTS, AND ADDS THEM TO A CUSTOM LIST WHERE YOU CAN DO STUFF WITH THEM LIKE ROTATE OR DUPLICATE
     *
     * @param x
     * @param y
     */
    public void selectionTool(int x, int y) {
        for (int i = selection.size() - 1; i >= 0; i--) {
            MovableShape current = selection.get(i).getShape(); // get shape
            if (current.contains(x, y)) { // if we selected the shape
                System.out.println("unselected a shape");
                shapes.add(selection.remove(i)); // move it over to rotation
                return;
            }
        }

        for (int i = shapes.size() - 1; i >= 0; i--) {
            MovableShape current = shapes.get(i).getShape(); // get shape
            if (current.contains(x, y)) { // if we selected the shape
                System.out.println("Selected a shape");
                selection.add(shapes.remove(i)); // move it over to rotation
                return;

                /**
                 * JUST IN CASE
                 // selection.add(shapes.remove(i));
                 boolean notFound = true;
                 for (ColoredShapeWrapper wrapped : selection) {
                 if (wrapped.getShape() == current) {
                 notFound = false;
                 break;
                 }
                 }
                 if (notFound) {
                 selection.add(shapes.get(i));
                 break;
                 }
                 **/
            }
        }
    } // selection tool

    public void createShape(Graphics2D g, String shape, int x, int y) {
        int x1 = x - (shapeWidth / 2);
        int y1 = y - (shapeLength / 2);

        if (shape.equals("rect")) {

            shapes.add(new ColoredShapeWrapper(currentColor, new ColoredRectangle(x1, y1, shapeWidth, shapeLength, currentColor)));
            // colors.add(currentColor);

        } else if (shape.equals("circle")) {
            // Shape circle = new Ellipse2D.Double(x1, y1, shapeWidth, shapeLength, currentColor);
            shapes.add(new ColoredShapeWrapper(currentColor, new ColoredCircle(x1, y1, shapeWidth, shapeLength, currentColor)));
            // colors.add(currentColor);
        }
        repaint();
    }

    public void updateState(String newState) {
        this.state = newState;
    }

    public void updateColor(Color newColor) {
        this.currentColor = newColor;
    }

    public void eraseEverything() {
        shapes.clear();
        selection.clear();
        lines.clear();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void saveImage(String fileName) {
        BufferedImage img = new BufferedImage(875, 580, BufferedImage.TYPE_INT_RGB);
        fileName = fileName + ".png";

        try {
            paintComponent(img.createGraphics());
            File output = new File(fileName);
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setSelectedDimension(int x, int y) {
        this.selectedDimension = new Dimension(x, y);
    }

    public Dimension getSelectedDimension() {
        return this.selectedDimension;
    }

    public void setShapeDimensions(int width, int length) {
        this.shapeWidth = width;
        this.shapeLength = length;
    }

    public static void main(String[] args) {
        // JFrame and JPanel stuff.
        JFrame frame = new JFrame("Sprinkles Paint Demo");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        D3 drawingPanel = new D3(800, 800);

        JFrame initialframe = new JFrame("Welcome!");
        initialframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel initialpanel = new JPanel();
        initialpanel.setLayout(null);
        initialpanel.setBackground(new Color(230, 230, 230));
        initialpanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        initialframe.pack();
        initialframe.setSize(475, 650);

        JLabel textLabel = new JLabel("Welcome to Sprinkles Paint!");
        textLabel.setBounds(150, 3, 1000, 15);
        initialpanel.add(textLabel);

        JLabel sizeLabel = new JLabel("Please select a size below:");
        sizeLabel.setBounds(150, 330, 1000, 15);
        initialpanel.add(sizeLabel);

        Icon logo = new ImageIcon("assets/logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        initialpanel.add(logoLabel);
        logoLabel.setBounds(85, 20, 300, 300);

        Icon instagram = new ImageIcon("assets/instagram.png");
        JLabel instagramLabel = new JLabel(instagram);
        instagramLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        initialpanel.add(instagramLabel);
        instagramLabel.setBounds(20, 360, 120, 120);

        Icon paper = new ImageIcon("assets/a4.png");
        JLabel paperLabel = new JLabel(paper);
        paperLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        initialpanel.add(paperLabel);
        paperLabel.setBounds(180, 360, 100, 120);

        Icon widescreen = new ImageIcon("widescreen.png");
        JLabel widescreenLabel = new JLabel(widescreen);
        widescreenLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
        initialpanel.add(widescreenLabel);

        widescreenLabel.setBounds(330, 390, 100, 60);

        JButton instagramButton = new JButton("Instagram");
        instagramButton.setBounds(20, 520, 120, 45);
        initialpanel.add(instagramButton);
        instagramButton.setBackground(Color.white);

        JButton paperButton = new JButton("A4 Paper");
        paperButton.setBounds(170, 520, 120, 45);
        initialpanel.add(paperButton);
        paperButton.setBackground(Color.white);

        JButton wideScreenButton = new JButton("Widescreen");
        wideScreenButton.setBounds(320, 520, 120, 45);
        initialpanel.add(wideScreenButton);
        wideScreenButton.setBackground(Color.white);

        instagramButton.addActionListener(e -> {
            initialframe.setVisible(false);
            // https://www.adobe.com/express/discover/sizes/instagram

            drawingPanel.setSelectedDimension(1080, 1080);
            frame.setVisible(true);
        });

        paperButton.addActionListener(e -> {
            initialframe.setVisible(false);
            frame.setVisible(true);
            drawingPanel.setSelectedDimension(250, 250);
        });

        wideScreenButton.addActionListener(e -> {
            initialframe.setVisible(false);
            frame.setVisible(true);
        });

        /**
         *
         *
         *
         *
         *
         *
         * MAIN WINDOW BELOW
         *
         *
         *
         *
         *
         *
         *
         *
         */

        initialframe.add(initialpanel);
        initialframe.setVisible(true);

        // Title Button X Offset.
        int cpx = 410;

        // Set layouts to null, allows for virtually any button location placement.
        panel.setLayout(null);
        panel.setBackground(new Color(220, 220, 220));
        drawingPanel.setLayout(null);

        frame.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        drawingPanel.setBorder(BorderFactory.createBevelBorder(0, Color.darkGray, Color.lightGray));
        drawingPanel.setSize(drawingPanel.getSelectedDimension());
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
        JMenu filemenu = new JMenu("Sprinkles Paint Version -1.0");
        menuBar.add(filemenu);

        JMenuItem saveAsItem = new JMenuItem("Credits - Alex Wills, Philip Gray, Ferris Whitney", KeyEvent.VK_T);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.ALT_MASK));
        filemenu.add(saveAsItem);

        // All Buttons
        Icon pencil = new ImageIcon("assets/pencil.png");
        JButton pencilButton = new JButton(pencil);
        pencilButton.setBounds(35, 30, 60, 45);
        panel.add(pencilButton);
        pencilButton.setBackground(Color.white);

        pencilButton.addActionListener(e -> {
            toolStateMessage.updateText("Pencil Selected");

            boolean selectionComplete = false;
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
            while (!selectionComplete) {
                String widthSelection = JOptionPane.showInputDialog(frame, "Please enter the width of the pencil in pixels (as a number):", null);

                try {
                    int width = Integer.parseInt(widthSelection);

                    drawingPanel.setStrokeWidth(width);
                    selectionComplete = true;

                } catch(NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. \nTry entering that again!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }
            drawingPanel.updateState("pen");


        });

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

        Icon text = new ImageIcon("assets/rotate.png");
        JButton rotateButton = new JButton(text);
        rotateButton.setBounds(565, 30, 60, 45);
        panel.add(rotateButton);
        rotateButton.setBackground(Color.white);

        rotateButton.addActionListener(e -> {
            toolStateMessage.updateText("Selection rotated");
            drawingPanel.rotateShapes();
        });

        Icon duplicateShape = new ImageIcon("assets/duplicate.png");
        JButton duplicateShapeButton = new JButton(duplicateShape);
        duplicateShapeButton.setBounds(640, 30, 60, 45);
        panel.add(duplicateShapeButton);
        duplicateShapeButton.setBackground(Color.white);

        duplicateShapeButton.addActionListener(e -> {
            toolStateMessage.updateText("Selection duplicated");
            drawingPanel.duplicateShapes();
        });
        /**
        Icon moveShape = new ImageIcon("assets/duplicate.png");
        JButton moveShapeButton = new JButton(moveShape);
        moveShapeButton.setBounds(640, 30, 60, 45);
        panel.add(moveShapeButton);
        moveShapeButton.setBackground(Color.white);

        moveShapeButton.addActionListener(e -> {
            toolStateMessage.updateText("Selection moved");
            drawingPanel.moveShapes(25, 25);
        });
         **/


        Icon group = new ImageIcon("assets/group.png");
        JButton groupButton = new JButton(group);
        groupButton.setBounds(715, 30, 60, 45);
        panel.add(groupButton);
        groupButton.setBackground(Color.white);

        groupButton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Selection");
            drawingPanel.updateState("select");
        });

        Icon save = new ImageIcon("assets/save.png");
        JButton saveButton = new JButton(save);
        saveButton.setBounds(35, 150, 60, 45);
        panel.add(saveButton);
        saveButton.setBackground(Color.white);

        Icon wand = new ImageIcon("assets/wand.png");
        JButton wandButton = new JButton(wand);
        wandButton.setBounds(35, 90, 60, 45);
        panel.add(wandButton);
        wandButton.setBackground(Color.white);

        wandButton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Wand");
            drawingPanel.loadImage("jcc11y.png");
            drawingPanel.eraseEverything();
        });

        Icon open = new ImageIcon("assets/open.png");
        JButton openbutton = new JButton(open);
        openbutton.setBounds(35, 210, 60, 45);
        panel.add(openbutton);
        openbutton.setBackground(Color.white);

        Icon share = new ImageIcon("assets/share.png");
        JButton sharebutton = new JButton(share);
        sharebutton.setBounds(35, 270, 60, 45);
        panel.add(sharebutton);
        sharebutton.setBackground(Color.white);

        JButton redButton = new JButton("");
        redButton.setBounds(cpx, 30, 20, 20);
        panel.add(redButton);
        redButton.setBackground(Color.RED);

        redButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Red");
            drawingPanel.updateColor(Color.RED);
        });

        saveButton.addActionListener(e -> {
            JFrame saveFrame = new JFrame("Save As...");
            saveFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            JPanel savePanel = new JPanel();

            JTextField savePath = new JTextField(1);
            savePath.setBounds(10, 30,300,20);

            savePanel.setLayout(null);
            savePanel.setBackground(new Color(230, 230, 230));
            savePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            savePanel.add(savePath);

            JLabel saveLabel = new JLabel("Type the name of the file you want to save:");
            saveLabel.setBounds(10, 7, 1000, 15);
            savePanel.add(saveLabel);

            saveFrame.pack();
            saveFrame.setSize(350, 135);

            JButton savePathButton = new JButton("Save!");
            savePathButton.setBounds(10, 55, 100, 20);
            savePanel.add(savePathButton);
            savePathButton.setBackground(Color.white);

            savePathButton.addActionListener(a -> {
                // Filename below:
                String saveFileName = savePath.getText();
                drawingPanel.saveImage(saveFileName);
                saveFrame.setVisible(false);
                JOptionPane.showMessageDialog(frame,"Image saved as \"" +saveFileName+".png\"");
            });
            saveFrame.add(savePanel);
            saveFrame.setVisible(true);
        });

        openbutton.addActionListener(e -> {
            JFrame loadFrame = new JFrame("Load File...");
            loadFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            JPanel loadPanel = new JPanel();

            JTextField savepath = new JTextField(1);
            savepath.setBounds(10, 30,300,20);

            loadPanel.setLayout(null);
            loadPanel.setBackground(new Color(230, 230, 230));
            loadPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            loadPanel.add(savepath);

            JLabel savetextLabel = new JLabel("Type the full path of the file you want to open:");
            savetextLabel.setBounds(10, 7, 1000, 15);
            loadPanel.add(savetextLabel);

            loadFrame.pack();
            loadFrame.setSize(350, 135);

            JButton savePathButton = new JButton("Open File...");
            savePathButton.setBounds(10, 55, 100, 20);
            loadPanel.add(savePathButton);
            savePathButton.setBackground(Color.white);

            savePathButton.addActionListener(a -> {
                // Filename below:
                String filename = savepath.getText();
                loadFrame.setVisible(false);
            });
            loadFrame.add(loadPanel);
            loadFrame.setVisible(true);
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

        brushbutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Brush Tool"));

        spraybutton.addActionListener(e -> toolStateMessage.updateText("Tool Selected: Spray Tool"));

        squarebutton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Rectangle Tool");

            boolean selectionComplete = false;
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
            while (!selectionComplete) {
                String widthSelection = JOptionPane.showInputDialog(frame, "Please enter the width in pixels (as a number):", null);
                String lengthSelection = JOptionPane.showInputDialog(frame, "Please enter the length in pixels (as a number):", null);
                try {
                    int width = Integer.parseInt(widthSelection);
                    int length = Integer.parseInt(lengthSelection);

                    drawingPanel.setShapeDimensions(width, length);
                    selectionComplete = true;

                } catch(NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. \nTry entering that again!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }

            drawingPanel.updateState("rect");
        });

        circlebutton.addActionListener(e -> {
            toolStateMessage.updateText("Tool Selected: Circle Tool");

            boolean selectionComplete = false;
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
            while (!selectionComplete) {
                String circleSelection = JOptionPane.showInputDialog(frame, "Please enter the size in pixels (as a number) of your circle:", null);
                try {
                    int width = Integer.parseInt(circleSelection);
                    int length = Integer.parseInt(circleSelection);

                    drawingPanel.setShapeDimensions(width, length);
                    selectionComplete = true;

                } catch(NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. \nTry entering that again!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }
            drawingPanel.updateState("circle");
        });

        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1024, 768);
        frame.setVisible(false);

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
            } else if (state.equals("select")) {
                selectionTool(clickX, clickY);
            } else {
                addMouseMotionListener(this);
            }
        }

        /**
         * mouseDragged might seem unimportant when we have mouseReleased,
         * however if we're trying to freeform draw or have some sort of
         * shape to visualize what size shape / item you're building, we
         * want to keep track of the X and Y constantly.
         **/

        public void mouseDragged(MouseEvent event) {
            if (state.equals("pen")) {
                clickX = event.getX();
                clickY = event.getY();
                clickX2 = clickX;
                clickY2 = clickY;
                lines.add(new Line2D.Double(clickX, clickY, clickX2, clickY2));
                repaint();
            }
        }

         public void mouseReleased(MouseEvent event) {
            removeMouseMotionListener(this);
         }
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


