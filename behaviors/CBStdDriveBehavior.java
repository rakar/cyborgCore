package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;
import org.montclairrobotics.cyborg.core.utils.CBErrorCorrection;

public class CBStdDriveBehavior extends CBBehavior {
    CBEdgeTrigger gyroLockState;
    CBErrorCorrection gyroLockTracker = null;

    protected CBStdDriveRequestData drd;
    protected CBStdDriveControlData dcd;

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
    public CBStdDriveBehavior setDebug(boolean debug) {
        super.setDebug(debug);
        return this;
    }

    @Override
    public void update() {
        super.update();

        dcd.active = drd.active;
        if (dcd.active) {
            dcd.direction.copy(drd.direction);
            dcd.rotation = drd.rotation;
            dcd.shiftToHighGear = drd.shiftToHighGear;
            dcd.shiftToLowGear = drd.shiftToLowGear;

            gyroLockState.update(drd.gyroLockActive);
            if (gyroLockTracker != null) {
                if (gyroLockState.getRisingEdge()) gyroLockTracker.setTarget(drd.gyroLockValue);
                if (gyroLockState.getState()) dcd.rotation = gyroLockTracker.update(drd.gyroLockValue);
            }

            dcd.motorControlMode = drd.motorControlMode;

            //
            // Turn off request.active to indicate that command was handled.
            // This will prevent re-processing a given request. For example
            // Autonomous may only issue drive requests periodically.
            //
            drd.active = false;
        }

        if(debug) {
            robot.logMessage("CBStdDriveBehavior: dcd active: "+Boolean.toString(dcd.active));
            robot.logMessage("CBStdDriveBehavior: dcd data (dy,dx,r): " + Double.toString(dcd.direction.getY()) + ":" + Double.toString(dcd.direction.getX()) + ":" + Double.toString(dcd.rotation));
        }
    }
}
