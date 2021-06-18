package com.gb.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.math.Rect;
import com.gb.utils.Regions;

public class Sprite extends Rect {
    private float angle;
    protected float scale = 1;
    protected TextureRegion[] animation;
    protected int frame;
    protected boolean destroyed;

    public Sprite() {
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = animation[frame].getRegionWidth() / (float) animation[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public Sprite(TextureRegion region){
        animation = new TextureRegion[1];
        animation[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames){
        animation = Regions.split(region, rows, cols, frames);
    }

    public void draw(SpriteBatch batch){
        batch.draw(animation[frame], getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
        scale, scale, angle);

    }

    public void setAnimation(TextureRegion[] animation) {
        this.animation = animation;
    }

    /**
     * Логика работы спрайта.
     * @param delta - отрезок времени.
     */
    public void update(float delta){

    }

    public void resize(Rect worldBounds){

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //Касание пальца или клик мышью
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        //Касание пальца или клик мышью
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        //Касание пальца или клик мышью
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void destroy(){
        destroyed = true;
    }

    public void flushDestroy(){
        destroyed = false;
    }
}
