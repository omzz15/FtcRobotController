package org.firstinspires.ftc.teamcode.positiontracking;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.DriveSettings;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;

public class PositionTracker extends RobotPart implements Runnable{
	/////////////
	//variables//
	/////////////
	//position
	public volatile Position currentPosition;

	//wheels
	int[] lastMotorPos;
	int[] currMotorPos;

	//thread
	Thread thread;

	//rotation
	volatile Orientation currentAllAxisRotations = new Orientation();
	protected double rotationOffset;

	//angular velocity
	volatile AngularVelocity currentAngularVelocity = new AngularVelocity();

	/////////////////////////
	//constructors and init//
	/////////////////////////
	public PositionTracker(Robot robot, RobotPartHardware hardware, RobotPartSettings settings) {
		super(robot, hardware, settings);
	}

	public PositionTracker(Robot robot){
		super(robot, new PositionTrackerHardware(), new PositionTrackerSettings());
	}

	@Override
	public void init(){
		super.init();
		//makes thread
		if(((PositionTrackerSettings) settings).useThread)
			thread = new Thread(this);
	}

	void setStartPosition(){
		currentPosition = ((PositionTrackerSettings) settings).startPosition;
	}

	//////////////////
	//angle tracking//
	//////////////////
	Orientation getAngles()
	{
		Orientation angles = ((PositionTrackerHardware) hardware).imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
		angles.thirdAngle *= -1;
		angles.thirdAngle -= rotationOffset;
		angles.thirdAngle = (float) Utils.AngleMath.scaleAngle(angles.thirdAngle);
		return angles;
	}

	void updateAngles()
	{
		currentAngularVelocity = ((PositionTrackerHardware) hardware).imu.getAngularVelocity();
		currentAllAxisRotations = getAngles();
		currentPosition.R = currentAllAxisRotations.thirdAngle;
	}

	void resetAngle()
	{
		rotationOffset += currentPosition.R;
	}

	void setAngle(float angle){
		rotationOffset += (currentPosition.R + angle);
	}


	/////////////////////
	//position tracking//
	/////////////////////
	public void initEncoderTracker(){
		lastMotorPos = ((Drive) robot.getPartByClass(Drive.class)).hardware.getMotorPositions();
	}

	void updateEncoderPosition(){
		//get motor difference from last measure
		currMotorPos = ((Drive) robot.getPartByClass(Drive.class)).hardware.getMotorPositions();

		int[] diff = new int[4];

		for(int i = 0; i < 4; i++)
		{
			diff[i] = currMotorPos[i] - lastMotorPos[i];
		}

		//get the X and Y movement of the robot
		double XMove;
		double YMove;
		if(((DriveSettings) settings).driveMode == DriveSettings.DriveMode.MECANUM) {
			XMove = (.25 * (-diff[0] + diff[2] + diff[1] - diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.25 * (diff[0] + diff[2] + diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		}
		else if (((DriveSettings) settings).driveMode == DriveSettings.DriveMode.TANK){
			//experimental
			XMove = 0;
			YMove = (.25 * (diff[0] + diff[1] + diff[2] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		}
		else{
			//experimental
			XMove = (.5 * (diff[1] + diff[3])) / ((PositionTrackerSettings) settings).ticksPerInchSideways;
			YMove = (.5 * (diff[0] + diff[2])) / ((PositionTrackerSettings) settings).ticksPerInchForward;
		}

		//rotate movement and add to robot positionTracker
		currentPosition.X += YMove * Math.sin(currentPosition.R * Math.PI / 180) - XMove * Math.cos(currentPosition.R * Math.PI / 180);
		currentPosition.Y += XMove * Math.sin(currentPosition.R * Math.PI / 180) + YMove * Math.cos(currentPosition.R * Math.PI / 180);

		//update last motor position
		lastMotorPos = currMotorPos;
	}

	/////////////
	//telemetry//
	/////////////
	@Override
	public void addTelemetry(){
		robot.addTelemetry("position", currentPosition.toString());
	}

	//////////
	//Thread//
	//////////
	public void startThread(){
		thread.start();
	}

	@Override
	public void run() {
		setStartPosition();
		if(((PositionTrackerSettings) settings).useEncoders)
			initEncoderTracker();
		while(!thread.isInterrupted()) {
			updateAngles();
			if(((PositionTrackerSettings) settings).useEncoders)
				updateEncoderPosition();
		}
	}

	public void stopThread(){
		thread.interrupt();
	}
}
