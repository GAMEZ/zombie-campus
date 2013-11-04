package com.gamez.zombiecampus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class Background {
	
	private final int WIDTH = 1184;
	private final int HEIGHT = 768;
	
	public static final Pixmap PM_CLOUD_1_DARK = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_DARK));
	public static final Pixmap PM_CLOUD_1_LIGHT = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_LIGHT));
	public static final Pixmap PM_CLOUD_1_MED = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_MED));
	public static final Pixmap PM_CLOUD_2_DARK = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_DARK));
	public static final Pixmap PM_CLOUD_2_LIGHT = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_LIGHT));
	public static final Pixmap PM_CLOUD_2_MED = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_MED));
	public static final Pixmap PM_GROUND_DAY = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_DAY));
	public static final Pixmap PM_GROUND_NIGHT = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_NIGHT));
	public static final Pixmap PM_GROUND_OVERCAST = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_OVERCAST));
	public static final Pixmap PM_MOON_FULL = new Pixmap(Gdx.files.internal(Assets.BG_MOON_FULL));
	public static final Pixmap PM_SKY_DAY = new Pixmap(Gdx.files.internal(Assets.BG_SKY_DAY));
	public static final Pixmap PM_SKY_NIGHT = new Pixmap(Gdx.files.internal(Assets.BG_SKY_NIGHT));
	public static final Pixmap PM_SKY_OVERCAST = new Pixmap(Gdx.files.internal(Assets.BG_SKY_OVERCAST));
	public static final Pixmap PM_SUN = new Pixmap(Gdx.files.internal(Assets.BG_SUN));
	
	private Pixmap m_staticBG;
	private Pixmap m_returnBG;
	
	private Integer m_cloudWait = 0;
	private enum cloudType{ Light, Medium, Dark	}
	
	// Store the moving objects in the background
	private Queue<BackgroundObject> m_objects = new LinkedList<BackgroundObject>();
	
	private int m_speed = 6;
		
	public Background(){
		// Default constructor
	} // end Background
	
	public Pixmap Draw() {
		// Draw the statics
		if (m_staticBG == null) {
			m_staticBG = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			drawSky();
			drawSun(0);
			drawGround();
		} // end if
		
		// Need to dispose the existing pixmap to release its memory
		if (!(m_returnBG == null))	
			m_returnBG.dispose();
		
		// Re-initialize the return pixmap
		m_returnBG = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		
		// Copy the static background pixmap
		m_returnBG.drawPixmap(m_staticBG, 0, 0);
				
		// Create objects
		drawClouds(cloudType.Light);
		
		// Draw the moving objects
		int count = m_objects.size();
		for (int i = 0; i < count; i++) {
			System.out.println("objects exist");
			
			// Get the object
			BackgroundObject bo = m_objects.poll();
			
			// Draw the object
			m_returnBG.drawPixmap(bo.getPixmap(), bo.getX(), bo.getY());
			
			// Move the object
			bo.setX(bo.getX() - bo.getSpeed());			
						
			// If the object is off the screen, don't re-add it to the queue
			if (bo.getX() > - (bo.getPixmap().getWidth() + bo.getSpeed()))
				m_objects.add(bo);	
		} // end while
			
		// Return the generated pixmap
		return m_returnBG;
	} // end Draw
	
	private void drawSky() {
		m_staticBG.drawPixmap(PM_SKY_DAY, 0, 0);
	}
	
	private void drawSun(Integer timeOfDay) {
		final Integer SUN_TOP = 50;
		final Integer SUN_BOTTOM = 490;
		
		Integer x_inc = WIDTH / 16;
		
		m_staticBG.drawPixmap(PM_SUN, 592, SUN_BOTTOM);
	}
	
	private void drawMoon() {
		m_staticBG.drawPixmap(PM_MOON_FULL, 592, 100);
	}
	
	private void drawGround() {
		m_staticBG.drawPixmap(PM_GROUND_DAY, 0, 0);
	}
	
	private void drawClouds(cloudType type) {
		// Constants for cloud control
		final int CLOUD_TOP = 50;
		final int CLOUD_BOTTOM = 300;
		final int CLOUD_WAIT_MIN = 50;
		final int CLOUD_WAIT_MAX = 300;
				
		// Initialize new random number generator
		Random rand = new Random();
		
		// Initialize cloud
		Pixmap cloud;
		
		// Get the cloud
		switch (type) {
			case Light:
				if (rand.nextInt(2) == 0)
					cloud = PM_CLOUD_1_LIGHT;
				else
					cloud = PM_CLOUD_2_LIGHT;
				break;
			case Medium:
				if (rand.nextInt(2) == 0)
					cloud = PM_CLOUD_1_MED;
				else
					cloud = PM_CLOUD_2_MED;
				break;
			case Dark:
				if (rand.nextInt(2) == 0)
					cloud = PM_CLOUD_1_DARK;
				else
					cloud = PM_CLOUD_2_DARK;
				break;
			default:
				cloud = PM_CLOUD_1_LIGHT;
				break;
		} // end switch		
		
		// Check if there has been enough time since the last cloud
		if (m_cloudWait == 0) {
			// Get a random height
			Integer y = rand.nextInt(CLOUD_BOTTOM - CLOUD_TOP) + CLOUD_TOP;
			
			// Create the cloud
			m_objects.add(new BackgroundObject(cloud, WIDTH, y, m_speed));
			
			// Set a random wait
			m_cloudWait = rand.nextInt(CLOUD_WAIT_MAX - CLOUD_WAIT_MIN) + CLOUD_WAIT_MIN;
		} // end if
		
		// Decrement wait
		m_cloudWait--;
	} // end drawClouds
	
	private Pixmap drawBuildings() {
		BuildingList bl = new BuildingList();
		bl.Fill();
		
		Pixmap pm = new Pixmap(0,0, Format.RGBA8888);
		
		return pm;
	}
}
