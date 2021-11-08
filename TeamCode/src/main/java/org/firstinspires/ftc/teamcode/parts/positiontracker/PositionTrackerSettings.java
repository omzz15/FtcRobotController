package org.firstinspires.ftc.teamcode.parts.positiontracker;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;

public class PositionTrackerSettings extends RobotPartSettings {
	////////////
	//settings//
	////////////
	//angle tracking
	boolean flipAngle = true;
	AxesOrder axesOrder = AxesOrder.XYZ; // the last axis should be the rotation of the robot

	//encoder tracking
	float ticksPerInchSideways = 100;
	float ticksPerInchForward = 100;

	//general
	Position startPosition = new Position(0,0,0);

	//flags
	public boolean useEncoders = true;


	public boolean positionTrackingEnabled(){
		return runMode > 0 && (useEncoders);
	}

	@Override
	public void onInit(Robot robot) {

	}
}