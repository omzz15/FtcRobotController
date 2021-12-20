package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "test auto tasks", group = "Test")
public class AutoTestTasks extends LinearOpMode {
    Movement move;
    Robot robot;

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        new PositionTracker(robot);
        new DuckSpinner(robot);
        new Intake(robot);
        move = new Movement(robot);
        Arm arm = new Arm(robot);
        new Vision(robot);

        // Testing tasks
        //arm.addDockArmTask();
        //addMove(new Position(0, -5 , 0));
        //addStep(() -> ((Intake) robot.getPartByClass(Intake.class)).setIntakeToPreset(Intake.IntakePosition.DOWN));
        addStep(() -> { arm.setToCradle(); });
        addMove(new Position(-16, -19 , 16.875));
        //addStep(() -> ((Intake) robot.getPartByClass(Intake.class)).setIntakeToPreset(Intake.IntakePosition.DOWN));
        addStep(() -> { arm.setToDump(); });

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
        robot.taskManager.getMain().addSequentialTask(this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).finalPosSettings));
    }

    private void addStep(Task.Step step) {
        Task task = new Task();
        Task.EndPoint end = () -> { return robot.shouldStop(); };
        task.addStep(step,end);
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
