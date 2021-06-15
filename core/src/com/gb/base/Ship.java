package com.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.math.Rect;
import com.gb.pool.BulletPool;
import com.gb.pool.ExplosionsPool;
import com.gb.sprites.Bullet;
import com.gb.sprites.EnemyShip;
import com.gb.sprites.Explosion;

public class Ship extends Sprite{
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.01f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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

    @Override
    public void update(float delta){
        damageAnimateTimer += delta;
        if(damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL){
            frame = 0;
        }
    }

    public void damage(int damage, ExplosionsPool explosionsPool, TextureAtlas atlas){
        hp -= damage;
        if (hp <= 0){
            hp = 0;
            destroy();
            this.explose(explosionsPool, atlas);
        }
        frame = 1;
        damageAnimateTimer = 0f;

    }

    @Override
    public void destroy(){
        super.destroy();
    }

    public void explose(ExplosionsPool explosionsPool, TextureAtlas atlas){
        Explosion expl = explosionsPool.obtain();
        expl.set(this.pos, getHeight(), atlas);
    }

    public Rect getWorldBounds() {
        return worldBounds;
    }

    public int getHp() {
        return hp;
    }
}
