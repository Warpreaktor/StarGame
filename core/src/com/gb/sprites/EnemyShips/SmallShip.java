package com.gb.sprites.EnemyShips;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;

public class SmallShip extends EnemyShip{

    private final float HEIGHT = 0.1f;
    private final float BULLET_HEIGHT = 0.01f;
    private final float BULLET_SPEED_Y = -0.9f;
    private final int DAMAGE = 1;
    private final float RELOAD_INTERVAL = 3.0f;
    private final int HP = 2;
    private final float RELOAD_WEAPON = 0.3f; //Чем меньше тем быстрее перезарядка и стрельба.

    private boolean atackMode = true; //true = нападаем, false = отступаем.

    public SmallShip(TextureRegion[] animation, TextureRegion bulletRegion, Rect worldBounds, BulletPool bulletPool, Sound bulletSnd) {
        super(worldBounds, bulletPool, bulletSnd);
        this.animation = animation;
        this.bulletRegion = bulletRegion;
        System.out.println(this.animation);
        this.setHeightProportion(HEIGHT);
        this.bulletHeight = BULLET_HEIGHT;
        this.bulletSpeed.set(0, BULLET_SPEED_Y);
        this.bulletDamage = DAMAGE;
        this.reloadInterval = RELOAD_INTERVAL;
        this.hp = HP;
        this.shootSpd = RELOAD_WEAPON;
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
        if (shootSpd <= 0 ) {
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
        if (getBottom() <= worldBounds.getBottom() && atackMode == true) {
            fintAtack();
        }
        if (getBottom() > 0 && atackMode == false){
            withdraw();
        }

    }
    /**
     * Метод выставляет рандомную точку по оси X для появления кораблей противника и задаёт
     * координату для нападения
     */
    public void atack() {
        this.pos.x = Rnd.nextFloat(-1, 1);
        this.pos.y = worldBounds.getTop();
        this.speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        this.speed.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        atackMode = true;
    }
    public void fintAtack() {
        speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getHalfHeight());
        atackMode = false;
    }
    public void withdraw(){
        this.speed0.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        this.speed.set(Rnd.nextFloat(-0.5f, 0.5f), worldBounds.getBottom());
        atackMode = false;
    }

}
