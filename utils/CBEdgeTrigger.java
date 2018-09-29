package org.montclairrobotics.cyborg.core.utils;
/**
 * Perform edge detection and extension on a boolean data stream.
 * @author Rich Kopelow
 */
public class CBEdgeTrigger {
	private boolean state = false;
	private boolean toggle = false;
	private boolean risingEdge = false;
	private boolean fallingEdge = false;
	private int pulseDuration = 1;
	private int pulseCount;
	private boolean risingEdgePulse = false;
	private boolean fallingEdgePulse = false;
	private int countTrue;
	private int countFalse;

    /**
     * In cases where initial state may not be 0/false,
     * this method can be used to avoid false trigger on
     * initial 1/true state.
     * @param value initial value
     * @return current object
     */
	public CBEdgeTrigger setInitialState(boolean value) {
		this.state = value;
		this.toggle = value;
		countTrue = 0;
		countFalse = 0;
		return this;
	}

    /**
     * Specify the duration of pulse extension.
     * @param pulseDuration duration of the output pulse
     * @return current object
     */
	public CBEdgeTrigger setPulseDuration(int pulseDuration) {
		this.pulseDuration = pulseDuration;
		return this;
	}

    /**
     * Process a signal sample and update all values
     * @param value input signal
     * @return current object
     */
	public CBEdgeTrigger update(boolean value) {

		risingEdge=false;
		fallingEdge=false;
		
		if(pulseCount>0) pulseCount--;
		if(pulseCount==0) {
			risingEdgePulse=false;
			fallingEdgePulse=false;
		}
		
		if(!state && value){
			risingEdge  = true;
			risingEdgePulse = true;
			fallingEdge = false;
			fallingEdgePulse = false;
			pulseCount = pulseDuration;
		}
		if(state && !value) {
			risingEdge  = false;
			risingEdgePulse = false;
			fallingEdge = true;
			fallingEdgePulse = true;
			pulseCount = pulseDuration;
		}
		if(risingEdge) {
			toggle = !toggle;
		}
		
		state = value;
		
		if(state) {
			countTrue++;
			countFalse=0;
		} else {
			countTrue=0;
			countFalse++;
		}
		
		return this;
	}

    /**
     * Return most recently updated signal value
     * @return state
     */
	public boolean getState() {
		return state;
	}

    /**
     * Output that is toggled with each rising edge
     * @return true for toggle
     */
	public boolean getToggle() {
		return toggle;
	}

    /**
     * True for rising edge last update
     * @return true for risingEdge
     */
	public boolean getRisingEdge() {
		return risingEdge;
	}

    /**
     * True for falling edge last update
     * @return true for falling edge
     */
	public boolean getFallingEdge() {
		return fallingEdge;
	}

    /**
     * True for pulseDuration after rising edge
     * @return true for elongated rising edge
     */
	public boolean getRisingEdgePulse() {
		return risingEdgePulse;
	}

    /**
     * True for pulseDuration after falling edge
     * @return true for elongated falling edge
     */
	public boolean getFallingEdgePulse() {
		return fallingEdgePulse;
	}

    /**
     * Number of samples signal held true
     * @return duration of true
     */
	public int getTrueCount() {
		return countTrue;
	}

    /**
     * Number of samples signal held false
     * @return
     */
	public int getFalseCount() {
		return countFalse;
	}
}
