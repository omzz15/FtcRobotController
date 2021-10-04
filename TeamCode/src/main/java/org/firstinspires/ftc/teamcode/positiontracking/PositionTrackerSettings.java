package org.firstinspires.ftc.teamcode.positiontracking;

import org.firstinspires.ftc.teamcode.basethreaded.RobotThreadedPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;

public class PositionTrackerSettings extends RobotThreadedPartSettings {
	//variables
	float ticksPerInchSideways = 100;
	float ticksPerInchForward = 100;
	boolean flipAngle = false;
	Position startPosition = new Position(0,0,0);

	//flags
	public boolean useEncoders = true;

	public boolean positionTrackingEnabled(){
		return canRun() && (useEncoders);
	}
}
