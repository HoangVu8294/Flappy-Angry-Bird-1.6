package com.fab.mainobjects;

import com.badlogic.gdx.math.Vector2;

public class MovingObject {
	
    protected Vector2 position;
    protected Vector2 velocity;
    protected boolean leaving = false; 
    protected float width, height;  

    public MovingObject(float x, float y, float w, float h) {
    	// Initialize position, velocity and size.
        position = new Vector2(x, y);
        velocity = new Vector2(-59, 0);
        width = w; height = h;
    }

    public void setSpeed(float s) { velocity.x += s; } 
    public void soberUp() { velocity.x = -59; } 
    
    public float x() { return position.x; }
    public float tailX() { return position.x + width; }
    public float y() { return position.y; }       
    public boolean leaving() { return leaving; } 

    public Vector2 velocity() { return velocity; }    
    public void setMovingSpeed(int speed) { velocity = new Vector2(speed, velocity.y); }
    
    public float width() { return width; }
    public float height() { return height; }

	public void restart(float x, float s) {	velocity.x = s; reset(x); }
    public void reset(float newX) { position.x = newX; leaving = false; }
    public void stop() { velocity.x = 0; }
}
