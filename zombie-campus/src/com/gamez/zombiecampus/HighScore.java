package com.gamez.zombiecampus;

import java.util.Date;

/**
 * Represents a high score.
 * @author Greg Breard
 */
public class HighScore {
	
	// Stores the object data
	private Integer m_id;
	private String m_playerName;
	private Integer m_distanceRan;
	private Integer m_enemiesKilled;
	private Integer m_obstaclesAvoided;
	private Date m_runDate;
	
	/**
	 * Initializes a new instance of a HighScore object.
	 */
	public HighScore() {
		// Default constructor
	} // end constructor

	public Integer getDistanceRan() {
		return m_distanceRan;
	} // end getDistanceRan
	
	public Integer getEnemiesKilled() {
		return m_enemiesKilled;
	} // end getEnemiesKilled
	
	public Integer getId() {
		return m_id;
	} // end getId
	
	public Integer getObstaclesAvoided() {
		return m_obstaclesAvoided;
	} // end getObstaclesAvoided
	
	public String getPlayerName() {
		return m_playerName;
	} // end getPlayerName
	
	public Date getRunDate() {
		return m_runDate;
	} // end getRunDate

	public void setDistanceRan(Integer distanceRan) {
		this.m_distanceRan = distanceRan;
	} // end setDistanceRan
	
	public void setEnemiesKilled(Integer enemiesKilled) {
		this.m_enemiesKilled = enemiesKilled;
	} // end setEnemiesKilled
	
	public void setId(Integer id) {
		this.m_id = id;
	} // end setId
	
	public void setObstaclesAvoided(Integer obstaclesAvoided) {
		this.m_obstaclesAvoided = obstaclesAvoided;
	} // end setObstaclesAvoided
	
	public void setPlayerName(String playerName) {
		this.m_playerName = playerName;
	} // end setPlayerName
	
	public void setRunDate(Date runDate) {
		this.m_runDate = runDate;
	} // end setRunDate
	
} // end HighScore
