package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;
import com.gb.math.Rnd;

public class Star extends Sprite {
    protected final Vector2 starSpeed;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star")); //Идем в атлас и ищем по имени нужную текстуру
        starSpeed = new Vector2();
        setStarsMovement(0,0);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        float starSize = Rnd.nextFloat(0.001f, 0.002f);
        setHeightProportion(starSize);
        this.worldBounds = worldBounds;
        //Генерируем случайную позицию появления объекта звезды
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(starSpeed, delta);
        checkBounds();
        animate(delta);
    }

    public void setStarsMovement(float x, float y){
        starSpeed.set(x, y);
    }

    protected void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() >  worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }
    }

    protected void animate(float delta) {
        float height = getHeight();
        height += 0.0001f;
        if (height >= 0.012f) {
            height = 0.008f;
        }
        setHeightProportion(height);
    }

}
