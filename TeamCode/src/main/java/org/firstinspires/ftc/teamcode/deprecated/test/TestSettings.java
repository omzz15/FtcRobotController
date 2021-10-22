package org.firstinspires.ftc.teamcode.deprecated.test;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;

@Deprecated
public class TestSettings extends RobotPartSettings {
	InputSupplier motorSupplier = new InputSupplier(gamepad -> (gamepad.left_trigger));

	@Override
	public void init(Robot robot) {
		motorSupplier.gamepad = robot.gamepad1;
	}
}
