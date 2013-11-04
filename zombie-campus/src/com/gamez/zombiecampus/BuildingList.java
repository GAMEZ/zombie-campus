package com.gamez.zombiecampus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;

/**
 * Represents the list of buildings.
 * @author Greg Breard
 */
public class BuildingList {
	
	// Constants for column names
	private final String COL_ID = "id";
	private final String COL_CAMPUS_ID = "campusId";
	private final String COL_BUILDING_NAME = "buildingName";
	private final String COL_IMAGE = "image";
	private final String COL_BUILDING_1 = "buildingId_1";
	private final String COL_BUILDING_2 = "buildingId_2";

	// Stores the building elements
	private HashMap<Integer, Building> m_buildings = new HashMap<Integer, Building>();
	private HashMap<Integer, ArrayList<Integer>> m_buildingRelations = new HashMap<Integer, ArrayList<Integer>>();
	
	// Stores the current building
	private Integer m_currentBuilding = 0;
	
	/**
	 * Initializes a new instance of a HighScoreList object.
	 */
	public BuildingList() {
		// Default constructor
	} // end constructor

	/**
	 * Fills the buildings.
	 */
	public void Fill(){
		// Get the data
		CSV csv = new CSV(Gdx.files.local(Assets.BUILDINGS_DB));
		
		// Get the rows
		for (int i = 1; i <= csv.GetRowCount(); i++) {
			// Create the building object for the row data
			Building b = new Building();
			b.setId(Integer.parseInt(csv.GetRowCol(i, COL_ID)));
			b.setCampusId(Integer.parseInt(csv.GetRowCol(i, COL_CAMPUS_ID)));
			b.setBuildingName(csv.GetRowCol(i, COL_BUILDING_NAME));
			b.setImage(csv.GetRowCol(i, COL_IMAGE));
			
			m_buildings.put(b.getId(), b);
		} // end for

		System.out.println("Filled Buildings");
		System.out.println("Rows: " + m_buildings.size());
		
		// Now fill the relations
		fillRelations();
	} // end Fill
	
	/**
	 * Fills the building relations.
	 */
	private void fillRelations(){
		// Get the data
		CSV csv = new CSV(Gdx.files.local(Assets.BUILDINGS_RELATION_DB));
		
		// Get the rows
		for (int i = 1; i <= csv.GetRowCount(); i++) {
			// Parse the data
			Integer building1 = Integer.parseInt(csv.GetRowCol(i, COL_BUILDING_1));
			Integer building2 = Integer.parseInt(csv.GetRowCol(i, COL_BUILDING_2));
			
			// Initialize the relation lists
			ArrayList<Integer> relation1;
			ArrayList<Integer> relation2;
			
			// Check if the first relation exists
			if (m_buildingRelations.containsKey(building1))
				relation1 = m_buildingRelations.get(building1);
			else
				relation1 = new ArrayList<Integer>();
			
			// Add building 2 to the list
			relation1.add(building2);
			
			// Check if the second relation exists
			if (m_buildingRelations.containsKey(building2))
				relation2 = m_buildingRelations.get(building2);
			else
				relation2 = new ArrayList<Integer>();
			
			// Add building 1 to the list
			relation2.add(building1);
			
			// Store the relations
			m_buildingRelations.put(building1, relation1);
			m_buildingRelations.put(building2, relation2);
		} // end for

		System.out.println("Filled Buildings Relations");
		System.out.println("Rows: " + m_buildingRelations.size());		
	} // end fillRelations
	
	/**
	 * Returns a building picked from the buildings related to the current building
	 * (if available), otherwise picks a random building from the building list.
	 * @return Building from the list.
	 */
	public Building GetNextBuilding() {
		// See if there is a current building
		if (m_currentBuilding == 0)
			return getFirstBuilding();
		
		// Check if the building has relations
		if (m_buildingRelations.containsKey(m_currentBuilding)) {
			// Get the ids
			ArrayList<Integer> relation = m_buildingRelations.get(m_currentBuilding);
			
			// Pick a random building to go to next with
			Random rand = new Random();
			int i = rand.nextInt(relation.size());
			
			System.out.println("picked " + relation.get(i));
			
			// Store the current id
			m_currentBuilding = relation.get(i);
			
			// Return the building
			return m_buildings.get(m_currentBuilding);
		} else 
			// Just choose random
			return getFirstBuilding();
	} // end GetNextBuilding
		
	/**
	 * Returns a randomly picked building from the building list.
	 * @return Random Building from the list.
	 */
	private Building getFirstBuilding () {
		// Get the ids
		Integer[] buildings = m_buildings.keySet().toArray(new Integer[0]);
		
		// Pick a random building to start with
		Random rand = new Random();
		int i = rand.nextInt(buildings.length) + 1; // Can't have 0, so need to add 1
		
		System.out.println("random " + i);
		
		// Store the current id
		m_currentBuilding = i;
		
		// Return the building
		return m_buildings.get(m_currentBuilding);
	} // end getFirstBuilding
	
} // end BuildingList
