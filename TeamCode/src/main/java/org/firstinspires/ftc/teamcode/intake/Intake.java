package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.arm.ArmHardware;
import org.firstinspires.ftc.teamcode.arm.ArmSettings;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;

public class Intake extends RobotPart {

	public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
		super(robot, hardware, settings);
	}

	public Intake(Robot robot){
		super(robot, new IntakeHardware(), new IntakeSettings());
	}

	@Override
	public void teleOpRunCode() {
		teleOpRunCode(settings.gamepad);
	}

	void teleOpRunCode(Gamepad gamepad){
		((IntakeHardware) hardware).intakeMotor.setPower(((IntakeSettings) settings).intakePowerSupplier.getFloat(gamepad));
	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("intake power", ((IntakeHardware) hardware).intakeMotor.getPower());
	}
}
