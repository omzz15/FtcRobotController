package org.firstinspires.ftc.teamcode.other.servo;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.other.EndPoints;

public class ServoExSettings extends  ServoSettings{
	public int maxSpeed;
	public RunMode runMode;

	public ServoExSettings(Number number) {
		super(number);
		maxSpeed = 100;
	}

	public ServoExSettings(Number number, Servo.Direction direction, int maxSpeed) {
		super(number, direction);
		this.maxSpeed = maxSpeed;
	}

	public ServoExSettings(Number number, Servo.Direction direction, Double targetPos, EndPoints endPoints, int maxSpeed, RunMode runMode) {
		super(number, direction, targetPos, endPoints);
		this.maxSpeed = maxSpeed;
		this.runMode = runMode;
	}

	public enum RunMode{
		RUN_AT_MAX,
		RUN_AT_RATE,
		RUN_WITH_TIME
	}
}
