package com.fab.mainobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Item extends MovingObject{

	private boolean activated = false, hid = false;
	Rectangle bound = new Rectangle(); 
	
	public Item(float x, float y, float width, float height) { super(x, y, width, height); }

    public void update(float delta) {
		position.add(velocity.cpy().scl(delta)); 
    	if (x() + width < 0) leaving = true; 
        bound.set(position.x, position.y, width, height);
	}
	
    @Override
    public void reset(float newX) {
        super.reset(newX);
        activated = false;
        hid = false;
    }
	
	 public Boolean collide(AngryBird b) {
		 if (position.x < 33 + b.width() && Intersector.overlaps(b.bound(), bound) == true) { 
			 hide(); return true; 
		 }
		 else return false;
	 }

	 public void setY(float newY) { position.y = newY; }
	 public Rectangle bound() { return bound; }
	 
	 public void activate() { activated = true; }
	 public void deactivate() { activated = false; }
	 public boolean isActivated() { return activated; }
	 
	 public boolean isHid() { return hid; }
	 public void hide() { hid = true; }
	 public void show() { hid = false; }
}
