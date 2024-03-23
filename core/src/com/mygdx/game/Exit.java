package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Exit {
    int x, y, width, height;
    Exit() {
        this.x = 1920 - 80; // the coordinates are currently hardcoded in - will eventually be able to place using keys
        this.y = 0;
        this.width = AStarVisualiser.widthTileConstant;
        this.height = AStarVisualiser.heightTileConstant;


    }
    public void renderExit() {
        AStarVisualiser.sr.setColor(Color.GREEN);
        AStarVisualiser.sr.rect(this.x, this.y, this.width, this.height);
    }
}
