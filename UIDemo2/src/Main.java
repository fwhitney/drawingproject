/*
Sprinkles Paint
 */
import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Main {

    public static void main(String[] args) {
        // Title Button X Offset.
        int cpx = 410;

        // Brush State for applying pixels.
        String toolstate = null;

        // JFrame and JPanel stuff.
        JFrame frame = new JFrame("Sprinkles Paint Demo");
        JPanel panel = new JPanel();
        DrawingPanel drawingPanel = new DrawingPanel();
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

        JButton linebutton = new JButton("");
        linebutton.setBounds(cpx, 30, 20, 20);
        panel.add(linebutton);
        linebutton.setBackground(Color.red);

        JButton orangebutton = new JButton("");
        orangebutton.setBounds(cpx + 25, 30, 20, 20);
        panel.add(orangebutton);
        orangebutton.setBackground(Color.orange);

        JButton yellowbutton = new JButton("");
        yellowbutton.setBounds(cpx + 50, 30, 20, 20);
        panel.add(yellowbutton);
        yellowbutton.setBackground(Color.yellow);

        JButton greenbutton = new JButton("");
        greenbutton.setBounds(cpx + 75, 30, 20, 20);
        panel.add(greenbutton);
        greenbutton.setBackground(Color.green);

        JButton bluebutton = new JButton("");
        bluebutton.setBounds(cpx + 100, 30, 20, 20);
        panel.add(bluebutton);
        bluebutton.setBackground(Color.blue);

        JButton magentabutton = new JButton("");
        magentabutton.setBounds(cpx + 125, 30, 20, 20);
        panel.add(magentabutton);
        magentabutton.setBackground(Color.magenta);

        JButton pinkbutton = new JButton("");
        pinkbutton.setBounds(cpx, 55, 20, 20);
        panel.add(pinkbutton);
        pinkbutton.setBackground(Color.pink);

        JButton whitebutton = new JButton("");
        whitebutton.setBounds(cpx+25, 55, 20, 20);
        panel.add(whitebutton);
        whitebutton.setBackground(Color.white);

        JButton blackbutton = new JButton("");
        blackbutton.setBounds(cpx+50, 55, 20, 20);
        panel.add(blackbutton);
        blackbutton.setBackground(Color.black);

        JButton graybutton = new JButton("");
        graybutton.setBounds(cpx+75, 55, 20, 20);
        panel.add(graybutton);
        graybutton.setBackground(Color.gray);

        JButton cyanbutton = new JButton("");
        cyanbutton.setBounds(cpx+100, 55, 20, 20);
        panel.add(cyanbutton);
        cyanbutton.setBackground(Color.cyan);

        JButton lightGraybutton = new JButton("");
        lightGraybutton.setBounds(cpx+125, 55, 20, 20);
        panel.add(lightGraybutton);
        lightGraybutton.setBackground(Color.lightGray);

        toolbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Pencil Tool");
            }
        });
        brushbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Brush Tool");
            }
        });
        spraybutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Spray Tool");
            }
        });
        textbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Text Tool");
            }
        });
        squarebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Rectangle Tool");
            }
        });
        circlebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolStateMessage.updateText("Tool Selected: Circle Tool");
            }
        });

        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1024, 768);
        frame.setVisible(true);

    }
}
