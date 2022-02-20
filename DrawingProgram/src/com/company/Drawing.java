package com.company;

import java.util.ArrayList;

public class Drawing {
    ArrayList<Grid> layers;
    int width;
    int height;
    State state;
    // int totalGroups;

    public Drawing(int width, int height) {
        this.width = width;
        this.height = height;

        this.layers = new ArrayList<>();
        layers.add(new Grid(height, width, false)); // should I do this?
    }

    public void newLayer() {
        layers.add(new Grid(this.height, this.width, true));
    }

    public void doAction() {
    }

    public ArrayList<Grid> getLayers() {
        return layers;
    }
}