package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;

/**
 * Diagnostic tool used to debug/trace overall cyborg mapper processing.
 */
public class CBTraceSensorMapper extends CBSensorMapper {
    private String id;

    /**
     * @param robot
     * @param id
     */
    public CBTraceSensorMapper(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    /**
     *
     */
    @Override
    public void init() {
        robot.logMessage("CBTraceSensorMapper: init - " + id);
    }

    /**
     *
     */
    @Override
    public void update() {
        robot.logMessage("CBTraceSensorMapper: update - " + id);
    }
}
