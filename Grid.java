import java.awt.*;

public class Grid {
    Pixel[][] drawingGrid;

    public Grid(int height, int width) {
        this.drawingGrid = new Pixel[height][width];

        for (int y = 0; y < drawingGrid.length; y++) {
            for (int x = 0; x < drawingGrid[y].length; x++) {
                drawingGrid[y][x] = new Pixel(0, x, y, Color.WHITE, 0);
            }
        }
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
        Grid newGrid = new Grid(2, 3);
        newGrid.print();
    }
}

