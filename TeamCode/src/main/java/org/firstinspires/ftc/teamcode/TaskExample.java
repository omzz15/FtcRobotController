package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.task.Task;

public class TaskExample extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		//create robot object and taskManager inside
		Robot r = new Robot(this);

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
		//attach to task manager as sequential task(add to a list of tasks that run one by one and remove themselves once done)
		r.taskManager.addSequentialTask(t);
		//put in task manager list and attach to task manager as a background task(runs all background tasks until tasks are done(does not delete them))
		r.taskManager.addTask("test", t, true);

		while(opModeIsActive()){
			//inside r.run() the taskManager.runAll(); is called which runs background and sequential tasks
			r.run();
			r.addAllTelemetry();
		}

		r.stop();
	}
}
