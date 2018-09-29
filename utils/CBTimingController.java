package org.montclairrobotics.cyborg.core.utils;

import org.montclairrobotics.cyborg.Cyborg;

/** Used to control the timing of operations.
 * A periodic "pulse" is generated every n updates
 * during selected periods of a match.
 * For example during "pre-game" or TeleOp
 * to update dashboard values.
 * @author Rich Kopelow
 */
public class CBTimingController extends CBEdgeTrigger {

	private int timingMode;
	private int timingDelay; 
	private int timingCount;

	public CBTimingController() {
		timingMode = 0;
		timingDelay = 0;
		timingCount = 0;
	}

    /**
     * Sets the timing mode and delay count.
     * @param mode must match values from CBGameMode
     * @param delay fire interval
     * @return this class
     */
	public CBTimingController setTiming(int mode, int delay) {
		timingMode = mode;
		timingDelay = delay;
		timingCount = delay;
		return this;
	}

    /**
     * Returns true periodically during selected
     * periods of a match.
     * @return true/false
     */
	public CBTimingController update() {
		boolean res = false;
		if((Cyborg.gameMode & timingMode)!=0 ) {
			timingCount--;
			if(timingCount<=0) {
				res = true;
				timingCount = timingDelay;
			}
		}
		super.update(res);
		return this;
	}
}
