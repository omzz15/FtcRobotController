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
	InputSupplier<Float> armMotorMovementSupplier = new InputSupplier<>(gamepad -> (gamepad.right_trigger - gamepad.left_trigger), Utils.GamepadNum.TWO);
	float minInputRegisterVal = 0.1f;
	//arm servo
	InputSupplier<Integer> armServoMovementSupplier = new InputSupplier<>(gamepad -> ((gamepad.x) ? 1 : (gamepad.b) ? -1 : 0), Utils.GamepadNum.TWO);
	//bucket servo
	InputSupplier<Integer> bucketServoMovementSupplier = new InputSupplier<>(gamepad -> ((gamepad.dpad_left) ? 1 : (gamepad.dpad_right) ? -1 : 0), Utils.GamepadNum.TWO);
	//preset
	InputSupplier<Integer> presetSupplier = new InputSupplier<>(gamepad -> ((gamepad.dpad_down) ? 1 : (gamepad.dpad_left) ? 2 : (gamepad.dpad_right) ? 3 : (gamepad.dpad_up) ? 4 : (gamepad.y) ? 5 : (gamepad.x) ? 6 : 0), gamepadNum);


	//bucket servo
	//speed
	int bucketServoSpeed = 300; //in degrees/second
	double bucketServoMovementSpeed = .005;
	//limits
	double bucketServoMinPos = 0;
	double bucketServoMaxPos = 1;
	//servo presets             	pickup	lift	bottom	middle  top    final dump
	double[] bucketServoPresets =   {0.58,	0.87,   0.14,   0.14,   0.23,   0.425};
	//double[] bucketServoPresets =   {0.57,	0.76,   0.33,   0.32,   0.425,  0.425};
	//start
	double bucketServoStartPos = bucketServoPresets[1];

	//arm servo
	//speed
	int armServoSpeed = 300; //in degrees/second
	double armServoMovementSpeed = .005;
	//limits
	double armServoMinPos = 0;
	double armServoMaxPos = 1;
	//servo presets             pickup	lift  bottom  middle	top
	double[] armServoPresets =   {0, 0.05, 0.995, 0.995, 0.905};
	//start
	double armServoStartPos = armServoPresets[1];

	//arm motor
	//speed
	int armMotorMovementSpeed = 1;
	//limits
	int armMotorMinPos = 0;
	int armMotorMaxPos = 3617;
	//arm presets       pickup    lift    bottom   middle top
	int[] armPresets = {0,     0,     0,          550 ,    1130};
	//other
	int armTolerance = 60;
	//start
	int armMotorStartPos = armPresets[1];


	@Override
	public void onInit(Robot robot) {
		armMotorMovementSupplier.init(robot);
		armServoMovementSupplier.init(robot);
		bucketServoMovementSupplier.init(robot);
		presetSupplier.init(robot);
	}
}
