package org.firstinspires.ftc.teamcode.parts.positiontracker;

//import com.arcrobotics.ftclib.geometry.Rotation2d;
//import com.arcrobotics.ftclib.geometry.Transform2d;
//import com.arcrobotics.ftclib.geometry.Translation2d;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.spartronics4915.lib.T265Camera;
import com.spartronics4915.lib.T265Helper;

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
	private Position encoderPosition;
	private Pose2d slamraPosition;
	private Position currentPosition;

	//wheels
	private int[] lastMotorPos;
	private int[] currMotorPos;

	//slamra
	private volatile T265Camera slamera = null;

	//rotation
	private Orientation currentAllAxisRotations = new Orientation();
	private double rotationOffset;

	//angular velocity
	private AngularVelocity currentAngularVelocity = new AngularVelocity();


	////////////////
	//constructors//
	////////////////
	public PositionTracker(Robot robot, PositionTrackerHardware hardware, PositionTrackerSettings settings) {
		super(robot, hardware, settings);
	}

	public PositionTracker(Robot robot) {
		super(robot, new PositionTrackerHardware(), new PositionTrackerSettings());
	}

	////////////////////
	//accessor methods//
	////////////////////
	public AngularVelocity getCurrentAngularVelocity(){
		return currentAngularVelocity;
	}

	public Orientation getCurrentAllAxisRotations(){
		return currentAllAxisRotations;
	}

	public Position getCurrentPosition(){
		return currentPosition;
	}

	////////
	//init//
	////////
	void setStartPosition() {
		updateAngles();
		setAngle((float) ((PositionTrackerSettings) settings).startPosition.R);
		currentPosition = ((PositionTrackerSettings) settings).startPosition;
		encoderPosition = ((PositionTrackerSettings) settings).encoderStartPosition;
		slamraPosition = ((PositionTrackerSettings) settings).slamraStartPosition;
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
		encoderPosition.R = currentAllAxisRotations.thirdAngle;
	}

	public void resetAngle() {
		setAngle(0);
	}

	public void setAngle(float angle) {
		updateAngles();
		rotationOffset += encoderPosition.R - angle;
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
		encoderPosition.X += YMove * java.lang.Math.sin(encoderPosition.R * java.lang.Math.PI / 180) - XMove * java.lang.Math.cos(encoderPosition.R * java.lang.Math.PI / 180);
		encoderPosition.Y += XMove * java.lang.Math.sin(encoderPosition.R * java.lang.Math.PI / 180) + YMove * java.lang.Math.cos(encoderPosition.R * java.lang.Math.PI / 180);

		//update last motor position
		lastMotorPos = currMotorPos;
	}


	//////////////////
	//slamra tacking//
	//////////////////
	//init
	void initSlamra() {
		if (slamera == null) {
			slamera = T265Helper.getCamera(
					new T265Camera.OdometryInfo(
							((PositionTrackerSettings) settings).robotOffset,.1
					), robot.hardwareMap.appContext);
		}
		slamera.setPose(((PositionTrackerSettings) settings).slamraStartPosition);
		if (!slamera.isStarted()) slamera.start();
	}

	//start
	void startSlamra(){

	}

	//get
	void updateSlamraPosition() {
		TelemetryPacket packet = new TelemetryPacket();
		T265Camera.CameraUpdate up = slamera.getLastReceivedCameraUpdate();
		if (up == null) return;
		Pose2d update = up.pose;
		slamraPosition = new Pose2d(update.getX(), update.getY(), update.getHeading());
	}

	//stop
	void stopSlamra(){

	}

	/////////////////////
	//RobotPart Methods//
	/////////////////////
	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		encoderPosition = new Position();
		if (((PositionTrackerSettings) settings).useSlamra)
			initSlamra();
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
			if (((PositionTrackerSettings) settings).useEncoders) {
				updateEncoderPosition();
				//currentPosition.X = encoderPosition.X;
				//currentPosition.Y = encoderPosition.Y;
				//currentPosition.R = encoderPosition.R;
			}

			if (((PositionTrackerSettings) settings).useSlamra) {
				updateSlamraPosition();
				currentPosition.X = slamraPosition.getX();
				currentPosition.Y = slamraPosition.getY();
				currentPosition.R = slamraPosition.getHeading();
			}

		}
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("position", currentPosition.toString());
		robot.addTelemetry("encoder Pos", encoderPosition.toString());
		if (((PositionTrackerSettings) settings).useSlamra)
			robot.addTelemetry("slamera Pos", slamraPosition.toString());
	}

	@Override
	public void onStop() {
		if (((PositionTrackerSettings) settings).useSlamra)
			stopSlamra();
	}
}