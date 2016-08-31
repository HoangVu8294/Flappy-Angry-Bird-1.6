package com.fab.helpers;

import java.util.HashMap;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.fab.mainobjects.AngryBird;
import com.fab.mainobjects.Grass;
import com.fab.mainobjects.Item;
import com.fab.mainobjects.Pipe;
import com.fab.ui.Button;
import com.fab.ui.Value;
import com.fab.ui.ValueTween;

public class ObjectsRenderer {
	
	// Game Objects
	private AngryBird b, deadB;	
	private MovingObjectsGenerator generator;
	private Grass prevGrass, nextGrass;
	private Pipe p1, p2;
	private Item q, t1, t2, e, n;

	// Game Textures
	private HashMap<Integer, TextureRegion[]> textureMap = AssetsLoader.textureMap;
	
	private ObjectsUpdater updater;
	private OrthographicCamera cam;
	private ShapeRenderer brush;
	private SpriteBatch batcher;
	
	private int midPointY;
	
	private Button playButton = ((InputListener) Gdx.input.getInputProcessor()).playButton(),
			evolve = ((InputListener) Gdx.input.getInputProcessor()).evolveButton(),
			replay = ((InputListener) Gdx.input.getInputProcessor()).replayButton();
	private TweenManager manager;
	private Value val = new Value();
	private Color c = new Color();;
	
	public ObjectsRenderer(ObjectsUpdater updater, int gameHeight, int midPointY) {
		// Set updater and midPointY
		this.updater = updater;
        this.midPointY = midPointY;
        
		// Initialize graphics tools.
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);
		brush = new ShapeRenderer();
        brush.setProjectionMatrix(cam.combined);
		batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        
		// Initialize main objects and assets
		initGameObjects();
		transit(255, 255, 255, 0.5f);
	}
	
	private void initGameObjects() {
		b = updater.bird(); deadB = updater.deadBird();
        generator = updater.generator();
        prevGrass = generator.prevGrass(); nextGrass = generator.nextGrass();
        p1 = generator.pipe1(); p2 = generator.pipe2();
        q = generator.question(); n = generator.nuke();
        t1 = generator.twin1(); t2 = generator.twin2();
        e = generator.egg();
    }
    
    private void drawGrass() {
        batcher.draw(AssetsLoader.grass, prevGrass.x(), prevGrass.y(),
                prevGrass.width(), prevGrass.height());
        batcher.draw(AssetsLoader.grass, nextGrass.x(), nextGrass.y(),
                nextGrass.width(), nextGrass.height());
    }

    private void drawPipes() {
    	//Pipe1 textures detector.
    	TextureRegion p1T = new TextureRegion();
    	if (p1.type() == 1) p1T = AssetsLoader.w;
    	else if (p1.type() == 2) { if (p1.height() > 35) p1T = AssetsLoader.ir; else p1T = AssetsLoader.is; }
    	else { if (p1.height() > 35) p1T = AssetsLoader.sr; else p1T = AssetsLoader.ss; }
    	// Draw pipe1's upper pipe.
		if (p1.upHit()) batcher.draw(p1T, p1.x(), p1.y() + p1.fallingY(), 0, 0, 
				p1.width(), p1.height(), 1, 1, p1.upRotation(), true);
		else batcher.draw(p1T, p1.x(), p1.y(), 0, 0, p1.width(), p1.height(), 1, 1, 0, true);	
    	// Draw pipe1's lower pipe.
		float lowLength = midPointY + 66 - (p1.height() + p1.verticalGap);
		if (p1.type() == 1) p1T = AssetsLoader.w;
    	else if (p1.type() == 2) { if (lowLength > 35) p1T = AssetsLoader.ir; else p1T = AssetsLoader.is; }
    	else { if (lowLength > 35) p1T = AssetsLoader.sr; else p1T = AssetsLoader.ss; }		
		if (p1.lowHit()) batcher.draw(p1T, p1.x() + p1.width(), p1.groundY(), 0, 0, p1.width(), 
				midPointY + 66 - (p1.height() + p1.verticalGap), 1, 1, 180 + p1.lowRotation(), true);
		else batcher.draw(p1T, p1.x() + p1.width(), p1.groundY(), 0, 0, p1.width(), 
				midPointY + 66 - (p1.height() + p1.verticalGap), 1, 1, 180, true);
		
    	//Pipe2 textures detector.
		TextureRegion p2T = new TextureRegion();
    	if (p2.type() == 1) p2T = AssetsLoader.w;
    	else if (p2.type() == 2) { if (p2.height() > 35) p2T = AssetsLoader.ir; else p2T = AssetsLoader.is; }
    	else { if (p2.height() > 35) p2T = AssetsLoader.sr; else p2T = AssetsLoader.ss; }
        // Draw pipe2's upper pipe.
        if (p2.upHit()) batcher.draw(p2T, p2.x(), p2.y() + p2.fallingY(), 
        		0, 0, p2.width(), p2.height(), 1, 1, p2.upRotation(), true);
    	else batcher.draw(p2T, p2.x(), p2.y(), 0, 0, p2.width(), p2.height(), 1, 1, 0, true);      
        // Draw pipe2's lower pipe.
        lowLength = midPointY + 66 - (p2.height() + p2.verticalGap);
		if (p2.type() == 1) p2T = AssetsLoader.w;
    	else if (p2.type() == 2) { if (lowLength > 35) p2T = AssetsLoader.ir; else p2T = AssetsLoader.is; }
    	else { if (lowLength > 35) p2T = AssetsLoader.sr; else p2T = AssetsLoader.ss; }
		if (p2.lowHit()) batcher.draw(p2T, p2.x() + p2.width(), p2.groundY(), 0, 0, p2.width(), 
				midPointY + 66 - (p2.height() + p2.verticalGap), 1, 1, 180 + p2.lowRotation(), true);
		else batcher.draw(p2T, p2.x() + p2.width(), p2.groundY(), 0, 0, p2.width(), 
				midPointY + 66 - (p2.height() + p2.verticalGap), 1, 1, 180, true);
    }
    
    private void drawSmoke(float runTime) {  
    	// Draw smoke.
    	Vector2 smokePosition = generator.smokePosition();
    	float[] smokeSize = generator.smoke();
    	if (generator.smokeOn()) batcher.draw(AssetsLoader.smoke.getKeyFrame(runTime, true), 
    			smokePosition.x, smokePosition.y, smokeSize[0], smokeSize[1]);	
    }
    
    private void drawFrozenBird() {
    	batcher.draw(textureMap.get(1)[0], 59, b.y() - 15, b.width() / 2.0f,
                b.height() / 2.0f, b.width(), b.height(), 1, 1, b.rotation());
    	batcher.draw(AssetsLoader.slingshot, 10, updater.midPointY() + 26, 23.4f, 43.8f);
	}
    
    private void drawDeadBird() {
        if (deadB.isOffDuty() == true) { 
        	// Draw the actual bird.
        	batcher.draw(textureMap.get(deadB.type())[deadB.maxStage() - 1], deadB.x(), 
        		deadB.y(), deadB.width() / 2.0f, deadB.height() / 2.0f, 
        		deadB.width(), deadB.height(), 1, 1, deadB.rotation());
        	// Draw the ouch.
        	batcher.draw(AssetsLoader.ouch, deadB.x() + 2 * deadB.width() / 3, deadB.y() - 3 * deadB.height() / 2, 
        			3 * deadB.width() / 2, 3 * deadB.height() / 2);
        }
    }
     
    private void drawAliveBird() {
    	if (b.angry() == false) {
    		if (b.type() == 4 && b.stage() > 3) batcher.draw(textureMap.get(4)[b.stage() - 100 + 3], b.x(), b.y(), 
    				b.width() / 2.0f, b.height() / 2.0f, b.width(), b.height(), 1, 1, b.rotation());
    		else batcher.draw(textureMap.get(b.type())[b.stage() - 1], b.x(), b.y(), b.width() / 2.0f,
    				b.height() / 2.0f, b.width(), b.height(), 1, 1, b.rotation());
    	}
    	else {
    		if (updater.gameOver() == false) batcher.draw(textureMap.get(b.type())[b.stage() - 100 + b.maxStage()], 
    				b.x(), b.y(), b.width() / 2.0f, b.height() / 2.0f, updater.superSizeMap.get(b.type())[0], 
    				updater.superSizeMap.get(b.type())[1], 1, 1, b.rotation());
    		else {
    			if (b.type() == 1) batcher.draw(textureMap.get(1)[9], b.x(), b.y(), b.width() / 2.0f, b.height() / 2.0f, 
    					updater.superSizeMap.get(b.type())[0], updater.superSizeMap.get(b.type())[1], 1, 1, b.rotation());
    			else if (b.type() == 3) batcher.draw(textureMap.get(3)[4], b.x(), b.y(), b.width() / 2.0f,
	                    b.height() / 2.0f, b.width(), b.height(), 1, 1, b.rotation());
    			// Draw the ouch.
            	batcher.draw(AssetsLoader.ouch, b.x() + 2 * b.width() / 3, b.y() - 3 * b.height() / 2, 
            			3 * b.width() / 2, 3 * b.height() / 2);
    		}
    	}      
    }
    
    private void drawScore() {
    	AssetsLoader.s.getData().setScale(0.25f, 0.25f);
        AssetsLoader.s.draw(batcher, updater.score(), (137 / 2) - (4 * updater.score().length() - 1), 10);
    }
    
    private void drawGameOverBoard() {
    	batcher.draw(AssetsLoader.gameOverBoard, 5, midPointY - 50, 125, 80);
		batcher.draw(AssetsLoader.sbEmpty, 10, midPointY - 10, 16.6f, 26.2f);
		batcher.draw(AssetsLoader.sbEmpty, 25, midPointY - 10, 16.6f, 26.2f);
		batcher.draw(AssetsLoader.sbEmpty, 40, midPointY - 10, 16.6f, 26.2f);
		AssetsLoader.s.getData().setScale(0.25f, 0.25f);
		AssetsLoader.s.draw(batcher, "G a m e  O v e r", (137 / 2) - 58, midPointY - 40);
		batcher.draw(AssetsLoader.replay, replay.x(), replay.y(), replay.radius() * 2, replay.radius() * 2 + 4);
		int score = Integer.parseInt(updater.score());
		if (score > 0) batcher.draw(AssetsLoader.sbFull, 10, midPointY - 10, 16.6f, 26.2f);
		if (score > 100) batcher.draw(AssetsLoader.sbFull, 25, midPointY - 10, 16.6f, 26.2f);
		if (score > 200) batcher.draw(AssetsLoader.sbFull, 40, midPointY - 10, 16.6f, 26.2f);
		// Draw score and top score.
		AssetsLoader.s.getData().setScale(0.15f, 0.15f);
        AssetsLoader.s.draw(batcher, "S c o r e : " + updater.score(), 60, midPointY - 10);
        AssetsLoader.s.draw(batcher, "T o p - D : " + AssetsLoader.getTopScore(), 60, midPointY + 5);
    }
    
    private void drawStartUpUI() {
        AssetsLoader.s.getData().setScale(0.22f, 0.22f);
    	AssetsLoader.s.draw(batcher, "F l a p p y", (137 / 2) - 25, updater.midPointY() - 86);
    	AssetsLoader.s.draw(batcher, "A n g r y  B i r d s", (137 / 2) - 55, updater.midPointY() - 66);
    	batcher.draw(AssetsLoader.play, playButton.x(), playButton.y(), playButton.radius() * 2, 
    			playButton.radius() * 2 + 4);
    }
    
    private void drawFinger() {
        AssetsLoader.s.draw(batcher, "T o u c h  M e", (137 / 2) - 50, updater.midPointY() - 50);
        batcher.draw(AssetsLoader.finger, b.x(), b.y() + 20, 45, 47);
    }
    
    private void drawInGameUI() {
    	batcher.draw(AssetsLoader.deact, evolve.x(), evolve.y(), evolve.radius() * 2, 
    			evolve.radius() * 2 + 4);
    	if (updater.isAttained() == true) batcher.draw(AssetsLoader.evolve, evolve.x(), 
    			evolve.y(), evolve.radius() * 2, evolve.radius() * 2 + 4);
		if (b.manaLevel() > updater.manaMap.get(b.type())) {
			AssetsLoader.s.getData().setScale(0.15f, 0.15f);
			AssetsLoader.s.draw(batcher, "x " + b.manaLevel() / updater.manaMap.get(b.type()), 
					evolve.x() + evolve.radius() * 2 + 4, evolve.y() + 10);
		}
    }
    
    private void drawItems() {
    	if (q.isHid() != true) batcher.draw(AssetsLoader.question, q.x(), q.y(), q.width(), q.height());
    	if (t1.isActivated() == true) {
    		batcher.draw(AssetsLoader.t100, t1.x(), t1.y(), t1.width(), t1.height());
    		batcher.draw(AssetsLoader.t100, t2.x(), t2.y(), t2.width(), t2.height());
    	}
    	if (e.isActivated() == true) batcher.draw(AssetsLoader.egg, e.x(), e.y(), 0, 0, 17.7f, 14f, 1, 1, 0, true);
    }
    
    public void transit(int r, int g, int b, float duration) {
		c.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		val.setValue(1);
		Tween.registerAccessor(Value.class, new ValueTween());
		manager = new TweenManager();
		Tween.to(val, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (val.value() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			brush.begin(ShapeType.Filled);
			brush.setColor(c.r, c.g, c.b, val.value());
			brush.rect(0, 0, 136, 300);
			brush.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}
	
	public void render(float delta, float runTime) {        
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
        batcher.begin();
        batcher.enableBlending();     
        // Draw background.
        batcher.draw(AssetsLoader.backGround, 0, midPointY + 23, 136, 43);
        batcher.draw(AssetsLoader.sky, 0, 0, 136, midPointY + 23);            
        // Draw nuke/if possible.
        if (n.isActivated() == true) batcher.draw(AssetsLoader.nuke, n.x(), n.y(), n.width(), n.height()); 
        // Draw Pipes 
        drawPipes();
            
        if (updater.rolling() == true) {
            drawSmoke(runTime);
            drawDeadBird();
            drawAliveBird();
            drawScore();
            drawInGameUI();
            drawItems();
		} else if (updater.ready() == true) {
            drawAliveBird();
            drawScore();
            drawFinger();
		} else if (updater.warmUp() == true) {
			drawFrozenBird();
			drawStartUpUI();
		} else if (updater.gameOver() == true) {
			drawAliveBird();
			drawGameOverBoard();
		}
        
        // Draw Grass
        drawGrass();        
        batcher.end();  
           
        // For testing.
        /*
        brush.begin(ShapeType.Filled);
        brush.setColor(Color.RED);   
        brush.circle(b.getBoundingCircle().x, b.getBoundingCircle().y, b.getBoundingCircle().radius);
        brush.end();
         */
        
        /*
        brush.begin(ShapeType.Filled);
        brush.setColor(Color.RED);   
        brush.rect(p1.getUpperPipe().x, p1.getUpperPipe().y,
                p1.getUpperPipe().width, p1.getUpperPipe().height);
        brush.rect(p1.getLowerPipe().x, p1.getLowerPipe().y,
                p1.getLowerPipe().width, p1.getLowerPipe().height);

        brush.rect(p2.getUpperPipe().x, p2.getUpperPipe().y,
                p2.getUpperPipe().width, p2.getUpperPipe().height);
        brush.rect(p2.getLowerPipe().x, p2.getLowerPipe().y,
                p2.getLowerPipe().width, p2.getLowerPipe().height);  
        brush.end();
        */
        
        drawTransition(delta);
	}
}
