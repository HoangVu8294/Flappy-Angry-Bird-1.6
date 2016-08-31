package com.fab.helpers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class AssetsLoader {
	
	// A hash map that keeps track of birds' stages and textures.	
	public static HashMap<Integer, TextureRegion[]> textureMap = new HashMap<Integer, TextureRegion[]>(); 
	public static Texture texture;
	
	public static TextureRegion backGround, grass, sky, w, ir, is, sr, ss, r1, r2, r3, r100, 
		r101, r102, r103, t1, t2, t3, t100, c1, c2, c3, c100, b1, b2, b3, b100, b101, b102, 
		m1, m2, m3, m4, ouch, play, evolve, deact, HCM, finger, logo, question, egg, slingshot, 
		sbFull, sbEmpty, gameOverBoard, replay, nuke;
    
    public static Animation smoke, ash;
    private static FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mainfont.TTF"));
    private static FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    
    public static BitmapFont s;
    public static Sound hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav")),
    		decelerate = Gdx.audio.newSound(Gdx.files.internal("sounds/decelerate.wav")),
    		score = Gdx.audio.newSound(Gdx.files.internal("sounds/score.wav")),
    		die = Gdx.audio.newSound(Gdx.files.internal("sounds/die.wav")),
    		theme = Gdx.audio.newSound(Gdx.files.internal("sounds/theme.wav")),
    		flap = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.wav")),
    		set = Gdx.audio.newSound(Gdx.files.internal("sounds/ready,set,go.wav"));
    
    // Creat file that keeps track of the highest score.
    public static Preferences top = Gdx.app.getPreferences("FABGamePreference");
    
    public static void load() {      	
    	texture = new Texture(Gdx.files.internal("textures/background.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);      
        // Background texture.
        backGround = new TextureRegion(texture, 0, 0, 800, 398);
        backGround.flip(false, true);   
        // Sky texture.
        sky = new TextureRegion(texture, 0, 0, 800, 1);
        sky.flip(false, true);         
        // Grass texture
        grass = new TextureRegion(texture, 0, 416, 700, 600);
        grass.flip(false, true);
        
        // Smoke textures and animation.
        texture = new Texture(Gdx.files.internal("textures/misc.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);      
        TextureRegion s1 = new TextureRegion(texture, 168, 513, 116, 111);
        s1.flip(false, true);   
        TextureRegion s2 = new TextureRegion(texture, 168, 278, 126, 121);
        s2.flip(false, true);  
        TextureRegion s3 = new TextureRegion(texture, 877, 0, 127, 124);
        s3.flip(false, true);   
        TextureRegion s4 = new TextureRegion(texture, 168, 155, 128, 122);
        s4.flip(false, true); 
        TextureRegion[] smokeAnimation = {s1, s2, s3, s4};
        smoke = new Animation(1f, smokeAnimation);
        smoke.setPlayMode(Animation.PlayMode.NORMAL);
        
        egg = new TextureRegion(texture, 667, 819, 46, 58);
        egg.flip(true, false);
        
    	// Attach wood texture.
		texture = new Texture(Gdx.files.internal("textures/woodpipe.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);  
        // Height > 35 texture
        w = new TextureRegion(texture, 251, 269, 207, 22);
        w.flip(false, true);
        
		// Attach ice texture.
		texture = new Texture(Gdx.files.internal("textures/icepipe.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);  
        // Height > 35 texture
        ir = new TextureRegion(texture, 287, 236, 207, 22);
        ir.flip(false, true);  
        // Height < 35 texture
        is = new TextureRegion(texture, 244, 126, 85, 44);
        is.flip(false, true);
        
    	// Attach stone texture.
		texture = new Texture(Gdx.files.internal("textures/stonepipe.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        // Height > 35 texture
        sr = new TextureRegion(texture, 246, 234, 207, 22);
        sr.flip(false, true);
        // Height < 35 texture
        ss = new TextureRegion(texture, 246, 124, 85, 43);
        ss.flip(false, true);
        
        // Detect birds' textures.
        texture = new Texture(Gdx.files.internal("textures/birds.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        // Red's textures.
		// Normal form.
        r1 = new TextureRegion(texture, 519, 114, 46, 45); r1.flip(false, true); 
		r2 = new TextureRegion(texture, 449, 511, 46, 45); r2.flip(false, true);
        r3 = new TextureRegion(texture, 520, 208, 46, 45); r3.flip(false, true); 
		// Terence form.
        r100 = new TextureRegion(texture, 277, 5, 97, 95); r100.flip(false, true);
        r101 = new TextureRegion(texture, 97, 219, 97, 95); r101.flip(false, true); 
        r102 = new TextureRegion(texture, 97, 322, 97, 95); r102.flip(false, true);
        r103 = new TextureRegion(texture, 97, 121, 97, 95); r103.flip(false, true); 
		// Put in the map.
        textureMap.put(1, new TextureRegion[]{r1, r2, r3, r100, r101, r102, r103});
		     
        // Triplet' textures.
 		// Normal form.
        t1 = new TextureRegion(texture, 517, 164, 31, 30); t1.flip(false, true); 
 		t2 = new TextureRegion(texture, 500, 546, 31, 27); t2.flip(false, true);
		t3 = new TextureRegion(texture, 519, 258, 30, 30); t3.flip(false, true);  
 		// Triplets form.
        t100 = new TextureRegion(texture, 536, 508, 31, 30); t100.flip(false, true);
        // Put in the map.
        textureMap.put(2, new TextureRegion[]{t1, t2, t3, t100});
        
        // Chuck's textures.
		// Normal form.
	    c1 = new TextureRegion(texture, 102, 515, 57, 53); c1.flip(false, true); 
		c2 = new TextureRegion(texture, 456, 449, 57, 54); c2.flip(false, true);
	    c3 = new TextureRegion(texture, 209, 488, 56, 52); c3.flip(false, true);    
		// Sonic form.
	    c100 = new TextureRegion(texture, 447, 391, 66, 52); c100.flip(false, true); 
	    // Put in the map.
        textureMap.put(3, new TextureRegion[]{c1, c2, c3, c100});  
	    
 		// Bomb's textures.
 		// Normal form.
        b1 = new TextureRegion(texture, 486, 6, 65, 85); b1.flip(false, true); 
 		b2 = new TextureRegion(texture, 448, 303, 65, 85); b2.flip(false, true);	
        b3 = new TextureRegion(texture, 306, 210, 64, 85); b3.flip(false, true); 
 		// Bomb form.
        b100 = new TextureRegion(texture, 306, 396, 65, 85); b100.flip(false, true); 
        b101 = new TextureRegion(texture, 306, 489, 65, 85); b101.flip(false, true); 
        b102 = new TextureRegion(texture, 377, 303, 65, 85); b102.flip(false, true); 
        // Put in the map.
        textureMap.put(4, new TextureRegion[]{b1, b2, b3, b100, b101, b102});
        
        // Matilda's textures.
        m1 = new TextureRegion(texture, 7, 13, 81, 95); m1.flip(false, true);
		m2 = new TextureRegion(texture, 187, 15, 81, 93); m2.flip(false, true);	
        m3 = new TextureRegion(texture, 3, 459, 85, 89); m3.flip(false, true);          
        m4 = new TextureRegion(texture, 106, 1, 65, 107); m4.flip(false, true); 
        // Put in the map.
        textureMap.put(5, new TextureRegion[]{m1, m2, m3, m4});
        
        // Initialize ouch texture for dead bird.
        texture = new Texture(Gdx.files.internal("textures/ouch.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        ouch = new TextureRegion(texture, 8, 25, 157, 123);
        ouch.flip(false, true);
       
        // Initialize fonts.
        parameter.size = 72;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 8;
        parameter.shadowOffsetY = 8;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest; 
        parameter.flip = true;
        s = fontGenerator.generateFont(parameter);
        s.setColor(Color.WHITE);
        
        if (!top.contains("topScore")) top.putInteger("topScore", 1); 
        
        texture = new Texture(Gdx.files.internal("textures/buttons.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        play = new TextureRegion(texture, 317, 672, 74, 82);
        play.flip(false, true);    
        
        evolve = new TextureRegion(texture, 3, 768, 99, 109); 
        evolve.flip(false, true); 
        
        deact = new TextureRegion(texture, 128, 174, 99, 109);
        deact.flip(false, true);
        
        replay = new TextureRegion(texture, 235, 173, 99, 109);
        replay.flip(false, true); 
        
        texture = new Texture(Gdx.files.internal("textures/HCM.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        HCM = new TextureRegion(texture, 0, 0, 215, 338);
        
        texture = new Texture(Gdx.files.internal("textures/logo.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        logo = new TextureRegion(texture, 0, 0, 500, 127);
        
        texture = new Texture(Gdx.files.internal("textures/misc2.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        finger = new TextureRegion(texture, 312, 1, 122, 175);
        finger.flip(false, true);  
        
        question = new TextureRegion(texture, 344, 951, 53, 64);
        question.flip(false, true);  
        
        gameOverBoard = new TextureRegion(texture, 279, 196, 257, 134);
        
        texture = new Texture(Gdx.files.internal("textures/slingshot.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        slingshot = new TextureRegion(texture, 3, 3, 154, 288);
        slingshot.flip(false, true);
        
        texture = new Texture(Gdx.files.internal("textures/scorebar.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        sbEmpty = new TextureRegion(texture, 668, 232, 31, 49); sbEmpty.flip(false, true);
        sbFull = new TextureRegion(texture, 668, 182, 31, 49); sbFull.flip(false, true);
        
        texture = new Texture(Gdx.files.internal("textures/nukesmoke.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest); 
        nuke = new TextureRegion(texture, 0, 0, 1994, 2400); 
        nuke.flip(false, true);
    }

    public static void dispose() { 
    	texture.dispose(); 
    	fontGenerator.dispose();
    	hit.dispose();
    	decelerate.dispose();
    	score.dispose();
    	die.dispose();
    	theme.dispose();
    	flap.dispose();
    	set.dispose();
    }
    
    public static void setTopScore(int scr) { top.putInteger("topScore", scr); top.flush(); }    
    public static int getTopScore() { return top.getInteger("topScore"); }
}
