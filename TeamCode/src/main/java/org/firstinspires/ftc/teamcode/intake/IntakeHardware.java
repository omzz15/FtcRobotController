package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

public class IntakeHardware extends RobotPartHardware {
	MotorSettings intakeMotorSettings = new MotorSettings(MotorSettings.Number.TWO, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);

	DcMotorEx intakeMotor;

	@Override
	public void init(Robot robot){
		intakeMotor = intakeMotorSettings.makeMotor(robot.hardwareMap);
	}
}
