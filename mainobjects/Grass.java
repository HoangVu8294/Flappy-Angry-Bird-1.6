package com.fab.mainobjects;

public class Grass extends MovingObject {
	
	public Grass(float x, float y, int width, int height) { super(x, y, width, height); }
	
	public void update(float delta) { 
    	position.add(velocity.cpy().scl(delta)); 
    	if (x() + width < 0) leaving = true; 
    }
}
