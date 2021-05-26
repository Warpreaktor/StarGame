package com.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaper;
	private TextureRegion region;

	@Override
	public void create () {
		batch = new SpriteBatch();
		wallpaper = new Texture("cosmos.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.33f, 0.68f, 0.45f, 1);
		batch.begin();
		batch.draw(wallpaper, 0, 0,Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		wallpaper.dispose();
	}
}
