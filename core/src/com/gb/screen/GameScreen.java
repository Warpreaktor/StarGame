package com.gb.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.base.BaseScreen;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.sprites.Background;
import com.gb.sprites.SpaceShip;
import com.gb.sprites.Star;

public class GameScreen extends BaseScreen {
    private Texture backgroungTexture;
    private Background background;

    private Texture spaceShipTexture;
    private SpaceShip spaceShip;

    private TextureAtlas mainAtlas;
    private TextureAtlas menuAtlas;

    private Vector2 touch; //координаты места на экране куда ткнул пользователь.

    private Star[] stars;
    private static final int STARS_COUNT = 256;

    @Override
    public void show() {
        super.show();

        backgroungTexture = new Texture("cosmos.png");
        background = new Background(backgroungTexture);

        spaceShipTexture = new Texture("space_ship.png");
        spaceShip = new SpaceShip(spaceShipTexture);

        touch = new Vector2();

        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        menuAtlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
            float speedX = Rnd.nextFloat(-0.0005f, 0.0005f); //скорость перемещения звезды по оси x
            float speedY = Rnd.nextFloat(-0.06f, -0.5f);//скорость перемещения звезды по оси y
            stars[i].setStarsMovement(speedX, speedY);
        }

    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        spaceShip.pos.set(0, worldBounds.getBottom() + spaceShip.getHalfHeight());
        for(Star star: stars){
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroungTexture.dispose();
        spaceShipTexture.dispose();
        mainAtlas.dispose();
        menuAtlas.dispose();
    }

    public void update(float delta){
        spaceShip.update(0.15f);
        for(Star star: stars){
            star.update(delta);
        }

    }
    public void draw(){
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
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        spaceShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        spaceShip.touchDragged(touch, pointer);
        return false;
    }


    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
}
