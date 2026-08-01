package edu.jhu.mrm.model;

/**
 * Representation of a location with a building number and apartment number.
 * 
 * @author Cory Drangel and Matthew Kim
 */
public class Location {
	private Integer building;
	
	private Integer apartmentNum;
	
	public Location() {
		
	}

	public Integer getBuilding() {
		return building;
	}

	public void setBuilding(Integer building) {
		this.building = building;
	}

	public Integer getApartmentNum() {
		return apartmentNum;
	}

	public void setApartmentNum(Integer apartmentNum) {
		this.apartmentNum = apartmentNum;
	}
	
}