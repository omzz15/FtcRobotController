package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.MoveToPosSettings;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

import java.text.ParsePosition;
@Disabled
@Autonomous(name = "Janky Blue Warehouse", group = "Test")
public class JankyBlueWH extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm arm;
    Intake intake;
    Boolean enableDelay = false;
    MoveToPosSettings losePos;
    Vision vision;
    MoveToPosSettings defaultPos;
    @Override
    public void runOpMode() {
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        vision = new Vision(robot);


        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        // Position midDumpPos = new Position(-0.2, 47, 72);
        Position midDumpPos = new Position(1, 45, 55);
        Position highDumpPos = new Position(-4.5, 41, 72);
        Position pipeLineUpOutsidePos = new Position(8, 43, 0);
        Position pipeLineUpInsidePos = new Position(40, 43, 0);
        Position startCheeseRunAt45Pos = new Position(48, 43, 45);
        Position deepInCheesePos = new Position(58, 52, 45);
        Position fieldStartPos = new Position(9.5, 60, 90);
        Position finalPark = new Position(42, 41, 0);
        Position lowDumpWall = new Position(fieldStartPos.X, fieldStartPos.Y - 8, fieldStartPos.R);
        Position midDumpForward = new Position(midDumpPos.X + 4, midDumpPos.Y + 4, midDumpPos.R);
        Position lowDumpForward = new Position((lowDumpPos.X) + 4, lowDumpPos.Y + 4, lowDumpPos.R);

        defaultPos =((MovementSettings) move.settings).defaultPosSettings;
        losePos = ((MovementSettings) move.settings).losePosSettings;
        pt.slamraFieldStart = fieldStartPos;
        pt.slamraRobotOffset = new Position(-6.5,2.25,90);
        enableDelay = true; // set to false to disable the testing delays

        Task autoTask = new Task();
        intake.isAutonomous = false;

        //autoTask.addStep(() -> {((Intake) robot.getPartByClass(Intake.class)).pause(false);});


        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        vision.start();
        while (!opModeIsActive()) {
            vision.onRunLoop((short) 1);
            vision.duckPos();
            robot.sendTelemetry();
            sleep(50);
        }

        if (vision.duckPos == 3) {
            autoTask.addStep(() -> arm.setToAPresetPosition((short) 2));//dump high
            move.addMoveToPositionToTask(autoTask, highDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
            autoTask.addDelay(500);
        } else if (vision.duckPos == 2) {
            autoTask.addStep(() -> arm.setToAPresetPosition((short) 5));//dump mid
            move.addMoveToPositionToTask(autoTask, midDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
            autoTask.addDelay(1000);
            move.addMoveToPositionToTask(autoTask, midDumpForward, losePos, true);
        } else if (vision.duckPos == 1) {
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.setToAPresetPosition((short) 6));//dump low
            move.addMoveToPositionToTask(autoTask, lowDumpWall, true);
            move.addMoveToPositionToTask(autoTask, lowDumpPos, true); //moves to dump cargo
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
            autoTask.addDelay(1000);
            move.addMoveToPositionToTask(autoTask, lowDumpForward, losePos, true);
        }
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 4));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, losePos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, losePos, true);//cross pipes
        autoTask.addStep(()-> arm.setToAPresetPosition((short)1));
        autoTask.addDelay(100);
        autoTask.addStep(() -> {
            intake.runIntake(0.8f);
        }, () -> (intake.intakeServoDoneMoving())); //run intake to run
        move.addMoveToPositionToTask(autoTask, startCheeseRunAt45Pos, losePos, true); //align with cheese pile on 45 deg
        // autoTask.addStep(() -> arm.setArmPosition(65));
        autoTask.addStep(() -> intake.startIntake(.8f));
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, losePos.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {
            intake.startIntake(0.8f);
        }, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 4)); // cradle bucket after intake finds cheese
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, losePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, losePos, true);//return across pipes
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 2));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true);//high dump again
        autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 4));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, defaultPos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, losePos, true);//cross pipes
        /*****************go again!************************************************************************************************************/
        autoTask.addStep(()-> arm.setToAPresetPosition((short)1));
        autoTask.addDelay(100);
        autoTask.addStep(() -> {
            intake.runIntake(0.8f);
        }, () -> (intake.intakeServoDoneMoving())); //run intake to run
        move.addMoveToPositionToTask(autoTask, startCheeseRunAt45Pos, losePos, true); //align with cheese pile on 45 deg
        // autoTask.addStep(() -> arm.setArmPosition(65));
        autoTask.addStep(() -> intake.startIntake(.8f));
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, losePos.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {
            intake.startIntake(0.8f);
        }, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 4)); // cradle bucket after intake finds cheese
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, losePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, losePos, true);//return across pipes
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 2));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true);//high dump again
        autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
        autoTask.addDelay(550); //change to less
        autoTask.addStep(() -> arm.setToAPresetPosition((short) 4));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, defaultPos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, finalPark, losePos, true);//cross pipes
        autoTask.addStep(() -> intake.setIntakeServoPosition(.6));
        autoTask.addStep(() -> arm.setToAPresetPosition((short)1));

        robot.taskManager.getMain().addSequentialTask(autoTask);

        intake.pause(true);
        intake.isAutonomous = true;
        waitForStart();
        robot.start();
        intake.pause(true);

        while (opModeIsActive()) {
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }
}
