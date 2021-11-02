package org.firstinspires.ftc.teamcode.intake;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;

public class IntakeSettings extends RobotPartSettings {
	//input stuff
	InputSupplier intakePowerSupplier = new InputSupplier(Utils.GamepadNum.TWO, gamepad -> (gamepad.right_trigger - gamepad.left_trigger));

	@Override
	public void init(Robot robot) {
		intakePowerSupplier.init(robot);
	}
}
