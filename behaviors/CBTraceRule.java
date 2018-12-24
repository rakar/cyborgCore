package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;

/**
 * Diagnostic used to debug/trace overall cyborg rule processing.
 */
public class CBTraceRule extends CBRule {
    String id;

    /**
     * @param robot
     * @param id
     */
    public CBTraceRule(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    /**
     *
     */
    public void init() {
        robot.logMessage("CBTraceRule: init - " + id);
    }

    /**
     *
     */
    public void update() {
        robot.logMessage("CBTraceRule: update - " + id);
    }
}
