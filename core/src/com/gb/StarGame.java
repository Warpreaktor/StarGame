package com.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.gb.screen.MenuScreen;

public class StarGame extends Game {


	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}

}
