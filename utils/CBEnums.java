package org.montclairrobotics.cyborg.core.utils;

public class CBEnums {

	/**
	 * DriveMode:
	 * {@link #Power}
	 * {@link #Speed}
	 * {@link #Conflict}
	 */
	public enum CBDriveMode { 
		/**
		 * Power: -1 <= raw power <= 1
		 */
		Power, 
		/**
		 * Speed: units/second (direction) degrees/second (rotation)
		 */
		Speed,
		/**
		 * Position: position in standard units 
		 */
		Position,
		/**
		 * Conflict: indicates an error due to mixed speed controller modes
		 */
		Conflict
		};
	
	/**
	 * SpeedControllerScheme:
	 * {@link #Basic}
	 * {@link #Advanced}
	 */
	public enum CBSpeedControllerScheme {
		/**
		 * Basic: Simple controllers used without internal intelligence
		 */
		Basic,		
		/**
		 * Advanced: Make use of SRX style control by off-loading correction to speed controller 
		 */
		Advanced	
		};

	/**
	 * CBEncoderScheme:
	 * {@link #None}
	 * {@link #RoboRio}
	 * {@link #SpeedController}
	 */
	public enum CBEncoderScheme {
		/*
		 * None: No encoder used
		 */
		None,
		/**
		 * RoboRio: Encoder wired directly to roboRio
		 */
		RoboRio,
		/**
		 * SpeedController: Encoder wired to an SRX style controller 
		 */
		SpeedController
		};

	public CBEnums() {
	}

}
