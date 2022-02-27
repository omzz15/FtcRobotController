package org.firstinspires.ftc.teamcode.parts.positiontracker;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.spartronics4915.lib.T265Camera;
import com.spartronics4915.lib.T265Helper;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.drive.DriveSettings;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

public class PositionTracker extends RobotPart {
	/////////////
	//variables//
	/////////////
	//position
	private Position encoderPosition;
	private Position slamraPosition;
	private Position visionPosition;
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
		super("Position Tracker", robot, hardware, settings);
	}

	public PositionTracker(Robot robot) {
		super("Position Tracker", robot, new PositionTrackerHardware(), new PositionTrackerSettings());
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
		encoderPosition = ((PositionTrackerSettings) settings).startPosition;
		visionPosition = ((PositionTrackerSettings) settings).startPosition;
		slamraPosition = ((PositionTrackerSettings) settings).startPosition;
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
		lastMotorPos = robot.getPartByClass(Drive.class).hardware.getMotorPositions("drive motors");
	}
	//update
	void updateEncoderPosition() {
		Drive d = (Drive) robot.getPartByClass(Drive.class);

		//get motor difference from last measure
		currMotorPos = d.hardware.getMotorPositions("drive motors");

		int[] diff = new int[4];

		for (int i = 0; i < 4; i++) {
			diff[i] = currMotorPos[i] - lastMotorPos[i];
		}

		//get the X and Y movement of the robot
		double XMove;
		double YMove;
		if (((DriveSettings) d.settings).driveMode == DriveSettings.DriveMode.MECANUM) {
			XMove = (.25 * (-diff[0] + diff[2] + diff[1] - diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.25 * (diff[0] + diff[2] + diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		} else if (((DriveSettings) d.settings).driveMode == DriveSettings.DriveMode.TANK) {
			//experimental
			XMove = 0;
			YMove = (.25 * (diff[0] + diff[1] + diff[2] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		} else {
			//experimental
			XMove = (.5 * (diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.5 * (diff[0] + diff[2])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		}

		//rotate movement and add to robot positionTracker
		encoderPosition.R = currentAllAxisRotations.thirdAngle;
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
							((PositionTrackerSettings) settings).robotOffset,
							((PositionTrackerSettings) settings).odometryCovariance
					), robot.hardwareMap.appContext);
		}
		if (!slamera.isStarted()) slamera.start();
	}

	//start
	void startSlamra(){
		slamera.setPose(((PositionTrackerSettings)settings).startPosition.toPose2d(true));
	}

	//get
	void updateSlamraPosition() {

	}

	//stop
	void stopSlamra(){

	}

	public void DrawOnDashboard  (Position pos, Canvas field) {
		field.clear();
		final int robotRadius = 9; // inches
		final int robotEdge = 18;

		field.strokeCircle(pos.X, pos.Y, robotRadius);
		//field.fillRect(pos.X, pos.Y,robotEdge,robotEdge);

		double arrowX = Math.cos(Math.toRadians(pos.R)) * robotRadius, arrowY = Math.sin(Math.toRadians(pos.R)) * robotRadius;
		double x1 = pos.X + arrowX  / 2, y1 = pos.Y + arrowY / 2;
		double x2 = pos.X + arrowX, y2 = pos.Y + arrowY;
		field.strokeLine(x1, y1, x2, y2);
	}


	//////////
	//vision//
	//////////
	void updateVisionPosition(){
		Vision v = (Vision) robot.getPartByClass(Vision.class);
		if(v != null && v.newPositionAvailable){
			visionPosition = v.getPosition();
		}
	}


	/////////////
	//main code//
	/////////////
	public void addMainTask(){
		Task t = new Task();
		t.addStep(() -> {run();}, () -> (false));
		getTaskRunner().addTask("Main", t, true, true);
	}

	public void run() {
		updateAngles();
		if (((PositionTrackerSettings) settings).useEncoders)
			updateEncoderPosition();

		if(((PositionTrackerSettings) settings).useVision)
			updateVisionPosition();

		//TODO combine all positions for current pos
		if (((PositionTrackerSettings) settings).useSlamra) {
			updateSlamraPosition();
		}
	}

	/////////////////////
	//RobotPart Methods//
	/////////////////////

	@Override
	public void onInit() {
		if (((PositionTrackerSettings) settings).useSlamra)
			initSlamra();
		//NOTE moved from onStart - make sure it works
		if(((PositionTrackerSettings) settings).useEncoders)
			initEncoderTracker();
		addMainTask();
	}

	@Override
	public void onStart() {
		setStartPosition();

		if(((PositionTrackerSettings) settings).useSlamra)
			startSlamra();
	}

	@Override
	public void onPause() {

	}

	@Override
	public void onUnpause() {

	}

	@Override
	public void telemetry() {
		robot.addTelemetry("position", currentPosition.toString());
		robot.addTelemetry("encoder Pos", encoderPosition.toString());
		robot.addTelemetry("vision Pos", visionPosition.toString());
		robot.addTelemetry("slamra Pos", slamraPosition.toString());
		DrawOnDashboard(currentPosition, robot.field);
	}

	@Override
	public void onStop() {
		if (((PositionTrackerSettings) settings).useSlamra)
			stopSlamra();
	}
}