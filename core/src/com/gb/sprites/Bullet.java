package com.gb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;

public class Bullet extends Sprite {
    private Rect worldbounds;
    private Vector2 speed;
    private int damage;
    private Sprite owner;

    private Sound shoot;
    private float volume = 0.5f;


    public Bullet(){
        animation = new TextureRegion[1];
        speed = new Vector2();

    }

    public void sound(){
        shoot.play(volume);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        if (isOutside(worldbounds)){
            destroy();
        }
    }

    public void set(Sprite owner,
                    TextureRegion textureRegion,
                    Vector2 pos0,
                    Vector2 speed0,
                    Rect worldbounds,
                    int damage,
                    float height){
        this.owner = owner;
        this.animation[0] = textureRegion;
        this.pos.set(pos0);
        this.speed = speed0;
        this.worldbounds = worldbounds;
        this.damage = damage;
        setHeightProportion(height);

        if (owner.getClass().getSimpleName().equals("SpaceShip")) {
            shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound1.mp3"));
        }else{
            shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound2.mp3"));
        }
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
