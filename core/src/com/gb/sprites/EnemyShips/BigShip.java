package com.gb.sprites.EnemyShips;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gb.base.Ship;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;

public class BigShip extends EnemyShip {
    private static final float HEIGHT = 0.2f;
    private static final float BULLET_HEIGHT = 0.04f;
    private static final float BULLET_SPEED_Y = -0.5f;
    private static final int DAMAGE = 10;
    private static final float RELOAD_INTERVAL = 0.5f;
    private static final int HP = 10;
    private final float RELOAD_WEAPON = 0.3f; //Чем меньше тем быстрее перезарядка и стрельба.

    //Ведение боя
    private boolean atackMode; //true = нападаем, false = отступаем.

    public BigShip(TextureRegion[] animation, TextureRegion bulletRegion, Rect worldBounds, BulletPool bulletPool, Sound bulletSnd) {
        super(worldBounds, bulletPool, bulletSnd);
        this.animation = animation;
        this.bulletRegion = bulletRegion;
        this.setHeightProportion(HEIGHT);
        this.bulletHeight = BULLET_HEIGHT;
        this.bulletSpeed.set(0, BULLET_SPEED_Y);
        this.bulletDamage = DAMAGE;
        this.reloadInterval = RELOAD_INTERVAL;
        this.hp = HP;
        this.shootSpd = RELOAD_WEAPON;

        //Стартовая позиция
        resetPosition();

        atackMode = true;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.delta += delta;
        this.shootSpd -= delta;

        bulletPos.set(pos.x, pos.y - getHalfHeight());

        if (getTop() < worldBounds.getTop()) {
            speed.set(speed0);
        } else {
            reloadTimer = reloadInterval * 0.5f;
        }
        if (worldBounds.isOutside(this)) {
            destroy();
        }
        //Стрельба
        if (shootSpd <= 0) {
            shoot();
            this.shootSpd = RELOAD_WEAPON;
        }

        pos.mulAdd(speed, delta);

        //Мониторинг границ
        if (getTop() >= worldBounds.getTop()) {
            setTop(worldBounds.getTop());
        }
        if (getLeft() <= worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        }
        if (getRight() >= worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }
        if (getTop() <= worldBounds.getBottom()) {
        }
        if (getTop() < 0) {
            specifyAtack();
        }
    }

    public void atack() {
        this.speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        this.speed.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        atackMode = true;
    }

    public void fintAtack() {
        speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getHalfHeight());
        atackMode = false;
    }

    public void withdraw() {
        this.speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        this.speed.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        atackMode = false;
    }

    public void targetAtack(Ship spaceShip) {
        this.speed0.set(spaceShip.pos.x, spaceShip.pos.y);
        this.speed.set(spaceShip.pos.x, spaceShip.pos.y);
        atackMode = false;
    }

    public void specifyAtack() {
        System.out.println(getRight());
        if (getLeft() < -0.25f && getRight() < 0.25f) {
            this.speed0.set(0.2f, 0);
            this.speed.set(0.2f, 0);
        }
        if (getLeft() > -0.25f && getRight() > 0.25f) {
            this.speed0.set(-0.2f, 0);
            this.speed.set(-0.2f, 0);
        }
    }

    public void resetPosition() {
        this.pos.x = Rnd.nextFloat(-1, 1);
        this.pos.y = worldBounds.getTop();
    }
}
