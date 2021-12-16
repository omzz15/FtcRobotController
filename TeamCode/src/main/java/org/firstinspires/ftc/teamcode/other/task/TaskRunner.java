package org.firstinspires.ftc.teamcode.other.task;

import java.util.ArrayList;
import java.util.Hashtable;

public class TaskRunner {
	private Hashtable<String, Task> allTasks = new Hashtable<>();

	private Hashtable<String , Task> backgroundTasks = new Hashtable<>();
	private ArrayList<Task> sequentialTasks = new ArrayList<>();


	public TaskRunner(String name, TaskManager taskManager){
		attachToManager(name, taskManager);
	}

	public TaskRunner(){}

	public void attachToManager(String name, TaskManager taskManager){
		taskManager.attachTaskRunner(name, this);
	}

	public void addTask(String key, Task task, boolean runInBackground){
		allTasks.put(key, task);
		if(runInBackground)
			addBackgroundTask(key, task);
	}

	public Task getTask(String key){
		return allTasks.get(key);
	}

	public void removeTask(String key){
		allTasks.remove(key);
	}

	public void addSequentialTask(Task task){
		task.restart();
		sequentialTasks.add(task);
	}

	public void addSequentialTask(String name){
		addSequentialTask(allTasks.get(name));
	}

	public void runSequentialTask(){
		if(!sequentialTasks.isEmpty()) {
			Task t = sequentialTasks.get(0);
			t.run();
			if (t.isDone())
				sequentialTasks.remove(0);
		}
	}

	public boolean sequentialTasksDone(){
		return sequentialTasks.isEmpty();
	}

	public void addBackgroundTask(String key, Task task){
		backgroundTasks.put(key, task);
	}

	public void addBackgroundTask(String key){
		backgroundTasks.put(key, allTasks.get(key));
	}

	public Task getBackgroundTask(String key){
		return backgroundTasks.get(key);
	}

	public void removeBackgroundTask(String key){allTasks.remove(key);}

	public void runAllBackgroundTasks(){
		for (Task t: backgroundTasks.values()) {
			t.run();
		}
	}

	public void run(){
		runAllBackgroundTasks();
		runSequentialTask();
	}
}
