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

public class StartScreen implements Screen {

    private TweenManager manager = new TweenManager();
    private SpriteBatch batcher = new SpriteBatch();
    private Sprite theme = new Sprite(AssetsLoader.logo);
    private FABGame game;

    public StartScreen(FABGame g) { game = g;}

    @Override
    public void show() {
		theme.setColor(1, 1, 1, 0);
		float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
		float scale = width/theme.getWidth();
		theme.setSize(theme.getWidth() * scale, theme.getHeight() * scale);
		theme.setPosition((width / 2) - (theme.getWidth() / 2), (height / 2) - (theme.getHeight() / 2));     
		setup(); 
    }

    private void setup() {
        Tween.registerAccessor(Sprite.class, new SpriteTween());
        TweenCallback theCall = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) { game.setScreen(new MainScreen()); }
        };

        Tween.to(theme, SpriteTween.VAR, .8f).target(1).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
                .setCallback(theCall).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(255, 255, 255, 1);
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
