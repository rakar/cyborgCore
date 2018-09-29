package org.montclairrobotics.cyborg.core.utils;

public class CBTracker {
	private CBSource source;
	private CBErrorCorrection controller;
	
	public CBTracker() {
		
	}
	
	/*
	public CBTracker(CBSource source, CBErrorCorrection controller) {
		this.source = source;
		this.controller = controller;
	}
	*/
	
	public CBTracker lock() {
		controller.setTarget(source.get());
		return this;
	}
	
	public CBTracker setTarget(double target) {
		setTarget(target, true);
		return this;
	}
	
	public CBTracker setTarget(double target, boolean reset) {
		controller.setTarget(target, reset);
		return this;
	}
	
	public double update() {
		return controller.update(source.get());
	}
	
	public double update(double feedback) {
		return controller.update(feedback);
	}
	
	public CBTracker setSource(CBSource source) {
		this.source = source;
		return this;
	}
	
	public CBTracker setErrorCorrection(CBErrorCorrection controller) {
		this.controller = controller;
		return this;
	}
}
