package org.firstinspires.ftc.teamcode.parts.positiontracker;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;

public class PositionTrackerHardware extends RobotPartHardware {
	BNO055IMU imu;

	@Override
	public void onInit(Robot robot){
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.mode = BNO055IMU.SensorMode.IMU;
		parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		parameters.calibrationDataFile = "BNO055IMUCalibration.json";
		parameters.loggingEnabled = false;
		parameters.loggingTag = "IMU";

		imu = robot.hardwareMap.get(BNO055IMU.class, "imu");
		imu.initialize(parameters);

		while (!imu.isGyroCalibrated() && !robot.shouldStop())
		{
			robot.opMode.idle();
		}
	}
}