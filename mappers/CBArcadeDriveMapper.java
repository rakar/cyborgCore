package org.montclairrobotics.cyborg.core.mappers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBJoystickIndex;

public class CBArcadeDriveMapper extends CBTeleOpMapper {
	private CBAxis fwdAxis, strAxis, rotAxis;
	private CBButton gyroLock; 
	private double  xScale, yScale, rScale;
	private CBStdDriveRequestData drd;

	public CBArcadeDriveMapper(Cyborg robot, CBStdDriveRequestData requestData) {
		super(robot);
		//setRequestData(Cyborg.requestData.driveData);
		drd = requestData;
	}

	public CBArcadeDriveMapper setAxes(CBDeviceID fwdDeviceID, CBDeviceID strDeviceID, CBDeviceID rotDeviceID) {
		// Undefined axes will return 0 deflection. ("InitHeavy/RunLight")
		fwdAxis = Cyborg.hardwareAdapter.getDefaultedAxis(fwdDeviceID);
		strAxis = Cyborg.hardwareAdapter.getDefaultedAxis(strDeviceID);
		rotAxis = Cyborg.hardwareAdapter.getDefaultedAxis(rotDeviceID);
		
		// Force gyroLock to undefined even though we may set it later ("InitHeavy/RunLight")
		if(gyroLock==null) {
			gyroLock = new CBButton(CBJoystickIndex.undefined());
		}

		// Set default scale
		xScale = 1;
		yScale = 1; 
		rScale = 1;
		
		return this;
	}

	/*
	public CBArcadeDriveMapper setRequestData(CBDriveRequestData data) {
		drd = data;
		return this;
	}
	*/

	public CBArcadeDriveMapper setGyroLockButton(CBDeviceID buttonDeviceID) {
		this.gyroLock = Cyborg.hardwareAdapter.getDefaultedButton(buttonDeviceID);
		return this;
	}

	public CBArcadeDriveMapper setAxisScales(double xScale, double yScale, double rScale) {
		this.xScale = xScale;
		this.yScale = yScale;
		this.rScale = rScale;
		return this;
	}

	@Override
	public void update() {
        CBStdDriveRequestData drd = (CBStdDriveRequestData) this.drd;
        drd.active = true;
        drd.direction.setXY(xScale * strAxis.get(), yScale * fwdAxis.get());
        drd.rotation = rScale * rotAxis.get();
        drd.gyroLockActive = gyroLock.getState();
        SmartDashboard.putNumber("Mapper speed:", drd.direction.getY());
    }
}
