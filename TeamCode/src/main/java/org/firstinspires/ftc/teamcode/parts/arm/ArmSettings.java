package org.firstinspires.ftc.teamcode.parts.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;

public class ArmSettings extends RobotPartSettings {
	//input stuff
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
		//arm
		InputSupplier armMovementSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger));
		InputSupplier armPresetSupplier = new InputSupplier(gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0));
		//bucket
		InputSupplier bucketMovementSupplier = new InputSupplier(gamepad -> (gamepad.x ? -1 : gamepad.b ? 1 : 0));
		InputSupplier bucketPresetSupplier = new InputSupplier(gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0));

	//servo presets				flat	dump	fdump	cradle
	double[] bucketPresets = 	{0.16,	0.68,	0.0,	0.36};
	int servoSpeed = 300; //in degrees/second
	//arm presets
	int[] armPresets = {0,0,0,0};

	//movement speeds
	int armMovementSpeed = 7;
	double bucketSpeed = .01;

	//end-stops
	 int armMinPos = 0;
	 int armMaxPos = 1600;
	 double servoMinPos = 0;
	 double servoMaxPos = 1;

	@Override
	public void init(Robot robot) {
		gamepad = gamepadNum.getGamepad(robot);
	}
}
