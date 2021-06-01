package com.gb.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gb.base.ScaleButton;
import com.gb.math.Rect;

public class ExitButton extends ScaleButton {
    private static final float HEIGHT = 0.1f;
    private static final float PADDING = 0.03f;


    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setRight(worldBounds.getRight() - PADDING);
    }

    @Override
    protected void action() {
        //Корректное завершение работы приложения
        Gdx.app.exit();
    }
}
