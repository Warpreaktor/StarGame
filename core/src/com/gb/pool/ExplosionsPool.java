package com.gb.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.base.SpritePool;
import com.gb.sprites.Explosion;

public class ExplosionsPool extends SpritePool<Explosion> {
    private final TextureAtlas explAtlas;
    private final Sound explSnd;

    public ExplosionsPool(TextureAtlas atlas, Sound explSnd){
        this.explAtlas = atlas;
        this.explSnd = explSnd;
    }
    @Override
    protected Explosion newObject() {
        return new Explosion(explAtlas, explSnd);
    }
}
