package com.gb.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Ship;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;

public class EnemyShip extends Ship {
    private static final float DISTANCE_LEN = 0.01f;
    private static final float SHIP_SIZE = 0.08f;


    //Движение
    private Vector2 distance = new Vector2();   //дистанция до цели
    private Vector2 target = new Vector2();
    private Vector2 speed0; //Начальная скорость и направление движения корабля
    private Vector2 speed = new Vector2();    //скорость и направление движения корабля

    private float delta;

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, Sound bulletSnd){
        this.worldBounds = worldBounds;
        this.speed0 = new Vector2(0.0f, -0.3f);
        this.speed = new Vector2();
        this.bulletPool = bulletPool;
        this.bulletSnd = bulletSnd;
        this.bulletSpeed = new Vector2();
        this.bulletPos = new Vector2();
    }

    public void set(
            TextureRegion[] animation,
            Vector2 speed0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletSpeedY,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ){
        this.animation = animation;
        this.speed0 = speed0;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletSpeedY);
        this.bulletDamage = damage;
        this.reloadInterval = reloadInterval;
        this.setHeightProportion(height);
        this.speed.set(0, -0.3f);
        this.hp = hp;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if ( getTop() < worldBounds.getTop()){
            speed.set(speed0);
        } else{
            reloadTimer = reloadInterval * 0.8f;
        }

        if (worldBounds.isOutside(this)){
            destroy();
        }

        this.delta+=delta;
        if (this.delta > 5f){
            shoot();
            this.delta = 0;
        }

        pos.mulAdd(speed, delta);
        distance.set(target);

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
        if (getTop() <= worldBounds.getBottom()){
            randomAtack();
        }
    }


    /**
     * В данном методе захардкодил числа. Но думаю, что он претерпет очень
     *      большие изменения либо вообще будет удален так что не страшно.
     */
    public void randomAtack(){
        this.pos.x = Rnd.nextFloat(0, 1);
        this.pos.y = worldBounds.getTop();
        this.target.set(Rnd.nextFloat(0 ,1), worldBounds.getBottom() - 0.6f);
        distance.set(0, worldBounds.getBottom());
        speed.set(target.cpy().sub(pos)).setLength(DISTANCE_LEN);
    }

}
