package com.mygdx.game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriorityQueue {
    ArrayList<Tile> queue;
        PriorityQueue() {
            this.queue = new ArrayList<>();

        }


        public Tile dequeue() {
            Tile result = this.queue.get(0);
            for (int i = 0; i < this.queue.size() - 1; i++) {
                this.queue.set(i, this.queue.get(i + 1));
            }
            this.queue.remove(this.queue.size() - 1);
            return result;
        }

        public void enqueue(Tile node) {
            this.queue.add(node);
            Collections.sort(this.queue, new DistanceComparator()); // lambda would not work with LibGDX so had to create a custom comparator
        }
}