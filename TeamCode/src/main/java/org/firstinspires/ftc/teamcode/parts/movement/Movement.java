package org.firstinspires.ftc.teamcode.parts.movement;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.other.PID;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.positiontracking.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.positiontracking.PositionTrackerSettings;


public class Movement extends RobotPart {
	Position currentPos;
	double[] targetPos = new double[3];
	double[] tol = new double[3];
	long startTime;
	int maxTime;
	double[] powers = new double[3];
	double errorVectorRot;
	double errorVectorMag;
	int numOfTimesInTolerance;
	int timesToStayInTolerance;


	PID xPID;
	PID yPID;
	PID rPID;


	public Movement(Robot robot, MovementSettings settings) {
		super(robot, null, settings);
	}

	public Movement(Robot robot){
		super(robot, null, new MovementSettings());
	}

	/////////////////
	//turn to angle//
	/////////////////
	/*
	public void turnToAngle(double targetAngle, double tolerance, int numberOfTimesToStayInTolerance, int maxTime, double maxSpeed, PIDCoefficients turnPID)
	{
		double currentAngle = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition.R;

		double error = Utils.Math.findAngleError(currentAngle, targetAngle);

		if(java.lang.Math.abs(error) > tolerance) {
			long startTime = System.currentTimeMillis();

			int numberOfTimesInTolerance = 0;
			PID pid = new PID(turnPID, -maxSpeed, maxSpeed);

			while (numberOfTimesInTolerance < numberOfTimesToStayInTolerance && System.currentTimeMillis() - startTime < maxTime && !shouldStop())
			{
				currentAngle = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition.R;

				error = Utils.Math.findAngleError(currentAngle, targetAngle);
				pid.updatePID(error);

				((Drive) robot.getPartByClass(Drive.class)).moveRobot(0, 0, pid.returnValue(),false,false);

				if (java.lang.Math.abs(error) < tolerance) numberOfTimesInTolerance++;
				else numberOfTimesInTolerance = 0;
			}

			((Drive) robot.getPartByClass(Drive.class)).stopMovement();
		}
	}

	public void turnToAngle(double targetAngle, double tolerance, int numberOfTimesToStayInTolerance, int maxRuntime, double maxSpeed) {
		turnToAngle(targetAngle, tolerance, numberOfTimesToStayInTolerance, maxRuntime, maxSpeed, ((MovementSettings) settings).turnPID);
	}

	public void turnToAngle(double targetAngle, RotToAngleSettings rtas)
	{
		if(!rtas.isPIDValid()){rtas.turnPID = ((MovementSettings) settings).turnPID;}
		turnToAngle(targetAngle, rtas.tol, rtas.timesInTol, rtas.maxRuntime, rtas.maxPower, rtas.turnPID);
	}

	 */

	////////////////////
	//move to position//
	////////////////////
	public void moveToPosition(double[] targetPos, double[] tol, int timesToStayInTolerance, int maxTime, PIDCoefficients moveXPID, PIDCoefficients moveYPID, PIDCoefficients turnPID, double maxSpeed)
	{
		if(((PositionTrackerSettings) robot.getPartByClass(PositionTracker.class).settings).positionTrackingEnabled() &&  robot.getPartByClass(Drive.class).settings.canRun())
		{
			currentPos = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition;

			if (!currentPos.inTolerance(targetPos, tol)) {
				xPID = new PID(moveXPID, -maxSpeed, maxSpeed);
				yPID = new PID(moveYPID, -maxSpeed, maxSpeed);
				rPID = new PID(turnPID, -maxSpeed, maxSpeed);

				numOfTimesInTolerance = 0;
				this.timesToStayInTolerance = timesToStayInTolerance;
				startTime = System.currentTimeMillis();
				this.maxTime = maxTime;

				this.targetPos = targetPos;
				this.tol = tol;

				robot.getPartByClass(Drive.class).settings.runMode = -1;
				settings.runMode = 1;
			}
		}
		else if(settings.addTelemetry()) robot.addTelemetry("error in Movement.moveToPosition: ", "robot can not move to positionTracker because it does not know its positionTracker");
	}

	public void moveToPosition(double[] targetPos, double[] tol, int timesToStayInTolerance, int maxTime, double maxSpeed) {
		moveToPosition(targetPos, tol, timesToStayInTolerance, maxTime, ((MovementSettings) settings).moveXPID, ((MovementSettings) settings).moveYPID, ((MovementSettings) settings).turnPID, maxSpeed);
	}

	public void moveToPosition(Position targetPos, double[] tol, int timesToStayInTolerance, int maxTime, double maxSpeed) {
		moveToPosition(targetPos.toArray(), tol, timesToStayInTolerance, maxTime, maxSpeed);
	}

	public void moveToPosition(Position targetPos, Position tol, int timesToStayInTolerance, int maxTime, double maxSpeed){
		moveToPosition(targetPos.toArray(), tol.toArray(), timesToStayInTolerance, maxTime, maxSpeed);
	}

	public void moveToPosition(double[] targetPos, MoveToPosSettings mtps)
	{
		if(mtps.isPIDValid()) moveToPosition(targetPos, mtps.tol, mtps.timesInTol, mtps.maxRuntime, mtps.xPID, mtps.yPID, mtps.turnPID, mtps.maxPower);
		else moveToPosition(targetPos, mtps.tol, mtps.timesInTol, mtps.maxRuntime, mtps.maxPower);
	}

	public void moveToPosition(Position targetPos, MoveToPosSettings mtps){
		moveToPosition(targetPos.toArray(), mtps);
	}

	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		settings.runMode = 0;
	}

	@Override
	public void onTeleOpLoop() {
		currentPos = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition;

		//calculate the error vector
		errorVectorMag = java.lang.Math.sqrt(java.lang.Math.pow((targetPos[0] - currentPos.X), 2) + java.lang.Math.pow((targetPos[1] - currentPos.Y), 2));
		errorVectorRot = java.lang.Math.toDegrees(java.lang.Math.atan2((targetPos[0] - currentPos.X), (targetPos[1] - currentPos.Y)));

		//take out robot rotation
		errorVectorRot -= currentPos.R;
		errorVectorRot = Utils.Math.scaleAngle(errorVectorRot);

		//get the errors comps
		powers[0] = xPID.updatePIDAndReturnValue(errorVectorMag * java.lang.Math.sin(java.lang.Math.toRadians(errorVectorRot)));
		powers[1] = yPID.updatePIDAndReturnValue(errorVectorMag * java.lang.Math.cos(java.lang.Math.toRadians(errorVectorRot)));
		powers[2] = rPID.updatePIDAndReturnValue(Utils.Math.findAngleError(currentPos.R, targetPos[2]));

		if (currentPos.inTolerance(targetPos, tol))
			numOfTimesInTolerance++;
		else numOfTimesInTolerance = 0;

		((Drive) robot.getPartByClass(Drive.class)).moveRobot(powers, false,false);

		if((System.currentTimeMillis() - startTime > maxTime) || (numOfTimesInTolerance > timesToStayInTolerance))
			stop();
	}

	@Override
	public void onRunLoop(short runMode) {

	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("x: ", currentPos.X);
		robot.addTelemetry("y: ", currentPos.Y);
		robot.addTelemetry("rot: ", currentPos.R);
		robot.addTelemetry("error mag: ", errorVectorMag);
		robot.addTelemetry("error rot: ", errorVectorRot);
		robot.sendTelemetry();
	}

	@Override
	public void onStop() {
		((Drive) robot.getPartByClass(Drive.class)).stopMovement();
	}
}
