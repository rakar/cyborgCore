package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.core.utils.CBEnums;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBSmartSpeedController;
import org.montclairrobotics.cyborg.devices.CBSpeedController;

/**
 * Controls an array of speed controllers. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered
 * to be the lead controller and all others will be considered followers.
 *
 * @author rich
 */
public class CBSmartSpeedControllerArray extends CBSpeedControllerArray {


    @Override
    public CBSmartSpeedControllerArray addSpeedController(CBDeviceID controllerId) {
        super.addSpeedController(controllerId);
        return this;
    }

    @Override
    public CBSmartSpeedControllerArray setMotorControlMode(CBEnums.CBMotorControlMode motorControlMode) {
        super.setMotorControlMode(motorControlMode);
        for (CBSpeedController sc : speedControllers) {
            ((CBSmartSpeedController) sc).setControlMode(motorControlMode);
        }
        return this;
    }

    @Override
    public CBSmartSpeedControllerArray update(double target) {
        return update(target, motorControlMode);
    }

    public CBSmartSpeedControllerArray update(double target, CBEnums.CBMotorControlMode motorControlMode) {
        this.motorControlMode = motorControlMode;
        for (CBSpeedController sc : speedControllers) {
            double v = target * direction;
            ((CBSmartSpeedController) sc).set(v, motorControlMode);
        }
        return this;
    }

    public double get() {
        return encoder.get();
    }
}

