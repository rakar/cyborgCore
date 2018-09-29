package org.montclairrobotics.cyborg.core.controllers;

//import java.time.Duration;
import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums.CBDriveMode;

public abstract class CBDriveController extends CBRobotController {

	protected ArrayList<CBDriveModule> driveModules = new ArrayList<>();
	protected CBDriveMode driveMode;
	protected double controlPeriod = 1/50.0;
	
	public class CBDriveFeedback {
		public CB2DVector translation;
		public double rotation;
		//public Duration timespan;
	}
	
	public interface CBDrivetrainFeedbackProvider {
		public boolean canProvideFeedback();
		public CBDriveFeedback getFeedback();
	}

	public CBDriveController(Cyborg robot) {
		super(robot);
	}

	public CBDriveController addDriveModule(CBDriveModule driveModule) {
		if(driveModules!=null) driveModules.add(driveModule);
		updateDriveMode(driveModule);
		return this;
	}
	
	/**
	 * @return the driveMode
	 */
	public CBDriveMode getDriveMode() {
		return driveMode;
	}

	protected void updateDriveMode(CBDriveModule driveModule) {
		if (driveMode==null) {
			driveMode = driveModule.getDriveMode();
		} else {
			if (driveModule.getDriveMode()!=driveMode) {
				driveMode = CBDriveMode.Conflict;
				throw new RuntimeException("Drivemode Conflict");
			}
		}
	}
	
	/* 
	 * The purpose of this function is to do "last minute" hardware configuration 
	 * required after construction. 
	 * configHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void configHardware() {
		
	}

	/**
	 * @return the controlPeriod
	 */
	public double getControlPeriod() {
		return controlPeriod;
	}

	/**
	 * @param controlPeriod the controlPeriod to set
	 */
	public CBDriveController setControlPeriod(double controlPeriod) {
		this.controlPeriod = controlPeriod;
		return this;
	}
	
	public boolean canProvideFeedback() {
		return false;
	}
	
}
