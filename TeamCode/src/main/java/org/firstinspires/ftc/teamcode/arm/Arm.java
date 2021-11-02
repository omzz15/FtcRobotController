package org.firstinspires.ftc.teamcode.arm;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.deprecated.test.TestHardware;
import org.firstinspires.ftc.teamcode.deprecated.test.TestSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.List;

public class Arm extends RobotPart {

	int motorPosition = 0;
	double servoPosition = 0;
	public boolean bucketDocked = false;

	public Arm(Robot robot, ArmHardware hardware, ArmSettings settings) {
		super(robot, hardware, settings);
	}

	public Arm(Robot robot){
		super(robot, new ArmHardware(), new ArmSettings());
	}

	@Override
	public void init() {
		super.init();
		servoPosition = ((ArmHardware) hardware).bucketServo.getPosition();
	}

	public void teleOpRunCode(){
		if(((ArmSettings) settings).bucketDockSupplier.getBoolean())
			dockBucket(true);
		else if(((ArmSettings) settings).bucketCradleSupplier.getBoolean())
			cradleBucket(false);
		else {
			//arm
			int preset = ((ArmSettings) settings).armPresetSupplier.getInt();
			if (preset > 0)
				motorPosition = ((ArmSettings) settings).armPresets[preset - 1];
			else
				motorPosition += ((ArmSettings) settings).armMovementSupplier.getFloat() * (((ArmSettings) settings).armMovementSpeed);
			motorPosition = Utils.Math.capInt(motorPosition, ((ArmSettings) settings).armMinPos, ((ArmSettings) settings).armMaxPos);
			((ArmHardware) hardware).armMotor.setTargetPosition(motorPosition);

			//bucket
			preset = ((ArmSettings) settings).bucketPresetSupplier.getInt();
			if (preset > 0)
				servoPosition = ((ArmSettings) settings).bucketPresets[preset - 1];
			else
				servoPosition += ((ArmSettings) settings).bucketSpeed * ((ArmSettings) settings).bucketMovementSupplier.getInt();
			servoPosition = Utils.Math.capDouble(servoPosition, ((ArmSettings) settings).servoMinPos, ((ArmSettings) settings).servoMaxPos);
			((ArmHardware) hardware).bucketServo.setPosition(servoPosition);
		}
	}

	public void cradleBucket(boolean waitToFinish){
		((ArmHardware) hardware).bucketServo.setPosition(((ArmSettings) settings).bucketPresets[3]);
		robot.delay(((ArmSettings) settings).servoMoveTime);
		servoPosition = ((ArmSettings) settings).bucketPresets[3];
		((ArmHardware) hardware).armMotor.setTargetPosition(((ArmSettings) settings).armPresets[3]);
		if(waitToFinish)
			hardware.waitForMotor(((ArmHardware) hardware).armMotor);
		motorPosition = ((ArmSettings) settings).armPresets[3];
		bucketDocked = false;
	}

	public void dockBucket(boolean waitToFinish){
		((ArmHardware) hardware).armMotor.setTargetPosition(((ArmSettings) settings).armPresets[0]);
		if(waitToFinish)
			while(((ArmHardware) hardware).armMotor.getCurrentPosition() > ((ArmSettings) settings).minArmDockPos){}
		motorPosition = ((ArmSettings) settings).armPresets[0];
		((ArmHardware) hardware).bucketServo.setPosition(((ArmSettings) settings).bucketPresets[0]);
		servoPosition = ((ArmSettings) settings).bucketPresets[0];
		robot.delay(((ArmSettings) settings).servoMoveTime);
		bucketDocked = true;
	}

	void setBucketPosition(double position, boolean wait){
		int waitTime = 0;
		//if(wait)
			//waitTime =
	}

	void setBucketPresetPosition(int preset){

	}

	void setMotorPosition(int position){

	}

	void setMotorPresetPosition(int preset){

	}

	void dockArm(){

	}

	void undockArm(){

	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("set motor pos", motorPosition);
		robot.addTelemetry("cur motor pos", ((ArmHardware) hardware).armMotor.getCurrentPosition());
		robot.addTelemetry("servo pos", servoPosition);
	}
}
