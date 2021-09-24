package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class DriveTest extends LinearOpMode {
    Robot r = new Robot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        r.init();

        waitForStart();

        while (!isStopRequested())
            r.runForTeleOp();
    }
}
