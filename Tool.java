import java.awt.*;
import java.util.ArrayList;

public class Tool {
    public void pixelTool(Drawing drawing, int x, int y, Color color) {
        ArrayList<Grid> layers = drawing.getLayers();
        for (int i = layers.size() - 1; i <= 0; i--) {
            Pixel pixel = layers.get(i).getPixel(x, y);
            if (pixel.getTransparency() < 100) {
                pixel.changeColor(color);
            } else if (pixel.getTransparency() > 100) {
                System.out.println("Pixel transparency greater than 100!!");
            }
        }
    }

    public void rectangleTool(Drawing drawing, int x1, int y1, int x2, int y2, Color color, int groupID) {
        drawing.newLayer();
        ArrayList<Grid> layers = drawing.getLayers();
        Grid lastLayer = layers.get(drawing.getLayers().size());
        Pixel[][] grid = lastLayer.getGrid();

        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x < x2; x++) {
                grid[y][x].changeColor(color);
                grid[y][x].changeGroupID(groupID);
            }
        }
    }
}
