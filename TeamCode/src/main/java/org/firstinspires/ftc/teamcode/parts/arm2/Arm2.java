package org.firstinspires.ftc.teamcode.parts.arm2;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.deprecated.arm.ArmHardware;
import org.firstinspires.ftc.teamcode.deprecated.arm.ArmSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

public class Arm2 extends RobotPart {
	double bucketServoPos;
	double armServoPos;
	public int armMotorPos;
	double capServoPos;
	double keyServoPos;

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
		capServoPos = ((Arm2Settings) settings).capServoStartPos;
		keyServoPos = ((Arm2Settings) settings).keyServoStartPos;

		((Arm2Hardware) hardware).armMotor.setTargetPosition(((Arm2Settings) settings).armMotorStartPos);
		((Arm2Hardware) hardware).armServo.setPosition(((Arm2Settings) settings).armServoStartPos);
		((Arm2Hardware) hardware).bucketServo.setPosition(((Arm2Settings) settings).bucketServoStartPos);
		((Arm2Hardware) hardware).capServo.setPosition(((Arm2Settings) settings).capServoStartPos);
		((Arm2Hardware) hardware).keyServo.setPosition(((Arm2Settings) settings).keyServoStartPos);
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
			//armServoPos = Utils.Math.capDouble(armServoPos + ((Arm2Settings) settings).armServoMovementSupplier.getInt() * ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
			//bucketServoPos = Utils.Math.capDouble(bucketServoPos + ((Arm2Settings) settings).bucketServoMovementSupplier.getInt() * ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
			capServoPos = Utils.Math.capDouble(capServoPos + ((Arm2Settings) settings).capServoMovementSupplier.getInt() * ((Arm2Settings) settings).capServoMovementSpeed, ((Arm2Settings) settings).capServoMinPos, ((Arm2Settings) settings).capServoMaxPos);
			//keyServoPos = Utils.Math.capDouble(keyServoPos + ((Arm2Settings) settings).keyServoMovementSupplier.getInt() * ((Arm2Settings) settings).keyServoMovementSpeed, ((Arm2Settings) settings).keyServoMinPos, ((Arm2Settings) settings).keyServoMaxPos);

			short armPreset = (short) ((Arm2Settings) settings).armPresetSupplier.getInt();
			armPreset--;
			if (armPreset < 0) {
				//setToAPresetPosition(preset);
			}
			else {
				armServoPos = Utils.Math.capDouble(((Arm2Settings) settings).armServoPresets[armPreset] + ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
				bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).bucketServoPresets[armPreset] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
				armMotorPos = Utils.Math.capInt(((Arm2Settings) settings).armPresets[armPreset] + (int)(((Arm2Settings) settings).armMotorMovementSupplier.getFloat() * ((Arm2Settings) settings).armMotorMovementSpeed), ((Arm2Settings) settings).armMotorMinPos, ((Arm2Settings) settings).armMotorMaxPos);
			}

			short capPreset = (short) ((Arm2Settings) settings).capPresetSupplier.getInt();
			capPreset--;
			if (capPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				capServoPos = Utils.Math.capDouble(((Arm2Settings) settings).capServoPresets[capPreset] +  ((Arm2Settings) settings).capServoMovementSpeed, ((Arm2Settings) settings).capServoMinPos, ((Arm2Settings) settings).capServoMaxPos);
			}

			short keyPreset = (short) ((Arm2Settings) settings).keyPresetSupplier.getInt();
			keyPreset--;
			if (keyPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				keyServoPos = Utils.Math.capDouble(((Arm2Settings) settings).keyServoPresets[keyPreset] +  ((Arm2Settings) settings).keyServoMovementSpeed, ((Arm2Settings) settings).keyServoMinPos, ((Arm2Settings) settings).keyServoMaxPos);
				((Arm2Hardware) hardware).keyServo.setPosition(keyServoPos);
			}
			short dumpPreset = (short) ((Arm2Settings) settings).dumpPresetSupplier.getInt();
			dumpPreset--;
			if (dumpPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).dumpPresets[dumpPreset] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
				((Arm2Hardware) hardware).bucketServo.setPosition(bucketServoPos);
			}

			((Arm2Hardware) hardware).armMotor.setTargetPosition(armMotorPos);
			((Arm2Hardware) hardware).armServo.setPosition(armServoPos);
			((Arm2Hardware) hardware).bucketServo.setPosition(bucketServoPos);
			((Arm2Hardware) hardware).capServo.setPosition(capServoPos);
		}
	}

	public void autonomousPresets(short armPreset){
		armPreset--;
		armServoPos = Utils.Math.capDouble(((Arm2Settings) settings).armServoPresets[armPreset] + ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
		bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).bucketServoPresets[armPreset] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
		armMotorPos = Utils.Math.capInt(((Arm2Settings) settings).armPresets[armPreset] + (int)(((Arm2Settings) settings).armMotorMovementSupplier.getFloat() * ((Arm2Settings) settings).armMotorMovementSpeed), ((Arm2Settings) settings).armMotorMinPos, ((Arm2Settings) settings).armMotorMaxPos);
	}

	public void autonomousDump(){
		bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).dumpPresets[0] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
	}

	public void autonomousArmPreset(){
		bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).dumpPresets[0] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
	}

	public void armDown(int armPreset){
		bucketServoPos = Utils.Math.capDouble(((Arm2Settings) settings).bucketServoPresets[armPreset] +  ((Arm2Settings) settings).bucketServoMovementSpeed, ((Arm2Settings) settings).bucketServoMinPos, ((Arm2Settings) settings).bucketServoMaxPos);
		armServoPos = Utils.Math.capDouble(((Arm2Settings) settings).armServoPresets[armPreset] + ((Arm2Settings) settings).armServoMovementSpeed, ((Arm2Settings) settings).armServoMinPos, ((Arm2Settings) settings).armServoMaxPos);
		armMotorPos = Utils.Math.capInt(((Arm2Settings) settings).armPresets[armPreset] + (int)(((Arm2Settings) settings).armMotorMovementSupplier.getFloat() * ((Arm2Settings) settings).armMotorMovementSpeed), ((Arm2Settings) settings).armMotorMinPos, ((Arm2Settings) settings).armMotorMaxPos);
	}

	public boolean isBucketFull() {
		double dist = ((Arm2Hardware) hardware).bucketRange.getDistance(DistanceUnit.INCH);
		if (dist < 1.2) //bucket full
			return true;
		else return false;
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("arm motor", armMotorPos);
		robot.addTelemetry("arm servo", armServoPos);
		robot.addTelemetry("bucket servo", bucketServoPos);
		robot.addTelemetry("cap servo", capServoPos);
		robot.addTelemetry("key servo", keyServoPos);
		robot.addTelemetry("Cheese Range Inch", String.format("%.1f", ((Arm2Hardware) hardware).bucketRange.getDistance(DistanceUnit.INCH)));
	}

	@Override
	public void onStop() {

	}
}
