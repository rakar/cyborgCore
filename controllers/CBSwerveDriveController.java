package org.montclairrobotics.cyborg.core.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.assemblies.CBSwerveDriveModule;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;

public class CBSwerveDriveController extends CBDriveController {

    CBStdDriveControlData dcd;

    public CBSwerveDriveController(Cyborg robot, CBStdDriveControlData controlData) {
        super(robot);
        //setControlData(Cyborg.controlData.driveData);
        dcd = controlData;
        System.err.println("Warning: CBSwerveDriveController implementation is highly experimental.");
    }

    /*
    public CBSwerveDriveController setControlData(CBDriveControlData data) {
        if (data instanceof CBStdDriveControlData) {
            CBStdDriveControlData dcd = (CBStdDriveControlData) data;
        } else {
            throw new RuntimeException("Error: Invalid DriveControlStatus for CBSwerveDriveController");
        }
        return this;
    }
    */

    @Override
    public void init() {

    }

    @Override
    public void update() {
        if (dcd.active) {
            for (CBDriveModule dm : driveModules) {
                updateModule(
                        (CBSwerveDriveModule) dm, dcd.direction, dcd.rotation,
                        dcd.orbitOffset, dcd.steerOnly
                );
            }
        }
    }

    protected void updateModule(CBSwerveDriveModule module, CB2DVector direction, double rotation,
                                CB2DVector orbitOffset, boolean steerOnly) {

        // Calculate the effective offset
        // based on the offset from standard center
        // and the orbit offset
        CB2DVector effectiveOffset = new CB2DVector(module.getPosition());
        effectiveOffset = effectiveOffset.sub(orbitOffset);

        // Calculate the target position for this module,
        // in other words calculate the position this module should be at by the next cycle
        // Rotate the module position by the rotation amount
        // Translate component
        // Calculate the net change from the original effective location
        CB2DVector Diff = effectiveOffset
                .scaledRotate(rotation, controlPeriod)
                .scaledTranslate(direction, controlPeriod)
                .sub(effectiveOffset);

        // Given the desired net change in module position calculate the angle and speed
        // the wheel should use to go there.
        double angle = Diff.getAngleDeg();
        double speed = 0.0;
        if (!steerOnly) {
            speed = Diff.getMag();
        }

        module.update(angle, speed);
    }

    public CBSwerveDriveController addDriveModule(CBDriveModule driveModule) {
        return (CBSwerveDriveController) super.addDriveModule(driveModule);
    }

    @Override
    public CBSwerveDriveController setControlPeriod(double controlPeriod) {
        return (CBSwerveDriveController) super.setControlPeriod(controlPeriod);
    }
}
