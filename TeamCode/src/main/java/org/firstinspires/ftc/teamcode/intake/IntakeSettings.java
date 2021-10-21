package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.other.MotorSettings;
import org.firstinspires.ftc.teamcode.other.ServoSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

public class IntakeSettings extends RobotPartSettings {
	//input stuff
	Utils.GamepadNum gamepadNum = Utils.GamepadNum.TWO;
	InputSupplier intakePowerSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger));

	@Override
	public void init(Robot robot) {
		gamepad = gamepadNum.getGamepad(robot);
	}
}
