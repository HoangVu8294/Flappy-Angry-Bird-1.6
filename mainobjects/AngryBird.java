package com.fab.mainobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.fab.helpers.AssetsLoader;

public class AngryBird {
	
	protected Vector2 position;	
	protected Vector2 velocity = new Vector2(0, 0);
	protected Vector2 acceleration = new Vector2(0, 460);
	
	protected float rotation, width, height; 	
	protected int stage, normalStage, type, maxStage, mana = 0;
	
	protected boolean offDuty = false, angry = false;
	// The circle for collision detection.
	protected Circle boundingCircle = new Circle();
	
	public AngryBird(float x, float y, float w, float h, int t, int s, int ms) {
		// Initialize position.
		position = new Vector2(x, y);      
		// Passing in size, type and stage.
		width = w; height = h; type = t; stage = s; normalStage = stage; maxStage = ms;
    }
	
	public void update(float delta) {		
		// Update velocity.
		velocity.add(acceleration.cpy().scl(delta)); 
		// Balance the speed.
		if (velocity.y > 200) velocity.y = 200; 
		// Update current position.
		position.add(velocity.cpy().scl(delta));
		if ((x() + width < 0)) offDuty = false;
		
		// If the bird is falling down. Rotate it perpendicular to the ground.
		if (velocity.y > 110) { 
			if (angry == true && type == 1) rotation += 220 * delta;
			else rotation += 480 * delta; 
			if (rotation > 90) rotation = 90; 
		}
		// Wouldn't let the bird fly upward off the screen.
		if (position.y < -13) { position.y = -13; velocity.y = 0; }	

		// Create boundingCircle for bird.
		if (angry == false) {
		    if (type == 1) boundingCircle.set(position.x + width/2 + 1.5f, 
		    		position.y + height/2 + 0.3f, height/2 - 0.75f);
		    else if (type == 2) boundingCircle.set(position.x + width/2 + 0.7f, 
		    		position.y + height/2, height/2 - 0.5f);
		    else if (type == 3) boundingCircle.set(position.x + width/2 + 1.2f, 
		    		position.y + height/2 + 1.0f, height/3 + 0.4f);
		    else if (type == 4) boundingCircle.set(position.x + width/2 + 1.4f, 
		    		position.y + height/2 + 2.0f, height/3 + 1.6f);
		    else {
		    	if (stage != 4) boundingCircle.set(position.x + width/2 + 2.4f, position.y + height/2 + 1.7f, height/2 - 3.3f);
		    	else boundingCircle.set(position.x + width/2 + 1.4f, position.y + height/2 + 1.5f, height/2 - 5.3f);
		    }
		} else {
			if (type == 1) boundingCircle.set(position.x + 20, position.y + 23.3f/2, 12f);
			else if (type == 3) boundingCircle.set(position.x + 12.2f, position.y + 8f, height/3 + 0.4f);
		}
	    
		if (offDuty != true) {	
		    if (velocity.y < 0) { rotation -= 600 * delta; if (rotation < -20) rotation = -20; }
		    if (stage < 100) normalStage = stage;
		}
    }    

	public void gotXHit(int collidedPipeType) { 
		if (angry == false) {
			stage++; if (type == 5 && stage == maxStage) reSize(19.7f, 28.5f);
			AssetsLoader.hit.play();
		}
		else {
			if (type == 3) { 
				if (collidedPipeType != 1) { stage = normalStage; angry = false; AssetsLoader.hit.play(); } 
			} else if (type == 1) { 
				stage++; if (stage > 103) { 
					boundingCircle.set(position.x + width/2 + 1.5f, 
				    		position.y + height/2 + 0.3f, height/2 - 0.75f);
					stage = normalStage; angry = false; AssetsLoader.hit.play(); 
				} 
			}
		}
	}
	
	public void transform(float w, float h, int t, int s, int ms) { 
		width = w; height = h; type = t; mana = 0;
		stage = s; normalStage = stage; maxStage = ms; 
	}
	
	public void offToTheWild(float py, float vy, float ay) {
		position.x = 33;
		position.y = py;
		velocity.x = -59;
		velocity.y = vy;
		acceleration.y = ay;
		offDuty = true;
	}
	
	public void restart(float w, float h, int t, int s, int ms, int y) {
        rotation = 0; position.y = y;
        velocity.x = 0; velocity.y = 0;
        acceleration.x = 0; acceleration.y = 460;
        width = w; height = h; type = t; 
        stage = s; normalStage = s; maxStage = ms;
        offDuty = false;
        mana = 0;
    }
	
    public void useAbility() { 
    	if (type == 1) mana -= 4;
    	else mana--;
    	if (mana < 0) mana = 0;
    }
    
	public void onClick() { 
		if (offDuty != true) { 
			AssetsLoader.flap.play(1, 2, 0); 
			if (angry == true && type == 1) velocity.y = -100;
			else velocity.y = -140; 
		} 
	}    
	
    public float x() { return position.x; }
    public float y() { return position.y; }  
    public Vector2 velocity() { return velocity; }
	public Vector2 acceleration() { return acceleration; }
    public void decelerate() { velocity.y = 0; acceleration.y = 0; }
    
    public float rotation() { return rotation; }  
    public float width() { return width; }
    public float height() { return height; }
    public void reSize(float w, float h) { width = w; height = h; }
    
    public void setStage(int s) { stage = s; } 
    public int stage() { return stage; } 
    public int normalStage() { return normalStage; }
    public int maxStage() { return maxStage; }
    public int type() { return type; }
    
    public boolean isOffDuty() { return offDuty; }
    public void off() { offDuty = true; }	
   
    public void beAngry() { stage = 100; angry = true; }
    public boolean angry() { return angry; }
    public void charge() { mana++;}
    public int manaLevel() { return mana; }
	
	public Circle bound() { return boundingCircle; }

	public void getSet(float runTime, float constY) { position.y = (float) (2 * Math.sin(7 * runTime) + constY); }
}
