package org.firstinspires.ftc.teamcode.basethreaded;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTrackerSettings;

public abstract class RobotThreadedPart extends RobotPart implements Runnable{
	Thread thread;

	public RobotThreadedPart(Robot robot, RobotPartHardware hardware, RobotThreadedPartSettings settings) {
		super(robot, hardware, settings);
	}

	public void init(){
		super.init();
		if(((RobotThreadedPartSettings) settings).makeThread)
			thread = new Thread(this);
	}

	@Override
	public void run() {
		onThreadInit();
		while(!thread.isInterrupted())
			onThreadLoop();
	}

	public void startThread(){
		thread.start();
	}

	public void stopThread(){
		thread.interrupt();
	}

	public abstract void onThreadInit();

	public abstract void onThreadLoop();
}
