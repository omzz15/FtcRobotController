package org.firstinspires.ftc.teamcode.parts.duckspinner;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

public class DuckSpinnerHardware extends RobotPartHardware {
	public MotorSettings duckSpinnerMotorSettings = new MotorSettings(MotorSettings.Number.THREE, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);

	DcMotor duckSpinnerMotor;

	@Override
	public void onInit(Robot robot){
		duckSpinnerMotor = duckSpinnerMotorSettings.makeMotor(robot.hardwareMap);
	}
}