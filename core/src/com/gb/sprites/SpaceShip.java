package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;

public class SpaceShip extends Sprite {
    private final static float DISTANCE_LEN = 0.01f;
    private final static float SHIP_SIZE = 0.10f;
    private Rect worldBounds;

    private Vector2 ssSpeed;    //скорость и направление движения корабля
    private Vector2 distance;   //дистанция до указателя
    private Vector2 touch;      //указатель


    public SpaceShip(TextureAtlas atlas){
        super(atlas.findRegion("space_ship"), 1, 1, 2);
        touch = new Vector2();
        ssSpeed = new Vector2();
        distance = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_SIZE);
        setBottom(worldBounds.getBottom() + 0.05f);
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
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
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
            System.out.println(getLeft());
        }else{
            pos.add(ssSpeed);
        }
        if (getTop() >= 0){
            setTop(0);
        }
        if (getLeft() <= worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }
        if (getRight() >= worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
        if (getBottom() <= worldBounds.getBottom()){
            setBottom(worldBounds.getBottom());
        }
    }

}
