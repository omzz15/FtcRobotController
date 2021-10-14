package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.movement.Movement;
import org.firstinspires.ftc.teamcode.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;
import org.firstinspires.ftc.teamcode.vision.Vision;

@TeleOp(name = "vision test", group = "Test")
public class VisionTest extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		Robot r = new Robot(this);
		new Drive(r);
		new PositionTracker(r);
		new Movement(r);
		new Vision(r);

		r.init();

		waitForStart();

		r.startThreads();

		while (!isStopRequested()) {
			r.runForTeleOp();
			r.addAllTelemetry();
			r.sendTelemetry();
		}
	}
}