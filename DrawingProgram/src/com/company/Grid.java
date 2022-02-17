package com.company;

import java.awt.*;

public class Grid {
    Pixel[][] drawingGrid;

    public Grid(int height, int width, boolean transparent) {
        this.drawingGrid = new Pixel[height][width];

        fillGrid(transparent);
    }

    public void fillGrid(boolean transparent) {
        if (transparent) {
            for (int y = 0; y < drawingGrid.length; y++) {
                for (int x = 0; x < drawingGrid[y].length; x++) {
                    drawingGrid[y][x] = new Pixel(100, x, y, Color.WHITE, 0);
                }
            }
        } else {
            for (int y = 0; y < drawingGrid.length; y++) {
                for (int x = 0; x < drawingGrid[y].length; x++) {
                    drawingGrid[y][x] = new Pixel(0, x, y, Color.WHITE, 0);
                }
            }
        }
    }

    public Pixel[][] getGrid() {
        return drawingGrid;
    }

    public Pixel getPixel(int xPosition, int yPosition) {
        return drawingGrid[yPosition][xPosition];
    }

    public void print() {
        for (Pixel[] x : drawingGrid) {
            for (Pixel y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Grid newGrid = new Grid(2, 3, false);
        newGrid.print();
    }
}

