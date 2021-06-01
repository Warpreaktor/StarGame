package com.gb.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.base.ScaleButton;
import com.gb.math.Rect;
import com.gb.screen.GameScreen;

public class PlayButton extends ScaleButton {
    private final Game game;

    private static final float HEIGHT = 0.13f;
    private static final float PADDING = 0.03f;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setLeft(worldBounds.getLeft() + PADDING);
    }
}
