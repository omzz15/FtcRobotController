package org.firstinspires.ftc.teamcode.other.task;

import java.util.Hashtable;

public class TaskManager {
	private Hashtable<String, Task> tasks;

	public void addTask(String key, Task task){
		tasks.put(key, task);
	}

	public Task getTask(String key){
		return tasks.get(key);
	}

	public void removeTask(String key){
		tasks.remove(key);
	}
}
