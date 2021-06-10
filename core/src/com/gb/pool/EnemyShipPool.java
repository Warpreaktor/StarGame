package com.gb.pool;

import com.badlogic.gdx.audio.Sound;
import com.gb.base.SpritePool;
import com.gb.math.Rect;
import com.gb.sprites.EnemyShip;

public class EnemyShipPool extends SpritePool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final Sound bulletSnd;

    public EnemyShipPool(Rect worldBounds, BulletPool bulletPool, Sound bulletSnd) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.bulletSnd = bulletSnd;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds, bulletPool, bulletSnd);
    }
}
