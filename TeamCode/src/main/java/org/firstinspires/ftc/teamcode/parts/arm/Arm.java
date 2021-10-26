package org.firstinspires.ftc.teamcode.parts.arm;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;

public class Arm extends RobotPart {

	int motorPosition = 0;
	double servoPosition = 0;

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

	@Override
	public void onTeleOpLoop() {
		teleOpRunCode(((ArmSettings) settings).gamepad);
	}

	void teleOpRunCode(Gamepad gamepad){
		//arm
		int preset = ((ArmSettings) settings).armPresetSupplier.getInt(gamepad);
		if(preset > 0)
			motorPosition = ((ArmSettings) settings).armPresets[preset - 1];
		else
			motorPosition += ((ArmSettings) settings).armMovementSupplier.getFloat(gamepad) * (((ArmSettings) settings).armMovementSpeed);
		motorPosition = Utils.Math.capInt(motorPosition, ((ArmSettings) settings).armMinPos, ((ArmSettings) settings).armMaxPos);
		((ArmHardware) hardware).armMotor.setTargetPosition(motorPosition);

		//bucket
		preset = ((ArmSettings) settings).bucketPresetSupplier.getInt(gamepad);
		if(preset > 0)
			servoPosition = ((ArmSettings) settings).bucketPresets[preset - 1];
		else
			servoPosition += ((ArmSettings) settings).bucketSpeed * ((ArmSettings) settings).bucketMovementSupplier.getInt(gamepad);
		servoPosition = Utils.Math.capDouble(servoPosition, ((ArmSettings) settings).servoMinPos, ((ArmSettings) settings).servoMaxPos);
		((ArmHardware) hardware).bucketServo.setPosition(servoPosition);
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
