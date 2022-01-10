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
    PositionTracker tracker;
    Boolean enableDelay = false;

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        new Vision(robot);

        enableDelay = true; // set to false to disable the testing delays

        Task autoTask = new Task();

        autoTask.addStep(() -> arm.setToAPresetPosition((short)6));//dump low
        move.addMoveToPositionToTask(autoTask, new Position(4.6, 44.5, 57.5), true);//Task Name: BDump

        autoTask.addDelay(5000);

        autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, new Position(8, 39, 0), true);//line up to cross pipes
        move.addMoveToPositionToTask(autoTask, new Position(38, 39, 0), true);//cross pipes
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, new Position(48, 41, 45), true);//align with cheese pile
        autoTask.addDelay(500);
        autoTask.addStep(() -> intake.runIntake(0.8f), () -> true); // queue intake to run
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, new Position(58, 52, 45), ((MovementSettings) move.settings).losePosSettings.withPower(.4), false); // task to move into cheese
        autoTask.addStep(() -> {}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            move.stopMovementTask();
        });//stop movement and intake
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, new Position(38, 39, 0), true);//line up to return across pipes
        move.addMoveToPositionToTask(autoTask,  new Position(8, 39, 0), true);//return across pipes
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask,  new Position(-4.5, 41, 72), true);//high dump again

        robot.taskManager.getMain().addSequentialTask(autoTask);

        /*****************************************
         * Start main opmode running
         *****************************************/
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