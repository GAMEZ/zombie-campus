package com.gamez.zombiecampus;

/**
 * Represents a building object.
 * @author Greg Breard
 */
public class Building {
	
	// Stores the object data
	private Integer m_id;
	private Integer m_campusId;
	private String m_buildingName;
	private String m_image;
	
	/**
	 * Initializes a new instance of a Building object.
	 */
	public Building() {
		// Default constructor
	} // end constructor

	public String getBuildingName() {
		return m_buildingName;
	} // end getBuildingName
	
	public Integer getCampusId() {
		return m_campusId;
	} // end getCampusId
	
	public Integer getId() {
		return m_id;
	} // end getId
	
	public String getImage() {
		return m_image;
	} // end getImage
	
	public void setBuildingName(String buildingName) {
		this.m_buildingName = buildingName;
	} // end setBuildingName
	
	public void setCampusId(Integer campusId) {
		this.m_campusId = campusId;
	} // end setCampusId
	
	public void setId(Integer id) {
		this.m_id = id;
	} // end setId
	
	public void setImage(String image) {
		this.m_image = image;
	} // end setImage
	
} // end Building
