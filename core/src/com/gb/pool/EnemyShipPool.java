package com.gb.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gb.base.Ship;
import com.gb.base.SpritePool;
import com.gb.math.Rect;
import com.gb.sprites.EnemyShips.EnemyShip;
import com.gb.sprites.EnemyShips.SmallShip;

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

    public SmallShip newSmallShip(TextureRegion[] animation, TextureRegion bulletRegion){
        SmallShip smallShip;
        if (freeObjects.isEmpty()){
            smallShip = new SmallShip(animation, bulletRegion, worldBounds, bulletPool, bulletSnd);
        } else{
            smallShip = (SmallShip) freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(smallShip);
        return smallShip;

    }
}
