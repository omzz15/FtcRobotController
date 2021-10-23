package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.arm.Arm;
import org.firstinspires.ftc.teamcode.arm.ArmHardware;
import org.firstinspires.ftc.teamcode.arm.ArmSettings;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.duckspinner.DuckSpinnerHardware;
import org.firstinspires.ftc.teamcode.duckspinner.DuckSpinnerSettings;
import org.firstinspires.ftc.teamcode.intake.Intake;
import org.firstinspires.ftc.teamcode.intake.IntakeHardware;
import org.firstinspires.ftc.teamcode.intake.IntakeSettings;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;

@TeleOp(name = "drive test 2 hub", group = "Test")
public class DriveTest2Hub extends LinearOpMode {

	@Override
	public void runOpMode(){
		Robot robot = new Robot(this);

		new Drive(robot);
		new PositionTracker(robot);

		//arm
		ArmHardware ah = new ArmHardware();
		ah.armMotorSettings.number = MotorSettings.Number.ONE_B;
		new Arm(robot, ah, new ArmSettings());
		//intake
		IntakeHardware ih = new IntakeHardware();
		ih.intakeMotorSettings.number = MotorSettings.Number.TWO_B;
		new Intake(robot, ih, new IntakeSettings());
		//duck spinner
		DuckSpinnerHardware dh = new DuckSpinnerHardware();
		dh.duckSpinnerMotorSettings.number = MotorSettings.Number.THREE_B;
		new DuckSpinner(robot, dh, new DuckSpinnerSettings());

		robot.init();

		waitForStart();

		robot.start();

		while(opModeIsActive()){
			robot.runForTeleOp();
			robot.addAllTelemetry();
			robot.sendTelemetry();
		}
	}
}

