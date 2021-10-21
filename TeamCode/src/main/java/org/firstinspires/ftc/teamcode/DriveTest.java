package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.arm.Arm;
import org.firstinspires.ftc.teamcode.intake.Intake;

@TeleOp(name = "drive test", group = "Test")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        Robot robot = new Robot(this);

        //new Drive(robot);
        //new PositionTracker(robot);
        new Arm(robot);
        new Intake(robot);

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
