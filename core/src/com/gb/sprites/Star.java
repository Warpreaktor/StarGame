package com.gb.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;
import com.gb.math.Rnd;

public class Star extends Sprite {

    private final Vector2 starSpeed;
    private final float STAR_SIZE = 0.01f;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star")); //Идем в атлас и ищем по имени нужную текстуру
        starSpeed = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(STAR_SIZE);
        //Генерируем случайную позицию появления объекта звезды
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }
}
