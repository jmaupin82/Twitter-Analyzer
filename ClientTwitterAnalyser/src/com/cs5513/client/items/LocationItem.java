package com.cs5513.client.items;

/**
 * 
 * @author Mikaël Perrin
 * Class modelizing the position for events, tweet, etc
 */
public class LocationItem {


	/**
	 * The latitude of this position 
	 */
	private double latitude;
	
	/**
	 * The longuitude of this position.
	 */
	private double longitude;
	
	/**
	 * Position constructor
	 * @param long the position longitude
	 * @param lat the position latitude
	 */
	public LocationItem(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLat() {
		return latitude;
	}
	
	public void setLat(double lat) {
		this.latitude = lat;
	}
	
	public double getLong() {
		return longitude;
	}
	
	public void setLong(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("latitude:");
		sb.append(latitude);
		sb.append(" longitude:");
		sb.append(longitude);
		return sb.toString();
	}
	
}