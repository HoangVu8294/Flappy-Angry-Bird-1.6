package com.fab.helpers;

import java.util.HashMap;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.fab.mainobjects.AngryBird;

public class ObjectsUpdater {
	
	private HashMap<Integer, Integer> stageTracker = new HashMap<Integer, Integer>() 
			{/**
				 * A HashMap that keep track of stages and health of all birds.
				 */
				private static final long serialVersionUID = 1L;
			{ put(1, 1); put(2, 1); put(3, 1); put(4, 1); put(5, 1); }};
			
	private final HashMap<Integer, Integer> maxStageMap = new HashMap<Integer, Integer>() 
			{/**
				 * A HashMap that keep track the max stages of all birds.
				 */
				private static final long serialVersionUID = 1L;
			{ put(1, 3); put(2, 3); put(3, 3); put(4, 3); put(5, 4); }};	

	private final HashMap<Integer, float[]> sizeMap = new HashMap<Integer, float[]>() 
			{/**
				 * A HashMap that keep track the size of all birds.
				 */
				private static final long serialVersionUID = 1L;
			{ put(1, new float[]{14f, 12f}); put(2, new float[]{9.5f, 7.6f});
				put(3, new float[]{17.1f, 14f}); put(4, new float[]{20f, 21.9f});
				put(5, new float[]{23.5f, 25.1f}); }};	
	
	private final HashMap<Integer, Integer> gapMap = new HashMap<Integer, Integer>() 
			{/**
				 * A HashMap that keep track of birds and the gap between pipes of all the birds.
	 			 * Since all birds have different sizes, we need to make the gaps to be different as well.
				 */
				private static final long serialVersionUID = 1L;
			{ put(1, 33); put(3, 34); put(4, 42); put(5, 42); }};	
	public final HashMap<Integer, Integer> manaMap = new HashMap<Integer, Integer>()	
			{/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			{ put(1,4); put(2,1); put(3,1); put(4,0); put(5,1); }};
	public final HashMap<Integer, float[]> superSizeMap = new HashMap<Integer, float[]>()
			{/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{ put(1, new float[]{29.5f, 25.3f}); put(3, new float[]{20f, 14f}); }};		
	
	private AngryBird b;
	private AngryBird deadB;
	
	private MovingObjectsGenerator generator;
	private ObjectsRenderer renderer;
	
	private int score = 0, midPointY = 0, timeTracker = 0, tracker2 = 0, birdsCount = 5;
	private boolean vulnerability = true, totallyDead = false, attained = false, chargeable = true, overHeated = false; 
	private float runTime = 0;
	
	private Rectangle ground;
	
	public enum status { WARMUP, READY, ROLLING, OVER}
	private status currentStatus;
	
	public ObjectsUpdater(int midPointY) {	
		currentStatus = status.WARMUP;
		this.midPointY = midPointY;
        b = new AngryBird(33, midPointY - 5, 14, 12, 1, 1, 3);
        deadB = new AngryBird(33, midPointY - 5, 14, 12, 1, 1, 3);
        generator = new MovingObjectsGenerator(midPointY + 66, -59);
        ground = new Rectangle(0, midPointY + 66, 137, 30);
    }
	
	// Update the game regardless of statuses.
    public void update(float delta) {
    	runTime += delta;
    	switch (currentStatus) {
    	case WARMUP:
    	case READY: 
    		getSet(delta, midPointY - 5); 
    		break;	
    		
    	case ROLLING:     	      	
    		letsRoll(delta); 
    		break;	
    		
		default: break;
    	}
    }
    
    // Updater while the game is running.
    private void letsRoll(float delta) {
    	// Check if the birds are ready to be hit.
    	if (vulnerability == false) { 
    		timeTracker++; 
    		if (b.type() == 2 && b.angry()) timeTracker *= delta;
    		if (timeTracker > 45 && b.angry() == true && timeTracker > 65) vulnerability = true; 
    		else if (timeTracker > 12 && b.angry() == false) vulnerability = true; 
    	}  	
    	// Charge the times of power.
    	if (chargeable == false) { tracker2++; if (tracker2 > 60) chargeable = true; }   	
    	// Deactivate or activate power button base on the bird.
    	if (b.angry() == true) {
    		generator.hideQuestions();
    		if (b.type() == 3) { overHeated = true; generator.DUI(-0.1f); }
    		attained = false;
    	} else { 
    		if (b.type() != 4 && b.type() != 2 && b.type() != 2 && b.manaLevel() / manaMap.get(b.type()) > 0) attained = true; 
    		else if (b.type() == 5) { if (b.stage() == b.maxStage()) attained = false; }
    	}    	
    	
    	// Now update everything.
    	if (delta > 0.15f) delta = 0.15f;
    	b.update(delta);
    	deadB.update(delta/2);
    	if (b.type() == 2) {
    		generator.update(delta, true, 0, b.angry());
    		if (generator.hit() == true && b.manaLevel() > 0) attained = true; 
    	} else if (b.type() == 3) generator.update(delta, false, gapMap.get(3), overHeated);
    	else if (b.type() == 4) generator.update(delta, false, gapMap.get(4), true);
    	else generator.update(delta, false, gapMap.get(b.type()), b.angry());
    	
    	// Collision detection.
    	groundAndBirdsCollisionDetect();
    	pipesAndBirdsCollisionDetect();
    }  
    
    // Check collisions of birds and the ground.
    private void groundAndBirdsCollisionDetect() {
    	// Check if bird hit the ground then stop it's acceleration.	
    	if (Intersector.overlaps(deadB.bound(), ground) == true) {
    		if (totallyDead) { AssetsLoader.die.play(); totallyDead = false; }
    		deadB.decelerate();     		
    	}
    	if (Intersector.overlaps(b.bound(), ground) == true) {
    		if (b.isOffDuty() == false) {
    			b.off();
    			renderer.transit(255, 255, 255, .3f);
    		}	
    		b.decelerate();
    		generator.stop();
    		if (b.angry() == false) {
	    		stageTracker.replace(b.type(), maxStageMap.get(b.type()));
	    		b.transform(b.width(), b.height(), b.type(), maxStageMap.get(b.type()), maxStageMap.get(b.type()));  	
    		}
    		currentStatus = status.OVER;
    		// Replace highest score if possible.
    		if (score > AssetsLoader.getTopScore()) AssetsLoader.setTopScore(score); 
    	}
    	
    	if (generator.hitQuestion(b) == true && chargeable == true) {
    		b.charge(); chargeable = false; tracker2 = 0;
    		if (b.manaLevel() / manaMap.get(b.type()) != 0) attained = true; 
    		if (b.type() == 5) { 
    			stageTracker.replace(5, b.stage() - 1); 
    			if (b.stage() > 1) b.setStage(b.stage() - 1); 
    			b.reSize(23.5f, 25.1f); 
    		}
    	}
    }
    
    // Check collisions of birds and pipes.
    private void pipesAndBirdsCollisionDetect() {
    	// Collision detection
    	float w = b.width();
    	if (b.angry()) w = superSizeMap.get(b.type())[0];
    	if (generator.collide(b, w) == false) {
    		if (generator.pipe1().scorable() == true && generator.pipe1().x() < b.width()) {
                score++; AssetsLoader.score.play(); 
                generator.noNo(1);
            } else if (generator.pipe2().scorable() == true && generator.pipe2().x() < b.width()) {
            	score++; AssetsLoader.score.play();
            	generator.noNo(2);
            }    		
    	} else {
    		if (vulnerability == true) {
    			if (b.angry() == true) { 
    				if (b.type() == 1 || (b.type() == 3 && generator.collidedPipe().type() == 1)) {
    					score++; AssetsLoader.score.play();
    				}  else generator.soberUp();
    				birdGotHit(); 
    				if (b.angry() == false) generator.showQuestions();
    			} 
				else if (b.stage() < maxStageMap.get(b.type()) && b.type() != 4) {
    				birdGotHit();    				
    				stageTracker.replace(b.type(), b.stage());
    			} else {
    				if (b.type() == 4) { 
    					birdGotHit();
    					if (b.stage() > 102) { 
    						replaceBird(); stageTracker.replace(4, 3); 
    						generator.boom(); renderer.transit(255, 255, 255, 4f);
    					} else if (b.stage() > 3 && b.stage() < 100) replaceBird();
    				} else replaceBird();
    			}
    		}
    	}
    }
    
    // Handle birds when they got hit.
    private void birdGotHit() {
    	vulnerability = false; timeTracker = 0;  
		int CPType = generator.collidedPipe().type();
		b.gotXHit(CPType);
    }

    // Replace a bird if it cant take it anymore.
	private void replaceBird() {
    	if (birdsCount == 1) {
    		generator.stop();
    		b.off();
    	} else {
    		birdsCount--;
    		if (b.type() == 1) generator.setGap(60);
    		attained = false;
			stageTracker.replace(b.type(), b.maxStage());
			totallyDead = true; AssetsLoader.decelerate.play();
			generator.createSmoke(b);
    		float y = b.acceleration().y;
			if (y < 0) y = -y;
			deadB.transform(b.width(), b.height(), b.type(), b.maxStage(), maxStageMap.get(b.type()));
			deadB.offToTheWild(b.y(), b.velocity().y, y);
			int type = b.type() + 1;
			b.transform(sizeMap.get(type)[0], sizeMap.get(type)[1], type, 1, maxStageMap.get(type));
			timeTracker = 0; vulnerability = false; 
			if (b.type() == 4) { generator.hideQuestions(); attained = true; }
    	}
    }
    
	// Restart the game.
    public void restart() {
    	score = 0; 
    	birdsCount = 5;
    	b.restart(14f, 12f, 1, 1, midPointY - 5, 3); 
    	deadB.restart(14f, 12f, 1, 1, midPointY - 5, 3);
    	generator.restart(); 
    	currentStatus = status.READY;
    	renderer.transit(0, 0, 0, 1f);
    	attained = false;
    }    
    
    // Birds' abilitites related methods.
    // Method that handles Matilda shoots eggs
    public void shoot() { 
    	if (b.stage() < b.maxStage()) {
	    	generator.shoot(b); 
	    	stageTracker.replace(b.type(), b.stage() + 1); 
	    	b.setStage(b.stage() + 1);
	    	if (b.stage() == b.maxStage()) b.reSize(19.7f, 28.5f); 
    	} if (b.manaLevel() < 1) attained = false;
    }
    // Method that handles Triplets when having reinforcements.
    public void split() { attained = false; generator.clearHit(); generator.split(); }
    // Check if power button is on or off.
    public boolean isAttained() { return attained; }
    // Deactivate power button.
    public void detain() { attained = false; }    
    
    // Return the bird that is currently in use.
    public AngryBird bird() { return b; }
    // Return the deadB.
    public AngryBird deadBird() { return deadB; }
    // Return the generator that is currently in use.
    public MovingObjectsGenerator generator() { return generator; }
    // Return the renderer that is currently in use.
    public void setRenderer(ObjectsRenderer r) { renderer = r; }
    
    // Return score.
    public String score() { return  Integer.toString(score);}
    public int midPointY() { return midPointY; }
    
    // Get everything warmed up for the game.
    private void getSet(float delta, float y) { b.getSet(runTime, y); generator.getSet(delta); } 
    // Get everything ready.
    public void set() { currentStatus = status.READY; }
    // Get the game going.
    public void go() { currentStatus = status.ROLLING; }
    
    // Return status of the game.
    public boolean warmUp() { return currentStatus == status.WARMUP; }
    public boolean ready() { return currentStatus == status.READY; }
    public boolean rolling() { return currentStatus == status.ROLLING; }
    public boolean gameOver() { return currentStatus == status.OVER; }   
}
