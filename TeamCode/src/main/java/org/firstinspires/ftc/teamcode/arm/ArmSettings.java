package org.firstinspires.ftc.teamcode.arm;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;

public class ArmSettings extends RobotPartSettings {
	//input stuff
		//arm
		InputSupplier armMovementSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.right_trigger - gamepad.left_trigger));
		InputSupplier armPresetSupplier = new InputSupplier(Utils.GamepadNum.TWO, gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0));
		//bucket
		InputSupplier bucketMovementSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.x ? -1 : gamepad.b ? 1 : 0));
		InputSupplier bucketPresetSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0));
		InputSupplier bucketCradleSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.y));
		InputSupplier bucketDockSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.a));

	//servo presets				flat	dump	fdump	cradle
	double[] bucketPresets = 	{0.2,	1.0,	0.0,	0.36};
	int servoMoveTime = 100; //in degrees/second
	//arm presets
	int[] armPresets = {10,1020,380,95};
	int minArmDockPos = 70;

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
		armMovementSupplier.init(robot);
		armPresetSupplier.init(robot);
		bucketMovementSupplier.init(robot);
		bucketPresetSupplier.init(robot);
		bucketCradleSupplier.init(robot);
		bucketDockSupplier.init(robot);
	}
}
