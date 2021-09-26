package org.firstinspires.ftc.teamcode.positiontracking;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.RobotPartHardware;

public class PositionTrackerHardware extends RobotPartHardware {
	BNO055IMU imu;

	@Override
	public void init(HardwareMap hardwareMap){
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.mode = BNO055IMU.SensorMode.IMU;
		parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		parameters.calibrationDataFile = "BNO055IMUCalibration.json";
		parameters.loggingEnabled = false;
		parameters.loggingTag = "IMU";

		imu = hardwareMap.get(BNO055IMU.class, "imu");
		imu.initialize(parameters);
	}
}
