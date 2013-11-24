package com.gamez.zombiecampus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.gamez.zombiecampus.WeatherTime.Condition;

public class Background {
	
	// Constants for size
	private final int WIDTH = 1184;
	private final int HEIGHT = 768;
	
	// Constants for background
	public static final Pixmap PM_CLOUD_1_DARK = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_DARK));
	public static final Pixmap PM_CLOUD_1_LIGHT = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_LIGHT));
	public static final Pixmap PM_CLOUD_1_MED = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_1_MED));
	public static final Pixmap PM_CLOUD_2_DARK = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_DARK));
	public static final Pixmap PM_CLOUD_2_LIGHT = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_LIGHT));
	public static final Pixmap PM_CLOUD_2_MED = new Pixmap(Gdx.files.internal(Assets.BG_CLOUD_2_MED));
	public static final Pixmap PM_GROUND_DAY = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_DAY));
	public static final Pixmap PM_GROUND_NIGHT = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_NIGHT));
	public static final Pixmap PM_GROUND_OVERCAST = new Pixmap(Gdx.files.internal(Assets.BG_GROUND_OVERCAST));
	public static final Pixmap PM_MOON_NEW = new Pixmap(Gdx.files.internal(Assets.BG_MOON_NEW));
	public static final Pixmap PM_MOON_WAX_CRES = new Pixmap(Gdx.files.internal(Assets.BG_MOON_WAX_CRES));
	public static final Pixmap PM_MOON_FIRST_QTR = new Pixmap(Gdx.files.internal(Assets.BG_MOON_FIRST_QTR));
	public static final Pixmap PM_MOON_WAX_GIB = new Pixmap(Gdx.files.internal(Assets.BG_MOON_WAX_GIB));
	public static final Pixmap PM_MOON_FULL = new Pixmap(Gdx.files.internal(Assets.BG_MOON_FULL));
	public static final Pixmap PM_MOON_WAN_GIB = new Pixmap(Gdx.files.internal(Assets.BG_MOON_WAN_GIB));
	public static final Pixmap PM_MOON_THIRD_QTR = new Pixmap(Gdx.files.internal(Assets.BG_MOON_THIRD_QTR));
	public static final Pixmap PM_MOON_WAN_CRES = new Pixmap(Gdx.files.internal(Assets.BG_MOON_WAN_CRES));
	public static final Pixmap PM_SKY_DAY = new Pixmap(Gdx.files.internal(Assets.BG_SKY_DAY));
	public static final Pixmap PM_SKY_NIGHT = new Pixmap(Gdx.files.internal(Assets.BG_SKY_NIGHT));
	public static final Pixmap PM_SKY_OVERCAST = new Pixmap(Gdx.files.internal(Assets.BG_SKY_OVERCAST));
	public static final Pixmap PM_SUN = new Pixmap(Gdx.files.internal(Assets.BG_SUN));
	
	// Stores static images
	private Pixmap m_staticBG;
	private Pixmap m_returnBG;
	
	// Used for random cloud generation
	private enum cloudType{ Light, Medium, Dark	}
	
	// Stores the current time and weather condition
	private WeatherTime m_weather;
	
	// Stores the building data and relations
	private BuildingList m_buildingList = null;
	
	// Stores the cloud and building objects in the background
	private Queue<BackgroundObject> m_clouds = new LinkedList<BackgroundObject>();
	private Queue<BackgroundObject> m_buildings = new LinkedList<BackgroundObject>();
	
	// Controls the object speed
	private int m_speed = 4;
	
	// Controls object wait times
	private Integer m_cloudWait = 0;
	private Integer m_buildingWait = 0;
	
	/**
	 * Instantiates a new instance of the Background class.
	 */
	public Background(WeatherTime wt){
		m_weather = wt;
	} // end Background
	
	public Pixmap Draw() {
		// Draw the statics
		if (m_staticBG == null) {
			m_staticBG = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			drawSky();
			
			if (m_weather.IsNightTime())
				drawMoon();
			else
				drawSun();
						
			drawGround();
		} // end if
		
		// Need to dispose the existing pixmap to release its memory
		if (!(m_returnBG == null))	
			m_returnBG.dispose();
		
		// Re-initialize the return pixmap
		m_returnBG = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		
		// Copy the static background pixmap
		m_returnBG.drawPixmap(m_staticBG, 0, 0);
				
		// Create clouds objects
		switch (m_weather.GetWeather()) {
			case Fog:
			case Cloudy:
				if (m_weather.IsNightTime())
					drawClouds(cloudType.Medium);
				else 
					drawClouds(cloudType.Light);
				break;
			case Overcast:
				drawClouds(cloudType.Medium);
				break;
			case Rain:
			case Snow:
				drawClouds(cloudType.Dark);
				break;
			case Clear:
			case Unknown:	
				// No clouds
				break;
		} // end switch
		
		drawBuildings();
			
		// Draw the moving objects
		updateObjects(m_clouds);
		updateObjects(m_buildings);
		
		// Return the generated pixmap
		return m_returnBG;
	} // end Draw
	
	/**
	 * Update the position of moving objects.
	 * @param layer
	 */
	private void updateObjects(Queue<BackgroundObject> layer) {
		int count = layer.size();
		
		for (int i = 0; i < count; i++) {
			// Get the object
			BackgroundObject bo = layer.poll();
			
			// Draw the object
			m_returnBG.drawPixmap(bo.getPixmap(), bo.getX(), bo.getY());
			
			// Move the object
			bo.setX(bo.getX() - bo.getSpeed());			
						
			// If the object is off the screen, don't re-add it to the queue
			if (bo.getX() > - (bo.getPixmap().getWidth() + bo.getSpeed()))
				layer.add(bo);	
		} // end while
	} // end updateObjects
	
	private void drawSky() {
		if (m_weather.IsNightTime())
			m_staticBG.drawPixmap(PM_SKY_NIGHT, 0, 0);
		else 
			if (m_weather.GetWeather() == Condition.Overcast)
				m_staticBG.drawPixmap(PM_SKY_OVERCAST, 0, 0);
			else
				m_staticBG.drawPixmap(PM_SKY_DAY, 0, 0);
		
	} // end drawSky

	private void drawSun() {
		// Day time:
		final Integer SUN_RISE = m_weather.GetSunRise();
		final Integer SUN_SET = m_weather.GetSunSet();
		final Integer TIME_OF_DAY = m_weather.GetTimeOfDay();
		final Integer DAY_HOURS = SUN_SET - SUN_RISE + 1;		
		// Sun location:
		final Integer SUN_LEFT = WIDTH / (DAY_HOURS + 2);
		final Integer SUN_RIGHT = SUN_LEFT * DAY_HOURS;
		final Integer SUN_TOP = 50;
		final Integer SUN_BOTTOM = 490;		
		// Increments:
		final Integer X_INC = (SUN_RIGHT - SUN_LEFT) / DAY_HOURS;
		final Integer Y_INC = (SUN_BOTTOM - SUN_TOP) / (DAY_HOURS / 2);
		
		// Initialize draw location
		Integer x = 0;
		Integer y = 0;
		
		// Get the horizontal and vertical position
		if (TIME_OF_DAY >= SUN_RISE && TIME_OF_DAY <= SUN_SET) {
			x += SUN_LEFT + ((TIME_OF_DAY - SUN_RISE) * X_INC);
					
			if (TIME_OF_DAY <= SUN_RISE + (DAY_HOURS / 2))
				y = SUN_BOTTOM - ((TIME_OF_DAY - SUN_RISE) * Y_INC);
			else
				y = SUN_TOP + ((TIME_OF_DAY - SUN_RISE - (DAY_HOURS / 2)) * Y_INC);
			
			m_staticBG.drawPixmap(PM_SUN, x, y);
		} // end if
		
	} // end drawSun
	
	private void drawMoon() {
		final Integer MOON_X = 592;
		final Integer MOON_Y = 100;		
		
		// Check phase
		switch (m_weather.GetMoonPhase()) {
			case NewMoon:
				m_staticBG.drawPixmap(PM_MOON_FULL, MOON_X, MOON_Y);
				break;
			case WaxingCrescent:
				m_staticBG.drawPixmap(PM_MOON_WAN_CRES, MOON_X, MOON_Y);
				break;
			case FirstQuarter:
				m_staticBG.drawPixmap(PM_MOON_FIRST_QTR, MOON_X, MOON_Y);
				break;
			case WaxingGibbous:
				m_staticBG.drawPixmap(PM_MOON_WAX_GIB, MOON_X, MOON_Y);
				break;
			case FullMoon:
				m_staticBG.drawPixmap(PM_MOON_FULL, MOON_X, MOON_Y);
				break;
			case WaningGibbous:
				m_staticBG.drawPixmap(PM_MOON_WAN_GIB, MOON_X, MOON_Y);
				break;
			case LastQuarter:	
				m_staticBG.drawPixmap(PM_MOON_THIRD_QTR, MOON_X, MOON_Y);
				break;
			case WaningCrescent:	
				m_staticBG.drawPixmap(PM_MOON_WAN_CRES, MOON_X, MOON_Y);
				break;
		} // end switch
	} // end drawMoon
	
	private void drawGround() {
		if (m_weather.IsNightTime())
			m_staticBG.drawPixmap(PM_GROUND_NIGHT, 0, 0);
		else
			if (m_weather.GetWeather() == Condition.Overcast)
				m_staticBG.drawPixmap(PM_GROUND_OVERCAST, 0, 0);
			else
				m_staticBG.drawPixmap(PM_GROUND_DAY, 0, 0);			
	} // end drawGround
	
	private void drawClouds(cloudType type) {
		// Constants for cloud control
		final Integer CLOUD_TOP = 50;
		final Integer CLOUD_BOTTOM = 300;
		final Integer CLOUD_WAIT_MIN = 50;
		final Integer CLOUD_WAIT_MAX = 300;
		final Double CLOUD_SPEED_MULT = 0.5;
		
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
						
			// Set speed
			Double speed = m_speed * CLOUD_SPEED_MULT;
			
			// Create the cloud
			m_clouds.add(new BackgroundObject(cloud, WIDTH, y, speed.intValue()));
			
			// Set a random wait
			m_cloudWait = rand.nextInt(CLOUD_WAIT_MAX - CLOUD_WAIT_MIN) + CLOUD_WAIT_MIN;
		} // end if
		
		// Decrement wait
		m_cloudWait--;
	} // end drawClouds
	
	private void drawBuildings() {
		// Constants for building control
		final Integer BUILDING_TOP = 200;
		final Integer BUILDING_WAIT = 300;
		
		// Check if there has been enough time since the last cloud
		if (m_buildingWait == 0) {
			if (m_buildingList == null) {
				m_buildingList = new BuildingList();
				m_buildingList.Fill();
			} // end if
			
			Building bd = m_buildingList.GetNextBuilding();
			
			Pixmap pm_building = new Pixmap(Gdx.files.internal(bd.getImage()));
			
			// Create the cloud
			m_buildings.add(new BackgroundObject(pm_building, WIDTH, BUILDING_TOP, m_speed));
			
			// Set a wait
			m_buildingWait = BUILDING_WAIT;
		} // end if
		
		// Decrement wait
		m_buildingWait--;
	} // end drawBuildings
	
} // end Background
