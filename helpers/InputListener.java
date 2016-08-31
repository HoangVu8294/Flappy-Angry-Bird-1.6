package com.fab.helpers;

import com.badlogic.gdx.InputProcessor;
import com.fab.mainobjects.AngryBird;
import com.fab.ui.Button;

public class InputListener implements InputProcessor {
	
	private ObjectsUpdater updater;
	private AngryBird b;
	private float sx, sy;
	
	private Button playButton; 
	private Button evolve;
	private Button replay;
	//activate;

	public InputListener(ObjectsUpdater updater, float scaleX, float scaleY) { 
		this.updater = updater; b = updater.bird();		
		sx = scaleX; sy = scaleY;
		playButton = new Button(137 / 2 - 11, updater.midPointY() + 20, 11);
		evolve = new Button(10, 10, 11);
		replay = new Button(137 / 2 - 11, updater.midPointY() + 20, 11);
	}	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (updater.warmUp() == true) { 
			if (playButton.isTouched(screenX/sx, screenY/sy) == true) { AssetsLoader.set.play(); updater.set(); } 
		} else if (updater.ready() == true) { updater.go(); b.onClick(); }
		else if (updater.rolling() == true) {
			if (evolve.isTouched(screenX/sx, screenY/sy) && updater.isAttained() == true) {  
				if (b.manaLevel() < updater.manaMap.get(b.type())) updater.detain(); 
				if (b.type() == 2) { updater.split(); b.useAbility(); }
				else if (b.type() == 1 || b.type() == 3) { b.useAbility(); b.beAngry(); }
				else if (b.type() == 5) {
					if (b.stage() < b.maxStage()) { b.useAbility(); updater.shoot(); }
				} else if (b.type() == 4) { b.setStage(100); updater.detain(); }
			}		
			b.onClick();
		}
		if (updater.gameOver() == true) {
			if (playButton.isTouched(screenX/sx, screenY/sy) == true) { AssetsLoader.set.play(); updater.restart(); }
		}
		return false;
	}
	
	public Button playButton() { return playButton; }
	public Button evolveButton() { return evolve; }
	public Button replayButton() { return replay; }
	@Override
	public boolean keyDown(int keycode) { return false; }
	@Override
	public boolean keyUp(int keycode) { return false; }
	@Override
	public boolean keyTyped(char character) { return false; }
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false; }
	@Override
	public boolean scrolled(int amount) { return false; } 
}
