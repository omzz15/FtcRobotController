package org.firstinspires.ftc.teamcode.other.hardware.motor;

import org.firstinspires.ftc.teamcode.other.Range;
import org.firstinspires.ftc.teamcode.other.task.Task;

public class MotorControllerSettings {
	public Range positionEnds;
	public int movementSpeed;
	public int tolerance;

	public MotorControllerSettings(Range positionEnds){
		construct(positionEnds, 100, 10);
	}

	public MotorControllerSettings(Range positionEnds, int movementSpeed, int tolerance){
		construct(positionEnds, movementSpeed, tolerance);
	}

	private void construct(Range positionEnds, int movementSpeed, int tolerance){
		this.positionEnds = positionEnds;
		this.movementSpeed = movementSpeed;
		this.tolerance = tolerance;
	}
}
