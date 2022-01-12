package org.firstinspires.ftc.teamcode.other.task;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TaskRunner {
	private String name;
	private boolean running = false;

	private Hashtable<String, Task> allTasks = new Hashtable<>();
	private Hashtable<String , Task> backgroundTasks = new Hashtable<>();
	private ArrayList<Task> sequentialTasks = new ArrayList<>();


	public TaskRunner(String name, TaskManager taskManager){
		this.name = name;
		taskManager.attachTaskRunner(name, this);
	}

	public TaskRunner(String name){
		this.name = name;
	}

	public TaskRunner(){
		this.name = "unnamed";
	}


	//---------------Attach---------------//
	/**
	 * attaches the current task runner to the task manager specified
	 * @param name the name that this will show up as in the task manager hashtable
	 * @param taskManager the taskManager that you want to attach the taskRunner to
	 * @deprecated Use TaskManager.attachTaskRunner() instead
	 */
	@Deprecated
	public void attachToManager(String name, @NonNull TaskManager taskManager){
		taskManager.attachTaskRunner(name, this);
	}


	//---------------Adders---------------//
	//all tasks
	/**
	 * adds a task to the list of tasks(allTasks) with a key and optionally attaches the task as a background task(put in backgroundTasks)
	 * @param key the name of the task(will be used in allTask and (optionally)backgroundTasks hashtable)
	 * @param task the task you want to add/attach to the lists and run
	 * @param runInBackground whether or not to add the task to backgroundTasks and run it
	 * @param startImmediately if the task should be started when it is added to background(only applies if runInBackground is true)
	 */
	public void addTask(String key, Task task, boolean runInBackground, boolean startImmediately){
		addTask(key, task);
		if(runInBackground)
			addBackgroundTask(key, task, startImmediately);
	}

	public void addTask(String key, Task task){
		key = Utils.Misc.getAvailableName(allTasks, key);
		allTasks.put(key, task);
	}

	public void addTask(Task task){
		addTask(task.getName(), task);
	}

	//background
	public void addBackgroundTask(String key, Task task, boolean startImmediately){
		key = Utils.Misc.getAvailableName(backgroundTasks, key);
		if(startImmediately) task.start();
		backgroundTasks.put(key, task);
	}

	public void addBackgroundTask(String key, boolean startImmediately){
		addBackgroundTask(key, allTasks.get(key), startImmediately);
	}

	public void addBackgroundTask(Task task, boolean startImmediately){
		addBackgroundTask(task.getName(), task, startImmediately);
	}

	//sequential
	public void addSequentialTask(@NonNull Task task){
		task.start();
		sequentialTasks.add(task);
	}

	public void addSequentialTask(String name){
		addSequentialTask(allTasks.get(name));
	}


	//---------------Getters---------------//
	//all task
	public Hashtable<String,Task> getAllTasks(){
		return allTasks;
	}

	/**
	 * gets the task that is listed in allTasks under the passed in key(name)
	 * @param key the key the task is listed as
	 * @return the task under the listed key
	 */
	public Task getTask(String key){
		return allTasks.get(key);
	}

	//background
	public Hashtable<String,Task> getBackgroundTasks(){
		return backgroundTasks;
	}

	public Task getBackgroundTask(String key){
		return backgroundTasks.get(key);
	}

	//sequential
	public List<Task> getSequentialTasks(){
		return sequentialTasks;
	}

	public Task getCurrSequentialTask(){
		return sequentialTasks.get(0);
	}


	//---------------Removers---------------//
	//all
	public void clearAll(){
		clearAllTasks();
		clearBackgroundTasks();
		clearSequentialTasks();
	}

	//all tasks
	/**
	 * removes the task from allTasks with the passed in key(name)
	 * @param key the key of the task you want to remove
	 */
	public void removeTask(String key){
		allTasks.remove(key);
	}

	/**
	 * removes the task from allTasks with the passed in key(name)
	 * @param key the key of the task you want to remove
	 * @param removeFromBackground whether or not you want to remove the task from background
	 */
	public void removeTask(String key, boolean removeFromBackground){
		removeTask(key);
		if(removeFromBackground) backgroundTasks.remove(key);
	}

	public void clearAllTasks(){
		allTasks.clear();
	}

	//background
	public void removeBackgroundTask(String key){allTasks.remove(key);}

	public void clearBackgroundTasks(){
		backgroundTasks.clear();
	}

	//sequential
	public void clearSequentialTasks(){
		sequentialTasks.clear();
	}

	//---------------Runners---------------//
	//all
	public void run(){
		if(running) {
			runAllBackgroundTasks();
			runSequentialTask();
		}
	}

	//background
	public void runAllBackgroundTasks(){
		for (Task t: backgroundTasks.values()) {
			t.run();
		}
	}

	//sequential
	public void runSequentialTask(){
		if(!sequentialTasks.isEmpty()) {
			Task t = sequentialTasks.get(0);
			t.run();
			if (t.isDone())
				sequentialTasks.remove(0);
		}
	}


	//---------------Misc---------------//
	public boolean sequentialTasksDone(){
		return sequentialTasks.isEmpty();
	}

	//name
	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	//running
	public void stop(){
		running = false;
	}

	public void start(){
		running = true;
	}

	public boolean isRunning() {
		return running;
	}
}
