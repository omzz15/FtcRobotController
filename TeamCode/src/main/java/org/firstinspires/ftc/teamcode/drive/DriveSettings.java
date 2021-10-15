package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

public class DriveSettings extends RobotPartSettings {
	////////////
	//settings//
	////////////
	//drive teleop settings
	public DriveMode driveMode = DriveMode.MECANUM;
	boolean useSmoothing = true;
	double[] smoothingValues = new double[]{.1,.1,.1};
	public double speedMultiplier = 1;
	Gamepad gamepad;

	//teleop input settings
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
	InputSupplier driveXSupplier = new InputSupplier(gamepad -> (gamepad.left_stick_x));
	InputSupplier driveYSupplier = new InputSupplier(gamepad -> (-gamepad.left_stick_y));
	InputSupplier driveRSupplier = new InputSupplier(gamepad -> (gamepad.right_stick_x));
	InputSupplier driveStopSupplier = new InputSupplier(gamepad -> (gamepad.x));

	@Override
	public void init(Robot robot){
		gamepad = gamepadNum.getGamepad(robot);
	}

	public enum DriveMode{
		TANK,
		MECANUM,
		OMNI
	}
}
