package com.gb.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;

public class SpaceShip extends Sprite {
    private Vector2 ssPosition;
    private float enginePower = 2f; //скорость корабля
    private Vector2 distance;
    private Vector2 ssSpeed;


    public SpaceShip(Texture texture){
        super(new TextureRegion(texture));
        this.ssSpeed = new Vector2(0,0);
        this.ssPosition = new Vector2(100,200);
        this.distance = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
//        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
//        ssPosition.set(touch);
        distance.set(touch);
        ssSpeed = touch.sub(ssPosition);
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        ssPosition.add(ssSpeed);

        if (distance.sub(ssPosition).len() <= ssSpeed.len()){
//            ssPosition.set(touch);
            ssSpeed.setZero();
        }
    }

}
