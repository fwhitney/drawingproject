import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Used to wrap the color, line, and stroke all into one object.
 */
public class Line2DWrapper {
    private final Line2D line;
    private final Color color;
    private final Stroke stroke;

    public Line2DWrapper(Line2D line, Color color, Stroke stroke) {
        this.line = line;
        this.color = color;
        this.stroke = stroke;
    }

    /**
     * getLine:
     * Getter method for getting the line2D object.
     * @return Line2D: The Line2D object.
     */
    public Line2D getLine() {
        return line;
    }

    /**
     * getColor:
     * Getter method for getting the current color of the line.
     * @return Color: The color of the line.
     */
    public Color getColor() {
        return color;
    }

    /**
     * getStroke:
     * Getter method for getting the current stroke applied to the line.
     * @return Stroke: The stroke applied to the line.
     */
    public Stroke getStroke() {
        return stroke;
    }
}
