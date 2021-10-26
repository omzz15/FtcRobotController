package org.firstinspires.ftc.teamcode.base.basethreaded;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;

public abstract class RobotThreadedPart extends RobotPart implements Runnable{
	Thread thread;

	public RobotThreadedPart(Robot robot, RobotPartHardware hardware, RobotThreadedPartSettings settings) {
		super(robot, hardware, settings);
	}

	protected void init(){
		super.init();
		if(((RobotThreadedPartSettings) settings).makeThread)
			thread = new Thread(this);
	}

	@Override
	public void run() {
		onThreadInit();
		while(!thread.isInterrupted())
			onThreadLoop();
		onThreadStop();
	}

	public void startThread(){
		thread.start();
	}

	public void stopThread(){
		thread.interrupt();
	}


	///////////////
	//run methods//
	///////////////
	public abstract void onThreadInit();

	public abstract void onThreadLoop();

	public abstract void onThreadStop();
}
