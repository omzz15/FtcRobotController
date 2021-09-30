package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;

import java.util.function.Function;

@TeleOp(name = "drive test", group = "Test")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        InputSupplier angleReset = new InputSupplier(gamepad1, gamepad -> (gamepad.a));
        InputSupplier angle180 = new InputSupplier(gamepad1, gamepad -> (gamepad.b));

        Robot r = new Robot(this);
        r.init();

        waitForStart();

        r.startParts();

        while (!isStopRequested()) {
            r.runForTeleOp();
            r.addAllTelemetry();
            r.sendTelemetry();
            if (angleReset.getBoolean()) {
                ((PositionTracker) r.getPartByClass(PositionTracker.class)).resetAngle();
            }
            if (angle180.getBoolean()) {
                ((PositionTracker) r.getPartByClass(PositionTracker.class)).setAngle(180);
            }
        }
    }
}
