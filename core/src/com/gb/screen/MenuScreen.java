package com.gb.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.base.BaseScreen;
import com.gb.math.Rect;
import com.gb.sprites.Background;
import com.gb.sprites.ButtonExit;
import com.gb.sprites.SpaceShip;
import com.gb.sprites.Star;


public class MenuScreen extends BaseScreen {
    private Texture backgroungTexture;
    private Background background;

    private Texture spaceShipTexture;
    private SpaceShip spaceShip;

    private TextureAtlas menuAtlas;

    private Vector2 touch; //координаты места на экране куда ткнул пользователь.

    private Star[] stars;
    private static final int STARS_COUNT = 256;

    private ButtonExit buttonExit;

    @Override
    public void show() {
        super.show();
        backgroungTexture = new Texture("cosmos.png");
        background = new Background(backgroungTexture);

        spaceShipTexture = new Texture("space_ship.png");
        spaceShip = new SpaceShip(spaceShipTexture);

        touch = new Vector2();

        menuAtlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }

        buttonExit = new ButtonExit(menuAtlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        for(Star star: stars){
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
    }

    /**
     * В этом методе будем высвобождать все захваченные ресурсы типа картинок.
     */
    @Override
    public void dispose() {
        super.dispose();
        backgroungTexture.dispose();
        spaceShipTexture.dispose();
        menuAtlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceShip.touchDown(touch, pointer, button);
        buttonExit.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        return false;
    }

    public void update(float delta){
        spaceShip.update(0.15f);
        for(Star star: stars){
            star.update(delta);
        }
        buttonExit.update(delta);
    }

    private void draw(){
        ScreenUtils.clear(0,0,0,1);
        batch.begin();

        //первый слой
        background.draw(batch);

        //второй слой
        for(Star star: stars){
            star.draw(batch);
        }

        //Третий слой
        spaceShip.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta); //обновляем информацию об объекте в т.ч. и его позицию на экране
        draw(); // отрисовываем объект с обновленными параметрами.
    }
}
