package org.firstinspires.ftc.teamcode.test2;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public class Test2 extends RobotPart {
	public Test2(Robot robot, RobotPartHardware hardware, RobotPartSettings settings) {
		super(robot, hardware, settings);
	}

	public Test2(Robot robot){
		super(robot, new Test2Hardware(), new Test2Settings());
	}

	@Override
	public void runForTeleOp() {
		super.runForTeleOp();
		((Test2Hardware) hardware).motor1.setPower(((Test2Settings) settings).motorSupplier.getFloat());

		if(((Test2Settings) settings).motor2UpSupplier.getBoolean())
			((Test2Hardware) hardware).motor2.setPower(((Test2Settings) settings).motor2UpSpeed);
		else if(((Test2Settings) settings).motor2DownSupplier.getBoolean())
			((Test2Hardware) hardware).motor2.setPower(((Test2Settings) settings).motor2DownSpeed);
		else
			((Test2Hardware) hardware).motor2.setPower(0);

	}
}
