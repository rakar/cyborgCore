package org.montclairrobotics.cyborg.core.assemblies;

/**
 * Represents an SRX controller array. Currently missing implementation!!!
 */
public class CBSrxArray extends CBSpeedControllerArray {

	public CBSrxArray() {
		throw new RuntimeException("CBSrxArray not implemented yet. Possibly use CBSimpleSpeedControllerArray.");
	}

	@Override
	public CBSpeedControllerArray update(double target) {
		return null;
	}

	@Override
	public double get() {
		return 0;
	}
}
