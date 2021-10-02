package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.other.PID;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTrackerSettings;


public class Movement extends RobotPart {
	public Movement(Robot robot, RobotPartSettings settings) {
		super(robot, null, settings);
	}

	public Movement(Robot robot){
		super(robot, null, new MovementSettings());
	}

	/////////////////
	//turn to angle//
	/////////////////
	public void turnToAngle(double targetAngle, double tolerance, int numberOfTimesToStayInTolerance, int maxTime, double maxSpeed, PIDCoefficients turnPID)
	{
		double currentAngle = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition.R;

		double error = Utils.AngleMath.findAngleError(currentAngle, targetAngle);

		if(Math.abs(error) > tolerance) {
			long startTime = System.currentTimeMillis();

			int numberOfTimesInTolerance = 0;
			PID pid = new PID(turnPID, -maxSpeed, maxSpeed);

			while (numberOfTimesInTolerance < numberOfTimesToStayInTolerance && System.currentTimeMillis() - startTime < maxTime && !shouldStop())
			{
				currentAngle = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition.R;

				error = Utils.AngleMath.findAngleError(currentAngle, targetAngle);
				pid.updatePID(error);

				((Drive) robot.getPartByClass(Drive.class)).moveRobot(0, 0, pid.returnValue(),false,false);

				if (Math.abs(error) < tolerance) numberOfTimesInTolerance++;
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

	////////////////////
	//move to position//
	////////////////////
	public void moveToPosition(double[] targetPos, double[] tol, int timesToStayInTolerance, int maxTime, PIDCoefficients moveXPID, PIDCoefficients moveYPID, PIDCoefficients turnPID, double maxSpeed)
	{
		if(((PositionTrackerSettings) robot.getPartByClass(PositionTracker.class).settings).positionTrackingEnabled() &&  robot.getPartByClass(Drive.class).settings.canRun())
		{

			Position currentPos = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition;

			if (!currentPos.inTolerance(targetPos, tol)) {
				long startTime = System.currentTimeMillis();
				PID xPID = new PID(moveXPID, -maxSpeed, maxSpeed);
				PID yPID = new PID(moveYPID, -maxSpeed, maxSpeed);
				PID rotPID = new PID(turnPID, -maxSpeed, maxSpeed);

				double[] powers = new double[3];

				double errorVectorRot;
				double errorVectorMag;

				int numOfTimesInTolerance = 0;

				while (!shouldStop() && (System.currentTimeMillis() - startTime < maxTime) && (numOfTimesInTolerance < timesToStayInTolerance)) {
					currentPos = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition;

					//calculate the error vector
					errorVectorMag = Math.sqrt(Math.pow((targetPos[0] - currentPos.X), 2) + Math.pow((targetPos[1] - currentPos.Y), 2));
					errorVectorRot = Math.toDegrees(Math.atan2((targetPos[0] - currentPos.X), (targetPos[1] - currentPos.Y)));

					//take out robot rotation
					errorVectorRot -= currentPos.R;
					errorVectorRot = Utils.AngleMath.scaleAngle(errorVectorRot);

					//get the errors comps
					powers[0] = xPID.updatePIDAndReturnValue(errorVectorMag * Math.sin(Math.toRadians(errorVectorRot)));
					powers[1] = yPID.updatePIDAndReturnValue(errorVectorMag * Math.cos(Math.toRadians(errorVectorRot)));
					powers[2] = rotPID.updatePIDAndReturnValue(Utils.AngleMath.findAngleError(currentPos.R, targetPos[2]));

					if (currentPos.inTolerance(targetPos, tol))
						numOfTimesInTolerance++;
					else numOfTimesInTolerance = 0;

					((Drive) robot.getPartByClass(Drive.class)).moveRobot(powers, false,false);


					if(settings.addTelemetry()) {
						robot.addTelemetry("x: ", currentPos.X);
						robot.addTelemetry("y: ", currentPos.Y);
						robot.addTelemetry("rot: ", currentPos.R);
						robot.addTelemetry("error mag: ", errorVectorMag);
						robot.addTelemetry("error rot: ", errorVectorRot);
						robot.sendTelemetry();
					}
				}
			}
			((Drive) robot.getPartByClass(Drive.class)).stopMovement();
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
}
