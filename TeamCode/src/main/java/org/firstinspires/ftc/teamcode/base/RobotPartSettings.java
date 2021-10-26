package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

public abstract class RobotPartSettings {
	public Gamepad gamepad;
	public boolean usePart = true;
	boolean initialized = false;
	public short runMode = 1; //-1 is reset, 0 is off, 1 is teleop, and rest defined later
	public boolean sendTelemetry = true;

	public void init(Robot robot){};

	public boolean canUse(){return usePart && initialized;}

	public boolean canRun(){return canUse() && runMode > 0;}

	public boolean runForTeleOp(){
		return canRun() && runMode == 1;
	}

	public boolean addTelemetry(){
		return canRun() && sendTelemetry;
	}
}
