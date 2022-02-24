import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.datatransfer.*;


// ferris

/**
 * Credit to David J. Eck for creating, "Introduction to Computer Graphics"
 * https://math.hws.edu/graphicsbook/index.html
 *
 * This book has been fundamental in helping me start building my understanding of
 * how to use graphics2D, java.awt, and swing.
 * https://math.hws.edu/graphicsbook/c2/s5.html
 *
 * Various resources:
 * https://docs.oracle.com/javase/tutorial/2d/advanced/user.html
 * https://docs.oracle.com/javase/tutorial/uiswing/painting/index.html
 * https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseAdapter.html
 * https://coderanch.com/t/338230/java/Painting-dissappears-resizing-mouse
 * https://www.oracle.com/java/technologies/painting.html
 * https://community.oracle.com/tech/developers/discussion/3770725/select-java-graphic-and-drag-them
 * https://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
 * https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html
 * https://docs.oracle.com/javase/8/docs/api/java/awt/Shape.html
 * https://stackoverflow.com/questions/35507152/java-graphics2d-drawing-into-bufferedimage
 *
 *
 *
 * TODO: REMOVE THE COLOREDSHAPEWRAPPER
 * TODO: ACTUAL MAGIC WAND
 *
 */


public class D3 extends JPanel {
    private String state = "pencil";
    private Color currentColor;

    private int shapeWidth = 50;
    private int shapeLength = 50;
    private int strokeWidth = 10;

    private BufferedImage bufferedImage;

    private Graphics2D graphics;
    private final ArrayList<ColoredShapeWrapper> shapes;
    private final ArrayList<ColoredShapeWrapper> selection;
    private final ArrayList<Line2DWrapper> lines;
    private Dimension selectedDimension;

    private int clickX;
    private int clickY;

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

    /**
     * paintComponent
     *
     * https://docs.oracle.com/javase/tutorial/uiswing/painting/closer.html
     * Where all the painting occurs.
     * @param g Graphics: The graphics object.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // wish someone explained why we do this
        graphics = (Graphics2D) g;

        if (bufferedImage != null) {
            graphics.drawImage(bufferedImage, 100, 100, null);
        }

        for (Line2DWrapper line : lines) {
            graphics.setColor(line.getColor());
            graphics.setStroke(line.getStroke());
            graphics.draw(line.getLine());
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

    /**
     * moveShapes
     *
     * Takes in a set of X and Y coordinates and moves each selected object by those coordinates.
     * @param x Integer: Represents the amount we move left or right. (Positive is right, negative is left)
     * @param y Integer: Represents the amount we move down or up. (Positive is down, negative is up)
     */
    public void moveShapes(int x, int y) {
        for (ColoredShapeWrapper wrapped : selection) {
            wrapped.getShape().moveShape(x, y);
        }
        repaint();
    }

    /**
     * loadImage
     *
     * Loads in an image, and paints it to the canvas.
     * @param fileName String The full filename of the image we're loading.
     */
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
     * rotateShapes
     *
     * https://danceswithcode.net/engineeringnotes/rotations_in_2d/rotations_in_2d.htm
     *
     * Used for rotating shapes, calls the rotate shape method in every shape that was selected.
     */
    public void rotateShapes() {
        MovableShape currentShape;
        for (ColoredShapeWrapper wrapped : selection) {
            currentShape = wrapped.getShape();

            currentShape.rotateShape();
            System.out.println(currentShape.getX());
            System.out.println(currentShape.getY());
        }
        repaint();
    }

    /**
     * duplicateShapes
     *
     * Duplicates every shape that was selected, and places it slightly further away from the original.
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
     * selectionTool
     *
     * https://docs.oracle.com/javase/tutorial/2d/advanced/user.html
     * Used for selecting, or deselecting objects. Uses the .contains() method in shapes.
     *
     * Checks through the shape or selection list to see if there are any
     * shapes at the entered coordinates, and if they are they're added to either the selection list, and removed from
     * the shapes list. For selected objects, it removes them from the selection list, and adds them the shapes list.
     * @param x Int: The x coordinate that we're checking.
     * @param y Int: The y coordinate we're checking.
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
            }
        }
    } // selectionTool


    /**
     * createShape
     *
     * Creates a shape based centered on entered X and Y coordinates, and the string shape that is passed.
     * TODO: Remove the shape string, as it's a global variable?
     *
     * @param shape String: The name of the shape we're building.
     * @param x Int: The x coordinate that we're centering our shape on.
     * @param y Int: The y coordinate that we're centering our shape on.
     */
    public void createShape(String shape, int x, int y) {
        int x1 = x - (shapeWidth / 2);
        int y1 = y - (shapeLength / 2);

        if (shape.equals("rect")) {
            shapes.add(new ColoredShapeWrapper(currentColor, new ColoredRectangle(x1, y1, shapeWidth, shapeLength, currentColor)));

        } else if (shape.equals("circle")) {
            shapes.add(new ColoredShapeWrapper(currentColor, new ColoredCircle(x1, y1, shapeWidth, shapeLength, currentColor)));
        }
        repaint();
    } // createShape

    /**
     * updateState
     * Updates the state we're currently in. I.E. Pen, Rectangle, etc. Used for determining various mouse actions.
     *
     * @param newState String: The new state that we're entering.
     */
    public void updateState(String newState) {
        this.state = newState;
    }

    /**
     * updateColor
     * Updates the color that is being used for drawing or shape creation.
     * @param newColor Color: The new color that we're using.
     */
    public void updateColor(Color newColor) {
        this.currentColor = newColor;
    }

    /**
     * eraseEverything
     * Erases all drawings from the screen, by removing everything from the shapes, selection, and lines lists.
     */
    public void eraseEverything() {
        lines.clear();
        shapes.clear();
        selection.clear();
    }

    /**
     * setStrokeWidth
     *
     * Updates the stroke width, for different pen movements.
     * @param newStrokeWidth Int: The new width to be used for strokes.
     */
    public void setStrokeWidth(int newStrokeWidth) {
        this.strokeWidth = newStrokeWidth;
    }

    /**
     * saveImage
     *
     * Saves a file as a .png, in the directory wherever the program is saved in.
     * @param fileName String: The name of the file WITHOUT the extension.
     */
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

    /**
     * toClipboard
     *
     * Copies an image to the system clipboard.
     */
    public void toClipboard() {

        BufferedImage img = new BufferedImage(selectedDimension.width, selectedDimension.height, BufferedImage.TYPE_INT_RGB);
        paintComponent(img.createGraphics());

        ClipboardImage ci = new ClipboardImage();
        ci.copyImage(img);
    }

    /**
     * setSelectedDimension
     *
     * Setter method for getting the selectedDimension, which represents the canvas size.
     * Not sure if saying setter method / getter method is better than just a description? Feels kinda obvious?
     * @param x Int: The width of the current selectedDimension.
     * @param y Int: The height of the current selectedDimension.
     */
    public void setSelectedDimension(int x, int y) {
        this.selectedDimension = new Dimension(x, y);
    }

    /**
     * getSelectedDimension
     *
     * Getter method for the selectedDimension.
     * @return Returns the selected dimension.
     */
    public Dimension getSelectedDimension() {
        return this.selectedDimension;
    }

    /**
     * setShapeDimensions
     *
     * Used for setting the width and length of newly created shapes.
     *
     * @param width Int: The width for newly created shapes.
     * @param length Int: The length for newly created shapes.
     */
    public void setShapeDimensions(int width, int length) {
        this.shapeWidth = width;
        this.shapeLength = length;
    }

    /**
     * I am sorry.
     */
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

        Icon widescreen = new ImageIcon("assets/widescreen.png");
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
            frame.setVisible(true);
            drawingPanel.setSelectedDimension(750, 750);
            drawingPanel.setSize(drawingPanel.getSelectedDimension());
        });

        paperButton.addActionListener(e -> {
            initialframe.setVisible(false);
            frame.setVisible(true);
            drawingPanel.setSelectedDimension(500, 500);
            drawingPanel.setSize(drawingPanel.getSelectedDimension());
        });

        wideScreenButton.addActionListener(e -> {
            initialframe.setVisible(false);
            frame.setVisible(true);
            drawingPanel.setSelectedDimension(1000, 1000);
            drawingPanel.setSize(drawingPanel.getSelectedDimension());
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

        drawingPanel.setBorder(BorderFactory.createEmptyBorder());
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
            drawingPanel.updateState("pencil");


        });

        Icon brush = new ImageIcon("assets/brush.png");
        JButton brushButton = new JButton(brush);
        brushButton.setBounds(110, 30, 60, 45);
        panel.add(brushButton);
        brushButton.setBackground(Color.white);

        brushButton.addActionListener(e -> {
            drawingPanel.updateState("brush");
            boolean selectionComplete = false;
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
            while (!selectionComplete) {
                String widthSelection = JOptionPane.showInputDialog(frame, "Please enter the width of the brush in pixels (as a number):", null);

                try {
                    int width = Integer.parseInt(widthSelection);

                    drawingPanel.setStrokeWidth(width);
                    selectionComplete = true;

                } catch(NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. \nTry entering that again!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
            drawingPanel.loadImage("jcc.png");
            drawingPanel.eraseEverything();
        });

        Icon open = new ImageIcon("assets/open.png");
        JButton openbutton = new JButton(open);
        openbutton.setBounds(35, 210, 60, 45);
        panel.add(openbutton);
        openbutton.setBackground(Color.white);

        Icon share = new ImageIcon("assets/share.png");
        JButton shareButton = new JButton(share);
        shareButton.setBounds(185, 30, 60, 45);
        panel.add(shareButton);
        shareButton.setBackground(Color.white);

        shareButton.addActionListener(e -> {
            toolStateMessage.updateText("Copied to your clipboard");
            drawingPanel.toClipboard();
        });

        JButton redButton = new JButton("");
        redButton.setBounds(cpx, 30, 20, 20);
        panel.add(redButton);
        redButton.setBackground(Color.RED);

        redButton.addActionListener(e -> {
            toolStateMessage.updateText("Color Selected: Red");
            drawingPanel.updateColor(Color.RED);
        });

        saveButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(frame,
                    "Please enter the name you want to save your beautiful drawing:", null);
            drawingPanel.saveImage(fileName);
            toolStateMessage.updateText("Image saved!");
        });

        openbutton.addActionListener(e -> {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            String fileName = JOptionPane.showInputDialog(frame,
                    "Please enter the name and extension i.e. .png, .jpg, etc. of the image you want to open:",
                    null);
            drawingPanel.loadImage(fileName);
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

        Icon move = new ImageIcon("assets/move.png");
        JButton moveButton = new JButton(move);
        moveButton.setBounds(790, 30, 60, 45);
        panel.add(moveButton);
        moveButton.setBackground(Color.white);

        moveButton.addActionListener(e -> {
            toolStateMessage.updateText("Selection moved");
            drawingPanel.updateState("move");
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
                createShape(state, clickX, clickY);
            } else if (state.equals("select")) {
                selectionTool(clickX, clickY);
            } else if (state.equals("move")) {
                moveShapes(clickX, clickY);
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
            if (state.equals("pencil") || state.equals("brush")) {
                clickX = event.getX();
                clickY = event.getY();
                // used for drawing and dragging movements
                int clickX2 = clickX;
                // ^
                int clickY2 = clickY;
                if (state.equals("pencil")) {
                    lines.add(new Line2DWrapper(new Line2D.Double(clickX, clickY, clickX2, clickY2),
                            currentColor, new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)));
                } else if (state.equals("brush")) {
                    System.out.println("test");
                    lines.add(new Line2DWrapper(new Line2D.Double(clickX, clickY, clickX2, clickY2),
                            currentColor, new BasicStroke(strokeWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL)));
                }
                repaint();
            }
        }

         public void mouseReleased(MouseEvent event) {
            removeMouseMotionListener(this);
         }
    } // MouseHandler

    private static class TextPanel extends JPanel {

        public TextPanel() {
        }

        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
        }

        public void updateText(String text){
            Graphics graphics = getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0,0,700,20);
            graphics.setColor(Color.black);
            graphics.drawString(text, 12, 12);

        }
    }

    /**
     * COMPLETELY TAKEN FROM:
     * https://coderanch.com/t/333565/java/BufferedImage-System-Clipboard
     * and
     * https://stackoverflow.com/questions/4552045/copy-bufferedimage-to-clipboard
     */
    public class ClipboardImage implements ClipboardOwner {

        // https://stackoverflow.com/questions/4552045/copy-bufferedimage-to-clipboard
        public void copyImage(BufferedImage bi)
        {
            TransferableImage trans = new TransferableImage( bi );
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents( trans, this );
        }

        public void lostOwnership(Clipboard clip, Transferable trans ) {
            System.out.println( "Lost Clipboard Ownership" );
        }

        // https://docs.oracle.com/javase/7/docs/api/javax/activation/DataHandler.html
        // https://docs.oracle.com/javase/7/docs/api/java/awt/datatransfer/Transferable.html
        private class TransferableImage implements Transferable {

            Image i;

            public TransferableImage( Image i ) {
                this.i = i;
            }

            public Object getTransferData( DataFlavor flavor )
                    throws UnsupportedFlavorException {
                if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                    return i;
                }
                else {
                    throw new UnsupportedFlavorException( flavor );
                }
            }

            public DataFlavor[] getTransferDataFlavors() {
                DataFlavor[] flavors = new DataFlavor[ 1 ];
                flavors[ 0 ] = DataFlavor.imageFlavor;
                return flavors;
            }

            public boolean isDataFlavorSupported( DataFlavor flavor ) {
                DataFlavor[] flavors = getTransferDataFlavors();
                for (DataFlavor dataFlavor : flavors) {
                    if (flavor.equals(dataFlavor)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}


