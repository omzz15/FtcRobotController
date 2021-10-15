package org.firstinspires.ftc.teamcode.arm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.MotorSettings;
import org.firstinspires.ftc.teamcode.other.ServoSettings;

public class ArmHardware extends RobotPartHardware {
	MotorSettings armMotorSettings = new MotorSettings(MotorSettings.Number.ONE_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_USING_ENCODER, 1);
	ServoSettings bucketServoSettings = new ServoSettings(ServoSettings.Number.ONE, Servo.Direction.FORWARD);

	DcMotorEx armMotor;
	Servo bucketServo;

	@Override
	public void init(Robot robot){
		armMotor = armMotorSettings.makeMotor(robot.hardwareMap);
		bucketServo = bucketServoSettings.makeServo(robot.hardwareMap);
	}
}
