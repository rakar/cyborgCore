package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;

/**
 * Diagnostic tool used to debug/trace overall cyborg behavior processing.
 */
public class CBTraceAutonomous extends CBAutonomous {
    private String id;

    /**
     * @param robot
     * @param id
     */
    public CBTraceAutonomous(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    /**
     *
     */
    @Override
    public void init() {
        robot.logMessage("CBTraceAutonomous: init - " + id);
    }

    /**
     *
     */
    @Override
    public void update() {
        robot.logMessage("CBTraceAutonomous: update - " + id);
    }
}
