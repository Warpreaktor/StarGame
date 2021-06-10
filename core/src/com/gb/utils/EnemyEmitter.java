package com.gb.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.math.Rect;
import com.gb.pool.EnemyShipPool;
import com.gb.sprites.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_SPEED = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3.0f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;


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

    public EnemyEmitter(Rect worldBounds, EnemyShipPool enemyShipPool, TextureAtlas textureAtlas) {
        this.worldBounds = worldBounds;
        System.out.println("EnemyEmitter = " + this.worldBounds.hashCode());
        this.enemyShipPool = enemyShipPool;
        this.bulletRegion = textureAtlas.findRegion("bulletEnemy");
        enemySmallRegions = Regions.split(textureAtlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(textureAtlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(textureAtlas.findRegion("enemy2"), 1, 2, 2);
        enemySmallSpeed = new Vector2(0, -0.2f);
        enemyMediumSpeed = new Vector2(0, -0.03f);
        enemyBigSpeed = new Vector2(0, -0.005f);
    }

    public void generate(float delta){
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,
                        enemySmallSpeed,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_SPEED,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP
                );
            } else if (type < 0.8f) {
                enemyShip.set(
                        enemyMediumRegions,
                        enemyMediumSpeed,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,
                        enemyBigSpeed,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP
                );
            }
            enemyShip.randomAtack();
        }
    }
}
