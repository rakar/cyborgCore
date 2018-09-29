package org.montclairrobotics.cyborg.core.assemblies;

/**
 * Represents an SRX controller array. Currently missing implementation!!!
 */
public class CBSrxArrayController extends CBSpeedControllerArrayController {

	public CBSrxArrayController() {
		throw new RuntimeException("CBSrxArrayController not implemented yet. Possibly use CBVictorArrayController.");
	}

	@Override
	public CBSpeedControllerArrayController update(double target) {
		return null;
	}

	@Override
	public double get() {
		return 0;
	}
}
