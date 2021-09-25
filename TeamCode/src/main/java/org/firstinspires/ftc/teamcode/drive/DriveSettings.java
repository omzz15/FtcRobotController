package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public class DriveSettings extends RobotPartSettings {
	////////////
	//settings//
	////////////
	DriveMode driveMode = DriveMode.MECANUM;
	InputSupplier.GamepadNum gamepadNum = InputSupplier.GamepadNum.ONE;
	InputSupplier driveXSupplier = new InputSupplier(gamepad -> (gamepad.left_stick_x));
	InputSupplier driveYSupplier = new InputSupplier(gamepad -> (gamepad.left_stick_y));
	InputSupplier driveRSupplier = new InputSupplier(gamepad -> (gamepad.right_stick_x));

	@Override
	public void init(Robot robot){
		if(gamepadNum == InputSupplier.GamepadNum.ONE) {
			((Drive)robot.getPartByClass(Drive.class)).gamepad = robot.gamepad1;
		}else{
			((Drive)robot.getPartByClass(Drive.class)).gamepad = robot.gamepad2;
		}
	}

	public enum DriveMode{
		TANK,
		MECANUM,
		OMNI
	}
}
