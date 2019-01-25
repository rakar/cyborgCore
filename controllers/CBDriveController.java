package org.montclairrobotics.cyborg.core.controllers;

//import java.time.Duration;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.core.utils.CBEnums;

import java.util.ArrayList;

public abstract class CBDriveController extends CBRobotController {

    protected ArrayList<CBDriveModule> driveModules = new ArrayList<>();
    protected CBEnums.CBMotorControlMode motorControlMode;
    protected double controlPeriod = 1 / 50.0;

    public class CBDriveFeedback {
        public CB2DVector translation;
        public double rotation;
        //public Duration timespan;
    }

    public interface CBDrivetrainFeedbackProvider {
        public boolean canProvideFeedback();

        public CBDriveFeedback getFeedback();
    }

    public CBDriveController(Cyborg robot) {
        super(robot);
    }

    public CBDriveController addDriveModule(CBDriveModule driveModule) {
        if (driveModules != null) driveModules.add(driveModule);
        updateDriveMode(driveModule);
        return this;
    }

    /**
     * @return the motorControlMode
     */
    public CBEnums.CBMotorControlMode getMotorControlMode() {
        return motorControlMode;
    }

    protected void updateDriveMode(CBDriveModule driveModule) {
        if (motorControlMode == null) {
            motorControlMode = driveModule.getMotorControlMode();
        } else {
            if (driveModule.getMotorControlMode() != motorControlMode && motorControlMode != CBEnums.CBMotorControlMode.FOLLOWER) {
                motorControlMode = CBEnums.CBMotorControlMode.CONFLICT;
                throw new RuntimeException("Drivemode Conflict");
            }
        }
    }

    /*
     * The purpose of this function is to do "last minute" hardware configuration
     * required after construction.
     * configHardware() to build whatever required WPI infrastructure is required.
     * This concept is not fleshed out yet.
     */
    @Override
    public void configHardware() {

    }

    /**
     * @return the controlPeriod
     */
    public double getControlPeriod() {
        return controlPeriod;
    }

    /**
     * @param controlPeriod the controlPeriod to set
     */
    public CBDriveController setControlPeriod(double controlPeriod) {
        this.controlPeriod = controlPeriod;
        return this;
    }

    public boolean canProvideFeedback() {
        return false;
    }

}
