package org.firstinspires.ftc.teamcode;

public abstract class RobotPartSettings {
	boolean usePart = true;
	boolean initialized = false;
	boolean runForTeleOp = true;
	boolean sendTelemetry = true;
	boolean stop = false;

	boolean canRun(){return usePart && initialized && !stop;}

	boolean runForTeleOp(){
		return canRun() && runForTeleOp;
	}
}
