package com.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.Music.Soundtrack;
import com.gb.base.BaseScreen;
import com.gb.base.Sprite;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;
import com.gb.pool.EnemyShipPool;
import com.gb.pool.ExplosionsPool;
import com.gb.sprites.Background;
import com.gb.sprites.EnemyShip;
import com.gb.sprites.Explosion;
import com.gb.sprites.SpaceShip;
import com.gb.sprites.Star;
import com.gb.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    protected Vector2 speed0; //Начальная скорость и направление движения корабля
    protected Vector2 speed;    //скорость и направление движения корабля

    private Texture backgroundTexture;
    private Background background;

    private SpaceShip spaceShip;
    private EnemyShip enemyShip;

    private TextureAtlas mainAtlas;
    private TextureAtlas menuAtlas;
    private TextureAtlas shipsAtlas;

    private Vector2 touch; //координаты места на экране куда тыкнул пользователь.

    private Star[] stars;
    private static final int STARS_COUNT = 256;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionsPool explosionsPool;
    private EnemyEmitter enemyEmitter;

    //Звуки и музыка
    private Soundtrack soundtrack;
    private Sound bulletSnd1;
    private Sound bulletSnd2;
    private Sound explosionSnd1;
    private Sound explosionSnd2;

    private float soundsVolume = 0.5f;
    private float musicVolume = 0.3f;

    private Explosion explosion;

    @Override
    public void show() {
        super.show();

        backgroundTexture = new Texture("cosmos.png");
        background = new Background(backgroundTexture);



        //Сейчас тут дублируются атласы, поотому что я хотел бы его заменить на свой, но пока с этим
        // проблема. В будущем обязательно поменяю.
        shipsAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        menuAtlas = new TextureAtlas("textures/menuAtlas.tpack");

        this.bulletSnd1 = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound1.mp3"));
        spaceShip = new SpaceShip(shipsAtlas, bulletPool, bulletSnd1);
        this.bulletSnd2 = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound2.mp3"));
        //        this.explosionSnd1 = new Gdx.audio.newSound(Gdx.files.internal("путь к звуку со взрывом"));
        bulletPool = new BulletPool();
        explosionsPool = new ExplosionsPool(mainAtlas);
        enemyShipPool = new EnemyShipPool(worldBounds, bulletPool, bulletSnd2);

        touch = new Vector2();

        //Звезды
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
            float speedX = Rnd.nextFloat(-0.0005f, 0.0005f); //скорость перемещения звезды по оси x
            float speedY = Rnd.nextFloat(-0.06f, -0.5f);//скорость перемещения звезды по оси y
            stars[i].setStarsMovement(speedX, speedY);
        }

        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, mainAtlas);

        //Инициализация фоновой музыки
        soundtrack = new Soundtrack();
        soundtrack.play();
        soundtrack.setVolume(musicVolume);
        soundtrack.setLooping(true);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        for(Star star: stars){
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        mainAtlas.dispose();
        menuAtlas.dispose();
        shipsAtlas.dispose();
        bulletPool.dispose();
        soundtrack.dispose();
        bulletSnd1.dispose();
        bulletSnd2.dispose();
        enemyShipPool.dispose();
        explosionsPool.dispose();

    }

    public void update(float delta){
        for(Star star: stars){
            star.update(delta);
        }
        spaceShip.update(0.15f);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        explosionsPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    public void freeAllDestroyed(){
        enemyShipPool.freeAllDestroyed();
        bulletPool.freeAllDestroyed();
        explosionsPool.freeAllDestroyed();
    }

    public void draw(){
        ScreenUtils.clear(0,0,0,1);
        batch.begin();

        //первый слой
        background.draw(batch);

        //второй слой
        for(Star star: stars){
            star.draw(batch);
        }

        //Третий слой
        spaceShip.draw(batch);
        enemyShipPool.drawActiveSprite(batch);
        bulletPool.drawActiveSprite(batch);
        explosionsPool.drawActiveSprite(batch);

        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        spaceShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        spaceShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceShip.keyUp(keycode);
        return false;
    }

    /**
     * Метод определяющий столкновения корабля игрока с кораблями противника и пулями.
     */
    public void collisions(){
        for(Sprite obj : enemyShipPool.getActiveObjects()){
            if (!obj.isOutside(spaceShip)){
                obj.destroy();
                Explosion expl = explosionsPool.obtain();
                expl.set(obj.pos, 0.5f, mainAtlas);
            }
        }
        for(Sprite obj : bulletPool.getActiveObjects()){
            if (!obj.isOutside(spaceShip)){
                obj.destroy();
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        collisions();
        freeAllDestroyed();
        draw();
    }
}
