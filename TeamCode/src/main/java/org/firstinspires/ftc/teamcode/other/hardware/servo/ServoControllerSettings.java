package org.firstinspires.ftc.teamcode.other.hardware.servo;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.other.Range;
import org.firstinspires.ftc.teamcode.other.task.Task;

//TODO fix default values
public class ServoControllerSettings extends  ServoSettings{
	public int maxSpeed;
	public Range positionRange;
	public Range angleRange;
	public RunMode runMode;
	public Task.EndPoint homeFunction;

	public ServoControllerSettings(Number number) {
		super(number);
		maxSpeed = 100;
		positionRange = new Range(0, 1);
		angleRange = new Range(0,180);
		runMode = RunMode.RUN_AT_MAX;
		homeFunction = () -> (true);
	}

	public ServoControllerSettings(Number number, Servo.Direction direction, int maxSpeed) {
		super(number, direction);
		this.maxSpeed = maxSpeed;
		positionRange = new Range(0, 1);
		angleRange = new Range(0,180);
		runMode = RunMode.RUN_AT_MAX;
		homeFunction = () -> (true);
	}

	public ServoControllerSettings(Number number, Servo.Direction direction, Double targetPos, Range positionRange, Range angleRange, int maxSpeed, RunMode runMode, Task.EndPoint homeFunction) {
		super(number, direction, targetPos);
		this.maxSpeed = maxSpeed;
		this.runMode = runMode;
		this.positionRange = positionRange;
		this.angleRange = angleRange;
		this.homeFunction = homeFunction;
	}

	public void setHomeFunction(Task.EndPoint homeFunction){
		this.homeFunction = homeFunction;
	}

	public enum RunMode{
		RUN_AT_MAX,
		RUN_AT_RATE,
		RUN_AT_RATE_WITH_CORRECTION,
		RUN_WITH_TIME
	}
}
