package org.montclairrobotics.cyborg.core.utils;

public interface CBErrorCorrection {
	
	enum CBOnTargetMode {Zero, HoldValue};
	
	CBErrorCorrection setOnTargetMode(CBOnTargetMode mode);
	CBOnTargetMode getOnTargetMode();

	/**
	 * @param minIn the minimum input, or 0 to ignore. Use with maxIn to "wrap" the values, 
	 * eg. so the error between 5 degrees and 355 degrees is 10 degrees
	 * @param maxIn the maximum input, or 0 to ignore
	 */
	CBErrorCorrection setInputLimits(double minIn, double maxIn);

	/**
	 * 
	 * @param minOut the minimum output to constrain to, or 0 to ignore
	 * @param maxOut the maximum output to constrain to, or 0 to ignore
	 */
	CBErrorCorrection setOutputLimits(double minOut, double maxOut);

	CBErrorCorrection setConstants(double[] k);

	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	//public CBPIDController copy()
	//{
	//	return new CBPIDController()
	//			.setConstants(k)
	//			.setInputLimits(minIn, maxIn)
	//			.setOutputLimits(minOut, maxOut);
	//}

	CBErrorCorrection setTarget();

	CBErrorCorrection setTarget(double t);

	/**
	 * Sets the setpoint
	 * @param t the target/setpoint
	 * @param reset true if the PID should reset, false otherwise
	 */
	CBErrorCorrection setTarget(double t, boolean reset);

	CBErrorCorrection reset();

	double update(double source);

	double getIn();

	double getError();

	double getCorrection();

	double getOut();
	
}