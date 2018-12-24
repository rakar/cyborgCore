package org.montclairrobotics.cyborg.core.assemblies;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBServo;

import java.util.ArrayList;

public class CBServoArray {
    protected ArrayList<CBServo> servos = new ArrayList<>();

    public CBServoArray addServo(CBDeviceID servo) {
        servos.add(Cyborg.hardwareAdapter.getServo(servo));
        return this;
    }

    public CBServoArray update(double target) {
        for (CBServo s : servos) {
            //Cyborg.hardwareAdapter.robot.logMessage("in update servo loop");
            s.setPosition(target);
        }
        return this;
    }
}
