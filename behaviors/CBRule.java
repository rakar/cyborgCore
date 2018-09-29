package org.montclairrobotics.cyborg.core.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBModule;

/**
 * Base class of all Rule behaviors. These are meant to
 * "pre-screen" request data before they are seen by
 * behaviors.
 * @author rich kopelow
 */
public abstract class CBRule extends CBModule {

	public CBRule(Cyborg robot) {
		super(robot);
	}

	public void init() {
	}

	public void update() {
	}
}
