package com.gb.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    private Texture wallpaper;

    private Texture spaceShip;
    float ssWidth = 100;
    float ssHeight = 70;
    private Vector2 ssPosition;
    private float enginePower = 1.007f;
    private float distance = 0;
    private Vector2 direction;
    private Vector2 destination;
    private TextureRegion region;

    @Override
    public void show() {
        //При открытии экрана
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        wallpaper = new Texture("cosmos.png");
        spaceShip = new Texture("space_ship.png");
        direction = new Vector2(0,0);
        ssPosition = new Vector2(120, 300);
        destination = new Vector2(ssPosition);
    }

    @Override
    public void render(float delta) {
        //Срабатывает 60 раз в секунду
        ssPosition.add(direction.scl(enginePower));
        distance = destination.cpy().sub(ssPosition).len();
        if (distance <= 10) {
            direction.set(0,0);
        }
        ScreenUtils.clear(0.33f, 0.68f, 0.45f, 1);
        batch.begin();
        batch.draw(wallpaper, 0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        batch.draw(spaceShip, ssPosition.x, ssPosition.y, ssWidth, ssHeight);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //Вызывается при изменении экрана
    }

    @Override
    public void pause() {
    //Сворачиваем экран
    }

    @Override
    public void resume() {
        //Разворачиваем экран

    }

    @Override
    public void hide() {
        //Закрывается экран
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallpaper.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //При нажатии
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //При отпускании
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //Факт нажатия клавиши и определение символа клавиши
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Касание пальца или клик мышью
        System.out.println(screenX + "  " + screenY);
        this.destination.set(screenX, Gdx.graphics.getHeight() - screenY);
        Vector2 direction = new Vector2(screenX, Gdx.graphics.getHeight() - screenY).sub(ssPosition);
        distance = direction.len();
        this.direction = direction.nor();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Палец убран
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Мышь или палец проведен по экрану
        //ssPosition.set(screenX - ssWidth / 2, Gdx.graphics.getHeight() - screenY - ssHeight / 2);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        //Скроллинг мыши
        return false;
    }
}
