package org.as.scad.dto;

public class Report {
	
	private int x;
	private int y;
	private String direction;

	public Report() {
	}

	public Report( int x, int y, String direction ) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}

	public void setX( int x ) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY( int y ) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection( String direction ) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ") dirección " + direction;
	}

	@Override
	public boolean equals( Object obj ) {
		if( !( obj instanceof Report ) ) return false;
		Report objReport = ( Report ) obj;
		return ( objReport.getX() == this.x
				&& objReport.getY() == this.y
				&& objReport.direction.equals( this.direction ));
	}

}
