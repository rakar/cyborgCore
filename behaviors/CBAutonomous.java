package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBModule;

/**
 * Base class for all Autonomous behaviors
 *
 * @author rich
 */
public abstract class CBAutonomous extends CBModule {

    public CBAutonomous(Cyborg robot) {
        super(robot);
    }

    public void init() {
    }

    public void update() {
    }
}
