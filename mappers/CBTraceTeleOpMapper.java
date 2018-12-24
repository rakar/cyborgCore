package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;

/**
 * Diagnostic tool used to debug/trace overall cyborg mapper processing.
 */
public class CBTraceTeleOpMapper extends CBTeleOpMapper {
    private String id;

    /**
     * @param robot
     * @param id
     */
    public CBTraceTeleOpMapper(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    /**
     *
     */
    @Override
    public void init() {
        robot.logMessage("CBTraceTeleOpMapper: init - " + id);
    }

    /**
     *
     */
    @Override
    public void update() {
        robot.logMessage("CBTraceTeleOpMapper: update - " + id);
    }
}
