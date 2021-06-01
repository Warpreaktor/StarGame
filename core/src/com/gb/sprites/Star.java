package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;
import com.gb.math.Rnd;

public class Star extends Sprite {

    private final Vector2 starSpeed;
    private Rect worldbounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star")); //Идем в атлас и ищем по имени нужную текстуру
        starSpeed = new Vector2();
        float speedX = Rnd.nextFloat(-0.0005f, 0.0005f); //скорость перемещения звезды по оси x
        float speedY = Rnd.nextFloat(-0.01f, -0.05f);//скорость перемещения звезды по оси y
        starSpeed.set(speedX, speedY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(starSpeed, delta);
        //Условие. Если левый край объекта заходит за правую границу экрана, значит
        // необходимо переместить объект к левой границе экрана.
        if (getRight() < worldbounds.getLeft()){
            setLeft(worldbounds.getRight());
        }
        if (getLeft() > worldbounds.getRight()){
            setRight(worldbounds.getLeft());
        }
        if (getTop() < worldbounds.getBottom()){
            setBottom(worldbounds.getTop());
        }
        if (getBottom() > worldbounds.getTop()){
            setTop(worldbounds.getBottom());
        }

        float height = getHeight();
        height += 0.0001f;
        if(height >= 0.012){
            height = 0.009f;
            setHeightProportion(height);
        }else{
            setHeightProportion(height);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(Rnd.nextFloat(0.001f, 0.011f));
        this.worldbounds = worldBounds;
        //Генерируем случайную позицию появления объекта звезды
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }
}
