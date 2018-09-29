package org.montclairrobotics.cyborg.core.controllers;

import org.montclairrobotics.cyborg.Cyborg;

public class CBTraceController extends CBRobotController {
    String id;

    public CBTraceController(Cyborg robot, String id) {
        super(robot);
        this.id=id;
    }

    @Override
    public void init() {
        robot.logMessage("CBTraceController: init - "+id);
    }

    @Override
    public void update() {
        robot.logMessage("CBTraceController: update - "+id);

    }
}
