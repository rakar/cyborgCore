package org.montclairrobotics.cyborg.core.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBSolenoid;

import java.util.ArrayList;


public class CBDifferentialDriveController extends CBDriveController implements CBDriveController.CBDrivetrainFeedbackProvider {

    CBDriveControlData dcd;
    protected ArrayList<CBDriveModule> leftDriveModules = new ArrayList<>();
    protected ArrayList<CBDriveModule> rightDriveModules = new ArrayList<>();
    protected ArrayList<CBSolenoid> highGearSolenoids = new ArrayList<>();
    protected ArrayList<CBSolenoid> lowGearSolenoids = new ArrayList<>();
    boolean defaultToHighGear = true;
    CBDriveFeedback feedback;
    boolean canProvideFeedback = false;

    public CBDifferentialDriveController(Cyborg robot, CBDriveControlData controlData) {
        super(robot);
        dcd = controlData;
    }

    @Override
    public void init() {
        //initiallize gearbox
        setSolenoids(lowGearSolenoids,!defaultToHighGear);
        setSolenoids(highGearSolenoids,defaultToHighGear);
    }

    @Override
    public void update() {
        if (dcd.active) {
            if (dcd instanceof CBDifferentialDriveControlData) {

                CBDifferentialDriveControlData dcd = (CBDifferentialDriveControlData) this.dcd;
                for (CBDriveModule m : this.leftDriveModules) {
                    m.update(dcd.leftPower);
                }
                for (CBDriveModule m : this.rightDriveModules) {
                    m.update(dcd.rightPower);
                }

            } else if (dcd instanceof CBStdDriveControlData) {

                CBStdDriveControlData dcd = (CBStdDriveControlData) this.dcd;
                for (CBDriveModule dm : driveModules) {
                    double power = calculate(dm, dcd.direction, dcd.rotation);
                    dm.update(power);
                }

                if(dcd.shiftToLowGear) {
                    setSolenoids(highGearSolenoids, false);
                    setSolenoids(lowGearSolenoids, true);
                }
                if(dcd.shiftToHighGear) {
                    setSolenoids(lowGearSolenoids, false);
                    setSolenoids(highGearSolenoids, true);
                }
            } else {
                String msg = "Error: Invalid DriveControlData for DifferentialDriveController";
                System.out.println(msg);
                throw new RuntimeException(msg);
            }
        }

        if (canProvideFeedback) {
            double leftTranslation = 0, rightTranslation = 0;

            for (CBDriveModule m : this.leftDriveModules) {
                if (m.canProvideFeedback()) {
                    leftTranslation += m.getFeedbackDistance();
                }
            }
            for (CBDriveModule m : this.rightDriveModules) {
                if (m.canProvideFeedback()) {
                    rightTranslation += m.getFeedbackDistance();
                }
            }
            //TODO: work this through to return correct trans/rot.
            // This needs to be worked up from the bottom so that all reverses, orientations, etc.
            // are included so that it all lines up.
            // this should be in the robot frame.
            //if(lastUpdateTime==null) lastUpdateTime = Instant.now();
            //Instant current = Instant.now();
            //feedback.timespan = Duration.between(lastUpdateTime,current);
            //feedback.translation = new CB2DVector(0,(leftTranslation+rightTranslation)/driveModules.size());
            ////feedback.rotation = (rightTranslation-leftTranslation)/
        }
    }

    protected void setSolenoids(ArrayList<CBSolenoid> solenoids, boolean value) {
        for(CBSolenoid solenoid:solenoids) {
            solenoid.set(value);
        }
    }

    protected double calculate(CBDriveModule module, CB2DVector direction, double rotation) {
        double res = 0;

        switch (driveMode) {
            case Power: {
                //CB2DVector diff = new CB2DVector(0,direction.getY()+Math.signum(module.getPosition().getX())*rotation);
                //res = module.getOrientationVector().dot(diff);
                res = (direction.getY() + Math.signum(module.getPosition().getX()) * rotation) * module.getOrientationCos();
            }
            break;
            case Speed: {
                CB2DVector pos = module.getPosition();
                CB2DVector targetPosition =
                        pos.rotate(rotation)
                                .translate(direction);
                CB2DVector diff = targetPosition.sub(pos);
                res = module.getOrientationVector().dot(diff);
                //SmartDashboard.putNumber("speed:", res);
            }
            break;
            case Conflict:
            default:
                break;
        }

        return res;
    }

    @Override
    public CBDifferentialDriveController addDriveModule(CBDriveModule driveModule) {
        throw new RuntimeException("\nCBDifferentialDriveController does not support addDriveModule.\nUse addLeftDriveModule and addRightDriveModule instead.\n");
        //return this;
        //if(!canProvideFeedback) canProvideFeedback = driveModule.canProvideFeedback();
        //return (CBDifferentialDriveController)super.addDriveModule(driveModule);
    }

    @Override
    public CBDifferentialDriveController setControlPeriod(double controlPeriod) {
        return (CBDifferentialDriveController) super.setControlPeriod(controlPeriod);
    }

    @Override
    public CBDriveFeedback getFeedback() {
        // TODO Auto-generated method stub
        return null;
    }

    public CBDifferentialDriveController addLeftDriveModule(CBDriveModule driveModule) {
        super.addDriveModule(driveModule);
        leftDriveModules.add(driveModule);
        return this;
    }

    public CBDifferentialDriveController addRightDriveModule(CBDriveModule driveModule) {
        super.addDriveModule(driveModule);
        rightDriveModules.add(driveModule);
        return this;
    }

    public CBDifferentialDriveController addHighGearSolenoid(CBDeviceID... solenoids) {
        for(CBDeviceID solenoid:solenoids) {
            highGearSolenoids.add(Cyborg.hardwareAdapter.getSolenoidValve(solenoid));
        }
        return this;
    }

    public CBDifferentialDriveController addLowGearSolenoid(CBDeviceID... solenoids) {
        for(CBDeviceID solenoid:solenoids) {
            lowGearSolenoids.add(Cyborg.hardwareAdapter.getSolenoidValve(solenoid));
        }
        return this;
    }

    public CBDifferentialDriveController setDefaultToHighGear(boolean defaultToHighGear) {
        this.defaultToHighGear = defaultToHighGear;
        return this;
    }
}
