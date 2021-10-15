package org.firstinspires.ftc.teamcode.arm;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.other.Utils;

public class ArmSettings extends RobotPartSettings {
	//input stuff
	Gamepad gamepad;
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
	InputSupplier armMovementSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger));
	InputSupplier bucketUpSupplier = new InputSupplier(gamepad -> (gamepad.x));
	InputSupplier bucketDownSupplier = new InputSupplier(gamepad -> (gamepad.b));

	//movement speeds
	int armMovementSpeed = 50;
	double bucketSpeed = .01;

	//end-stops
	 int armMinPos = 0;
	 int armMaxPos = 1000;
	 double servoMinPos = 0;
	 double servoMaxPos = 1;

	@Override
	public void init(Robot robot) {
		gamepad = gamepadNum.getGamepad(robot);
	}
}
