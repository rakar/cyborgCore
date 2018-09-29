package org.montclairrobotics.cyborg.core.utils;

public class CBTarget1D extends CBEdgeTrigger {
    double xTarget;
    double xRange;
    double xPosition;
    boolean active;

    public CBTarget1D() {
        xPosition = 0;
        active = false;
    }

    public CBTarget1D setTarget(double xTarget, double xRange) {
        this.xTarget = xTarget;
        this.xRange = xRange;
        return this;
    }

    public CBTarget1D setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public CBTarget1D setPosition(double x) {
        xPosition = x;
        return this;
    }

    public CBTarget1D update(double x) {
        xPosition = x;
        super.update(active && isOnTarget(xPosition));
        return this;
    }

    public double getXPosition() {
        return xPosition;
    }
    public double getXTarget() { return xTarget; }

    public boolean isAboveTarget() {
        return (active && xPosition>(xTarget+xRange));
    }

    public boolean isOnTarget() {
        return (active && xPosition>=(xTarget-xRange) && xPosition<=(xTarget+xRange));
    }

    public boolean isBelowTarget() {
        return (active && xPosition<(xTarget-xRange));
    }
    @Deprecated
    public boolean isAboveTarget(double value) {
        return (active && value>(xTarget+xRange));
    }
    @Deprecated
    public boolean isOnTarget(double value) {
        return (active && value>=(xTarget-xRange) && value<=(xTarget+xRange));
    }
    @Deprecated
    public boolean isBelowTarget(double value) {
        return (active && value<(xTarget-xRange));
    }

}


