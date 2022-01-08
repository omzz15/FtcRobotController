package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "test auto", group = "Test")
public class AutoTest extends LinearOpMode {
	Position[] positions = {
			new Position(-16, -19 , 16.875),
			new Position(-1, -17 , 90),
			new Position(30, -17, 90),
			new Position(40, -9, 45),
			new Position(45,-4,45),
			new Position(40, -9, 45),
			new Position(30, -17, 90),
			new Position(-1, -17 , 90),
			new Position(-16, -19 ,16.875),
	};
	int delay = 100;

	@Override
	public void runOpMode(){
		Robot robot = new Robot(this);

		new Drive(robot);
		new PositionTracker(robot);
		Movement move = new Movement(robot);
		new Vision(robot);

		for(Position p : positions){
			robot.taskManager.getMain().addSequentialTask(move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).finalPosSettings, true));
		}
		// tjk task creation test
		//createTask(robot);

		robot.init();
		waitForStart();
		robot.start();

		while(opModeIsActive()){
			robot.run();
			robot.taskManager.printCallStack();
			if(robot.taskManager.getMain().sequentialTasksDone())
				robot.addTelemetry("task done!", "");
			robot.sendTelemetry();
		}

		robot.stop();
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
