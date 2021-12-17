package org.firstinspires.ftc.teamcode.other.hardware.motor;

import org.firstinspires.ftc.teamcode.other.EndPoints;

public class MotorControllerSettings {
	public EndPoints positionEnds;
	public int movementSpeed;
	public int tolerance;

	public MotorControllerSettings(EndPoints positionEnds){
		construct(positionEnds, 100, 10);
	}

	public MotorControllerSettings(EndPoints positionEnds, int movementSpeed, int tolerance){
		construct(positionEnds, movementSpeed, tolerance);
	}

	private void construct(EndPoints positionEnds, int movementSpeed, int tolerance){
		this.positionEnds = positionEnds;
		this.movementSpeed = movementSpeed;
		this.tolerance = tolerance;
	}
}
