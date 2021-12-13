package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.slamra.Slamra;

@TeleOp(name = "test", group = "Test")
public class Driver extends LinearOpMode {
    @Override
    public void runOpMode(){
        Robot robot = new Robot(this);
        //new Arm(robot);
        new Arm2(robot);
        new Drive(robot);
        //new DuckSpinner(robot);
        //new Intake(robot);
        //new Movement(robot);
        new PositionTracker(robot);
        //new Vision(robot);
        //new Slamra(robot);

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
