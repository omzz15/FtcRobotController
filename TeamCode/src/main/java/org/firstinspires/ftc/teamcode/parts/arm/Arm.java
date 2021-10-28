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
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		servoPosition = ((ArmHardware) hardware).bucketServo.getPosition();
	}

	@Override
	public void onTeleOpLoop() {
		teleOpRunCode(settings.gamepad);
	}

	@Override
	public void onRunLoop(short runMode) {

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


	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("set motor pos", motorPosition);
		robot.addTelemetry("cur motor pos", ((ArmHardware) hardware).armMotor.getCurrentPosition());
		robot.addTelemetry("servo pos", servoPosition);
	}

	@Override
	public void onStop() {

	}
}
