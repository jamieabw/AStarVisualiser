package com.mygdx.game;

import java.util.Comparator;

    public class DistanceComparator implements Comparator<Tile> {
        // custom comparator to sort the list (smallest -> largest)
        @Override
        public int compare(Tile tile1, Tile tile2) {
            return Double.compare(tile1.getTotalDistanceWeight(), tile2.getTotalDistanceWeight());
        }
}
