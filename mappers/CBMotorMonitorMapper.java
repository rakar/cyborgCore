package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.devices.CBSpeedControllerFault;

import java.util.ArrayList;

public class CBMotorMonitorMapper extends CBSensorMapper {
    ArrayList<CBSpeedController> controllers = new ArrayList();

    public CBMotorMonitorMapper(Cyborg robot) {
        super(robot);
    }

    public CBMotorMonitorMapper add(CBDeviceID controller) {
        add(Cyborg.hardwareAdapter.getSpeedController(controller));
        return this;
    }

    public CBMotorMonitorMapper add(CBSpeedController controller) {
        controllers.add(controller);
        return this;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        String[] status = new String[controllers.size()];
        int i = 0;
        for(CBSpeedController controller: controllers) {
            CBSpeedControllerFault fault = controller.getSpeedControllerFault();
            double current = controller.getActualCurrent();
            String name = controller.getName();
            String value;
            if (fault==null) {
                value = Double.toString(current);
            } else {
                value = fault.errMsg;
            }
            status[i] = name +" "+value;
        }
        robot.putStringArray("MotorMonitor", status);
    }
}
