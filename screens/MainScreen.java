package com.fab.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.fab.helpers.AssetsLoader;
import com.fab.helpers.InputListener;
import com.fab.helpers.ObjectsRenderer;
import com.fab.helpers.ObjectsUpdater;

public class MainScreen implements Screen{

	private ObjectsUpdater updater;
	private ObjectsRenderer renderer;
	private float runTime;
	
	public MainScreen () {
		AssetsLoader.theme.loop(0.2f);
		float screenW = Gdx.graphics.getWidth(), screenH = Gdx.graphics.getHeight();
        float gameW = 136, gameH = screenH/(screenW/gameW);	
        
        updater = new ObjectsUpdater((int) (gameH/2));		
		Gdx.input.setInputProcessor(new InputListener(updater, screenW/gameW, screenH/gameH));
		renderer = new ObjectsRenderer(updater, (int) gameH, (int) (gameH/2));
		updater.setRenderer(renderer);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		updater.update(delta);
		renderer.render(delta, runTime);
	}
	
	@Override
	public void show() {}
	@Override
	public void resize(int width, int height) { }
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {}
}
