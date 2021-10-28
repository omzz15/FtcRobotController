package org.firstinspires.ftc.teamcode.parts.intake;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;

public class Intake extends RobotPart {

	public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
		super(robot, hardware, settings);
	}

	public Intake(Robot robot){
		super(robot, new IntakeHardware(), new IntakeSettings());
	}

	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {

	}

	@Override
	public void onTeleOpLoop() {
		teleOpRunCode(settings.gamepad);
	}

	@Override
	public void onRunLoop(short runMode) {

	}

	void teleOpRunCode(Gamepad gamepad){
		((IntakeHardware) hardware).intakeMotor.setPower(((IntakeSettings) settings).intakePowerSupplier.getFloat(gamepad));
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("intake power", ((IntakeHardware) hardware).intakeMotor.getPower());
	}

	@Override
	public void onStop() {

	}
}
