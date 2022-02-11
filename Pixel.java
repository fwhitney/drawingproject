import java.awt.*;

public class Pixel {
    int xPosition;
    int yPosition;
    Color color;
    int groupID;
    int transparency;

    public Pixel(int transparency, int xPosition, int yPosition, Color color, int groupID) {
        this.transparency = transparency;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.groupID = groupID;
    } // pixel

    public Pixel(int transparency, int xPosition, int yPosition) {
        this.transparency = transparency;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public String toString() {
        return color.toString();
    }
}
