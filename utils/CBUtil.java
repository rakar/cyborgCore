package org.montclairrobotics.cyborg.core.utils;

public class CBUtil {

    public static double ApplyDeadzone(double value, double deadzone) {
        if (Math.abs(value) < deadzone) return 0.0;
        if (value>deadzone) return (value-deadzone)/(1-deadzone);
        else return (value+deadzone)/(1-deadzone);
    }
}
