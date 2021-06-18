package com.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.Music.Soundtrack;
import com.gb.base.BaseScreen;
import com.gb.base.Font;
import com.gb.math.Rect;
import com.gb.math.Rnd;
import com.gb.pool.BulletPool;
import com.gb.pool.EnemyShipPool;
import com.gb.pool.ExplosionsPool;
import com.gb.sprites.Background;
import com.gb.sprites.Bullet;
import com.gb.sprites.EnemyShips.EnemyShip;
import com.gb.sprites.Explosion;
import com.gb.sprites.GameOver;
import com.gb.sprites.NewGame;
import com.gb.sprites.SpaceShip;
import com.gb.sprites.Star;
import com.gb.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private enum State {PLAYING, GAME_OVER}
    protected Vector2 speed0; //Начальная скорость и направление движения корабля
    protected Vector2 speed;    //скорость и направление движения корабля

    //Экранные картинки
    private Texture backgroundTexture;
    private Background background;
    private GameOver gameOver;
    private NewGame newGame;

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

    //Статистика
    private static final float PADDING = 0.01f;
    private Font font;
    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private int frags;

    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private State state;

    @Override
    public void show() {
        super.show();
        //Сейчас тут дублируются атласы, поотому что я хотел бы его заменить на свой, но пока с этим
        // проблема. В будущем обязательно поменяю.
        shipsAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        menuAtlas = new TextureAtlas("textures/menuAtlas.tpack");

        backgroundTexture = new Texture("cosmos.png");
        background = new Background(backgroundTexture);
        gameOver = new GameOver(mainAtlas);

        this.bulletSnd1 = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound1.mp3"));
        bulletPool = new BulletPool();
        spaceShip = new SpaceShip(shipsAtlas, bulletPool, bulletSnd1);
        newGame = new NewGame(mainAtlas, this, spaceShip);
        this.bulletSnd2 = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound2.mp3"));
        this.explosionSnd1 = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletSound2.mp3"));
        explosionsPool = new ExplosionsPool(mainAtlas, explosionSnd1);
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
        //Инициализация статистики
        font = new Font("font/mainFont.fnt", "font/mainFont.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        frags = 0;
        //Инициализация фоновой музыки
        soundtrack = new Soundtrack();
        soundtrack.play();
        soundtrack.setVolume(musicVolume);
        soundtrack.setLooping(true);
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
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
        font.dispose();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }

        explosionsPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            enemyEmitter.generate(delta, frags);
            spaceShip.update(0.15f);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
        }
        if (state == State.GAME_OVER){
            newGame.update(delta);
        }
    }

    public void freeAllDestroyed() {
        enemyShipPool.freeAllDestroyed();
        bulletPool.freeAllDestroyed();
        explosionsPool.freeAllDestroyed();
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        //первый слой
        background.draw(batch);

        //второй слой
        for (Star star : stars) {
            star.draw(batch);
        }

        //Третий слой
        if (state == State.PLAYING) {
            spaceShip.draw(batch);
            enemyShipPool.drawActiveSprite(batch);
            bulletPool.drawActiveSprite(batch);
        }else{
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionsPool.drawActiveSprite(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        font.draw(batch, sbHP.append(HP).append(spaceShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            spaceShip.touchDown(touch, pointer, button);
        }else {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            spaceShip.touchUp(touch, pointer, button);
        }else {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            spaceShip.touchDragged(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            spaceShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            spaceShip.keyUp(keycode);
        }
        return false;
    }

    /**
     * Метод определяющий столкновения корабля игрока с кораблями противника и пулями.
     */
    public void collisions() {
        for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + spaceShip.getHalfWidth();
            if (enemyShip.pos.dst(spaceShip.pos) < minDist) {
                enemyShip.destroy();
                spaceShip.damage(enemyShip.getHp(), explosionsPool, mainAtlas);
                enemyShip.explose(explosionsPool, mainAtlas);
            }
        }
        for (Bullet bullet : bulletPool.getActiveObjects()) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == spaceShip) {
                for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage(), explosionsPool, mainAtlas);
                        bullet.destroy();
                    }
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                }
            }else{
                if (spaceShip.isBulletCollision(bullet)){
                    spaceShip.damage(bullet.getDamage(), explosionsPool, mainAtlas);
                    bullet.destroy();
                }
            }
        }
        if (spaceShip.isDestroyed()){
            state = State.GAME_OVER;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        collisions();
        freeAllDestroyed();
        draw();
    }

    public void switchState() {
        if (state == State.GAME_OVER){
            state = State.PLAYING;
        }else{
            state = State.GAME_OVER;
        }
    }
}
