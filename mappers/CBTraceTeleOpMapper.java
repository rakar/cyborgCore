package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;

public class CBTraceTeleOpMapper extends CBTeleOpMapper {
    private String id;

    public CBTraceTeleOpMapper(Cyborg robot, String id) {
        super(robot);
        this.id=id;
    }

    @Override
    public void init() {
        robot.logMessage("CBTraceTeleOpMapper: init - "+id);
    }

    @Override
    public void update() {
        robot.logMessage("CBTraceTeleOpMapper: update - "+id);
    }
}
