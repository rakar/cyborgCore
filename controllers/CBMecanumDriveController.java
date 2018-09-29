package org.montclairrobotics.cyborg.core.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;

import java.util.ArrayList;

public class CBMecanumDriveController extends CBDriveController {
	//protected double tErr;
	//protected double minMA = Double.MAX_VALUE;
	//protected double maxAbsV;
	private double qtrPi = Math.PI/4.0;
	private double halfPi = Math.PI/2.0;

	private CBStdDriveControlData dcd;
	private ArrayList<CBCalcModule> calcModules = new ArrayList<>();

	public enum CBMecanumDriveCalcMode {ASYMMETRIC, SYMMETRIC}

	private CBMecanumDriveCalcMode calcMode = CBMecanumDriveCalcMode.SYMMETRIC;

	protected class CBCalcModule {
	    int fbm;
	    int lrm;
	    double momentArm;
	    double vTrans;
	    double vCorr;
	    double vTheta;
	    double vTotal;
	    CBDriveModule driveModule;
	    public CBCalcModule(CBDriveModule driveModule) {
	        this.driveModule = driveModule;
        }
    }
	

	public CBMecanumDriveController(Cyborg robot, CBStdDriveControlData controlData) {
		super(robot);
		dcd = controlData;
		System.err.println("Warning: CBMecanumDriveController implementation is highly experimental.");
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		if(dcd.active) {
			calculate();
		}
	}

	private void calculate() {

		switch (calcMode) {
            case ASYMMETRIC: {
                // This needs a rethink.
                // The original code assumed the modules were added
                // in a particular order. I don't like the:
                //    theta-i*halfPi
                // I would like a new approach that uses the
                // position of the module to determine the force vector
                // for that wheel turning "forward" and then do the calcs.
                // I'm also thinking about do more conventional calcs
                // to get the base translation and then scaling those by
                // the moment arms and then adding the rotation.
                // This might need some experimentation.
                /*
                double theta = dcd.direction.getAngleRad() - qtrPi;
                double vd = dcd.direction.getMag();
                tErr = 0;
                for (CBCalcModule cm : calcModules) {
                    double vtrans = vd * Math.cos(theta - i * halfPi);
                    vTrans.set(i, vtrans);
                    tErr += momentArms.get(i) * vtrans;
                    double vtheta = 4.0 * dcd.rotation * minMA;
                    vTheta.set(i, vtheta);
                }
                maxAbsV = 0;
                for (int i = 0; i < dmCount; i++) {
                    double vtotal = vTrans.get(i) + (vTheta.get(i) - tErr) / 4.0 * momentArms.get(i);
                    vTotal.set(i, vtotal);
                    double absV = Math.abs(vtotal);
                    if (absV > maxAbsV) maxAbsV = absV;
                }
                if (maxAbsV > 1.0) {
                    for (int i = 0; i < dmCount; i++) {
                        vTotal.set(i, vTotal.get(i) / maxAbsV);
                    }
                }
                for(int i=0;i<dmCount;i++) {
                    CBDriveModule dm = driveModules.get(i);
                    dm.update(vTotal.get(i));
                }
                */
            }
            break;
            case SYMMETRIC: {
                double maxSpeed = 0;
                for (CBCalcModule cm : calcModules) {
                    double speed = cm.fbm * cm.lrm * dcd.direction.getX() + dcd.direction.getY() + cm.lrm * dcd.rotation;
                    if(Math.abs(speed)>maxSpeed) {
                        maxSpeed = speed;
                    }
                    cm.vTotal = speed;
                }
                double speedScale = maxSpeed<((double)1)?1:1.0/maxSpeed;
                for(CBCalcModule cm : calcModules) {
                    cm.driveModule.update(cm.vTotal*speedScale);
                }
            }
                break;
        }
	}
	
	public CBMecanumDriveController addDriveModule(CBDriveModule driveModule) {

	    CBCalcModule calcModule = new CBCalcModule(driveModule);
	    calcModule.fbm = driveModule.getPosition().getY() > 0 ? 1 : -1;
	    calcModule.lrm = driveModule.getPosition().getX() < 0 ? 1 : -1;
	    calcModule.vCorr = 0;
	    calcModule.vTheta = 0;
	    calcModule.vTrans = 0;
	    calcModule.vTotal = 0;
        CB2DVector pos = driveModule.getPosition();
	    calcModule.momentArm = pos.getMag() * Math.cos(Math.atan2(Math.abs(pos.getX()), Math.abs(pos.getY())) - qtrPi);

		//if(calcModule.momentArm<minMA) minMA=calcModule.momentArm;
		return this;
	}

	@Override
	public CBMecanumDriveController setControlPeriod(double controlPeriod) {
		return (CBMecanumDriveController)super.setControlPeriod(controlPeriod);
	}
}
