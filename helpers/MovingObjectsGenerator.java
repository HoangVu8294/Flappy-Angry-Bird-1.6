package com.fab.helpers;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.fab.mainobjects.AngryBird;
import com.fab.mainobjects.Grass;
import com.fab.mainobjects.Item;
import com.fab.mainobjects.Pipe;

public class MovingObjectsGenerator {
	
	private Grass prevGrass, nextGrass;
    private Pipe p1, p2, p;
    private Item q, t1, t2, e, n;
    
	private boolean smokeOn = false, hit = false;
	private float[] smoke = new float[]{0, 0}; 
	private Vector2 smokeP = new Vector2(33, 0), smokeV = new Vector2(0, 0); 
    
    private Random r = new Random();;
    private int typeCheck = r.nextInt(90);
    public static final int PIPE_GAP = 120;

    public MovingObjectsGenerator(float yPosition, int s) {
    	smokeV.x = s;
    	// Initialize grass.
        prevGrass = new Grass(0, yPosition, 143, 63);
        nextGrass = new Grass(prevGrass.tailX(), yPosition, 143, 63);
        // Initialize pipe.
        p1 = new Pipe(210, 0, 22, 60, yPosition);
        p2 = new Pipe(p1.tailX() + PIPE_GAP, 0, 22, 70, yPosition);
        p2.changeType(2);
        p = p1;
        q = new Item(p2.tailX() + PIPE_GAP/5, r.nextInt(90) + 5, 22, 22);
        t1 = new Item(0, 20, 9.5f, 7.6f); t1.setMovingSpeed(2 * 59);
        t2 = new Item(0, 120, 9.5f, 7.6f); t2.setMovingSpeed(2 * 59);
        e = new Item(0, 0, 14f, 15f); e.setMovingSpeed(2 * 59 / 3);
        n = new Item(136, yPosition - 120.4f, 100f, 120.4f);  n.setMovingSpeed(-45);
    }

    public void update(float delta, boolean coming, int gap, boolean HOrN) {
    	if (n.isActivated() == true) {
    		n.update(delta);
    		if (n.x() > 0) { p1.defuse(); p2.defuse(); }
    	}
        prevGrass.update(delta); nextGrass.update(delta);
        p1.update(delta, coming, gap); p2.update(delta, coming, gap);
        q.update(delta);
        t1.update(delta); t2.update(delta);
        e.update(delta);
         
        smokeP.add(smokeV.cpy().scl(delta)); 
    	if (smokeP.x + smoke[0] < 0) smokeOn = false; 
        		
        // When pipes disappear.
    	typeCheck = r.nextInt(90);
        if (p1.leaving()) {
        	if (p1.equals(p)) p = p2;
            if (typeCheck % 6 == 0) { p1.changeType(2); } 
            else if (typeCheck % 5 == 0) { p1.changeType(3); }
            else p1.changeType(1);
            p1.reset(p2.tailX() + PIPE_GAP);
            t1.setY(p2.upperPipe().y + 5);
            t2.setY(p2.lowerPipe().y + 10);
        } else if (p2.leaving()) {
        	if (p2.equals(p)) p = p1;
        	if (typeCheck % 3 == 0) { p2.changeType(3); }
        	else p2.changeType(2);
        	p2.reset(p1.tailX() + PIPE_GAP);
        	t1.setY(p1.upperPipe().y + 5);
        	t2.setY(p1.lowerPipe().y + 10);
        } 
        
        if (t1.isActivated() == true) {
	        if (p1.x() < t1.x() + 9.6f || p2.x() < t1.x() + 9.6f) {
		    	if (Intersector.overlaps(t1.bound(), p1.upperPipe()) == true && p1.upHit() == false) {
			    		t1.deactivate(); t1.reset(0);
			    		t2.deactivate(); t2.reset(0);
			    		p1.defuse(); hit = true; 
		    	} else if (Intersector.overlaps(t1.bound(), p2.upperPipe()) == true && p2.upHit() == false) {
			    		t1.deactivate(); t1.reset(0);
			    		t2.deactivate(); t2.reset(0);
			    		p2.defuse(); hit = true; 
		    	}
	        }
        }
        if (e.isActivated() == true) {
        	if (p1.x() < e.x() + 14f) {
        		if (Intersector.overlaps(e.bound(), p1.upperPipe()) == true && p1.upHit() == false) { 
        			e.deactivate(); e.reset(0); p1.defuseUp(); 
        		} 
        		else if (Intersector.overlaps(e.bound(), p1.lowerPipe()) == true && p1.lowHit() == false) { 
        			e.deactivate(); e.reset(0); p1.defuseLow();  
        		}        		
        	} else if (p2.x() < e.x() + 14f) {
        		if (Intersector.overlaps(e.bound(), p2.upperPipe()) == true && p2.upHit() == false) { 
        			e.deactivate(); e.reset(0); p2.defuseUp();  
        		} 
        		else if (Intersector.overlaps(e.bound(), p2.lowerPipe()) == true && p2.upHit() == false) { 
        			e.deactivate(); e.reset(0); p2.defuseLow();  
        		}   
        	}
        }
        	
        // When grass disappears then regenerate the next one/re-show the prev. 
        if (prevGrass.leaving()) prevGrass.reset(nextGrass.tailX());
        else if (nextGrass.leaving()) nextGrass.reset(prevGrass.tailX());
        
        // When items disappears.
        if (q.leaving() && HOrN == false) { 
        	if (r.nextInt(90) % 3 == 0) q.reset(p1.tailX() + 15); 
        	else q.reset(p2.tailX() + 15);
        	if (r.nextInt(90) % 2 == 0) q.setY(p2.lowerPipe().y + 5);
        	else q.setY(p2.upperPipe().y + 15);
    	}
        
        if (n.leaving() == true) { n.reset(136); n.deactivate(); }
    }   
    
    public boolean collide(AngryBird b, float w) {
    	if (p1.checkCollision(b, w) == true) { p = p1; p1.noMorePointOnMeSir(); return true; }
    	else if (p2.checkCollision(b, w) == true) { p = p2; p2.noMorePointOnMeSir(); return true; }
    	else return false;
    }
    
    public boolean hitQuestion(AngryBird b) {
    	if (b.angry() == true || b.type() == 4) return false;
    	else { if (q.collide(b) == true) return true; return false; }
    }
    
    public void noNo(int i) { 
    	if (i==1) p1.noMorePointOnMeSir();
    	else p2.noMorePointOnMeSir();
    }
    
    public void stop() {
        prevGrass.stop(); nextGrass.stop();
        p1.stop(); p2.stop(); p.stop();
        q.stop(); n.stop();
    }
    
    public void restart() {
    	prevGrass.restart(0, -59); nextGrass.restart(prevGrass.tailX(), -59);
        p1.restart(210, -59); p2.restart(p1.tailX() + PIPE_GAP, -59);
        q.restart(p2.tailX() + PIPE_GAP/5, -59); 
        n.restart(136, -45); n.deactivate(); e.restart(0, 2 * 59 / 3);
    }

    public void createSmoke(AngryBird b) { 
    	smokeP.x = 33; smokeP.y = b.y(); 
    	smoke = new float[]{b.width(), b.height()}; 
    	smokeOn = true; 
    }
    
    public void getSet(float delta) {
    	prevGrass.update(delta); nextGrass.update(delta);
        // When grass disappears then regenerate the next one/re-show the prev.
        if (prevGrass.leaving()) prevGrass.reset(nextGrass.tailX());
        else if (nextGrass.leaving()) nextGrass.reset(prevGrass.tailX());
    }
    
    public void DUI(float s) {
    	prevGrass.setSpeed(s); nextGrass.setSpeed(s);
	    p1.setSpeed(s); p2.setSpeed(s);
    }
    
    public void soberUp() { 
    	prevGrass.soberUp(); nextGrass.soberUp();
	    p1.soberUp(); p2.soberUp();
    }  
    
    public void shoot(AngryBird b) { e.reset(56.5f); e.activate(); e.setY(b.y() + 12.5f); }
    public void split() { t1.reset(0); t2.reset(0); t1.activate(); t2.activate(); }
    public void boom () { n.activate(); p1.defuse(); p2.defuse(); } 
    
    public Grass prevGrass() { return prevGrass; }
    public Grass nextGrass() { return nextGrass; }     
    
    public Pipe pipe1() { return p1; }
    public Pipe pipe2() { return p2; }
    public Pipe collidedPipe() { return p; }
    public void setGap(float g) { p1.setGap(g); p2.setGap(g); }
    
    public void hideQuestions() { q.hide(); }
    public void showQuestions() { q.show(); }
    public Item question() { return q; }
    
    public boolean hit() { return hit; }
    public void clearHit() { hit = false; }
    
    public Item twin1() { return t1; }
    public Item twin2() { return t2; }
    public Item egg() { return e; }
    public Item nuke() { return n; }
    
    public boolean smokeOn() { return smokeOn; }
    public float[] smoke() {return smoke; }
    public Vector2 smokePosition() { return smokeP; }

}
