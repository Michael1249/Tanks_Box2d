package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.GameScreen;
/*
General Class which associate all screans and another parts of game
 */
public class GameTank extends Game {
	GameScreen screen;
	@Override
	public void create() {
		screen = new GameScreen();
		setScreen(screen);
	}
}
