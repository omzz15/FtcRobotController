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
	public boolean useEncoders = true;
	static public float ticksPerInchSideways = 55;
	static public float ticksPerInchForward = 48;

	//slamra tracking
	public boolean useSlamra = true;
	int robotRadius = 9; //in inches
	double odometryCovariance = 0.1;
	Position slamraRobotOffset = new Position(0,0,0);

	//general
	Position startPosition = new Position(0,0,0);
	Position slamraStartPosition = new Position(0,0,0);
	Position encoderStartPosition = new Position(0,0,0);

	public boolean positionTrackingEnabled(){
		return runMode > 0 && (useEncoders);
	}

	@Override
	public void onInit(Robot robot) { startPosition.toPose2d(false);
	}
}