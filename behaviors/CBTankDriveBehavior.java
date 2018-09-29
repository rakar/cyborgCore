package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBTankDriveRequestData;

@Deprecated
public class CBTankDriveBehavior extends CBBehavior {

	CBTankDriveRequestData drd;
	CBDifferentialDriveControlData dcd;


	public CBTankDriveBehavior(Cyborg robot, CBTankDriveRequestData requestData, CBDifferentialDriveControlData controlData) {
		super(robot);
        drd = requestData;
        dcd = controlData;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		// Copy simple Tank drive command info
		dcd.leftPower = drd.leftPower;
		dcd.rightPower = drd.rightPower;
		dcd.active = drd.active;
		
		//
		// Turn off request.active to indicate that command was handled. 
		// This will prevent re-processing a given request. For example
		// Autonomous may only issue drive requests periodically.
		//  
		drd.active = false;
	}
}
