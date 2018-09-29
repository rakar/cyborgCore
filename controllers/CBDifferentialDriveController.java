package org.montclairrobotics.cyborg.core.controllers;

//import java.time.Instant;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.core.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBDriveControlData;
import org.montclairrobotics.cyborg.core.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.core.utils.CB2DVector;

import java.util.ArrayList;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CBDifferentialDriveController extends CBDriveController implements CBDriveController.CBDrivetrainFeedbackProvider {

	protected ArrayList<CBDriveModule> leftDriveModules = new ArrayList<>();
	protected ArrayList<CBDriveModule> rightDriveModules = new ArrayList<>();
	//Instant lastUpdateTime;
	CBDriveFeedback feedback;
	boolean canProvideFeedback=false;
	CBDriveControlData dcd;
	
	public CBDifferentialDriveController(Cyborg robot, CBDriveControlData controlData) {
		super(robot);
		driveModules = null;
		dcd = controlData;
	}

	@Override
	public void init() {

	}

	@Override
	public void update() {
		if(dcd.active) {
			if(dcd instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData dcd = (CBDifferentialDriveControlData) this.dcd;
				for(CBDriveModule m:this.leftDriveModules){
					m.update(dcd.leftPower);
				}
				for(CBDriveModule m:this.rightDriveModules) {
					m.update(dcd.rightPower);
				}

			} else if(dcd instanceof CBStdDriveControlData) {
				
				CBStdDriveControlData dcd = (CBStdDriveControlData) this.dcd;
				for(CBDriveModule dm:leftDriveModules) {
					double power = calculate(dm, dcd.direction, dcd.rotation);
					dm.update(power);
				}				
				for(CBDriveModule dm:rightDriveModules) {
					double power = calculate(dm, dcd.direction, dcd.rotation);
					dm.update(power);
				}	
			} else {
				String msg = "Error: Invalid DriveControlData for DifferentialDriveController";
				System.out.println(msg);
				throw new RuntimeException(msg);
			}
		}

		if(canProvideFeedback) {
			double leftTranslation=0, rightTranslation=0;
			
			for(CBDriveModule m:this.leftDriveModules){
				if(m.canProvideFeedback()) {
					leftTranslation+=m.getFeedbackDistance();
				}
			}
			for(CBDriveModule m:this.rightDriveModules){
				if(m.canProvideFeedback()) {
					rightTranslation+=m.getFeedbackDistance();
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
	
	protected double calculate(CBDriveModule module, CB2DVector direction, double rotation) {
		double res = 0;

		switch (driveMode) {
		case Power:
		{
			CB2DVector diff = new CB2DVector(0,direction.getY()+Math.signum(module.getPosition().getX())*rotation);
			res = module.getOrientationVector().dot(diff);
		}
			break;
		case Speed:
		{
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
		return (CBDifferentialDriveController)super.setControlPeriod(controlPeriod);
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
}
