import java.awt.*;

/**
 * Think a rectangle, but you can move it and color it!!
 */
public class ColoredRectangle extends Rectangle implements MovableShape {
    Color color;

    public ColoredRectangle(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * getColor
     *
     * Getter method for getting the ColoredRectangle's color.
     * @return Color: The current color of the rectangle.
     */
    public Color getColor() {
        return color;
    }

    public void moveShape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void moveShape(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public void rotateShape() {
        double theta = Math.toRadians(90);
        int centerX = width / 2 + x;
        int centerY = height / 2 + y;

        this.x = (int) (centerX*Math.cos(theta) - centerY*Math.sin(theta));
        this.y = (int) (centerX * Math.sin(theta) + centerY * Math.cos(theta));
    }

    public ColoredRectangle duplicate() {
        return new ColoredRectangle(this.x + 25, y + 25, width, height, color);
    }
}
