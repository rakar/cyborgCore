package org.montclairrobotics.cyborg.core.utils;

public class CBTarget2D extends CBEdgeTrigger {
	double xTarget;
	double yTarget;
	double xRange;
	double yRange;
	double xPosition;
	double yPosition;
	boolean active;

	public CBTarget2D() {
		xPosition = 0;
		yPosition = 0;
	}

	public CBTarget2D setTarget(double xTarget, double yTarget, double xRange, double yRange) {
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		this.xRange = xRange;
		this.yRange = yRange;
		return this;
	}

	public CBTarget2D setActive(boolean active) {
		this.active = active;
		return this;
	}

	public boolean isActive() {
		return active;
	}
	
	public CBTarget2D setPosition(double x, double y){
		xPosition = x;
		yPosition = y;
		return this;
	}
	
	public CBTarget2D update(double x, double y) {
		xPosition = x;
		yPosition = y;
		super.update(active && (Math.abs(xTarget-xPosition)<xRange
				&& Math.abs(yTarget-yPosition)<yRange));
		return this;
	}
	
	public double getXPosition() {
		return xPosition;
	}
	
	public double getYPosition() {
		return yPosition;
	}

    public boolean isOnTarget(double value) {
        return (active
                && value>=(xTarget-xRange) && value<=(xTarget+xRange)
                && value>=(yTarget-yRange) && value<=(yTarget+yRange));
    }

}
