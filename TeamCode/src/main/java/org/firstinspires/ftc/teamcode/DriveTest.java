package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.movement.Movement;
import org.firstinspires.ftc.teamcode.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;

@TeleOp(name = "drive test", group = "Test")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        InputSupplier moveToPos = new InputSupplier(gamepad1, gamepad -> (gamepad.y));

        Robot r = new Robot(this);
        new Drive(r);
        new PositionTracker(r);
        new Movement(r);
        r.init();

        waitForStart();

        r.startThreads();

        while (!isStopRequested()) {
            if(moveToPos.getBoolean())
                ((Movement) r.getPartByClass(Movement.class)).moveToPosition(new double[]{10,10,90}, ((MovementSettings)  r.getPartByClass(Movement.class).settings).finalPosSettings);
            r.runForTeleOp();
            r.addAllTelemetry();
            r.sendTelemetry();
        }
    }
}
