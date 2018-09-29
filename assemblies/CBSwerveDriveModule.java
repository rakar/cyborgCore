package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.core.utils.CB2DVector;

public class CBSwerveDriveModule extends CBDriveModule {
	boolean driveReversed;
	double minAngle, maxAngle;

	public CBSwerveDriveModule() {
		// TODO Auto-generated constructor stub
		System.err.println("Warning: CBSwerveDriveModule implementation is highly experimental.");
	}

	public CBSwerveDriveModule(CB2DVector position, double orientation) {
		super(position, orientation);
	}
	
	@Override
	public CBDriveModule update(double target) {
		//System.out.println("Error: update(double taget) not valid for CBSwerveModule.");
		throw new RuntimeException("Error: update(double taget) not valid for CBSwerveModule.");
		//return this;
	}
	
	private double shortAngle(double angle) {
		return ((angle+180.0)%360.0)-180.0;
	}
	
	/**
	 * Update the module with current control info.
	 * This routine is responsible for performing angle 
	 * optimization re: closest half-turn with reverse.
	 * @param targetAngle
	 * @param targetSpeed
	 * @return this module
	 */
	public CBDriveModule update(double targetAngle, double targetSpeed) {
		CBSpeedControllerArrayController angleController = controllerArrays.get(0);
		CBSpeedControllerArrayController driveController = controllerArrays.get(1);
		double flip = 1.0;

		double currentAngle = angleController.get();
		double rem = shortAngle(currentAngle);
		
		targetAngle = shortAngle(targetAngle);		
		
		double diffAngle = shortAngle(rem-targetAngle);
		if(diffAngle<-90) {
			targetAngle+=180.0;
			flip = -1.0;
		} else if (diffAngle>90) {
			targetAngle-=180.0;
			flip = -1.0;
		}
		diffAngle = shortAngle(rem-targetAngle);
		
		targetAngle = currentAngle-diffAngle;
		targetSpeed *= flip;
		
		angleController.update(targetAngle);
		driveController.update(targetSpeed);
		return this;
	}
	
	public CBDriveModule setAngleLimits(double minAngle, double maxAngle) {
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		return this;
	}

	public double getAngle() {
		return controllerArrays.get(0).get();
	}
	
}
