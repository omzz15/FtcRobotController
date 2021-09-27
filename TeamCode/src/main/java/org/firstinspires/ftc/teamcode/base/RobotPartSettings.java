package org.firstinspires.ftc.teamcode.base;

import org.firstinspires.ftc.teamcode.Robot;

public abstract class RobotPartSettings {
	public boolean usePart = true;
	boolean initialized = false;
	boolean runForTeleOp = true;
	public boolean sendTelemetry = true;
	boolean stop = false;

	public void init(Robot robot){};

	public boolean canRun(){return usePart && initialized && !stop;}

	public boolean runForTeleOp(){
		return canRun() && runForTeleOp;
	}

	public boolean addTelemetry(){
		return canRun() && sendTelemetry;
	}
}
