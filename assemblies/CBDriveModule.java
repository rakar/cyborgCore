package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums.CBDriveMode;

import java.util.ArrayList;

public class CBDriveModule {

	protected ArrayList<org.montclairrobotics.cyborg.core.assemblies.CBSpeedControllerArrayController> controllerArrays = new ArrayList<>();
	private CB2DVector position = new CB2DVector();
	private double orientation;
	private double orientationRadians;
	private CB2DVector orientationVector;
	private CBDriveMode driveMode=null;
	private org.montclairrobotics.cyborg.core.assemblies.CBSpeedControllerArrayController feedbackArray = null;
	
	public CBDriveModule() {
	}
	
	public CBDriveModule(CB2DVector position, double orientation) {
		setPlacement(position, orientation);
	}
	
	public CBDriveModule setPlacement(CB2DVector position, double orientation) {
		this.position = position;
		this.orientation = orientation;
		this.orientationRadians = Math.PI*orientation/180.0;
		this.orientationVector = new CB2DVector(-Math.sin(orientationRadians),Math.cos(orientationRadians));
		return this;
	}
	
	public CBDriveModule addSpeedControllerArray(org.montclairrobotics.cyborg.core.assemblies.CBSpeedControllerArrayController controllerArray) {
		if (driveMode==null) {
			driveMode = controllerArray.getDriveMode();
		} else {
			if (controllerArray.getDriveMode()!=driveMode) {
				driveMode = CBDriveMode.Conflict;
			}
		}
		controllerArrays.add(controllerArray);
		
		if(feedbackArray==null && controllerArray.encoder!=null) {
			feedbackArray = controllerArray;
		}		
		return this;
	}

	/**
	 * @return the controllerArrays
	 */
	public ArrayList<org.montclairrobotics.cyborg.core.assemblies.CBSpeedControllerArrayController> getControllerArrays() {
		return controllerArrays;
	}
	
	public CBDriveModule update(double target) {
		for(org.montclairrobotics.cyborg.core.assemblies.CBSpeedControllerArrayController c:controllerArrays) {
			c.update(target);
		}
		return this;
	}

	/**
	 * @return the position
	 */
	public CB2DVector getPosition() {
		return position.copy();
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * @return the orientation in radians
	 */
	public double getOrientationRadians() {
		return orientationRadians;
	}

	/**
	 * @return the orientation vector
	 */
	public CB2DVector getOrientationVector() {
		return orientationVector;
	}

	/**
	 * @return the driveMode
	 */
	public CBDriveMode getDriveMode() {
		return driveMode;
	}
	
	public boolean canProvideFeedback() {
		return feedbackArray!=null;
	}
	
	public double getFeedbackDistance() {
		return feedbackArray==null?0:feedbackArray.getFeedbackDistance();
	}
}
