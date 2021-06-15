package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.base.Sprite;
import com.gb.math.Rect;

public class GameOver extends Sprite {
    private static final float HEIGHT = 0.05f;

    public GameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.pos.y + 0.2f);
    }

}
