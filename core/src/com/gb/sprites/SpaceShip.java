package com.gb.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Ship;
import com.gb.math.Rect;
import com.gb.pool.BulletPool;

public class SpaceShip extends Ship {
    private static final float DISTANCE_LEN = 0.01f;
    private static final float SHIP_SIZE = 0.08f;

    //Мониторинг касания и нажатий
    private  boolean pressedLeft;
    private  boolean pressedRight;
    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    //Движение
    private Vector2 distance;   //дистанция до указателя
    private Vector2 touch;      //указатель
    private final Vector2 speed0 = new Vector2(0.005f, 0); //Начальная скорость и направление движения корабля
    private Vector2 speed = new Vector2();    //скорость и направление движения корабля

    private int hitpoint = 100;


    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSnd){
        super(atlas.findRegion("main_ship"), 1, 2, 2);

        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletPos = new Vector2();
        bulletSpeed = new Vector2(0, 0.5f);
        this.bulletSnd = bulletSnd;
        bulletDamage = 1;
        bulletHeight = 0.01f;

        touch = new Vector2();
        distance = new Vector2();

        this.hp = hitpoint;

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_SIZE);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x){
            if (leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        }else{
            if (rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    public boolean alterTouchDown(Vector2 touch, int pointer, int button){
        this.touch.set(touch);
        distance.set(touch);
        //Вычисление направления векотра происходит путём вычитания вектора текущей позиции объекта
        // из вектора цели.
        // Далее получаем длинную этого вектора и задаем скорость указанием пропорции этой длинны.
        speed.set(touch.cpy().sub(pos)).setLength(DISTANCE_LEN);
        return false;
    }
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER){
                moveRight();
            }else{
                stop();
            }
        }else if(pointer == rightPointer ){
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER){
                moveLeft();
            }else{
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        this.touch.set(touch);
        distance.set(touch);
        //Вычисление направления векотра происходит путём вычитания вектора текущей позиции объекта
        // из вектора цели.
        // Далее получаем длинную этого вектора и задаем скорость указанием пропорции этой длинны.
        speed.set(touch.cpy().sub(pos)).setLength(DISTANCE_LEN);
        return false;
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        pos.mulAdd(speed, delta);
        distance.set(touch);
        if (distance.sub(pos).len() <= DISTANCE_LEN){
            pos.set(touch);
        }else{
            pos.add(speed);
        }
        if (getTop() >= 0){
            setTop(0);
        }
        if (getLeft() <= worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }
        if (getRight() >= worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
        if (getBottom() <= worldBounds.getBottom()){
            setBottom(worldBounds.getBottom());
        }
    }

    public boolean isBulletCollision(Rect bullet){
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > pos.y
                        || bullet.getTop() < getBottom()
        );
    }

    private void moveRight(){
        speed.set(speed0);
    }

    private void moveLeft(){
        speed.set(speed0).rotateDeg(180);
    }

    private void stop(){
        speed.setZero();
    }
    public boolean keyUp(int keycode){
        switch(keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight){
                    moveRight();
                }else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft){
                    moveLeft();
                }else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean keyDown(int keycode){
        switch(keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.SPACE:
                shoot();
                break;
        }
        return false;
    }
    public void ressurection(){
        flushDestroy();
        setBottom(worldBounds.getBottom() + 0.05f);
        hp = hitpoint;
    }
}
