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

    public Color changeColor(Color color) {
        Color previousColor = this.color;
        this.color = color;
        return previousColor;
    } // changeColor

    public int changeTransparency(int transparency) {
        int previousTransparency = this.transparency;
        this.transparency = transparency;
        return previousTransparency;
    } // changeTransparency

    public Color getColor() {
        return color;
    }

    public int getTransparency() {
        return transparency;
    }

    public void changeGroupID(int newGroupID) {
        this.groupID = newGroupID;
    }

}
