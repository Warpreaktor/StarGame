package com.gb.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Sprite;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;

public class EnemyShip extends Sprite {
    private static final float DISTANCE_LEN = 0.01f;
    private static final float SHIP_SIZE = 0.08f;

    //Пули
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletSpeed;
    private Vector2 bulletPos;

    //Движение
    private Vector2 distance;   //дистанция до цели
    private Vector2 target;
    private final Vector2 speed0 = new Vector2(0.005f, 0); //Начальная скорость и направление движения корабля
    private Vector2 speed = new Vector2();    //скорость и направление движения корабля

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool){
        super(atlas.findRegion("enemy1"), 1, 2, 2);

        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletPos = new Vector2();
        bulletSpeed = new Vector2(0, 0.5f);

        target = new Vector2();
        distance = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_SIZE);
        setTop(worldBounds.getTop());

        //Это здесь только для ДЗ
        randomAtack();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        distance.set(target);
        System.out.println(target);
        if (distance.sub(pos).len() <= DISTANCE_LEN){
            pos.set(target);
        }else{
            pos.add(speed);
        }
        if (getTop() >= worldBounds.getTop()){
            setTop(worldBounds.getTop());
        }
        if (getLeft() <= worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
        }
        if (getRight() >= worldBounds.getRight()){
            setRight(worldBounds.getRight());
        }
        //Изменить логику
        if (getBottom() <= worldBounds.getBottom() - 0.5f){
            setTop(worldBounds.getTop() + 0.1f);
            randomAtack();
        }
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

    private Bullet shoot(){
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, this.bulletPos, bulletSpeed, worldBounds, 1, 0.01f);
        return bullet;
    }

    /**
     * В данном методе захардкодил числа. Но думаю, что он претерпет очень
     *      большие изменения либо вообще будет удален так что не страшно.
     */
    private void randomAtack(){
        this.pos.x = Rnd.nextFloat(0, 1);
        this.target.set(Rnd.nextFloat(0 ,1), worldBounds.getBottom() - 0.6f);
        distance.set(0, worldBounds.getBottom());
        speed.set(target.cpy().sub(pos)).setLength(DISTANCE_LEN);
    }
}
