package com.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.math.Rect;
import com.gb.pool.BulletPool;
import com.gb.sprites.Bullet;

public class Ship extends Sprite{
    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;

    //Пули
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletSpeed;
    protected Vector2 bulletPos;
    protected Sound bulletSnd;
    protected float bulletHeight; //размер пули
    protected int bulletDamage; // урон от пули
    protected int hp;

    public Ship(){
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    protected void shoot(){
        bulletSnd.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, this.bulletPos, bulletSpeed, worldBounds, bulletDamage, bulletHeight);
    }

    public Rect getWorldBounds() {
        return worldBounds;
    }
}
