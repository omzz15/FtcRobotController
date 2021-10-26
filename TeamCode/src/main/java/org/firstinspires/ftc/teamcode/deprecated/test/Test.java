package org.firstinspires.ftc.teamcode.deprecated.test;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;

@Deprecated
public class Test extends RobotPart {
	public Test(Robot robot, TestHardware hardware, TestSettings settings) {
		super(robot, hardware, settings);
	}

	public Test(Robot robot){
		super(robot, new TestHardware(), new TestSettings());
	}

	@Override
	public void onTeleOpLoop() {
		((TestHardware) hardware).motor1.setPower(((TestSettings) settings).motorSupplier.getFloat());
	}
}
