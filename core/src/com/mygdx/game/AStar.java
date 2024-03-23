package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class AStar {
    static ArrayList<Tile> previouslySeenTiles, allTilesInvolved;
    static ArrayList<Tile> pathToTarget;
    public static void performAlgorithm (Tile startingTile, Tile targetTile) throws InterruptedException {
        boolean found = false;
        previouslySeenTiles = new ArrayList<>(); // only tiles which have been searched
        allTilesInvolved = new ArrayList<>(); // all neighbours and searched tiles placed here
        pathToTarget = new ArrayList<>(); // to store the tile path from the entrance to the exit (target)
        startingTile.shortestPathValue = 0;
        AStarVisualiser.queue.enqueue(startingTile);
        Tile currentTile = startingTile;
        /*
        in each run of the loop, all neighbouring tiles of the current tile are enqueued to the priority queue and the current
        tile is checked for whether it is the target
        * */
        while (!found) {
            if (currentTile == targetTile) {
                found = true;
                break;
            }
            getNeighbours(currentTile);
            previouslySeenTiles.add(currentTile);
            try {
                currentTile = AStarVisualiser.queue.dequeue();
            } catch (IndexOutOfBoundsException e) {
                return;
            }


        }
        if (found) {
            while (currentTile != startingTile) {
                pathToTarget.add(currentTile);
                currentTile = currentTile.previousTile;
            }

        }



    }
    /*
    the only way i could figure out preventing an out of bounds exception was to manually check each neighbours existence within the array,to
    check whether they were a wall or not (which would be shown by them having a much larger distanceValue or by using the property field) and also to
    check whether they have already been seen before.
    * */
    public static void getNeighbours(Tile currentTile) throws InterruptedException {
        int[] tileIndex = {currentTile.x / 80, currentTile.y / 45};
        Tile[] tempNeighbours = new Tile[4];
        if (currentTile.y < 1035 && !Tile.tiles[tileIndex[0]][tileIndex[1] +1].property.equals("wall") && !allTilesInvolved.contains(Tile.tiles[tileIndex[0]][tileIndex[1] +1])) {
            tempNeighbours[0] = Tile.tiles[tileIndex[0]][tileIndex[1] +1];
            allTilesInvolved.add(tempNeighbours[0]);
            assignNewMinDistance(tempNeighbours[0], currentTile, 45);
            //tempNeighbours[0].colour = Color.DARK_GRAY;
        }
        if (currentTile.y > 0&& !Tile.tiles[tileIndex[0]][tileIndex[1] -1].property.equals("wall") && !allTilesInvolved.contains(Tile.tiles[tileIndex[0]][tileIndex[1] -1])) {
            tempNeighbours[3] = Tile.tiles[tileIndex[0]][tileIndex[1] -1];
            assignNewMinDistance(tempNeighbours[3], currentTile, 45);
            allTilesInvolved.add(tempNeighbours[3]);
        }
        if (currentTile.x < 1840&& !Tile.tiles[tileIndex[0] + 1][tileIndex[1]].property.equals("wall") && !allTilesInvolved.contains(Tile.tiles[tileIndex[0] + 1][tileIndex[1]])) {
            tempNeighbours[2] = Tile.tiles[tileIndex[0] + 1][tileIndex[1]];
            assignNewMinDistance(tempNeighbours[2], currentTile, 80);
            allTilesInvolved.add(tempNeighbours[2]);
        }
        if (currentTile.x > 0&& !Tile.tiles[tileIndex[0] - 1][tileIndex[1]].property.equals("wall") && !allTilesInvolved.contains(Tile.tiles[tileIndex[0] - 1][tileIndex[1]])) {
            tempNeighbours[1] = Tile.tiles[tileIndex[0] - 1][tileIndex[1]];
            assignNewMinDistance(tempNeighbours[1], currentTile, 80);
            allTilesInvolved.add(tempNeighbours[1]);

        }
        for (Tile tile : tempNeighbours) {
            if (tile != null) {
                if (!previouslySeenTiles.contains(tile)) {
                    AStarVisualiser.queue.enqueue(tile);
                    AStarVisualiser.sr.begin(ShapeRenderer.ShapeType.Filled);
                    AStarVisualiser.sr.end();
                }
            }
        }

    }
    public static void assignNewMinDistance(Tile tile, Tile previousTile, int distanceFromPreviousTile) {
        if (tile.shortestPathValue > previousTile.shortestPathValue + distanceFromPreviousTile) {
            tile.shortestPathValue = previousTile.shortestPathValue + distanceFromPreviousTile;
            tile.previousTile = previousTile;
        }
    }
    // called before running the algorithm to prevent any previous calls from interfering with the current call
    public static void resetAlgorithm() {
        for (Tile[] tiles : Tile.tiles) {
            for (Tile tile : tiles) {
                if (tile.colour != Color.BLACK) {
                    tile.shortestPathValue = 99999999;
                    tile.totalDistanceWeight = tile.shortestPathValue + tile.distanceValue;
                    tile.colour = Color.WHITE;
                    AStarVisualiser.queue.queue.clear();
                }
            }
        }
    }
}