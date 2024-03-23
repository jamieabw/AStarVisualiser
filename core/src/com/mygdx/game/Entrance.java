package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Entrance {
    int x, y, width, height;
    Entrance() {
        this.x = 0;
        this.y = 1080 - 45; // refer the comment on Exit.java
        this.width = AStarVisualiser.widthTileConstant;
        this.height = AStarVisualiser.heightTileConstant;


    }
    public void renderEntrance() {
        AStarVisualiser.sr.setColor(Color.RED);
        AStarVisualiser.sr.rect(this.x, this.y, this.width, this.height);
    }
}
