package org.firstinspires.ftc.teamcode.parts.positiontracker;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;

public class PositionTrackerSettings extends RobotPartSettings {
	//angle tracking
	boolean flipAngle = true;
	AxesOrder axesOrder = AxesOrder.XYZ; // the last axis should be the rotation of the robot

	// Start position of robot on field in Autonomous (need an autonomous flag here)
	Position startPosition = new Position(0,0,0);

	//encoder tracking
	public boolean useEncoders = true;
	static public float ticksPerInchSideways = 55;
	static public float ticksPerInchForward = 48;

	//slamra tracking
	public boolean useSlamra = true;
	double odometryCovariance = 0.1;
	Pose2d robotOffset = new Pose2d();

	//vision tracking
	public boolean useVision = true;

	public boolean positionTrackingEnabled(){
		return isRunning() && (useEncoders || useSlamra || useVision);
	}

	@Override
	public void onInit(Robot robot) {
		makeTeleOpCodeTask = false;
	}
}