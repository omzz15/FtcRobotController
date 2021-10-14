package org.firstinspires.ftc.teamcode.test2;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;

@Deprecated
public class Test2Settings extends RobotPartSettings {
	InputSupplier motorSupplier = new InputSupplier(gamepad -> (gamepad.left_trigger - gamepad.right_trigger));
	InputSupplier motor2UpSupplier = new InputSupplier(gamepad -> (gamepad.x));
	InputSupplier motor2DownSupplier = new InputSupplier(gamepad -> (gamepad.b));

	double motor2UpSpeed = .3;
	double motor2DownSpeed = -.3;

	@Override
	public void init(Robot robot) {
		motorSupplier.gamepad = robot.gamepad1;
		motor2UpSupplier.gamepad = robot.gamepad1;
		motor2DownSupplier.gamepad = robot.gamepad1;
	}
}
