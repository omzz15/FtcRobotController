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

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        tracker = new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        new Vision(robot);

        addTask("Cradle", () -> arm.setToAPresetPosition((short)4));
        //addMove(new Position(9.5, 39 , 72));
        addMove(new Position(-4.5, 41 , 72));  // dump position
        addTask("Dump", () -> arm.setToAPresetPosition((short)2));
        addDelay(500);
        addTask("Cradle", () -> arm.setToAPresetPosition((short)4));
        addDelay(500);
        addMove(new Position(8, 39, 0));  // line up to cross pipes
        //addDelay(500);
        addMove(new Position(38, 39, 0));  // cross pipes
        addDelay(500);
        addMove(new Position(48, 41, 45));
        addDelay(500);
        // drop intake
        //addTask("lowerIntake", () -> intake.setIntakeToPreset(Intake.IntakePosition.DOWN), () -> intake.intakeServoDoneMoving());
        //addDelay(500);
        addTask("runIntake", () -> intake.runIntake(0.8f), () -> {return true;});
        addDelay(500);
        addMoveBackground("forward", new Position(58, 52, 45), () -> arm.isBucketFull());
        addTask("fireBackground", () -> robot.taskManager.getMain().getBackgroundTask("forward").restart(), () -> {return true;});
        addTask("runIntake", () -> intake.runIntake(0.8f), () -> arm.isBucketFull());
        addDelay(500);
        // start intake of blocks
         /*
        Task task = new Task();
        task.addStep(() -> intake.runIntake(0.8f), () -> arm.isBucketFull());
        robot.taskManager.getMain().addTask("test", task, true);
        */
        // drive into pile of blocks until bucket is full
        //addMoveBackground("forward", new Position(35, -12, 45), () -> arm.isBucketFull());

        addTask("Cradle", () -> arm.setToAPresetPosition((short)4));
        addDelay(500);
        addMove(new Position(38, 39, 0));  // line up to cross pipes
        //addDelay(500);
        addMove(new Position(8, 39, 0));  // cross pipes
        addDelay(500);
        addMove(new Position(-4.5, 41, 72));  // dump position
        addTask("Dump", () -> arm.setToAPresetPosition((short)2));


        //addTask("Flat", () -> arm.setToAPresetPosition((short)1));
        //addTask("Fdump", () -> arm.setToAPresetPosition((short)3));
		/*
		addMove(new Position(-16, -19 , 16.875));
		addMove(new Position(-1, -17 , 90));
		addMove(new Position(30, -17, 90));
		arm.addDockArmTask();
		addMove(new Position(40, -9, 45));
		addMove(new Position(45,-4,45));
		addMove(new Position(40, -9, 45));
		addMove(new Position(30, -17, 90));
		addMove(new Position(-1, -17 , 90));
		addMove(new Position(-16, -19 ,16.875));
        */

        robot.init();
        waitForStart();
        robot.start();

        while(opModeIsActive()){
            robot.run();
            robot.sendTelemetry();
        }

        robot.stop();
    }

    private void addMove(Position p) {
        robot.taskManager.getMain().addSequentialTask(this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).losePosSettings));
    }
    private void addMoveBackground(String name, Position p, Task.EndPoint end) {
        Task task = new Task();
        task = this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).losePosSettings);
        task.addStep(end);
        robot.taskManager.getMain().addTask(name, task,true);

    }

    private void addDelay(int delay) {
        Task task = new Task();
        task.addDelay(delay);
        robot.taskManager.getMain().addSequentialTask(task);
    }

    private void addTask(String name, Task.Step step) {
        Task.EndPoint end = () -> (arm.settings.runMode == 1);
        addTask(name,step,end );
    }

    private void addTask(String name, Task.Step step, Task.EndPoint end){
        Task task = new Task();
        task.addStep(step, end);
        task.addStep(() -> { robot.addTelemetry("Task status", name);});
        robot.taskManager.getMain().addSequentialTask(task);
    }
    private void createTask(Robot r) {
        //create simple task
        Task t = new Task();
        //create step to print message
        Task.Step s = () -> {
            r.addTelemetry("hello", "world");
            r.sendTelemetry();
        };
        //create endpoint to check if robot stopped
        Task.EndPoint e = () -> {
            return r.shouldStop();
        };
        //add steps to task
        //this runs the step once
        t.addStep(s);
        //this keeps running nothing until the endpoint is true
        t.addStep(e);
        //this keeps running step until endpoint is true
        t.addStep(s,e);
        //attach to main task runner in task manager as sequential task(add to a list of tasks that run one by one and remove themselves once done)
        r.taskManager.getMain().addSequentialTask(t);
        //put in main task runner list in task manager and attach to task runner as a background task(runs all background tasks until tasks are done(does not delete them))
        //r.taskManager.getMain().addTask("test", t, true);
    }
}