package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;
import org.firstinspires.ftc.teamcode.test2.Test2;

@TeleOp(name = "drive test 2", group = "Test")
public class DriveTest2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        InputSupplier angleReset = new InputSupplier(gamepad1, gamepad -> (gamepad.a));
        InputSupplier angle180 = new InputSupplier(gamepad1, gamepad -> (gamepad.b));

        Robot r = new Robot(this);
        new Drive(r);
        new PositionTracker(r);
        new Test2(r);
        r.init();

        waitForStart();

        r.startThreads();

        while (!isStopRequested()) {
            r.runForTeleOp();
            r.addAllTelemetry();
            r.sendTelemetry();
            /*
            if (angleReset.getBoolean()) {
                ((PositionTracker) r.getPartByClass(PositionTracker.class)).resetAngle();
            }
            if (angle180.getBoolean()) {
                ((PositionTracker) r.getPartByClass(PositionTracker.class)).setAngle(180);
            }
             */
        }
    }
}
