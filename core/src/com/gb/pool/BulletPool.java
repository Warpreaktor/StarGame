package com.gb.pool;

import com.gb.base.SpritePool;
import com.gb.sprites.Bullet;

public class BulletPool extends SpritePool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
