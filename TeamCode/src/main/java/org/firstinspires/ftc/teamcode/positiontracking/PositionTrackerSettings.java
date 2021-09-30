package org.firstinspires.ftc.teamcode.positiontracking;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;

public class PositionTrackerSettings extends RobotPartSettings {
	//variables
	float ticksPerInchSideways = 100;
	float ticksPerInchForward = 100;
	boolean flipAngle = false;
	Position startPosition = new Position(0,0,0);

	//flags
	public boolean useThread = true;
	public boolean useEncoders = true;
}
