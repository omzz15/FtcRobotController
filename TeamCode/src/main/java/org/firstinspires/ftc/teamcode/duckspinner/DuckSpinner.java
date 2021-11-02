package org.firstinspires.ftc.teamcode.duckspinner;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.duckspinner.DuckSpinnerHardware;
import org.firstinspires.ftc.teamcode.duckspinner.DuckSpinnerSettings;
import org.firstinspires.ftc.teamcode.intake.IntakeHardware;
import org.firstinspires.ftc.teamcode.intake.IntakeSettings;

public class DuckSpinner extends RobotPart {

	public DuckSpinner(Robot robot, DuckSpinnerHardware hardware, DuckSpinnerSettings settings) {
		super(robot, hardware, settings);
	}

	public DuckSpinner(Robot robot){
		super(robot, new DuckSpinnerHardware(), new DuckSpinnerSettings());
	}

	public void teleOpRunCode(){
		((DuckSpinnerHardware) hardware).duckSpinnerMotor.setPower(((DuckSpinnerSettings) settings).duckSpinnerPowerSupplier.getRampedFloat());
	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("duck spinner power", ((DuckSpinnerHardware) hardware).duckSpinnerMotor.getPower());
	}
}
