package com.gb.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;

public class SpaceShip extends Sprite {
    private final static float DISTANCE_LEN = 0.01f;
    private final static float SHIP_SIZE = 0.10f;

    private Vector2 ssSpeed;    //скорость и направление движения корабля
    private Vector2 distance;   //дистанция до указателя
    private Vector2 touch;      //указатель


    public SpaceShip(Texture texture){
        super(new TextureRegion(texture));
        touch = new Vector2();
        ssSpeed = new Vector2();
        distance = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SHIP_SIZE);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        distance.set(touch);
        //Вычисление направления векотра происходит путём вычитания вектора текущей позиции объекта
        // из вектора цели.
        // Далее получаем длинную этого вектора и задаем скорость указанием пропорции этой длинны.
        ssSpeed.set(touch.cpy().sub(pos)).setLength(DISTANCE_LEN);
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        distance.set(touch);
        if (distance.sub(pos).len() <= DISTANCE_LEN){
            pos.set(touch);
        }else{
            pos.add(ssSpeed);
        }
    }

}
