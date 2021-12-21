package org.firstinspires.ftc.teamcode.other.hardware.motor;

import org.firstinspires.ftc.teamcode.other.Range;
import org.firstinspires.ftc.teamcode.other.task.Task;

public class MotorControllerSettings {
	public Range positionEnds;
	public int movementSpeed;
	public int tolerance;
	public Task.EndPoint homeFunction;

	public MotorControllerSettings(Range positionEnds){
		construct(positionEnds, 100, 10, () -> (true));
	}

	public MotorControllerSettings(Range positionEnds, int movementSpeed, int tolerance, Task.EndPoint homeFunction){
		construct(positionEnds, movementSpeed, tolerance, homeFunction);
	}

	private void construct(Range positionEnds, int movementSpeed, int tolerance, Task.EndPoint homeFunction){
		this.positionEnds = positionEnds;
		this.movementSpeed = movementSpeed;
		this.tolerance = tolerance;
		this.homeFunction = homeFunction;
	}

	public void setHomeFunction(Task.EndPoint homeFunction){
		this.homeFunction = homeFunction;
	}
}
