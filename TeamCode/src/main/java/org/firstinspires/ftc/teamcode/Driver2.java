package org.firstinspires.ftc.teamcode;

import android.text.method.MovementMethod;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

@TeleOp(name = "test2", group = "Test")
public class Driver2 extends LinearOpMode {
	@Override
	public void runOpMode(){
		Robot robot = new Robot(this);
		new Arm(robot);
		//new Arm2(robot);
		new Drive(robot);
		//new DuckSpinner(robot);
		new Intake(robot);
		//new Movement(robot);
		new PositionTracker(robot);
		//new Vision(robot);

		robot.init();

		waitForStart();

		robot.start();

		while(opModeIsActive()){
			robot.run();
			robot.sendTelemetry();
		}

		robot.stop();
	}
}
