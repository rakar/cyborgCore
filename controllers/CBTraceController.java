package org.montclairrobotics.cyborg.core.controllers;

import org.montclairrobotics.cyborg.Cyborg;

/**
 * Diagnostic tool used to debug/trace overall cyborg controller processing.
 */
public class CBTraceController extends CBRobotController {
    String id;

    /**
     * @param robot
     * @param id
     */
    public CBTraceController(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    /**
     *
     */
    @Override
    public void init() {
        robot.logMessage("CBTraceController: init - " + id);
    }

    /**
     *
     */
    @Override
    public void update() {
        robot.logMessage("CBTraceController: update - " + id);
    }
}
