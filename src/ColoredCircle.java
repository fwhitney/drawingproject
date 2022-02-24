import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ColoredCircle extends Ellipse2D.Double implements MovableShape {
    Color color;

    public ColoredCircle(double x, double y, double w, double h, Color color) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = color;
    }

    public void moveShape(int x, int y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    public void moveShape(double x, double y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    public Color getColor() {
        return this.color;
    }

    public void rotateShape() {
    }

    public ColoredCircle duplicate() {
        return new ColoredCircle(this.x + 25, y + 25, width, height, color);
    }
}
