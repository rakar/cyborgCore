package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.core.utils.CBErrorCorrection.CBOnTargetMode;

/**
 * Controls an array of speed controllers. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered 
 * to be the lead controller and all others will be considered followers.  
 * @author rich
 */
public class CBVictorArrayController extends CBSpeedControllerArrayController { 
	double currentPower;

	public CBVictorArrayController() {
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController#update(double)
	 */
	@Override
	public CBSpeedControllerArrayController update(double target) {
		double outputValue = 0;
		
		switch(driveMode) {
		case Power:
			outputValue = target;
			break;
		case Speed:
			if(errorCorrection==null || encoder==null){
				//System.out.println("Error: Drive mode=Speed, but CBErrorCorrection or CBEncoder not set.");
				throw new RuntimeException("Error: Drive mode=Speed, but CBErrorCorrection or CBEncoder not set.");
				//outputValue = 0;
			} else {
				double encoderRate = encoder.getRate();
				// rich - this looks backwards.
				if(errorCorrection.getOnTargetMode()==CBOnTargetMode.HoldValue) {
					currentPower = errorCorrection.setTarget(target).update(encoderRate);
				} else {
					currentPower += errorCorrection.setTarget(target).update(encoderRate);
				}
				outputValue = currentPower;
			}
			break;
		case Position:
			if(errorCorrection==null || encoder==null){
				//System.out.println("Error: Drive mode=Position, but CBErrorCorrection or CBEncoder not set.");
				throw new RuntimeException("Error: Drive mode=Position, but CBErrorCorrection or CBEncoder not set.");
				//target = 0;
			} else {
				outputValue = errorCorrection.setTarget(target).update(encoder.getDistance());
			}
			break;
		case Conflict:
			outputValue = 0;
			break;
		}

		for(CBSpeedController sc:speedControllers) sc.set(outputValue*direction);

		return this;
	}
	
	public double get() {
		return encoder.get();
	}
	
	
}
