package org.firstinspires.ftc.teamcode.duckspinner;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.inputsupplier.RampedInputSupplier;

public class DuckSpinnerSettings extends RobotPartSettings {
	//input stuff
	float spinnerSpeed = 1;
	float ramp = 0.03f;
	RampedInputSupplier duckSpinnerPowerSupplier = new RampedInputSupplier(Utils.GamepadNum.TWO, gamepad -> (gamepad.left_bumper ? -spinnerSpeed : gamepad.right_bumper ? spinnerSpeed : 0), ramp, true);

	@Override
	public void init(Robot robot) {
		duckSpinnerPowerSupplier.init(robot);
	}
}
