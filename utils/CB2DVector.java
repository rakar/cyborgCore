package org.montclairrobotics.cyborg.core.utils;

/**
 * Generalized 2D Vector
 * Includes Cartesian and Polar conversions and utilities  
 */
public class CB2DVector {
	
	private double x,y;
	private double angle,mag;
	
	public CB2DVector() {
		
	}
	
	public CB2DVector( double x, double y) {
		setXY(x,y);
	}
	
	public CB2DVector( CB2DVector v ) {
		setXY(v.getX(), v.getY());
	}

	/**
	 * Sets the DirectionVector based on Cartesian coordinates 
	 * 
	 * @param x left(-)/right(+) direction
	 * @param y forward(+)/back(-) direction
	 * @return  current object
	 */
	public CB2DVector setXY(double x, double y) {
		this.x = x;
		this.y = y;
		// angle is based on 0 degrees being "forward" 
		// and + is counterclockwise.
		this.angle = Math.atan2(-x, y)*180.0/Math.PI;
		this.mag =  Math.sqrt(x*x+y*y);
		return this;
	}
		
	/**
	 * Returns the left(-)/right(+) direction of the Cartesian coordinates 
	 * 
	 * @return x left(-)/right(+) direction
	 * 
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the forward(+)/back(-) direction of the Cartesian coordinates 
	 * 
	 * @return y forward(+)/back(-) direction
	 * 
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the angle of the direction vector 
	 * 
	 * @return angle in degrees (0 = forward)
	 * 
	 */
	public double getAngleDeg() {
		return angle;
	}
	
	/**
	 * Returns the angle of the direction vector 
	 * 
	 * @return angle in radians (0 = forward)
	 * 
	 */
	public double getAngleRad() {
		return angle*Math.PI/180.0;
	}
	
	/**
	 * Returns the magnitude of the direction vector 
	 * 
	 * @return magnitude of direction vector
	 * 
	 */
	public double getMag() {
		return mag;
	}

	public CB2DVector copy(CB2DVector src) {
		return this.setXY(src.getX(), src.getY()); // , src.getRotDeg());
	}
	
	public CB2DVector copy() {
		return new CB2DVector(x,y);
	}
	
	public CB2DVector rotate(double degrees) {
		double radians = Math.PI*degrees/180;
		double xp = x*Math.cos(radians) - y*Math.sin(radians);
		double yp = y*Math.cos(radians) + x*Math.sin(radians);
		return new CB2DVector(xp,yp);
	}
	
	public CB2DVector translate(CB2DVector translation) {
		double xp = x+translation.x;
		double yp = y+translation.y;
		return new CB2DVector(xp,yp);
	}
	
	public CB2DVector scaledRotate(double degrees, double scale) {
		double radians = (Math.PI*degrees/180.0)*scale;
		double xp = x*Math.cos(radians) - y*Math.sin(radians);
		double yp = y*Math.cos(radians) + x*Math.sin(radians);
		//SmartDashboard.putString("rotate", xp +":"+yp);
		return new CB2DVector(xp,yp);
	}
	
	public CB2DVector scaledTranslate(CB2DVector translation, double scale) {
		double xp = x+translation.x*scale;
		double yp = y+translation.y*scale;
		return new CB2DVector(xp,yp);
	}
	
	public CB2DVector add(CB2DVector vector) {
		double xp = x+vector.x;
		double yp = y+vector.y;
		return new CB2DVector(xp,yp);
	}
	
	public CB2DVector sub(CB2DVector vector) {
		double xp = x-vector.x;
		double yp = y-vector.y;
		return new CB2DVector(xp,yp);
	}
	
	public double dot(CB2DVector vector) {
		return x*vector.x + y*vector.y;
	}
}
