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

@TeleOp(name = "Auto tasks", group = "Test")
public class AutoTestTasks extends LinearOpMode {
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

      // addTask("arm to dump pos", () -> arm.setArmToPreset((short) 2), );
      //  addTask("fireBG arm to dump pos", () -> robot.taskManager.getMain().getBackgroundTask("arm to dump pos"))

        addTask("dump low", () -> arm.setToAPresetPosition((short)6), () -> {return true;});
        //addTask("MDump", new Position(-0.2, 47, 72));
        addMTPTask(new Position(4.6, 44.5, 57.5), true);//Task Name: BDump

        addDelay(5000);
        //addTask("Cradle", () -> arm.setToAPresetPosition((short)4));
      //  addTask("high dump", new Position(-4.5, 41 , 72));
       // addTask("dump high", () -> arm.setToAPresetPosition((short)2));
        addDelay(1000);
        addTask("cradle", () -> arm.setToAPresetPosition((short)4));
        addDelay(500);
        addMTPTask(new Position(8, 39, 0), true);//line up to cross pipes
        addMTPTask(new Position(38, 39, 0), true);//cross pipes
        addDelay(500);
        addMTPTask(new Position(48, 41, 45), true);//align with cheese pile
        addDelay(500);
        addTask("runIntake", () -> intake.runIntake(0.8f), () -> true); // queue intake to run
        addDelay(500);
        addMTPTask(new Position(58, 52, 45), ((MovementSettings) move.settings).losePosSettings.withPower(.4), false); // task to move into cheese
        //addTask("fireBG cheese move", () -> robot.taskManager.getMain().getBackgroundTask("into cheese pile").start(), () -> {return true;});
        addTask("wait for intake full", () -> {}, () -> arm.isBucketFull());//task to wait for bucket
        addTask("stop movement and intake", () -> {
            intake.stopIntake();
            move.stopMovementTask();
        }, () -> true);
        addDelay(500);
        addTask("cradle", () -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        addDelay(500);
        addMTPTask(new Position(38, 39, 0), true);//line up to return across pipes
        addMTPTask( new Position(8, 39, 0), true);//return across pipes
        addTask("dump high", () -> arm.setToAPresetPosition((short)2), () -> {return true;});
        addMTPTask(new Position(-4.5, 41, 72), true);//high dump again


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

    /*****************************************
     * Helper methods for creating tasks
     *****************************************/
    private void addDelay(int delay) {
        if (enableDelay) {
            Task task = new Task();
            task.addDelay(delay);
            robot.taskManager.getMain().addSequentialTask(task);
        }
    }

    private void addMTPTask(Position p, MoveToPosSettings mtps, boolean waitForFinish) {
        robot.taskManager.getMain().addSequentialTask(this.move.addMoveToPositionToTask(new Task(), p, mtps, waitForFinish));
    }

    private void addMTPTask(Position p, boolean waitForFinish){
        addMTPTask(p, ((MovementSettings) move.settings).losePosSettings, waitForFinish);
    }

    private void addTask(String name, Task.Step step) {
        Task.EndPoint end = () -> (arm.settings.runMode == 1);
        addTask(name,step,end );
    }

    private void addTask(String name, Task.Step step, Task.EndPoint end){
        Task task = new Task();
        task.addStep(step, end);
        //task.addStep(() -> { robot.addTelemetry("Task status", name);});
        robot.taskManager.getMain().addSequentialTask(task);
    }


}