package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracking.PositionTracker;

@TeleOp(name = "drive test", group = "Test")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        InputSupplier move = new InputSupplier(gamepad -> (gamepad.a), gamepad1);

        Robot robot = new Robot(this);

        new Drive(robot);
        new PositionTracker(robot);
        new Movement(robot);
        //new Arm(robot);
        //new Intake(robot);

        robot.init();

        waitForStart();

        robot.start();

        while(opModeIsActive()){
            robot.runParts();
            robot.addAllTelemetry();
            robot.sendTelemetry();

            if(move.getBoolean())
                ((Movement) robot.getPartByClass(Movement.class)).moveToPosition(new double[]{10,10,90},
            ((MovementSettings) robot.getPartByClass(Movement.class).settings).finalPosSettings);
        }
    }
}
