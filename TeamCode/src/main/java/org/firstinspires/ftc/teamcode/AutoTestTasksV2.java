package org.firstinspires.ftc.teamcode;

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

@TeleOp(name = "Auto tasks 2", group = "Test")
public class AutoTestTasksV2 extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm arm;
    Intake intake;
    Boolean enableDelay = false;

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        new Vision(robot);

        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        Position midDumpPos = new Position(-0.2, 47, 72);
        Position highDumpPos = new Position(-4.5, 41, 72);
        Position pipeLineUpOutsidePos = new Position(8, 39, 0);
        Position pipeLineUpInsidePos = new Position(38, 39, 0);
        Position startCheeseRunAt45Pos = new Position(48, 41, 45);
        Position deepInCheesePos = new Position(58, 52, 45);
        Position fieldStartPos = new Position(9.5, 60, 90);
        pt.slamraFieldStart = fieldStartPos;

        enableDelay = false; // set to false to disable the testing delays

        Task autoTask = new Task();
        intake.isAutonomous = false;

        //autoTask.addStep(() -> {((Intake) robot.getPartByClass(Intake.class)).pause(false);});
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true); //moves to dump cargo
        autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle
        //autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, true);//cross pipes
        autoTask.addStep(() -> { intake.runIntake(0.8f); }, () -> (intake.intakeServoDoneMoving())); //run intake to run
       // autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, startCheeseRunAt45Pos, true); //align with cheese pile on 45 deg
        autoTask.addDelay(500);
       // autoTask.addStep(() -> arm.setArmPosition(65));
        autoTask.addStep(() -> intake.startIntake(.8f));
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, ((MovementSettings) move.settings).losePosSettings.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {intake.startIntake(0.8f);}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask,  pipeLineUpOutsidePos, true);//return across pipes
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask,  highDumpPos, true);//high dump again
        autoTask.addStep(() -> arm.setBucketToPreset((short) 2));
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, true);//cross pipes
              /* autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, pipeLineUpOutsidePos, true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, true);//cross pipes
        autoTask.addStep(() -> { intake.runIntake(0.8f); }); //run intake to run
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, startCheeseRunAt45Pos, true); //align with cheese pile on 45 deg
        autoTask.addDelay(500);
        autoTask.addStep(() -> { intake.runIntake(0.8f); }); //run intake to run
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, deepInCheesePos, ((MovementSettings) move.settings).losePosSettings.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {intake.runIntake(0.8f);}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, pipeLineUpInsidePos, true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask,  pipeLineUpOutsidePos, true);//return across pipes
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask,  highDumpPos, true);//high dump again
*/
        robot.taskManager.getMain().addSequentialTask(autoTask);

        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();

        intake.pause(true);
        intake.isAutonomous = true;
        waitForStart();
        robot.start();

        while(opModeIsActive()){
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }
}