package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBDriveRequestData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.core.data.CBTankDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;

public class CBTankDriveMapper extends CBTeleOpMapper {
	private CBAxis left;
	private CBAxis right;
	private double deadzone = 0.0;
	private CBButton gyroLock=null;
	private CBStdDriveRequestData sdrd;
	private CBTankDriveRequestData tdrd;
	

	public CBTankDriveMapper(Cyborg robot, CBDriveRequestData requestData, CBDeviceID leftDeviceID, CBDeviceID rightDeviceID) {
		super(robot);
		this.left = Cyborg.hardwareAdapter.getAxis(leftDeviceID);
		this.right = Cyborg.hardwareAdapter.getAxis(rightDeviceID);
		setRequestData(requestData);
	}

	private CBTankDriveMapper setRequestData(CBDriveRequestData data) {
	    sdrd = null;
	    tdrd = null;
		if(data instanceof CBStdDriveRequestData ) {
			sdrd = (CBStdDriveRequestData)data;
		} else if (data instanceof CBTankDriveRequestData) {
			tdrd = (CBTankDriveRequestData)data;
		} else {
			data.active = false; // If we don't know what type of request it is shut down drive
			throw new RuntimeException("Unknown driveRequestData type in CBTankDriveMapper.");
		}
		return this;
	}

	public CBTankDriveMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	public CBTankDriveMapper setGyroLockButton(CBDeviceID buttonID) {
		this.gyroLock = Cyborg.hardwareAdapter.getButton(buttonID);
		return this;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		double leftStick  = 0; // y-axis of first stick
		double rightStick = 0; // y-axis of second stick;
		
		if(left!=null && left.isDefined()) leftStick = -left.get();
		if(right!=null && right.isDefined()) rightStick = right.get();

		// Implement dead zone
		if(Math.abs( leftStick)<deadzone)  leftStick=0.0;
		if(Math.abs(rightStick)<deadzone) rightStick=0.0;
		
		if(sdrd!=null) {
			double velocity = (leftStick+rightStick)/2.0;// Average stick value "forward"
			double rotation = leftStick - velocity;

			sdrd.active = true;
			sdrd.direction.setXY(0, velocity);
			sdrd.rotation = rotation;

			if(gyroLock!=null && gyroLock.isDefined()) {
				sdrd.gyroLockActive = gyroLock.getState();
			}
		} else if (tdrd!=null) {
			tdrd.leftPower = leftStick;
			tdrd.rightPower = rightStick;
			tdrd.active = true;
		}
	}
}
