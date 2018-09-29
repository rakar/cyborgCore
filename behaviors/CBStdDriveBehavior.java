package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;
import org.montclairrobotics.cyborg.core.utils.CBErrorCorrection;

public class CBStdDriveBehavior extends CBBehavior {
	CBEdgeTrigger gyroLockState;
	CBErrorCorrection gyroLockTracker=null;

	CBStdDriveRequestData drd;
	CBStdDriveControlData dcd;

	public CBStdDriveBehavior(Cyborg robot, CBStdDriveRequestData requestData, CBStdDriveControlData controlData) {
		super(robot);
		drd = requestData;
		dcd = controlData;
		gyroLockState = new CBEdgeTrigger();
	}

	public CBStdDriveBehavior setGyroLockTracker(CBErrorCorrection pid) {
		this.gyroLockTracker = pid;
		return this;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		dcd.active = drd.active;

		if(dcd.active) {
			dcd.direction.copy(drd.direction);
			dcd.rotation = drd.rotation;

			gyroLockState.update(drd.gyroLockActive);
			if(gyroLockTracker!=null) {
				if(gyroLockState.getRisingEdge()) gyroLockTracker.setTarget(drd.gyroLockValue);
				if(gyroLockState.getState()) dcd.rotation = gyroLockTracker.update(drd.gyroLockValue);
			}

			//
			// Turn off request.active to indicate that command was handled. 
			// This will prevent re-processing a given request. For example
			// Autonomous may only issue drive requests periodically.
			//  
			drd.active = false;
		}
	}
}
