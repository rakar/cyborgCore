package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;

public class CBTraceBehavior extends CBBehavior {
    private String id;

    public CBTraceBehavior(Cyborg robot, String id) {
        super(robot);
        this.id = id;
    }

    @Override
    public void init() {
        robot.logMessage("CBTraceBehavior: init - "+id);
    }

    @Override
    public void update() {
        robot.logMessage("CBTraceBehavior: update - "+id);
    }
}
