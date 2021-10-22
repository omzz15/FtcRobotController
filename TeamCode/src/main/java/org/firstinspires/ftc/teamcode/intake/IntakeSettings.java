package org.firstinspires.ftc.teamcode.intake;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;

public class IntakeSettings extends RobotPartSettings {
	//input stuff
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.TWO;
	InputSupplier intakePowerSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger));

	@Override
	public void init(Robot robot) {
		gamepad = gamepadNum.getGamepad(robot);
	}
}
