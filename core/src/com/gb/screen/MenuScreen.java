package com.gb.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.base.BaseScreen;
import com.gb.math.Rect;
import com.gb.sprites.Background;
import com.gb.sprites.SpaceShip;

public class MenuScreen extends BaseScreen {
    private Texture backgroungTexture;
    private Background background;

    private Texture spaceShipTexture;
    private SpaceShip spaceShip;


    private Vector2 touch; //координаты места на экране куда ткнул пользователь.

    @Override
    public void show() {
        super.show();
        backgroungTexture = new Texture("cosmos.png");
        background = new Background(backgroungTexture);

        spaceShipTexture = new Texture("space_ship.png");
        spaceShip = new SpaceShip(spaceShipTexture);

        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0,0,0,1);
        batch.begin();
        //первый слой
        background.draw(batch);
        //второй слой
        spaceShip.draw(batch);
        spaceShip.update(0.15f);
        batch.end();

    }


    @Override
    public void dispose() {
        super.dispose();
        backgroungTexture.dispose();
        spaceShipTexture.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceShip.touchDown(touch, pointer, button);
        return false;
    }
}
