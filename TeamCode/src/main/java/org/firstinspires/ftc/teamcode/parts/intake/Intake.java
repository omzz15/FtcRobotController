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
	public void onTeleOpLoop() {
		teleOpRunCode(settings.gamepad);
	}

	void teleOpRunCode(Gamepad gamepad){
		((IntakeHardware) hardware).intakeMotor.setPower(((IntakeSettings) settings).intakePowerSupplier.getFloat(gamepad));
	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("intake power", ((IntakeHardware) hardware).intakeMotor.getPower());
	}

	@Override
	public void onStop() {

	}
}
