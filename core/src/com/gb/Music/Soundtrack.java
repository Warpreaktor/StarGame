package com.gb.Music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Soundtrack implements Music{
    private Music track;

    public Soundtrack() {
        track = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundtrack.mp3"));
    }

    @Override
    public void play() {
        track.play();
    }

    @Override
    public void pause() {
        track.pause();
    }

    @Override
    public void stop() {
        track.stop();
    }

    @Override
    public boolean isPlaying() {
        return track.isPlaying();
    }

    @Override
    public void setLooping(boolean isLooping) {
        track.setLooping(isLooping);
    }

    @Override
    public boolean isLooping() {
        return track.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        track.setVolume(volume);
    }

    @Override
    public float getVolume() {
        return track.getVolume();
    }

    @Override
    public void setPan(float pan, float volume) {
        track.setPan(pan, volume);
    }

    @Override
    public void setPosition(float position) {
        track.setPosition(position);
    }

    @Override
    public float getPosition() {
        return track.getPosition();
    }

    @Override
    public void dispose() {
        track.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        track.setOnCompletionListener(listener);
    }
}
