package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.armtest.ArmTest;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.led.Led;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;

@TeleOp(name = "test for cones", group = "Test")
public class TestDriver extends LinearOpMode {
    PositionTracker pt;

    @Override
    public void runOpMode(){
        Robot robot = new Robot(this);
        pt = new PositionTracker(robot);
        new ArmTest(robot);
        new Drive(robot);

        //Position leftSlamraOffset = new Position(-4.5,-0.75,-90);
        //Position rightSlamraOffset = new Position(-4.5, -.5, 90);
        //pt.slamraRobotOffset = new Position(-6.5,2.25,-90);

        Position fieldStartPos = new Position(9.5, 60, 90);
        pt.slamraFieldStart = fieldStartPos;
        pt.useLeftSlamra();

        robot.init();
        waitForStart();
        robot.start();
        pt.start();

        while(opModeIsActive()){
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }

}
