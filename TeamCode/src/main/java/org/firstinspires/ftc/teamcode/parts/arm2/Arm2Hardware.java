package org.firstinspires.ftc.teamcode.parts.arm2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;
import org.firstinspires.ftc.teamcode.other.servo.ServoSettings;
//TODO finish settings for hardware
public class Arm2Hardware extends RobotPartHardware {
	////////////
	//settings//
	////////////
	public MotorSettings armMotorSettings = new MotorSettings(MotorSettings.Number.ONE_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_TO_POSITION, 0.6);
	public ServoSettings armServoSettings = new ServoSettings(ServoSettings.Number.ONE, Servo.Direction.REVERSE);
	public ServoSettings bucketServoSettings = new ServoSettings(ServoSettings.Number.THREE, Servo.Direction.REVERSE);

	///////////
	//objects//
	///////////
	DcMotor armMotor;
	Servo armServo;
	Servo bucketServo;

	@Override
	public void onInit(Robot robot) {
		armMotor = armMotorSettings.makeMotor(robot.hardwareMap);
		armServo = armServoSettings.makeServo(robot.hardwareMap);
		bucketServo = bucketServoSettings.makeServo(robot.hardwareMap);
	}
}
