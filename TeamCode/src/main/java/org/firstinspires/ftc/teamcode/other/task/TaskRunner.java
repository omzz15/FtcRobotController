package org.firstinspires.ftc.teamcode.other.task;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TaskRunner {
	private Hashtable<String, Task> allTasks = new Hashtable<>();

	private Hashtable<String , Task> backgroundTasks = new Hashtable<>();
	private ArrayList<Task> sequentialTasks = new ArrayList<>();


	public TaskRunner(String name, TaskManager taskManager){
		taskManager.attachTaskRunner(name, this);
	}

	public TaskRunner(){}


	/**
	 * attaches the current task runner to the task manager specified
	 * @param name the name that this will show up as in the task manager hashtable
	 * @param taskManager the taskManager that you want to attach the taskRunner to
	 * @deprecated Use TaskManager.attachTaskRunner() instead
	 */
	@Deprecated
	public void attachToManager(String name, TaskManager taskManager){
		taskManager.attachTaskRunner(name, this);
	}

	/**
	 * adds a task to the list of tasks(allTasks) with a key and optionally attaches the task as a background task(put in backgroundTasks)
	 * @param key the name of the task(will be used in allTask and (optionally)backgroundTasks hashtable)
	 * @param task the task you want to add/attach to the lists and run
	 * @param runInBackground whether or not to add the task to backgroundTasks and run it
	 */
	public void addTask(String key, Task task, boolean runInBackground, boolean startImmediately){
		allTasks.put(key, task);
		if(runInBackground)
			addBackgroundTask(key, task, startImmediately);
	}

	/**
	 * gets the task that is listed in allTasks under the passed in key
	 * @param key
	 * @return
	 */
	public Task getTask(String key){
		return allTasks.get(key);
	}

	public void removeTask(String key){
		allTasks.remove(key);
	}

	public void addSequentialTask(Task task){
		task.start();
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

	public void addBackgroundTask(String key, Task task, boolean startImmediately){
		if(startImmediately) task.start();
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

	public Hashtable<String,Task> getBackgroundTasks(){
		return backgroundTasks;
	}

	public List<Task> getSequentialTasks(){
		return sequentialTasks;
	}
}
