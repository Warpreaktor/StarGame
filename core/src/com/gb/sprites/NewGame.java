package com.gb.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.base.ScaleButton;
import com.gb.math.Rect;
import com.gb.screen.GameScreen;

public class NewGame extends ScaleButton {
    private boolean scaler = true; //если истина, то увеличиваем объект, наоборот уменьшаем.
    private final float INTERVAL = 0.09f;
    private float timer = 0f;
    private static float height = 0.03f;

    public NewGame(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
    }

    @Override
    public void update(float delta) {
        timer += delta;
        super.update(delta);
        if (timer > INTERVAL){
            if (scaler == true){
                this.scale = getScale() + 0.1f;
            }
            if (scaler == false){
                this.scale = getScale() - 0.1f;
            }
            if (getScale() >= 1.2f){
                scaler = false;
            }
            if (getScale() <= 1f){
                scaler = true;
            }
            timer = 0;
        }

    }

    @Override
    protected void action() {
        new GameScreen();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(height);
        setTop(worldBounds.pos.y - 0.1f);
    }
}
