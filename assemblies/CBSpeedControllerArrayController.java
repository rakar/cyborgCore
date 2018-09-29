package org.montclairrobotics.cyborg.core.assemblies;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.core.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.core.utils.CBEnums.CBDriveMode;
import org.montclairrobotics.cyborg.core.utils.CBEnums.CBEncoderScheme;

/**
 * Base class for an array of speed controllers regulated as a unit with a
 * single feedback (encoder) device. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered
 * to be the lead controller and all others will be considered followers.
 * @author rich
 */

public abstract class CBSpeedControllerArrayController {
	protected ArrayList<CBSpeedController> speedControllers = new ArrayList<>();
	
	protected CBEncoder encoder = null;
	protected CBErrorCorrection errorCorrection = null;
	protected CBEncoderScheme encoderScheme = CBEncoderScheme.None;
	
	protected CBDriveMode driveMode = CBDriveMode.Power;

	protected boolean reversed=false;
	protected int direction=1;

	public CBSpeedControllerArrayController setErrorCorrection(CBErrorCorrection errorCorrection){
		this.errorCorrection = errorCorrection;
		return this;
	};

	public CBSpeedControllerArrayController setDriveMode(CBDriveMode driveMode) {
		this.driveMode = driveMode;
		return this;
	}
	
	public CBSpeedControllerArrayController setReversed(boolean reversed) {
		this.reversed = reversed;
		this.direction = reversed?-1:1;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#addSpeedController(org.montclairrobotics.cyborg.devices.CBSpeedController)
	 */
	public CBSpeedControllerArrayController addSpeedController(CBDeviceID controllerId) {
		speedControllers.add(Cyborg.hardwareAdapter.getSpeedController(controllerId));
		return this;
	}


	public abstract CBSpeedControllerArrayController update(double target);

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#getReversed()
	 */
	public boolean getReversed() {
		return reversed;
	}
	

	public CBSpeedControllerArrayController setEncoder(CBDeviceID encoderId) {
		this.encoder = Cyborg.hardwareAdapter.getEncoder(encoderId);
		return this;
	}
	
	public CBSpeedControllerArrayController setEncoderScheme(CBEncoderScheme encoderScheme) {
		this.encoderScheme = encoderScheme;
		return this;
	}

	public CBDriveMode getDriveMode() {
		return driveMode;
	}

	public abstract double get();

	public boolean canProvideFeedback() {
		return encoder!=null;
	}
	
	public double getFeedbackDistance() {
		return encoder==null?0:encoder.getDistance();
	}
}