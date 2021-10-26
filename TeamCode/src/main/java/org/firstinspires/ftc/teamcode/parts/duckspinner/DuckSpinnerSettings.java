package org.firstinspires.ftc.teamcode.parts.duckspinner;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.inputsupplier.RampedInputSupplier;

public class DuckSpinnerSettings extends RobotPartSettings {
	//input stuff
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.TWO;
	float spinnerSpeed = 1;
	float ramp = 0.03f;
	RampedInputSupplier duckSpinnerPowerSupplier = new RampedInputSupplier(gamepad -> (gamepad.left_bumper ? -spinnerSpeed : gamepad.right_bumper ? spinnerSpeed : 0), ramp, true);

	@Override
	public void init(Robot robot) {
		gamepad = gamepadNum.getGamepad(robot);
	}
}
