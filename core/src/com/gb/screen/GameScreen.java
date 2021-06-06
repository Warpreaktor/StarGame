package com.gb.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.Music.Soundtrack;
import com.gb.base.BaseScreen;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;
import com.gb.sprites.Background;
import com.gb.sprites.EnemyShip;
import com.gb.sprites.SpaceShip;
import com.gb.sprites.Star;

public class GameScreen extends BaseScreen {
    private Texture backgroungTexture;
    private Background background;

    private SpaceShip spaceShip;
    private EnemyShip enemyShip;

    private TextureAtlas mainAtlas;
    private TextureAtlas menuAtlas;
    private TextureAtlas shipsAtlas;

    private Vector2 touch; //координаты места на экране куда тыкнул пользователь.

    private Star[] stars;
    private static final int STARS_COUNT = 256;

    private BulletPool bulletPool;

    private Soundtrack soundtrack;

    @Override
    public void show() {
        super.show();

        backgroungTexture = new Texture("cosmos.png");
        background = new Background(backgroungTexture);

        bulletPool = new BulletPool();

        shipsAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        spaceShip = new SpaceShip(shipsAtlas, bulletPool);
        enemyShip = new EnemyShip(shipsAtlas, bulletPool);

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
        soundtrack = new Soundtrack();
        soundtrack.play();
        soundtrack.setVolume(0.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        enemyShip.resize(worldBounds);
        for(Star star: stars){
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroungTexture.dispose();
        mainAtlas.dispose();
        menuAtlas.dispose();
        shipsAtlas.dispose();
        bulletPool.dispose();
        soundtrack.dispose();
    }

    public void update(float delta){
        spaceShip.update(0.15f);
        enemyShip.update(0.15f);
        for(Star star: stars){
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
    }

    public void freeAllDestroyed(){
        bulletPool.freeAllDestroyed();
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
        enemyShip.draw(batch);
        bulletPool.drawActiveSprite(batch);
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
    public boolean keyDown(int keycode) {
        spaceShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceShip.keyUp(keycode);
        return false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }
}
