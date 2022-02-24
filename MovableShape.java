import java.awt.*;

public interface MovableShape extends Shape {
    /**
     * moveShape
     *
     * Moves the shape to a new X, Y coordinate.
     * @param x Int: The new x coordinate.
     * @param y Int: The new y coordinate.
     */
    void moveShape(int x, int y);

    /**
     * moveShape
     *
     * Moves the shape to a new X, Y coordinate.
     * @param x Double: The new x coordinate.
     * @param y Double: The new y coordinate.
     */
    void moveShape(double x, double y);

    /**
     * getX The getter method for getting the x coordinate.
     * @return Int: The X coordinate the shape is centered on.
     */
    double getX();
    /**
     * getY The getter method for getting the y coordinate.
     * @return Int: The Y coordinate the shape is centered on.
     */
    double getY();

    /**
     * rotateShape:
     * Rotates a shape 90 degrees.
     */
    void rotateShape();

    /**
     * duplicate:
     * Duplicates the current shape, and places it a few pixels higher than the original.
     * @return MovableShape: The newly duplicated shape.
     */
    MovableShape duplicate();

    /**
     * getColor:
     * Getter method for getting the current color of the shape.
     * @return Color: The color of the shape.
     */
    Color getColor();
}
