package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
public class Tile {
    int width, height, x, y;
    double distanceValue, shortestPathValue, totalDistanceWeight;
    String property;

    static final int constant = 24;
    Tile previousTile;
    static Tile[][] tiles = new Tile[constant][constant];
    Color colour;
    Tile(int x, int y, Color colour) {
        this.width = AStarVisualiser.widthTileConstant;
        this.height = AStarVisualiser.heightTileConstant;
        this.x = x;
        this.y = y;
        this.distanceValue = assignDistanceValue(); // distance value is the heuristic value, euclidean distance is used here
        this.shortestPathValue = 9999; // the default value which will most likely change when running the algorithm
        this.colour = colour;
        this.totalDistanceWeight = this.distanceValue + this.shortestPathValue; // the total weighted value which each tile is sorted on in the queue
        this.previousTile = null;
        this.property = "";
    }
    public static void fillMap() {
        for (int i = 0; i < constant; i++) {
            for (int j = 0; j < constant; j++) {
                tiles[j][i] = new Tile(80 * j, 45 * i, Color.WHITE);
            }
        }
    }
    public static void renderTiles() {
        for (int i = 0; i < constant; i++) {
            for (int j = 0; j < constant; j++) {
                Tile tempTile = tiles[j][i];
                AStarVisualiser.sr.setColor(tempTile.colour);
                AStarVisualiser.sr.rect(tempTile.x, tempTile.y, tempTile.width, tempTile.height);
            }

        }
    }
    public double assignDistanceValue() { // uses euclidean distance for the heuristic value
        int x2 = AStarVisualiser.exit.x;
        int y2 = AStarVisualiser.exit.y;
        return Math.sqrt(Math.pow(x2 - this.x, 2)) + Math.sqrt(Math.pow(y2 - this.y, 2));
    }
    public double getTotalDistanceWeight() {
        return this.totalDistanceWeight;
    }
    public static void resetTiles() { // for right clicking, erasing all walls and distanceValue changes
        for (Tile[] tiles : tiles) {
            for (Tile tile : tiles) {
                tile.colour = Color.WHITE;
                tile.distanceValue = tile.assignDistanceValue();
                tile.totalDistanceWeight = tile.distanceValue + tile.shortestPathValue;
                tile.property = "";

            }
        }
    }
}