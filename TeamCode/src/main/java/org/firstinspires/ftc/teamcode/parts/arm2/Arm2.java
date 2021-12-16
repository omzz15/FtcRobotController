package org.firstinspires.ftc.teamcode.parts.arm2;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.deprecated.arm.ArmSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

public class Arm2 extends RobotPart {
	double bucketServoPos;
	double armServoPos;
	int armMotorPos;

	public Arm2(Robot robot){
		super(robot, new Arm2Hardware(), new Arm2Settings());
	}
	public Arm2(Robot robot, Arm2Hardware hardware, Arm2Settings settings){
		super(robot, hardware, settings);
	}

	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		armMotorPos = ((Arm2Settings) settings).armMotorStartPos;
		armServoPos = ((Arm2Settings) settings).armServoStartPos;
		bucketServoPos = ((Arm2Settings) settings).bucketServoStartPos;

		((Arm2Hardware) hardware).armMotor.setTargetPosition(((Arm2Settings) settings).armMotorStartPos);
		((Arm2Hardware) hardware).armServo.setPosition(((Arm2Settings) settings).armServoStartPos);
		((Arm2Hardware) hardware).bucketServo.setPosition(((Arm2Settings) settings).bucketServoStartPos);
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onUnpause() {

	}

	@Override
	public void onRunLoop(short runMode) {
		if(runMode == 1){
			armMotorPos = Utils.Math.capInt(armMotorPos + (int)(((Arm2Settings) settings).armMotorMovementSupplier.getFloat() * ((Arm2Settings) settings).armMotorMovementSpeed), ((Arm2Settings) settings).armMotorMinPos, ((Arm2Settings) settings).armMotorMaxPos);
			armServoPos = Utils.Math.capDouble(armServoPos + ((Arm2Settings) settings).armServoMovementSupplier.getInt() * ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
			bucketServoPos = Utils.Math.capDouble(bucketServoPos + ((Arm2Settings) settings).bucketServoMovementSupplier.getInt() * ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);

			short preset = (short) ((Arm2Settings) settings).presetSupplier.getInt();
			preset--;
			if (preset < 0) {
				//setToAPresetPosition(preset);
			}
			else {
				armServoPos = Utils.Math.capDouble(((Arm2Settings) settings).armServoPresets[preset] + ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
				bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).bucketServoPresets[preset] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
				armMotorPos = Utils.Math.capInt(((Arm2Settings) settings).armPresets[preset] + (int)(((Arm2Settings) settings).armMotorMovementSupplier.getFloat() * ((Arm2Settings) settings).armMotorMovementSpeed), ((Arm2Settings) settings).armMotorMinPos, ((Arm2Settings) settings).armMotorMaxPos);
			}
			((Arm2Hardware) hardware).armMotor.setTargetPosition(armMotorPos);
			((Arm2Hardware) hardware).armServo.setPosition(armServoPos);
			((Arm2Hardware) hardware).bucketServo.setPosition(bucketServoPos);
		}
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("arm motor", armMotorPos);
		robot.addTelemetry("arm servo", armServoPos);
		robot.addTelemetry("bucket servo", bucketServoPos);
	}

	@Override
	public void onStop() {

	}
}
