package org.firstinspires.ftc.teamcode.deprecated.test2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

@Deprecated
public class Test2Hardware extends RobotPartHardware {
	MotorSettings motor1Settings = new MotorSettings(MotorSettings.Number.ONE_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);
	MotorSettings motor2Settings = new MotorSettings(MotorSettings.Number.TWO_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);

	DcMotorEx motor1;
	DcMotorEx motor2;

	@Override
	public void init(Robot robot){
		motor1 = motor1Settings.makeMotor(robot.hardwareMap);
		motor2 = motor2Settings.makeMotor(robot.hardwareMap);
	}
}
