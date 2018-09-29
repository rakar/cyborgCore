package org.montclairrobotics.cyborg.core.utils;

import java.util.Date;

public class CBRunStatistics {

	public int totalCycles;
	public long totalTime;
	public long   firstUpdate, thisUpdate; 
	public double averageCycles;
	
	public CBRunStatistics() {

	}
	
	public void teleopPeriodicUpdate() {
		totalCycles++;
		thisUpdate = new Date().getTime();
		totalTime =  (thisUpdate-firstUpdate);
		averageCycles = 1000.0*totalCycles/(totalTime+1);
	}

	public void teleopInitUpdate() {
		firstUpdate = new Date().getTime();
		totalCycles = 0;
	}

}
