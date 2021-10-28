package org.firstinspires.ftc.teamcode.parts.duckspinner;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;

public class DuckSpinner extends RobotPart {

	public DuckSpinner(Robot robot, DuckSpinnerHardware hardware, DuckSpinnerSettings settings) {
		super(robot, hardware, settings);
	}

	public DuckSpinner(Robot robot){
		super(robot, new DuckSpinnerHardware(), new DuckSpinnerSettings());
	}

	@Override
	public void onTeleOpLoop() {
		teleOpRunCode(settings.gamepad);
	}

	@Override
	public void onRunLoop(short runMode) {

	}

	@Override
	public void onStop() {

	}

	void teleOpRunCode(Gamepad gamepad){
		((DuckSpinnerHardware) hardware).duckSpinnerMotor.setPower(((DuckSpinnerSettings) settings).duckSpinnerPowerSupplier.getRampedFloat(gamepad));
	}

	@Override
	public void onAddTelemetry() {
		robot.addTelemetry("duck spinner power", ((DuckSpinnerHardware) hardware).duckSpinnerMotor.getPower());
	}

	@Override
	public void onConstruct() {

	}

	@Override
	public void onInit() {

	}
}
