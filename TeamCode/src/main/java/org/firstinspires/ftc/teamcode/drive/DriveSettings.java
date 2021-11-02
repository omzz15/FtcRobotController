package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

public class DriveSettings extends RobotPartSettings {
	////////////
	//settings//
	////////////
	//drive teleop settings
	public DriveMode driveMode = DriveMode.MECANUM;
	boolean useSmoothing = false;
	double[] smoothingValues = new double[]{.1,.1,.1};
	public double speedMultiplier = 1;

	//teleop input settings
	InputSupplier driveXSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.left_stick_x));
	InputSupplier driveYSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (-gamepad.left_stick_y));
	InputSupplier driveRSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.right_stick_x));
	InputSupplier driveStopSupplier = new InputSupplier(Utils.GamepadNum.ONE, gamepad -> (gamepad.x));

	@Override
	public void init(Robot robot){
		driveXSupplier.init(robot);
		driveYSupplier.init(robot);
		driveRSupplier.init(robot);
		driveStopSupplier.init(robot);
	}

	public enum DriveMode{
		TANK,
		MECANUM,
		OMNI
	}
}
