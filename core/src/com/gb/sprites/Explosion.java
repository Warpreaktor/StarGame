package com.gb.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;

public class Explosion extends Sprite {
    private static final float ANIMATE_INTERVAL = 0.1f;
    private float delta;

    private Sound explSnd;

    public Explosion(TextureAtlas textureAtlas, Sound explSnd){
        super(textureAtlas.findRegion("explosion"), 9, 9,74);
        this.explSnd = explSnd;
    }

    public void set(Vector2 position, float height, TextureAtlas textureAtlas){
        this.pos.set(position);
        setHeightProportion(height);
        explSnd.play();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.delta += delta;
        if (this.delta >= ANIMATE_INTERVAL){
            this.delta = 0f;
            if (frame == animation.length-1){
                this.destroy();
            }else {
                frame++;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
    @Override
    public void destroy(){
        super.destroy();
        frame = 0;
    }
}
