package org.firstinspires.ftc.teamcode.other.task;

import java.util.ArrayList;
import java.util.List;

public class Task {
	private String name;
	private List<Step> steps = new ArrayList<>();
	private List<EndPoint> ends = new ArrayList<>();

	private short task = 0;
	private boolean done = false;
	private boolean running = false;

	private long delayStart;

	public Task(String name){
		this.name = name;
	}

	public Task(){
		this.name = "unnamed";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addStep(Step step, EndPoint end){
		steps.add(step);
		ends.add(end);
	}

	public void addStep(EndPoint end){
		steps.add(() -> {});
		ends.add(end);
	}

	public void addStep(Step step){
		steps.add(step);
		ends.add(() -> {return true;});
	}

	public void addDelay(int delay){
		if(delay <= 0) return;
		addStep(() -> {delayStart = System.currentTimeMillis();});
		addStep(() -> (System.currentTimeMillis() - delayStart >= delay));
	}

	public void clear(){
		steps.clear();
		ends.clear();
		reset();
	}

	public void reset(){
		task = 0;
		done = false;
		running = false;
	}

	public void start(){
		task = 0;
		done = false;
		running = true;
	}

	public void pause(){
		running = false;
	}

	public void unPause(){
		running = true;
	}

	public boolean isRunning(){
		return running;
	}

	public void run(){
		if(!done && running){
			steps.get(task).apply();
			if(ends.get(task).apply()) {
				task++;
				if (task == steps.size()) {
					done = true;
					running = false;
				}
			}
		}
	}

	public boolean isDone(){
		return done;
	}

	public interface Step{
		void apply();
	}

	public interface EndPoint{
		boolean apply();
	}
}
