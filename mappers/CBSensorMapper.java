package org.montclairrobotics.cyborg.core.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBModule;

public abstract class CBSensorMapper extends CBModule {

	public CBSensorMapper(Cyborg robot) {
		super(robot);
	}

	public abstract void init();

	public abstract void update();
}
