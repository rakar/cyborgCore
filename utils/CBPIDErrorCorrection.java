package org.montclairrobotics.cyborg.core.utils;

import java.util.Date;

public class CBPIDErrorCorrection implements CBErrorCorrection {
	private double minIn,maxIn,minOut,maxOut;
	private double[] k;
	private double in,out;
	private double target;
	private double totalError, prevError, error;
	private long   lastUpdate, thisUpdate; 
	private double timeSpan;
	private double holdValue;
	private double correction;
	private CBOnTargetMode onTargetMode = CBOnTargetMode.Zero;
	private CBSource pidSource;

	/**
	 * x@param P the Proportional Constant
	 * x@param I the Integral Constant
	 * x@param D the Derivative Constant
	 */
	public CBPIDErrorCorrection()
	{
		//this.k = k;
	}
	
	@Override
	public CBErrorCorrection setOnTargetMode(CBOnTargetMode mode) {
		this.onTargetMode = mode;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setInputLimits(double, double)
	 */
	@Override
	public CBErrorCorrection setInputLimits(double minIn,double maxIn)
	{
		this.minIn = minIn;
		this.maxIn = maxIn;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setOutputLimits(double, double)
	 */
	@Override
	public CBErrorCorrection setOutputLimits(double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setConstants(double[])
	 */
	@Override
	public CBErrorCorrection setConstants(double[] k){
		this.k = k;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setTarget()
	 */
	//public CBPIDController copy()
	//{
	//	return new CBPIDController()
	//			.setConstants(k)
	//			.setInputLimits(minIn, maxIn)
	//			.setOutputLimits(minOut, maxOut);
	//}
	
	
	@Override
	public CBErrorCorrection setTarget()
	{
		return setTarget(0.0,false);
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setTarget(double)
	 */
	@Override
	public CBErrorCorrection setTarget(double t)
	{
		return setTarget(t,false);
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#setTarget(double, boolean)
	 */
	@Override
	public CBErrorCorrection setTarget(double t, boolean reset)
	{
		target=t;
		if(reset)
		{
			this.reset();
		}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#reset()
	 */
	@Override
	public CBErrorCorrection reset() {
		error=0.0;
		prevError=0.0;
		totalError=0.0;
		lastUpdate = 0;
		holdValue = 0;
		return this;
	}
		
	public CBErrorCorrection resetIAccum() {
		totalError=0.0;	
		return this;
	}
		
	protected double calculateCorrection(double actual)
	{
		double P = k[0];
		double I = k[1];
		double D = k[2];
		
		error=(target-actual) * timeSpan;
		
		// If circular wrap to shortest error
		if(minIn!=0&&maxIn!=0)
		{
			double diff=maxIn-minIn;
			error=((error-minIn)%diff+diff)%diff+minIn;
		}
		
		// Accumulate total error for I term
		// and clamp result 
		// (not sure why were clamping the partial result)
		totalError+=error;
		if (I != 0) 
		{
			double potentialIGain = (error+totalError) * I;
			if (potentialIGain < maxOut) {
				if (potentialIGain > minOut) {
					totalError += error;
				} else {
					totalError = minOut / I;
				}
			} else {
				totalError = maxOut / I;
			}
		}
	
		// calculate correction
		double correction = P * error + I * totalError +
	             D * (error - prevError); //+ calculateFeedForward();

		return correction;
	}

	public double update() {
		if(pidSource==null) return update(0.0);
		return update(pidSource.get());
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#update(double)
	 */
	@Override
	public double update(double source)
	{
		thisUpdate = new Date().getTime();
		prevError=error;
		in=source;
		if (lastUpdate == 0) {
			timeSpan = 0;
			correction = 0;
		} else {
			timeSpan =  (thisUpdate-lastUpdate)/1000.0;
			correction = calculateCorrection(source);
		}
		switch(onTargetMode) {
		case HoldValue:
			holdValue+=correction;
			out = holdValue;
			break;
		case Zero:
			out = correction;
			break;
		}
		// Clamp output
		if(minOut!=0 || maxOut!=0)
		{
			if (out > maxOut) out=maxOut;
			else if (out < minOut) out=minOut;
		}
		
		lastUpdate = thisUpdate;
		return out;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#getIn()
	 */
	@Override
	public double getIn()
	{
		return in;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#getError()
	 */
	@Override
	public double getError(){
		return error;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#getOut()
	 */
	@Override
	public double getOut()
	{
		return out;
	}
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.core.utils.CBErrorCorrection#getOut()
	 */
	@Override
	public double getCorrection()
	{
		return correction;
	}
	
	@Override
	public CBOnTargetMode getOnTargetMode() {
		return onTargetMode;
	}
}
