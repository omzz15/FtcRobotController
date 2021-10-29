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
		while(!thread.isInterrupted()) {
			if (((RobotThreadedPartSettings) settings).threadRunMode == -1) {
				onThreadPause();
				((RobotThreadedPartSettings) settings).threadRunMode = 0;
			} else if (((RobotThreadedPartSettings) settings).threadRunMode > 0)
				onThreadLoop(((RobotThreadedPartSettings) settings).threadRunMode);
		}
		onThreadStop();
	}

	public void startThread(){
		thread.start();
	}

	public void pauseThread(){
		((RobotThreadedPartSettings) settings).threadRunMode = -1;
	}

	public void unPauseThread(){
		((RobotThreadedPartSettings) settings).threadRunMode = 1;
	}

	public void stopThread(){
		thread.interrupt();
	}


	///////////////
	//run methods//
	///////////////
	public abstract void onThreadInit();

	public abstract void onThreadLoop(short runMode);

	public abstract void onThreadPause();

	public abstract void onThreadStop();
}
