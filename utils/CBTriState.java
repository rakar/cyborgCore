package org.montclairrobotics.cyborg.core.utils;

public class CBTriState {

	public enum CBTriStateValue {low, nil, high}
	
	private CBTriStateValue value=CBTriStateValue.nil;
	
	public CBTriState set(CBTriStateValue value) {
		this.value = value;
		return this;
	}
	
	public CBTriState set(boolean high, boolean low) {
		if(high) value=CBTriStateValue.high;
		else if(low) value=CBTriStateValue.low;
		else value=CBTriStateValue.nil;
		return this;
	}
	
	public CBTriStateValue get() {
		return value;
	};
	
	public Object select(Object high, Object low, Object nil) {
		if(value==CBTriStateValue.high) return high;
		if(value==CBTriStateValue.low) return low;
		return nil;
	}
	
	public boolean isHigh() {
		return value==CBTriStateValue.high;
	}
	public boolean isLow() {
		return value==CBTriStateValue.low;
	}
	public boolean isNil() {
		return value==CBTriStateValue.nil;
	}
}
