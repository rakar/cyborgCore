package org.montclairrobotics.cyborg.core.data;

import org.montclairrobotics.cyborg.core.utils.CBTarget1D;

public class CBLiftControlData {
    /**
     * Request the lift move Up
     */
    public boolean requestUp;
    /**
     * Request the lift move Down
     */
    public boolean requestDown;
    /**
     * Request the lift move to Target
     */
    public CBTarget1D target;
    /**
     * Height of top Margin or slow zone
     * Above this height the lift will move up slowly
     */
    public CBTarget1D topMargin;
    /**
     * Height of bottom Margin or slow zone
     * Below this height the lift will move down slowly
     */
    public CBTarget1D bottomMargin;
    /**
     * Height at which the lift tops out.
     * Also a top limit switch activation will set
     * the encoder to this target's value.
     */
    public CBTarget1D topEncoderLimit;
    /**
     * Height at which the lift bottoms out.
     * Also a bottom limit switch activation will set
     * the encoder to this target's value.
     */
    public CBTarget1D bottomEncoderLimit;
    /**
     * Speed or power used when moving Up Slowly
     */
    public double slowUp;
    /**
     * Speed or power used when moving Down Slowly
     */
    public double slowDown;
    /**
     * Speed or power used when moving Up Normally
     */
    public double normUp;
    /**
     * Speed or power used when moving Down Normally
     */
    public double normDown;


    public CBLiftControlData() {
        target = new CBTarget1D();
        topMargin = new CBTarget1D();
        bottomMargin = new CBTarget1D();
        topEncoderLimit = new CBTarget1D();
        bottomEncoderLimit = new CBTarget1D();
    }
}
