package com.gamez.zombiecampus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

/**
 * Represents the list of high scores.
 * @author Greg Breard
 */
public class HighScoreList {
	
	// Constants for column names
	private final String COL_ID = "id";
	private final String COL_PLAYER_NAME = "playerName";
	private final String COL_DISTANCE_RAN = "distanceRan";
	private final String COL_ENEMIES_KILLED = "enemiesKilled";
	private final String COL_OBSTACLES_AVOIDED = "obstaclesAvoided";
	private final String COL_RUN_DATE = "runDate";
	
	// Stores the high score elements
	private List<HighScore> m_highScores = new ArrayList<HighScore>();
	
	/**
	 * Initializes a new instance of a HighScoreList object.
	 */
	public HighScoreList() {
		// Default constructor
	} // end constructor

	/**
	 * Fills the high scores.
	 */
	public void Fill(){
		// Get the data
		CSV csv = new CSV(Gdx.files.local(Assets.HIGH_SCORES_DB));
		
		// Get the rows
		for (int i = 1; i <= csv.GetRowCount(); i++) {
			// Create the high score object for the row data
			HighScore hs = new HighScore();
			hs.setId(Integer.parseInt(csv.GetRowCol(i, COL_ID)));
			hs.setPlayerName(csv.GetRowCol(i, COL_PLAYER_NAME));
			hs.setDistanceRan(Integer.parseInt(csv.GetRowCol(i, COL_DISTANCE_RAN)));
			hs.setEnemiesKilled(Integer.parseInt(csv.GetRowCol(i, COL_ENEMIES_KILLED)));
			hs.setObstaclesAvoided(Integer.parseInt(csv.GetRowCol(i, COL_OBSTACLES_AVOIDED)));
			
			// Need to convert the date
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				hs.setRunDate(df.parse(csv.GetRowCol(i, COL_RUN_DATE)));
			} catch (ParseException e) {
				e.printStackTrace();
			} // end catch
			
			m_highScores.add(hs);
		} // end for
		
		System.out.println("Filled High Scores");
		System.out.println("Rows: " + m_highScores.size());
	} // end Fill
	
} // end HighScoreList
