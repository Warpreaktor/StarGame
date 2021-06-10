package com.gb.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.math.MatrixUtils;
import com.gb.math.Rect;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;

    private Vector2 touch; //координаты места на экране куда ткнул пользователь.

    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGL;
    private Matrix3 screenToWorld;
    private TextureRegion region;

    @Override
    public void show() {
        //При открытии экрана
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f,1f);

        Gdx.input.setInputProcessor(this);
        touch = new Vector2();

        worldToGL = new Matrix4();
        screenToWorld = new Matrix3();

    }

    @Override
    public void render(float delta) {
        //Срабатывает 60 раз в секунду
        ScreenUtils.clear(0.33f, 0.68f, 0.45f, 1);
        batch.begin();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //Вызывается при изменении экрана

        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);


        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGL, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGL);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {

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
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld); //Переворачиваем Y для приведения к единой системе коорлдинат.
        touchDown(touch, pointer, button);
        return false;
    }
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println(touch.x + "  " + touch.y);
        //Касание пальца или клик мышью
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Палец убран
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld); //Переворачиваем Y для приведения к единой системе коорлдинат.
        touchUp(touch, pointer, button);
        return false;
    }
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        //Касание пальца или клик мышью
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Мышь или палец проведен по экрану
        //ssPosition.set(screenX - ssWidth / 2, Gdx.graphics.getHeight() - screenY - ssHeight / 2);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld); //Переворачиваем Y для приведения к единой системе коорлдинат.
        touchDragged(touch, pointer);
        return false;
    }
    public boolean touchDragged(Vector2 touch, int pointer) {
        //Касание пальца или клик мышью
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

    public Rect getWorldBounds() {
        return worldBounds;
    }
}
