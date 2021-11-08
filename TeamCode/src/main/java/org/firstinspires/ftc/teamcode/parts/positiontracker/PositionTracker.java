package org.firstinspires.ftc.teamcode.parts.positiontracker;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.drive.DriveSettings;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;

public class PositionTracker extends RobotPart {
	/////////////
	//variables//
	/////////////
	//position
	public volatile Position currentPosition;

	//wheels
	int[] lastMotorPos;
	int[] currMotorPos;

	//rotation
	volatile Orientation currentAllAxisRotations = new Orientation();
	protected double rotationOffset;

	//angular velocity
	volatile AngularVelocity currentAngularVelocity = new AngularVelocity();


	////////////////
	//constructors//
	////////////////
	public PositionTracker(Robot robot, PositionTrackerHardware hardware, PositionTrackerSettings settings) {
		super(robot, hardware, settings);
	}

	public PositionTracker(Robot robot) {
		super(robot, new PositionTrackerHardware(), new PositionTrackerSettings());
	}


	////////
	//init//
	////////
	void setStartPosition() {
		updateAngles();
		setAngle((float) ((PositionTrackerSettings) settings).startPosition.R);
		currentPosition = ((PositionTrackerSettings) settings).startPosition;
	}


	//////////////////
	//angle tracking//
	//////////////////
	Orientation getAngles() {
		Orientation angles = ((PositionTrackerHardware) hardware).imu.getAngularOrientation(AxesReference.EXTRINSIC, ((PositionTrackerSettings) settings).axesOrder, AngleUnit.DEGREES);
		if (((PositionTrackerSettings) settings).flipAngle)
			angles.thirdAngle *= -1;
		angles.thirdAngle -= rotationOffset;
		angles.thirdAngle = (float) Utils.Math.scaleAngle(angles.thirdAngle);
		return angles;
	}
	void updateAngles() {
		currentAngularVelocity = ((PositionTrackerHardware) hardware).imu.getAngularVelocity();
		currentAllAxisRotations = getAngles();
		currentPosition.R = currentAllAxisRotations.thirdAngle;
	}

	public void resetAngle() {
		setAngle(0);
	}

	public void setAngle(float angle) {
		updateAngles();
		rotationOffset += currentPosition.R - angle;
	}


	/////////////////////////////
	//encoder position tracking//
	/////////////////////////////
	//init
	public void initEncoderTracker() {
		lastMotorPos = ((Drive) robot.getPartByClass(Drive.class)).hardware.getMotorPositions("drive motors");
	}
	//update
	void updateEncoderPosition() {
		//get motor difference from last measure
		currMotorPos = ((Drive) robot.getPartByClass(Drive.class)).hardware.getMotorPositions("drive motors");

		int[] diff = new int[4];

		for (int i = 0; i < 4; i++) {
			diff[i] = currMotorPos[i] - lastMotorPos[i];
		}

		//get the X and Y movement of the robot
		double XMove;
		double YMove;
		if (((DriveSettings) ((Drive) robot.getPartByClass(Drive.class)).settings).driveMode == DriveSettings.DriveMode.MECANUM) {
			XMove = (.25 * (-diff[0] + diff[2] + diff[1] - diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.25 * (diff[0] + diff[2] + diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		} else if (((DriveSettings) ((Drive) robot.getPartByClass(Drive.class)).settings).driveMode == DriveSettings.DriveMode.TANK) {
			//experimental
			XMove = 0;
			YMove = (.25 * (diff[0] + diff[1] + diff[2] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		} else {
			//experimental
			XMove = (.5 * (diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.5 * (diff[0] + diff[2])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		}

		//rotate movement and add to robot positionTracker
		currentPosition.X += YMove * java.lang.Math.sin(currentPosition.R * java.lang.Math.PI / 180) - XMove * java.lang.Math.cos(currentPosition.R * java.lang.Math.PI / 180);
		currentPosition.Y += XMove * java.lang.Math.sin(currentPosition.R * java.lang.Math.PI / 180) + YMove * java.lang.Math.cos(currentPosition.R * java.lang.Math.PI / 180);

		//update last motor position
		lastMotorPos = currMotorPos;
	}


	/////////////////////
	//RobotPart Methods//
	/////////////////////
	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		currentPosition = new Position();
		settings.runMode = 0;
	}

	@Override
	public void onStart() {
		setStartPosition();
		if(((PositionTrackerSettings) settings).useEncoders)
			initEncoderTracker();
	}

	@Override
	public void onPause() {

	}

	@Override
	public void onUnpause() {

	}

	@Override
	public void onRunLoop(short runMode) {
		if(runMode == 1) {
			updateAngles();
			if (((PositionTrackerSettings) settings).useEncoders)
				updateEncoderPosition();
		}
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("position", currentPosition.toString());
	}

	@Override
	public void onStop() {

	}
}