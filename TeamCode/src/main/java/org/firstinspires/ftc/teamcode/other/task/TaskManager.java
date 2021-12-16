package org.firstinspires.ftc.teamcode.other.task;

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
}
