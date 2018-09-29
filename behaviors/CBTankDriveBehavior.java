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
		//setRequestData((CBTankDriveRequestData)Cyborg.requestData.driveData);
		//setControlData((CBDifferentialDriveControlData)Cyborg.controlData.driveData);
	}

	/*
	public CBTankDriveBehavior setData(CBTankDriveRequestData requestData, CBDifferentialDriveControlData controlData) {
		drd = requestData;
		dcd = controlData;
		return this;
	}

	public CBTankDriveBehavior setRequestData(CBTankDriveRequestData data) {
		drd = data;
		return this;
	}

	public CBTankDriveBehavior setControlData(CBDifferentialDriveControlData data) {
		dcd = data;
		return this;
	}
	*/

	@Override
	public void update() {
		super.update();
		
		//CBTankDriveRequestData drd = (CBTankDriveRequestData)Cyborg.requestData.driveData;
		//CBDifferentialDriveControlData dcd = (CBDifferentialDriveControlData)Cyborg.controlData.driveData;
		
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
