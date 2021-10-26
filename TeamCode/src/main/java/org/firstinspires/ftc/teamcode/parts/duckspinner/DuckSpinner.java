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

	void teleOpRunCode(Gamepad gamepad){
		((DuckSpinnerHardware) hardware).duckSpinnerMotor.setPower(((DuckSpinnerSettings) settings).duckSpinnerPowerSupplier.getRampedFloat(gamepad));
	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("duck spinner power", ((DuckSpinnerHardware) hardware).duckSpinnerMotor.getPower());
	}
}
