package org.firstinspires.ftc.teamcode.parts.arm2;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.input.InputSupplier;
//TODO finish the settings for arm2
public class Arm2Settings extends RobotPartSettings {
	////////////
	//settings//
	////////////
	//input
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
	//arm motor
	InputSupplier armMotorMovementSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger), gamepadNum);
	float minInputRegisterVal = 0.1f;
	//arm servo
	InputSupplier armServoMovementSupplier = new InputSupplier(gamepad -> (1), gamepadNum);
	//bucket servo
	InputSupplier bucketServoMovementSupplier = new InputSupplier(gamepad -> (1), gamepadNum);
	//preset
	InputSupplier presetSupplier = new InputSupplier(gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0), gamepadNum);


	//bucket servo
	//start
	double bucketServoStartPos = 0.41;
	//speed
	int bucketServoSpeed = 300; //in degrees/second
	double bucketServoMovementSpeed = .002;
	//limits
	double bucketServoMinPos = 0.30;
	double bucketServoMaxPos = 0.41;
	//servo presets             	pickup	lift	dump	top
	double[] bucketServoPresets =   {0.41,	0.77,	0.3,	0.34};

	//servo
	//start
	double armServoStartPos = 0;
	//speed
	int armServoSpeed = 300; //in degrees/second
	double armServoMovementSpeed = .002;
	//limits
	double armServoMinPos = 0;
	double armServoMaxPos = 1;
	//servo presets             pickup	bottom/middle	top
	double[] armServoPresets =   {0,	1.0,			0.96};

	//arm
	//speed
	int armMotorMovementSpeed = 10;
	//limits
	int armMotorMinPos = 0;
	int armMotorMaxPos = 1600;
	//arm presets       flat    dump    fdump   cradle
	//int[] armPresets = {10,     1020,     380,     95};
	//other
	//int armTolerance = 60;


	@Override
	public void onInit(Robot robot) {
	}
}
