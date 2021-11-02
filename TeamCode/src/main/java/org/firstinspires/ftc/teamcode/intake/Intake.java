package org.firstinspires.ftc.teamcode.intake;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.arm.Arm;
import org.firstinspires.ftc.teamcode.base.RobotPart;

public class Intake extends RobotPart {

	public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
		super(robot, hardware, settings);
	}

	public Intake(Robot robot){
		super(robot, new IntakeHardware(), new IntakeSettings());
	}

	public void teleOpRunCode(){
		float intakePower = ((IntakeSettings) settings).intakePowerSupplier.getFloat();
		if(Math.abs(intakePower) > .1 && !((Arm) robot.getPartByClass(Arm.class)).bucketDocked)
			((Arm) robot.getPartByClass(Arm.class)).dockBucket(true);
		((IntakeHardware) hardware).intakeMotor.setPower(intakePower);
	}

	@Override
	public void addTelemetry() {
		robot.addTelemetry("intake power", ((IntakeHardware) hardware).intakeMotor.getPower());
	}
}
