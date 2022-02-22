import java.awt.*;

public interface MovableShape extends Shape {
    void moveShape(int x, int y);
    void moveShape(double x, double y);
    double getX();
    double getY();
    void rotateShape();
    MovableShape duplicate();
    Color getColor();
}
