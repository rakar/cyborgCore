package org.montclairrobotics.cyborg.core.mappers;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.*;
//import org.montclairrobotics.cyborg.devices.CBButtonRef;


public class CBArcadeDriveMapper extends CBTeleOpMapper {
	private CBAxis fwdAxis, strAxis, rotAxis;
	private CBButton gyroLock; 
	private double strScale, fwdScale, rotScale;
	private CBStdDriveRequestData drd;
	private boolean debug;

	public CBArcadeDriveMapper(Cyborg robot, CBStdDriveRequestData requestData) {
		super(robot);
		drd = requestData;
		debug=false;
	}

	public CBArcadeDriveMapper setAxes(CBDeviceID fwdDeviceID, CBDeviceID strDeviceID, CBDeviceID rotDeviceID) {
		// Undefined axes will return 0 deflection. ("InitHeavy/RunLight")
		fwdAxis = Cyborg.hardwareAdapter.getDefaultedAxis(fwdDeviceID);
		strAxis = Cyborg.hardwareAdapter.getDefaultedAxis(strDeviceID);
		rotAxis = Cyborg.hardwareAdapter.getDefaultedAxis(rotDeviceID);
		
		// Force gyroLock to undefined even though we may set it later ("InitHeavy/RunLight")
		if(gyroLock==null) {
			gyroLock = new CBButton(CBButtonRef.undefined());
		}

		// Set default scale
		fwdScale = -1;
		strScale = +1;
		rotScale = -1;
		
		return this;
	}

	public CBArcadeDriveMapper setDebug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public CBArcadeDriveMapper setGyroLockButton(CBDeviceID buttonDeviceID) {
		this.gyroLock = Cyborg.hardwareAdapter.getDefaultedButton(buttonDeviceID);
		return this;
	}

	public CBArcadeDriveMapper setAxisScales(double fwdScale, double strScale, double rotScale) {
		this.strScale = strScale;
		this.fwdScale = fwdScale;
		this.rotScale = rotScale;
		return this;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
        CBStdDriveRequestData drd = (CBStdDriveRequestData) this.drd;
        drd.active = true;
        drd.direction.setXY(strScale * strAxis.get(), fwdScale * fwdAxis.get());
        drd.rotation = rotScale * rotAxis.get();
        drd.gyroLockActive = gyroLock.getState();

        if(debug) {
        	robot.logMessage("Raw Axes (f,s,r): "+Double.toString(fwdAxis.get())+":"+Double.toString(strAxis.get())+":"+Double.toString(rotAxis.get()));
        	robot.logMessage("drd data (dy,dx,r): "+Double.toString(drd.direction.getY())+":"+Double.toString(drd.direction.getX())+":"+Double.toString(drd.rotation));
		}
    }
}
