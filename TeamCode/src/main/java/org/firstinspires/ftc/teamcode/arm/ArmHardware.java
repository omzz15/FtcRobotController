package org.firstinspires.ftc.teamcode.arm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;
import org.firstinspires.ftc.teamcode.other.servo.ServoSettings;

public class ArmHardware extends RobotPartHardware {
	public MotorSettings armMotorSettings = new MotorSettings(MotorSettings.Number.ONE, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_TO_POSITION, 1);
	public ServoSettings bucketServoSettings = new ServoSettings(ServoSettings.Number.ONE, Servo.Direction.FORWARD, 0.16);

	DcMotorEx armMotor;
	Servo bucketServo;

	@Override
	public void init(Robot robot){
		armMotor = armMotorSettings.makeMotor(robot.hardwareMap);
		bucketServo = bucketServoSettings.makeServo(robot.hardwareMap);
	}
}
