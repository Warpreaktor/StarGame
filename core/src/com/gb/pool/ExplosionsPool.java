package com.gb.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.base.SpritePool;
import com.gb.sprites.Explosion;

public class ExplosionsPool extends SpritePool<Explosion> {
    private TextureAtlas explAtlas;
    public ExplosionsPool(TextureAtlas atlas){
        this.explAtlas = atlas;
    }
    @Override
    protected Explosion newObject() {
        return new Explosion(explAtlas);
    }
}
