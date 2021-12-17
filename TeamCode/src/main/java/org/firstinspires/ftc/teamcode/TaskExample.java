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
		//attach to main task runner in task manager as sequential task(add to a list of tasks that run one by one and remove themselves once done)
		r.taskManager.getMain().addSequentialTask(t);
		//put in main task runner list in task manager and attach to task runner as a background task(runs all background tasks until tasks are done(does not delete them))
		r.taskManager.getMain().addTask("test", t, true);

		while(opModeIsActive()){
			//inside r.run() the taskManager.run(); is called which calls TaskRunner.run() for every task runner. it runs all background and sequential tasks
			r.run();
			//checks if the main task runner has any sequential tasks left
			if(r.taskManager.getMain().sequentialTasksDone())
				r.addTelemetry("running", "done");
			r.addAllTelemetry();
		}

		r.stop();
	}
}
