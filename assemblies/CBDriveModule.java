package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums;

import java.util.ArrayList;

public class CBDriveModule {

    protected ArrayList<CBSpeedControllerArray> controllerArrays = new ArrayList<>();
    private CB2DVector position = new CB2DVector();
    private double orientation;
    private double orientationRadians;
    private double orientationCos;
    private CB2DVector orientationVector;
    private CBEnums.CBMotorControlMode motorControlMode = null;
    private CBSpeedControllerArray feedbackArray = null;

    public CBDriveModule() {
    }

    public CBDriveModule(CB2DVector position, double orientation) {
        setPlacement(position, orientation);
    }

    public CBDriveModule setPlacement(CB2DVector position, double orientation) {
        this.position = position;
        this.orientation = orientation;
        this.orientationRadians = Math.PI * orientation / 180.0;
        this.orientationCos = Math.cos(orientationRadians);
        this.orientationVector = new CB2DVector(-Math.sin(orientationRadians), Math.cos(orientationRadians));
        return this;
    }

    public CBDriveModule addSpeedControllerArray(CBSpeedControllerArray controllerArray) {
        //Cyborg.hardwareAdapter.robot.logMessage("drive module adding speed controller array");

        if (motorControlMode == null) {
            motorControlMode = controllerArray.getMotorControlMode();
        } else {
            if (controllerArray.getMotorControlMode() != motorControlMode) {
                motorControlMode = CBEnums.CBMotorControlMode.CONFLICT;
            }
        }
        controllerArrays.add(controllerArray);

        if (feedbackArray == null && controllerArray.encoder != null) {
            feedbackArray = controllerArray;
        }
        return this;
    }

    /**
     * @return the controllerArrays
     */
    public ArrayList<CBSpeedControllerArray> getControllerArrays() {
        return controllerArrays;
    }

    public CBDriveModule update(double target) {
        //Cyborg.hardwareAdapter.robot.logMessage("in drive module update");
        for (CBSpeedControllerArray c : controllerArrays) {
            //Cyborg.hardwareAdapter.robot.logMessage("Calling speed controller array update");
            c.update(target);
        }
        return this;
    }

    public CBDriveModule update(double target, CBEnums.CBMotorControlMode motorControlMode) {
        //Cyborg.hardwareAdapter.robot.logMessage("in drive module update");
        for (CBSpeedControllerArray c : controllerArrays) {
            //Cyborg.hardwareAdapter.robot.logMessage("Calling speed controller array update");
            if(c instanceof CBSmartSpeedControllerArray) {
                ((CBSmartSpeedControllerArray) c).update(target, motorControlMode);
            } else {
                c.update(target);
            }
        }
        return this;
    }

    /**
     * @return the position
     */
    public CB2DVector getPosition() {
        return position.copy();
    }

    /**
     * @return the orientation
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * @return the cosine of the orientation
     */
    public double getOrientationCos() {
        return orientationCos;
    }

    /**
     * @return the orientation in radians
     */
    public double getOrientationRadians() {
        return orientationRadians;
    }

    /**
     * @return the orientation vector
     */
    public CB2DVector getOrientationVector() {
        return orientationVector;
    }

    /**
     * @return the motorControlMode
     */
    public CBEnums.CBMotorControlMode getMotorControlMode() {
        return motorControlMode;
    }

    public boolean canProvideFeedback() {
        return feedbackArray != null;
    }

    public double getFeedbackDistance() {
        return feedbackArray == null ? 0 : feedbackArray.getFeedbackDistance();
    }
}
