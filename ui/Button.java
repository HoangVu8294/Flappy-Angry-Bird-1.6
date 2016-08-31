package com.fab.ui;

import com.badlogic.gdx.math.Circle;

public class Button {

    private float x, y, r;
    private Circle bound;

    public Button(float x, float y, float radius) {
        this.x = x; this.y = y; r = radius;
        bound = new Circle(x + 11, y + 11, r);
    }

    public boolean isTouched(float screenX, float screenY) { 
    	if (bound.contains(screenX, screenY) == true) return true;
    	return false;
    }
    
    public float x() { return x; }
    public float y() { return y; }
    public float radius() { return r; }
    public Circle bound() { return bound; }
}
