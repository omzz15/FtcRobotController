package org.firstinspires.ftc.teamcode.positiontracking;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;

public class PositionTrackerSettings extends RobotPartSettings {
	//variables
	float ticksPerInchSideways = 0;
	float ticksPerInchForward = 0;
	Position startPosition = new Position(4,4,0);

	//flags
	public boolean useThread = true;
	public boolean useEncoders = true;
}
