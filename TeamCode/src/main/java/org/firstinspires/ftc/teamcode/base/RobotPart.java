package org.firstinspires.ftc.teamcode.base;

public abstract class RobotPart{
	public Robot robot;
	public RobotPartHardware hardware;
	public RobotPartSettings settings;

	//////////////////
	//initialization//
	//////////////////
	public RobotPart(Robot robot, RobotPartHardware hardware, RobotPartSettings settings){
		this.robot = robot;
		this.hardware = hardware;
		this.settings = settings;
		onConstruct();
		robot.parts.add(this);
	}

	protected void init(){
		if(hardware != null) hardware.init(robot);
		settings.init(robot);
		onInit();
		settings.initialized = true;
	}

	protected void init(short runMode){
		init();
		settings.runMode = runMode;
	}

	void runPart(){
		if(settings.runMode == -1) {
			onStop();
			settings.runMode = 0;
		}
		else if(settings.runMode > 0) {
			onRunLoop(settings.runMode);
			if (settings.runMode == 1)
				onTeleOpLoop();
		}
	}

	void addTelemetry(){
		onAddTelemetry();
	}

	public void stop(){
		settings.runMode = -1;
	}


	///////////////
	//run methods//
	///////////////
	public abstract void onConstruct();

	public abstract void onInit();

	public abstract void onTeleOpLoop();

	public abstract void onRunLoop(short runMode);

	public abstract void onAddTelemetry();

	public abstract void onStop();
}
