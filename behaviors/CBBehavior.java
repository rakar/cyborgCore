package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBModule;

public abstract class CBBehavior extends CBModule {

	public CBBehavior(Cyborg robot) {
		super(robot);
	}

	public void init() {}

	public void update() {}
}
