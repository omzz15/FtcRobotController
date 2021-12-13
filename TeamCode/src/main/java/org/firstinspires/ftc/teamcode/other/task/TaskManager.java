package org.firstinspires.ftc.teamcode.other.task;

import java.util.ArrayList;
import java.util.Hashtable;

public class TaskManager {
	private Hashtable<String, Task> allTasks = new Hashtable<>();

	private Hashtable<String , Task> backgroundTasks = new Hashtable<>();
	private ArrayList<Task> sequentialTasks = new ArrayList<>();

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
		Task t = sequentialTasks.get(0);
		try {
			t.run();
			if (t.isDone())
				sequentialTasks.remove(0);
		} catch (Exception e) {
		}
	}

	public boolean sequentialTasksDone(){
		return sequentialTasks.isEmpty();
	}

	public void addBackgroundTask(String key, Task task){
		allTasks.put(key, task);
	}

	public Task getBackgroundTask(String key){
		return allTasks.get(key);
	}

	public void removeBackgroundTask(String key){allTasks.remove(key);}

	public void runAllBackgroundTasks(){
		for (Task t: backgroundTasks.values()) {
			t.run();
		}
	}

	public void runAll(){
		runAllBackgroundTasks();
		runSequentialTask();
	}
}
