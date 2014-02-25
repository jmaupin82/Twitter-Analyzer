package com.cs5513.entities;

/**
 * 
 * @author Mikaël Perrin
 * Class modelizing the position for events, tweet, etc
 */
public class Position {


	/**
	 * The latitude of this position 
	 */
	private float latitude;
	
	/**
	 * The longuitude of this position.
	 */
	private float longitude;
	
	/**
	 * Position constructor
	 * @param long the position longitude
	 * @param lat the position latitude
	 */
	public Position(float latitude, float longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Default constructor
	 */
	public Position()
	{
		latitude = 0;
		longitude = 0;
	}
	
	public float getLat() {
		return latitude;
	}
	
	public void setLat(float lat) {
		this.latitude = lat;
	}
	
	public float getLong() {
		return longitude;
	}
	
	public void setLong(float longitude) {
		this.longitude = longitude;
	}
	
}