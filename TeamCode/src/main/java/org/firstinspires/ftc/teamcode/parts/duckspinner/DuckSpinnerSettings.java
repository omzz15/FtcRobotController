package org.firstinspires.ftc.teamcode.parts.duckspinner;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.input.RampedInputSupplier;

public class DuckSpinnerSettings extends RobotPartSettings {
	//input stuff
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
	float spinnerSpeed = 0.7f;
	float ramp = 0.0070f;
	RampedInputSupplier duckSpinnerPowerSupplier = new RampedInputSupplier(gamepad -> (gamepad.left_bumper ? -spinnerSpeed : gamepad.right_bumper ? spinnerSpeed : 0), gamepadNum, ramp, true);

	@Override
	public void onInit(Robot robot) {
		duckSpinnerPowerSupplier.init(robot);
	}
}