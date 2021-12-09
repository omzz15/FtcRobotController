package org.firstinspires.ftc.teamcode.parts.slamra;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.spartronics4915.lib.T265Camera;

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

public class Slamra extends RobotPart {
	/////////////
	//variables//
	/////////////
	public volatile T265Camera slamra = null;

	////////////////
	//constructors//
	////////////////

	public Slamra(Robot robot, SlamraHardware hardware, SlamraSettings settings) {
		super(robot, hardware, settings);
	}

	public Slamra(Robot robot) {
		super(robot, new SlamraHardware(), new SlamraSettings());
	}

	/////////////////////
	//RobotPart Methods//
	/////////////////////
	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		if (slamra == null) {
			slamra = new T265Camera(new Transform2d(), 0.1, robot.hardwareMap.appContext);
		}
		if (!slamra.isStarted()) slamra.start();
	}

	@Override
	public void onStart() {
		if (!slamra.isStarted()) slamra.start();
	}

	@Override
	public void onPause() {

	}

	@Override
	public void onUnpause() {

	}

	@Override
	public void onRunLoop(short runMode) {
		final int robotRadius = 9; // inches

		T265Camera.CameraUpdate up = slamra.getLastReceivedCameraUpdate();
		if (up == null) return;

		//robot.field.clear();
		// We divide by 0.0254 to convert meters to inches
		Translation2d translation = new Translation2d(up.pose.getTranslation().getX() / 0.0254, up.pose.getTranslation().getY() / 0.0254);
		Rotation2d rotation = up.pose.getRotation();

		robot.field.strokeCircle(translation.getX(), translation.getY(), robotRadius);
		double arrowX = rotation.getCos() * robotRadius, arrowY = rotation.getSin() * robotRadius;
		double x1 = translation.getX() + arrowX  / 2, y1 = translation.getY() + arrowY / 2;
		double x2 = translation.getX() + arrowX, y2 = translation.getY() + arrowY;
		robot.field.strokeLine(x1, y1, x2, y2);
	}

	@Override
	public void onAddTelemetry() {
	}

	@Override
	public void onStop() {
		slamra.stop();
	}
}