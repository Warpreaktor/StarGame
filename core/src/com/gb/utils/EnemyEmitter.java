package com.gb.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.base.Ship;
import com.gb.math.Rect;
import com.gb.pool.EnemyShipPool;
import com.gb.sprites.EnemyShips.MediumShip;
import com.gb.sprites.EnemyShips.SmallShip;

public class EnemyEmitter {
    private static final float GENERATE_INTERVAL = 1f; //Частота генерации врагов

    protected TextureAtlas atlas;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_SPEED = -0.5f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 0.5f;
    private static final int ENEMY_BIG_HP = 10;

    private int level;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallSpeed;
    private final Vector2 enemyMediumSpeed;
    private final Vector2 enemyBigSpeed;

    private final TextureRegion bulletRegion;
    private final EnemyShipPool enemyShipPool;
    private final Rect worldBounds;

    public EnemyEmitter(Rect worldBounds, EnemyShipPool enemyShipPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.atlas = atlas;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        enemySmallSpeed = new Vector2(0, -0.4f);
        enemyMediumSpeed = new Vector2(0, -0.2f);
        enemyBigSpeed = new Vector2(0, -0.1f);
        level = 1;
    }

    public void generate(Ship spaceShip, float delta, int frags){
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            //EnemyShip enemyShip = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                SmallShip smallShip = enemyShipPool.newSmallShip(enemySmallRegions, bulletRegion);
                smallShip.atack();

            } else if (type < 0.8f) {
                MediumShip mediumShip = enemyShipPool.newMediumShip(enemyMediumRegions, bulletRegion);
                mediumShip.targetAtack(spaceShip);
            }
//            else {
//                enemyShip.set(
//                        enemyBigRegions,
//                        enemyBigSpeed,
//                        bulletRegion,
//                        ENEMY_BIG_BULLET_HEIGHT,
//                        ENEMY_BIG_BULLET_SPEED,
//                        ENEMY_BIG_DAMAGE,
//                        ENEMY_BIG_RELOAD_INTERVAL,
//                        ENEMY_BIG_HEIGHT,
//                        ENEMY_BIG_HP
//                );
            }
//            enemyShip.randomAtack();
        }

    public int getLevel() {
        return level;
    }
}
