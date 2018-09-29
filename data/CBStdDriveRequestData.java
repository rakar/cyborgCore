package org.montclairrobotics.cyborg.core.data;

import org.montclairrobotics.cyborg.core.utils.CB2DVector;

public class CBStdDriveRequestData extends CBDriveRequestData {
	
	/**
	 * Drive direction and magnitude for the robot. For 
	 * drive trains that do not allow strafing, the x
	 * component should be 0, positive y is forward and
	 * positive x is strafe left. 
	 */
	public CB2DVector direction = new CB2DVector();
	
	/**
	 * Drive rotation magnitude. Positive values rotate
	 * counter-clockwise. The unit depends on drive train
	 * mode. For power mode, the unit is arbitrary. For
	 * speed mode, the default unit is degrees/second. 
	 */
	public double rotation;
	
	/**
	 * true - Indicates that rotation should be corrected using 
	 * the installed errorCorrection class and feedback from 
	 * the value in gyroLockSource. The error correction 
	 * control target is set on the rising edge of this value.
	 * 
	 * false - no correction is calculated or applied.
	 */
	public boolean gyroLockActive;
	
	/**
	 * Feedback value for rotation correction. This should be
	 * loaded by an appropriate sensor mapper.
	 */
	public double gyroLockValue;

	/**
	 * When using drive trains such as Swerve there are times when 
	 * there is a need to rotate the wheels to a particular
	 * vector without actually driving in that direction. 
	 * One example of this is when controlling the robot in 
	 * autonomous, it is more accurate to turn the wheels without 
	 * driving and then when the wheels have fully aligned
	 * (maxError gets below a certain
	 * threshold,) drive along that vector. In order to do this
	 * the vector needs to be requested, but no translation 
	 * is desired. This flag is used to suppress the speed
	 * output of the Swerve controller. 
	 */
	public boolean steerOnly; 
	
	public CB2DVector orbitOffset = new CB2DVector(); 
		
	// To turn off orbitMode, set offset to (0,0)
	// or simply never use it.
	// public boolean orbitMode;

}
