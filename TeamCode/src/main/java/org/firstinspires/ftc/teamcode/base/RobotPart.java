package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;

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
		robot.parts.add(this);
		construct();
	}

	public void construct(){}

	public void init(){
		if(hardware != null) hardware.init(robot);
		settings.init(robot);
		settings.initialized = true;
	}

	public void runPart(){
		if(settings.runMode == -1) {
			onStop();
			settings.runMode = 0;
		}
		else if(settings.runMode == 1)
			teleOpRunCode();
	}

	public void teleOpRunCode(){}

	public void addTelemetry(){}

	public abstract void onStop();

	public void stop(){
		settings.runMode = -1;
	}
}
