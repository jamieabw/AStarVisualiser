package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Wall {
    int x, y, width, height;
    Wall(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = AStarVisualiser.widthTileConstant;
        this.height = AStarVisualiser.heightTileConstant;
        this.changeDistanceValue();

    }
    public static void placeWall(int x, int y) {
        x = ((int) x / 80) * 80; // this formula turns the coordinate within any tile to its index in the Tile.tiles 2d array
        y = (23 - ((int) y / 45)) * 45;
        new Wall(x, y);
    }
    public void changeDistanceValue()
    {
        Tile.tiles[x / 80][y / 45].distanceValue = 999999999;
        Tile.tiles[x / 80][y / 45].colour = Color.BLACK;
        Tile.tiles[x/80][y/45].property = "wall";
        // both the property and the distanceValue show that this tile is now being used by a wall so cannot be included in the A* path
    }

}
