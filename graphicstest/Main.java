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
import java.awt.Graphics;
import javax.swing.WindowConstants;

public class Main {

    public static void main(String[] args) {
        /*
        JFrame stuff
         */
        Drawing drawing = new Drawing(800,600);
        Grid grid = new Grid(600, 800, true);

        // Brush State for applying pixels.
        int drawstate = 0;
        JFrame frame = new JFrame("Graphics Test");
        DrawingPanel panel = new DrawingPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        frame.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        frame.add(panel);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        menuBar.add(filemenu);
        JMenu brushmenu = new JMenu("Brush");
        menuBar.add(brushmenu);

        JMenuItem saveAsItem = new JMenuItem("Save As...",
                KeyEvent.VK_T);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        saveAsItem.getAccessibleContext().setAccessibleDescription(
                "Save file with a specified type...");

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

        JRadioButtonMenuItem lineItem = new JRadioButtonMenuItem("Line Tool");
        lineItem.setSelected(true);
        JRadioButtonMenuItem squareItem = new JRadioButtonMenuItem("Square Tool");
        squareItem.setSelected(false);
        JRadioButtonMenuItem circleItem = new JRadioButtonMenuItem("Circle Tool");
        circleItem.setSelected(false);

        filemenu.add(saveAsItem);
        filemenu.add(loadItem);
        filemenu.add(importItem);
        brushmenu.add(lineItem);
        brushmenu.add(squareItem);
        brushmenu.add(circleItem);

        /*
        JButton toolbutton = new JButton("Tools");
        toolbutton.setBounds(30, 30, 100, 40);
        panel.add(toolbutton);
        JButton brushbutton = new JButton("Brush");
        brushbutton.setBounds(160, 30, 100, 40);
        panel.add(brushbutton);
        JButton circlebutton = new JButton("Circle");
        circlebutton.setBounds(290, 30, 100, 40);
        panel.add(circlebutton);
        JButton squarebutton = new JButton("Square");
        squarebutton.setBounds(420, 30, 100, 40);
        panel.add(squarebutton);
        JButton linebutton = new JButton("Line");
        linebutton.setBounds(550, 30, 100, 40);
        panel.add(linebutton);
        */

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
        loadItem.addActionListener(new ActionListener() {
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
        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1024, 768);
        frame.setVisible(true);

    }
}
