package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBErrorCorrection.CBOnTargetMode;
import org.montclairrobotics.cyborg.devices.CBSpeedController;

/**
 * Controls an array of speed controllers. If more than one speed controller
 * is used in an Advanced mode, then the first controller is considered
 * to be the lead controller and all others will be considered followers.
 *
 * @author rich
 */
public class CBSimpleSpeedControllerArray extends CBSpeedControllerArray {
    double currentPower;

    public CBSimpleSpeedControllerArray() {
    }

    /* (non-Javadoc)
     * @see org.montclairrobotics.cyborg.devices.CBSpeedControllerArray#update(double)
     */
    @Override
    public CBSpeedControllerArray update(double target) {
        double outputValue = 0;
        //Cyborg.hardwareAdapter.robot.logMessage("in speed controller array update " + driveMode.name());

        switch (driveMode) {
            case Power:
                outputValue = target;
                break;
            case Speed:
                if (errorCorrection == null || encoder == null) {
                    //System.out.println("Error: Drive mode=Speed, but CBErrorCorrection or CBEncoder not set.");
                    throw new RuntimeException("Error: Drive mode=Speed, but CBErrorCorrection or CBEncoder not set.");
                    //outputValue = 0;
                } else {
                    double encoderRate = encoder.getRate();
                    if (errorCorrection.getOnTargetMode() == CBOnTargetMode.HoldValue) {
                        currentPower += errorCorrection.setTarget(target).update(encoderRate);
                    } else {
                        currentPower = errorCorrection.setTarget(target).update(encoderRate);
                    }
                    outputValue = currentPower;
                }
                break;
            case Position:
                if (errorCorrection == null || encoder == null) {
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

        for (CBSpeedController sc : speedControllers) {
            double v = outputValue * direction;
            sc.set(v);
        }
        return this;
    }

    public double get() {
        return encoder.get();
    }


}
