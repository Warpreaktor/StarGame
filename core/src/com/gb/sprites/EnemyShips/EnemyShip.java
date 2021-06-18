package com.gb.sprites.EnemyShips;

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
    protected Vector2 distance = new Vector2();   //дистанция до цели
    private Vector2 target = new Vector2();
    protected Vector2 speed0; //Начальная скорость и направление движения корабля
    protected Vector2 speed;    //скорость и направление движения корабля

    //Стрельба
    protected float shootSpd;

    protected float delta;

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, Sound bulletSnd) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.bulletSnd = bulletSnd;
        this.speed0 = new Vector2();
        this.speed = new Vector2();
        this.bulletPos = new Vector2();
        this.bulletSpeed = new Vector2();
        this.target = new Vector2();
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
    ) {
        this.animation = animation;
        this.speed0 = speed0;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletSpeedY);
        this.bulletDamage = damage;
        this.reloadInterval = reloadInterval;
        this.setHeightProportion(height);
        this.hp = hp;
        this.speed.set(0 , -0.3f);
        this.bulletPos.set(getHalfWidth(), getBottom());
    }




    public boolean isBulletCollision(Rect bullet) {
        return !(
                        bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }

}
