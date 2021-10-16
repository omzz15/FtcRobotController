package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.arm.Arm;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;

@TeleOp(name = "drive test", group = "Test")
public class DriveTest extends LinearOpMode {

    Robot robot = new Robot(this);

    @Override
    public void runOpMode(){

        new Drive(robot);
        new PositionTracker(robot);
        new Arm(robot);

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
