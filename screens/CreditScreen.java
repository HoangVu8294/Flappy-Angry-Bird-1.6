package com.fab.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.BaseTween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fab.game.FABGame;
import com.fab.helpers.AssetsLoader;
import com.fab.ui.SpriteTween;
import com.badlogic.gdx.graphics.GL20;

public class CreditScreen implements Screen {

    private TweenManager manager = new TweenManager();
    private SpriteBatch batcher = new SpriteBatch();
    private Sprite theme = new Sprite(AssetsLoader.HCM);
    private FABGame game;

    public CreditScreen(FABGame g) { game = g; }

    @Override
    public void show() {
		theme.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		theme.setPosition(0, 0); setup(); 
    }

    private void setup() {
        Tween.registerAccessor(Sprite.class, new SpriteTween()); 
        TweenCallback theCall = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) { game.setScreen(new StartScreen(game)); }
        };
        Tween.to(theme, SpriteTween.VAR, .8f).target(1).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
                .setCallback(theCall).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(255, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        theme.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}
}
