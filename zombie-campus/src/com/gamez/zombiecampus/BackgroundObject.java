package com.gamez.zombiecampus;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * Represents a moving object in the background.
 * @author Greg Breard
 */
public class BackgroundObject {
	
	// Stores the object data
	private Pixmap m_pixmap;
	private Integer m_x;
	private Integer m_y;
	private Integer m_speed;
	
	/**
	 * Initializes a new instance of a BackgroundObject object.
	 */
	public BackgroundObject(Pixmap pixmap, Integer x, Integer y, Integer speed) {
		m_pixmap = pixmap;
		m_x = x;
		m_y = y;
		m_speed = speed;
	} // end constructor

	public Pixmap getPixmap() {
		return m_pixmap;
	} // end getPixmap
	
	public Integer getX() {
		return m_x;
	} // end getX
	
	public Integer getY() {
		return m_y;
	} // end getY
	
	public Integer getSpeed() {
		return m_speed;
	} // end getSpeed
	
	public void setPixmap(Pixmap pixmap) {
		this.m_pixmap = pixmap;
	} // end setPixmap
	
	public void setX(Integer x) {
		this.m_x = x;
	} // end setX
	
	public void setY(Integer y) {
		this.m_y = y;
	} // end setY
	
	public void setSpeed(Integer speed) {
		this.m_y = speed;
	} // end setY
	
} // end BackgroundObject
