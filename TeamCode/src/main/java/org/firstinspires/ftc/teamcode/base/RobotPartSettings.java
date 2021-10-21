package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;

public abstract class RobotPartSettings {
	public Gamepad gamepad;
	public boolean usePart = true;
	boolean initialized = false;
	public boolean runForTeleOp = true;
	public boolean sendTelemetry = true;
	boolean stop = false;

	public void init(Robot robot){};

	public boolean canUse(){return usePart && initialized;}

	public boolean canRun(){return canUse() && !stop;}

	public boolean runForTeleOp(){
		return canRun() && runForTeleOp;
	}

	public boolean addTelemetry(){
		return canRun() && sendTelemetry;
	}
}
