package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;

public class Explosion extends Sprite {
    private float delta;
    private float cadre = 0.1f;

    public Explosion(TextureAtlas textureAtlas){
        super(textureAtlas.findRegion("explosion"), 9, 9,74);
    }

    public void set(Vector2 position, float height, TextureAtlas textureAtlas){
        this.pos.set(position);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.delta += delta;
        if (this.delta >= cadre){
            if (frame == animation.length-1){
                frame = 0;
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
}
