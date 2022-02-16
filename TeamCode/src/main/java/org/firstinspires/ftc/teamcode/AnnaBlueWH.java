package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.MoveToPosSettings;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

@Autonomous(name = "Anna Blue Warehouse", group = "Test")
public class AnnaBlueWH extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm2 arm;
    Intake intake;
    Boolean enableDelay = false;
    MoveToPosSettings losePos;
    MoveToPosSettings wallLoosePos;
    Vision vision;
    MoveToPosSettings defaultPos;
    Position fieldStartPos;

    public void setAutoVar (){
        robot.autoBlue = true;
        fieldStartPos = new Position(9.5, 60, 90);
    }

    @Override
    public void runOpMode() {
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm2(robot);
        vision = new Vision(robot);

        setAutoVar();
        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        Position midDumpPos = new Position(-4.5, 39.5, 72);
        Position highDumpPos = new Position(-3.5, 39, 72);
        lowDumpPos = highDumpPos;
        Position pipeLineUpOutsidePos = new Position(11, 65, 0);
        Position pipeLineUpInsidePos = new Position(40, 65, 0);
        Position startCheeseRunPos = new Position(48, 58, 0);
        Position deepInCheesePos = new Position(62, 60, -15);
        Position finalPark = new Position(46, 65, 0);
        Position lowDumpWall = new Position(fieldStartPos.X, fieldStartPos.Y - 8, fieldStartPos.R);
        Position midDumpForward = new Position(midDumpPos.X + 4, midDumpPos.Y + 4, midDumpPos.R);
        Position lowDumpForward = new Position((lowDumpPos.X) + 4, lowDumpPos.Y + 4, lowDumpPos.R);

        defaultPos =((MovementSettings) move.settings).defaultPosSettings;
        losePos = ((MovementSettings) move.settings).losePosSettings;
        wallLoosePos = ((MovementSettings) move.settings).wallLoosePosSettings;

        pt.slamraFieldStart = fieldStartPos;
        pt.slamraRobotOffset = new Position(-4.5,-.5,90);
        enableDelay = true; // set to false to disable the testing delays

        Task autoTask = new Task();
        intake.isAutonomous = false;

        //autoTask.addStep(() -> {((Intake) robot.getPartByClass(Intake.class)).pause(false);});


        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        vision.start();
        pt.start();
        while (!opModeIsActive()) {
            vision.onRunLoop((short) 1);
            pt.slamraInfo();
            vision.duckPos();
            robot.sendTelemetry();
            sleep(50);
        }

        if (vision.duckPos == 3) {
            autoTask.addStep(() -> arm.autonomousPresets((short) 5));//dump high
            move.addMoveToPositionToTask(autoTask, highDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.autonomousDump(0));
            autoTask.addDelay(500);
        } else if (vision.duckPos == 2) {
            autoTask.addStep(() -> arm.autonomousPresets((short) 4));//dump mid
            move.addMoveToPositionToTask(autoTask, midDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.autonomousDump(1));
            autoTask.addDelay(1000);
           // move.addMoveToPositionToTask(autoTask, midDumpForward, losePos, true);
        } else if (vision.duckPos == 1) {
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.autonomousPresets((short) 3));//dump low
         //   move.addMoveToPositionToTask(autoTask, lowDumpWall, true);
            move.addMoveToPositionToTask(autoTask, lowDumpPos, true); //moves to dump cargo
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.autonomousDump(1));
            autoTask.addDelay(1000);
          //  move.addMoveToPositionToTask(autoTask, lowDumpForward, losePos, true);
        }
        autoTask.addStep(() -> arm.autonomousPresets((short) 2));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, wallLoosePos, true);//line up to cross pipes
        autoTask.addStep(() -> intake.runIntake(0.8f)); //run intake to run
        autoTask.addStep(()-> arm.autonomousPresets((short)1));
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, wallLoosePos, true);//cross pipes
        // autoTask.addDelay(100);
        // move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, wallLoosePos, true); //align with cheese pile on 45 deg
        // autoTask.addStep(() -> arm.setArmPosition(65));
        autoTask.addStep(() -> intake.startIntake(.8f));
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, wallLoosePos.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {
            intake.startIntake(0.8f);
        }, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> intake.startIntake(-0.8f));
        autoTask.addDelay(250);
        autoTask.addStep(() -> arm.autonomousPresets((short) 2)); // cradle bucket after intake finds cheese
        autoTask.addStep(() -> intake.stopIntake());
        // move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, wallLoosePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, wallLoosePos, true);//return across pipes
        autoTask.addStep(() -> arm.autonomousPresets((short) 5));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true);//high dump again
        autoTask.addStep(() -> arm.autonomousDump(0));
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.autonomousPresets((short) 2));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, wallLoosePos, true);//line up to cross pipes
        autoTask.addStep(() -> intake.runIntake(0.8f)); //run intake to run
        autoTask.addStep(()-> arm.autonomousPresets((short)1));
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, wallLoosePos, true);//cross pipes
        /*****************go again!************************************************************************************************************/
        autoTask.addStep(() -> intake.startIntake(.8f));
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, wallLoosePos.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {
            intake.startIntake(0.8f);
        }, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> intake.startIntake(-0.8f));
        autoTask.addDelay(250);
        autoTask.addStep(() -> arm.autonomousPresets((short) 2)); // cradle bucket after intake finds cheese
        autoTask.addStep(() -> intake.stopIntake());
        // move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, wallLoosePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, wallLoosePos, true);//return across pipes
        autoTask.addStep(() -> arm.autonomousPresets((short) 5));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true);//high dump again
        autoTask.addStep(() -> arm.autonomousDump(0));
        autoTask.addDelay(550); //change to less
        autoTask.addStep(() -> arm.autonomousPresets((short) 2));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, wallLoosePos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, finalPark, wallLoosePos, true);//cross pipes
        autoTask.addStep(() -> arm.autonomousPresets((short)1));

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
