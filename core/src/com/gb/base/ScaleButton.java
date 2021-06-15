package com.gb.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.math.Rect;

public abstract class ScaleButton extends Sprite{
    private boolean pressed;
    private int pointer;
    private static final float SCALE = 0.9f;

    public ScaleButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //Проверяем. Не нажата ли уже кнопка и попал ли щелчок мышью по кнопке метод isMe().
        if (pressed || !isMe(touch) ){
            return false;
        }
        //Сохраняем pointer которым было осуществлено нажатие.
        this.pointer = pointer;
        //При нажатии кнопка будет уменьшаться на величину SCALE
        this.scale = SCALE;
        pressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed){
            return false;
        }
        if(isMe(touch)){
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    protected abstract void action();

}
