import java.util.ArrayList;

public class Layer {
    ArrayList<Grid> layer;
    int height;
    int width;

    public Layer(int width, int height) {
        this.layer = new ArrayList<>();
        layer.add(new Grid(height, width, false));
    }

    public void newLayer() {
        this.layer.add(new Grid(height, width, true));
    }

    public ArrayList<Grid> getLayer() {
        return layer;
    }
}
