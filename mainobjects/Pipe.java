package com.fab.mainobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends MovingObject {
	
	private Random r = new Random();
	private int type = 1;

	private Rectangle upperPipe = new Rectangle(), lowerPipe = new Rectangle();
    private float groundY, fallingY = 0; 
    
    public float verticalGap = 34;
    private float upRotation = 0, lowRotation = 0;
    private boolean scorable = true, upHit = false, lowHit = false, hitAble = true;
	
    public Pipe(float x, float y, int width, int height, float groundY) { super(x, y, width, height); this.groundY = groundY; }

    public void update(float delta, boolean tripletsComing, int gap) {
    	position.add(velocity.cpy().scl(delta)); 
    	if (x() + width < 0) leaving = true; 
        // If hit pull the pipe down depending where the bird hit.
        if (upHit == true) { 
        	upRotation--; if (upRotation < -90) upRotation = -90;
        	fallingY += 2; if (fallingY > groundY) fallingY = groundY;
        }  
        if (lowHit == true) { lowRotation += 2; if (lowRotation > 90) lowRotation = 90; }   
        // If we have Triplets on play we're gonna raise pipes so its a bit challenging.
        if (tripletsComing == true) { if (verticalGap > 0.04) verticalGap -= 0.04; }
        else { if (verticalGap < gap) verticalGap += 0.4; else verticalGap = gap; }
        upperPipe.set(position.x, position.y, width, height);
        lowerPipe.set(position.x, position.y + height + verticalGap, width,
                groundY - (position.y + height + verticalGap));
    }
    
    public Boolean checkCollision(AngryBird b, float w) {
    	if (hitAble == true) {
	    	if (position.x < 33 + w) {
		    	if (Intersector.overlaps(b.bound(), upperPipe) == true && upHit == false) { 
		    		upHit = true; scorable = false; return true; }
		    	else if (Intersector.overlaps(b.bound(), lowerPipe) == true && lowHit == false) { 
		    		lowHit = true; scorable = false; return true; }
		    	else return false;
	    	}
	        return false;
    	} else return false;
    }

	@Override
    public void reset(float newX) {
        super.reset(newX);
        // Change the height.
        height = r.nextInt(90) + 5;
        upHit = false; lowHit = false;
        upRotation = 0; lowRotation = 0;
        fallingY = 0;
        scorable = true; hitAble = true;
    }
    
    public int type() { return type; }
    public void changeType(int t) { type = t; }
    
    public Rectangle upperPipe() { return upperPipe; }
    public Rectangle lowerPipe() { return lowerPipe; }
    public void setGap(float g) { verticalGap = g; }
    
    public boolean upHit() { return upHit; }
    public boolean lowHit() { return lowHit; }
    public float upRotation() { return upRotation; }
    public float lowRotation() { return lowRotation; }

    public float groundY() { return groundY; }
    public float fallingY() { return fallingY; }
    
    public boolean scorable() { return scorable; }
    public void noMorePointOnMeSir() { scorable = false; }
    
    public void defuse() { hitAble = false; upHit = true; lowHit = true; }
    public void defuseUp() { upHit = true; }
    public void defuseLow() { lowHit = true; }
}
