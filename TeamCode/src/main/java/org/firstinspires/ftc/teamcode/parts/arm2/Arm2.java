package org.firstinspires.ftc.teamcode.parts.arm2;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.deprecated.arm.ArmHardware;
import org.firstinspires.ftc.teamcode.deprecated.arm.ArmSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.drive.DriveSettings;
import org.firstinspires.ftc.teamcode.parts.led.Led;

public class Arm2 extends RobotPart<Arm2Hardware, Arm2Settings> {
	double bucketServoPos;
	double armServoPos;
	public int armMotorPos;
	double capServoPos;
	double keyServoPos;
	double cheeseStartTime = 0;
	int offset = 0;
	double cheeseRange = 0;

	public Arm2(Robot robot) {
		super(robot, new Arm2Hardware(), new Arm2Settings());
	}

	public Arm2(Robot robot, Arm2Hardware hardware, Arm2Settings settings) {
		super(robot, hardware, settings);
	}

	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {
		armMotorPos = settings.armMotorStartPos;
		armServoPos = settings.armServoStartPos;
		bucketServoPos = settings.bucketServoStartPos;
		capServoPos = settings.capServoStartPos;
		keyServoPos = settings.keyServoStartPos;

		hardware.armMotor.setTargetPosition(settings.armMotorStartPos);
		hardware.armServo.setPosition(settings.armServoStartPos);
		hardware.bucketServo.setPosition(settings.bucketServoStartPos);
		hardware.capServo.setPosition(settings.capServoStartPos);
		hardware.keyServo.setPosition(settings.keyServoStartPos);
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
		cheeseRange = hardware.bucketRange.getDistance(DistanceUnit.INCH);
		if (robot.getPartByClass(Led.class) != null) {
			if(cheeseRange < settings.blockSensorMinDist){
				((Led)robot.getPartByClass(Led.class)).setLedStatus(1);
			} else{
				((Led)robot.getPartByClass(Led.class)).setLedStatus(0);
			}
		}
		if (runMode == 1) {
			//armMotorPos = Utils.Math.capInt(armMotorPos + (int) (settings.armMotorMovementSupplier.getFloat() * settings.armMotorMovementSpeed), settings.armMotorMinPos, settings.armMotorMaxPos);
			armMotorPos = Math.min(armMotorPos + (int) (settings.armMotorMovementSupplier.get() * settings.armMotorMovementSpeed), settings.armMotorMaxPos);
			if (hardware.limitSwitch.isPressed() && settings.armMotorMovementSupplier.get() != 0){
				armMotorPos = Math.max(armMotorPos, 0);
				if (armMotorPos > 25) armMotorPos = 0;
				offset = hardware.armMotor.getCurrentPosition();
			}
			//armServoPos = Utils.Math.capDouble(armServoPos + settings.armServoMovementSupplier.getInt() * settings.armServoMovementSpeed, settings.armServoMinPos, settings.armServoMaxPos);
			//bucketServoPos = Utils.Math.capDouble(bucketServoPos + settings.bucketServoMovementSupplier.getInt() * settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
			capServoPos = Utils.Math.capDouble(capServoPos + settings.capServoMovementSupplier.get() * settings.capServoMovementSpeed, settings.capServoMinPos, settings.capServoMaxPos);
			//keyServoPos = Utils.Math.capDouble(keyServoPos + settings.keyServoMovementSupplier.getInt() * settings.keyServoMovementSpeed, settings.keyServoMinPos, settings.keyServoMaxPos);

			short armPreset = (short) settings.armPresetSupplier.get();
			armPreset--;
			if (armPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				armServoPos = Utils.Math.capDouble(settings.armServoPresets[armPreset] + settings.armServoMovementSpeed, settings.armServoMinPos, settings.armServoMaxPos);
				bucketServoPos = Utils.Math.capDouble(settings.bucketServoPresets[armPreset] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
				armMotorPos = Utils.Math.capInt(settings.armPresets[armPreset] + (int) (settings.armMotorMovementSupplier.get() * settings.armMotorMovementSpeed), settings.armMotorMinPos, settings.armMotorMaxPos);
			}

			short capPreset = (short) settings.capPresetSupplier.get();
			capPreset--;
			if (capPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				capServoPos = Utils.Math.capDouble(settings.capServoPresets[capPreset] + settings.capServoMovementSpeed, settings.capServoMinPos, settings.capServoMaxPos);
			}

			short keyPreset = (short) settings.keyPresetSupplier.get();
			keyPreset--;
			if (keyPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				keyServoPos = Utils.Math.capDouble(settings.keyServoPresets[keyPreset] + settings.keyServoMovementSpeed, settings.keyServoMinPos, settings.keyServoMaxPos);
				hardware.keyServo.setPosition(keyServoPos);
			}
			short dumpPreset = (short) settings.dumpPresetSupplier.get();
			dumpPreset--;
			if (dumpPreset < 0) {
				//setToAPresetPosition(preset);
			} else {
				bucketServoPos = Utils.Math.capDouble(settings.dumpPresets[dumpPreset] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
				hardware.bucketServo.setPosition(bucketServoPos);
			}

			hardware.armMotor.setTargetPosition(armMotorPos + offset);
			hardware.armServo.setPosition(armServoPos);
			hardware.bucketServo.setPosition(bucketServoPos);
			hardware.capServo.setPosition(capServoPos);
		}
	}

	public void autonomousPresets(short armPreset) {
		armPreset--;
		armServoPos = Utils.Math.capDouble(settings.armServoPresets[armPreset] + settings.armServoMovementSpeed, settings.armServoMinPos, settings.armServoMaxPos);
		bucketServoPos = Utils.Math.capDouble(settings.bucketServoPresets[armPreset] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
		armMotorPos = Utils.Math.capInt(settings.armPresets[armPreset] + (int) (settings.armMotorMovementSupplier.get() * settings.armMotorMovementSpeed), settings.armMotorMinPos, settings.armMotorMaxPos);
	}

	public void autonomousDump(int preset) {
		bucketServoPos = Utils.Math.capDouble(settings.dumpPresets[preset] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
	}

	public void autonomousArmPreset() {
		bucketServoPos = Utils.Math.capDouble(settings.dumpPresets[0] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
	}

	public void armDown(int armPreset) {
		bucketServoPos = Utils.Math.capDouble(settings.bucketServoPresets[armPreset] + settings.bucketServoMovementSpeed, settings.bucketServoMinPos, settings.bucketServoMaxPos);
		armServoPos = Utils.Math.capDouble(settings.armServoPresets[armPreset] + settings.armServoMovementSpeed, settings.armServoMinPos, settings.armServoMaxPos);
		armMotorPos = Utils.Math.capInt(settings.armPresets[armPreset] + (int) (settings.armMotorMovementSupplier.get() * settings.armMotorMovementSpeed), settings.armMotorMinPos, settings.armMotorMaxPos);
	}

	public boolean isBucketFull() {
		if (cheeseStartTime == 0) {
			cheeseStartTime = System.currentTimeMillis();
		}

		double dist = cheeseRange; //if no worky worky this is why
		if (dist < settings.blockSensorMinDist || System.currentTimeMillis() > cheeseStartTime + 2000) {//bucket full
			cheeseStartTime = 0;
			return true;
		}
		else return false;
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("arm motor", armMotorPos);
		robot.addTelemetry("arm servo", armServoPos);
		robot.addTelemetry("bucket servo", bucketServoPos);
		robot.addTelemetry("cap servo", capServoPos);
		robot.addTelemetry("key servo", keyServoPos);
		robot.addTelemetry("Cheese Range Inch", String.format("%.1f", cheeseRange));
		robot.addTelemetry("limit switch", hardware.limitSwitch.isPressed());
	}

	@Override
	public void onStop() {

	}
}
