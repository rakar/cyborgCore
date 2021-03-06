package org.montclairrobotics.cyborg.core.utils;

import org.montclairrobotics.cyborg.Cyborg;

public abstract class CBModule {
	public Cyborg robot;
	protected boolean isActive;
	protected boolean initialized;
	protected boolean debug;

	public boolean IsActive() {
		return isActive;
	}

	public void SetActive(boolean active) {
		isActive = active;
	}

	public CBModule(Cyborg robot) {
		this.robot = robot;
		this.debug = false;
	}

	public void moduleInit() {
	    if (!initialized) {
	        init();
	        initialized = true;
        }
    }

	public abstract void init();

	public CBModule setDebug(boolean debug) {
		this.debug = debug;
		return this;
	}
}
