/*
Line Test Java Program for Graphics + JFrame
 */

package com.company;
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
        /*
        JFrame stuff
         */
        Drawing drawing = new Drawing(800,600);
        Grid grid = new Grid(600, 800, true);
        int cpx = 260;
        // Brush State for applying pixels.
        int drawstate = 0;

        JFrame frame = new JFrame("Sprinkles Paint Demo");

        JPanel panel = new JPanel();
        DrawingPanel drawingPanel = new DrawingPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        drawingPanel.setLayout(null);
        frame.getContentPane();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3, true));
        drawingPanel.setSize(new Dimension(860, 570));
        drawingPanel.setVisible(true);
        drawingPanel.setLocation(110,100);

        frame.add(drawingPanel);
        frame.add(panel);

        ImageIcon icon = new ImageIcon("paint.png");
        frame.setIconImage(icon.getImage());

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu filemenu = new JMenu("UI Demo - Not Representative of Final Product");
        menuBar.add(filemenu);

        //JMenu brushmenu = new JMenu("Brush");
        //menuBar.add(brushmenu);

        JMenuItem saveAsItem = new JMenuItem("Credits - Alex Wills, Philip Gray, Ferris Whitney",
                KeyEvent.VK_T);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_9, ActionEvent.ALT_MASK));
        saveAsItem.getAccessibleContext().setAccessibleDescription(
                "Credits");
/*
        JMenuItem loadItem = new JMenuItem("Load...",
                KeyEvent.VK_T);
        loadItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        loadItem.getAccessibleContext().setAccessibleDescription(
                "Loads an Image from a path");

        JMenuItem importItem = new JMenuItem("Import",
                KeyEvent.VK_T);
        importItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        importItem.getAccessibleContext().setAccessibleDescription(
                "Imports an image for use with current project.");
*/
        /*
        JRadioButtonMenuItem lineItem = new JRadioButtonMenuItem("Line Tool");
        lineItem.setSelected(true);
        JRadioButtonMenuItem squareItem = new JRadioButtonMenuItem("Square Tool");
        squareItem.setSelected(false);
        JRadioButtonMenuItem circleItem = new JRadioButtonMenuItem("Circle Tool");
        circleItem.setSelected(false);


        //filemenu.add(loadItem);
        //filemenu.add(importItem);
        //brushmenu.add(lineItem);
        //brushmenu.add(squareItem);
        //brushmenu.add(circleItem);
*/
        filemenu.add(saveAsItem);
        Icon pencil = new ImageIcon("pencil.png");
        JButton toolbutton = new JButton(pencil);
        toolbutton.setBounds(35, 30, 60, 45);
        panel.add(toolbutton);
        toolbutton.setBackground(Color.white);

        Icon zoomin = new ImageIcon("zoomin.png");
        JButton zoominbutton = new JButton(zoomin);
        zoominbutton.setBounds(55, 600, 30, 25);
        panel.add(zoominbutton);
        zoominbutton.setBackground(Color.white);

        Icon zoomout = new ImageIcon("zoomout.png");
        JButton zoomoutbutton = new JButton(zoomout);
        zoomoutbutton.setBounds(55, 630, 30, 25);
        panel.add(zoomoutbutton);
        zoomoutbutton.setBackground(Color.white);



        Icon square = new ImageIcon("square.png");
        JButton squarebutton = new JButton(square);
        squarebutton.setBounds(110, 30, 60, 45);
        panel.add(squarebutton);
        squarebutton.setBackground(Color.white);

        Icon circle = new ImageIcon("circle.png");
        JButton circlebutton = new JButton(circle);
        circlebutton.setBounds(185, 30, 60, 45);
        panel.add(circlebutton);
        circlebutton.setBackground(Color.white);

        Icon text = new ImageIcon("text.png");
        JButton textbutton = new JButton(text);
        textbutton.setBounds(415, 30, 60, 45);
        panel.add(textbutton);
        textbutton.setBackground(Color.white);

        Icon save = new ImageIcon("save.png");
        JButton savebutton = new JButton(save);
        savebutton.setBounds(35, 90, 60, 45);
        panel.add(savebutton);
        savebutton.setBackground(Color.white);

        Icon undo = new ImageIcon("undo.png");
        JButton undobutton = new JButton(undo);
        undobutton.setBounds(35, 150, 60, 45);
        panel.add(undobutton);
        undobutton.setBackground(Color.white);

        Icon redo = new ImageIcon("redo.png");
        JButton redobutton = new JButton(redo);
        redobutton.setBounds(35, 210, 60, 45);
        panel.add(redobutton);
        redobutton.setBackground(Color.white);


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


        saveAsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame1 = new JFrame("Save As?");
                frame1.setSize(300, 100);
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);
                frame1.add(panel1);
                JTextField field = new JTextField(10);
                panel1.add(field, BorderLayout.SOUTH);
                frame1.getContentPane();
                frame1.setVisible(true);
            }
        });
        /*loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame1 = new JFrame("Load..");
                frame1.setSize(300, 100);
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);
                frame1.add(panel1);
                JTextField field = new JTextField(10);
                panel1.add(field, BorderLayout.SOUTH);
                frame1.getContentPane();
                frame1.setVisible(true);
            }
        });
        */

        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1024, 768);
        frame.setVisible(true);

    }
}
