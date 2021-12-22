package org.firstinspires.ftc.teamcode.other.hardware;

import org.firstinspires.ftc.teamcode.other.task.Task;

public abstract class Hardware{
	public boolean homed = false;
	public Task.EndPoint homeFunction = () -> (true);

	public abstract void run();
	boolean home(){
		return homeFunction.apply();
	}
}
