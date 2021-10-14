package org.firstinspires.ftc.teamcode.test;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.drive.DriveHardware;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTrackerSettings;

@Deprecated
public class Test extends RobotPart {
	public Test(Robot robot, TestHardware hardware, TestSettings settings) {
		super(robot, hardware, settings);
	}

	public Test(Robot robot){
		super(robot, new TestHardware(), new TestSettings());
	}

	@Override
	public void runForTeleOp() {
		((TestHardware) hardware).motor1.setPower(((TestSettings) settings).motorSupplier.getFloat());
	}
}
