package org.firstinspires.ftc.teamcode.other.task;

import org.firstinspires.ftc.teamcode.base.Robot;

import java.util.Hashtable;

public class TaskManager {
	private Hashtable<String, TaskRunner> taskRunners = new Hashtable<>();

	public TaskManager(){
		taskRunners.put("main", new TaskRunner());
	}

	public void attachTaskRunner(String key, TaskRunner taskRunner){
		taskRunners.put(key, taskRunner);
	}

	public TaskRunner getTaskRunner(String key){
		return taskRunners.get(key);
	}

	public TaskRunner getMain(){
		return taskRunners.get("main");
	}

	public void detachTaskRunner(String key){
		taskRunners.remove(key);
	}

	public void run(){
		for(TaskRunner runner : taskRunners.values())
			runner.run();
	}

	public void printCallStack(Robot r){
		r.addTelemetry("Call Stack:", null);
		r.addTelemetry("\t Runners:", null);
		for(String key : taskRunners.keySet()){
			r.addTelemetry("\t \t" , key);
			r.addTelemetry("\t \t \t" + "Background:", null);
			for(String task : taskRunners.get(key).getBackgroundTasks().keySet())
				r.addTelemetry("\t \t \t \t" + task, " - Running: " + taskRunners.get(key).getBackgroundTasks().get(task).isRunning());
			r.addTelemetry("\t \t \t" + "Sequential:", null);
			for(Task task : taskRunners.get(key).getSequentialTasks())
				r.addTelemetry("\t \t \t \t" + task, " - Running: " + task.isRunning());
		}

	}
}
