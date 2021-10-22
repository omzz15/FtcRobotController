package org.firstinspires.ftc.teamcode.deprecated.test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

@Deprecated
public class TestHardware extends RobotPartHardware {
	MotorSettings motor1Settings = new MotorSettings(MotorSettings.Number.ONE_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);

	DcMotorEx motor1;

	@Override
	public void init(Robot robot){
		motor1 = motor1Settings.makeMotor(robot.hardwareMap);
	}
}
