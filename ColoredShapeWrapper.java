import java.awt.Color;

/**
 * God bless Stackoverflow
 * https://stackoverflow.com/questions/24324066/java-changing-colour-of-shape
 *
 * This wrapped combines both a shape and color object, so instead of trying to create a colored shape that doesn't work,
 * we can pass both the color and shape to graphics!
 */
public class ColoredShapeWrapper {
    Color color;
    MovableShape shape;

    public ColoredShapeWrapper(Color color, MovableShape shape) {
        this.color = color;
        this.shape = shape;
    }

    /**
     * getColor:
     * Getter method for getting the current color of the shape.
     * @return Color: The color of the line.
     */
    public Color getColor() {
        return color;
    }

    /**
     * getShape:
     * Getter method for getting the current shape.
     * @return Shape: The shape object.
     */
    public MovableShape getShape() {
        return shape;
    }

    public void moveShape(int x, int y) {
        this.shape.moveShape(x, y);
    }

}
