package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;


public class AStarVisualiser extends ApplicationAdapter {

	// constants initialisation
	/*
	widthTileConstant and heightTileConstant are used so all tiles fit onto the screen by subtracting one from the width and height
	 */
	static ShapeRenderer sr;
	Entrance entrance;
	static Exit exit;
	static int width, height, widthTileConstant, heightTileConstant, counter;
	static PriorityQueue queue;
	static boolean notSearched;


	@Override
	public void create () {
		sr = new ShapeRenderer();
		notSearched = true;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		widthTileConstant = width / Tile.constant -1;
		heightTileConstant = height / Tile.constant -1;
		entrance = new Entrance();
		exit = new Exit();
		Tile.fillMap();
		queue = new PriorityQueue();
		counter = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		Tile.renderTiles();
		entrance.renderEntrance();
		exit.renderExit();
		sr.end();
		if (Gdx.input.isButtonPressed(1)) { // right click resets all walls
			Tile.resetTiles();
		}
		if (notSearched && Gdx.input.isButtonPressed(0)) { // left click to place walls
			Wall.placeWall(Gdx.input.getX(), Gdx.input.getY());
		}

		if (notSearched && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { // press space to start A* algorithm
				try {
					AStar.resetAlgorithm();
					AStar.performAlgorithm(Tile.tiles[entrance.x / 80][entrance.y / 45],Tile.tiles[23][0]);
					notSearched = false;
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			if (!notSearched) { // draw the process of the A* algorithm
				if (AStar.allTilesInvolved.size()-1 > counter) {
					Tile tempTile = AStar.allTilesInvolved.get(counter);
					Tile.tiles[tempTile.x / 80][tempTile.y / 45].colour = Color.DARK_GRAY;;
					counter++;
				}
				else { // after the A* process is drawn, draw the path in blue
					for (Tile tile : AStar.pathToTarget) {
					tile.colour = Color.BLUE;
					}
					notSearched = true;
					counter = 0;
				}
			}
		}

	@Override
	public void dispose () {
		sr.dispose();

	}
}