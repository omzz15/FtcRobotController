package org.firstinspires.ftc.teamcode.base;

import org.firstinspires.ftc.teamcode.Robot;

public abstract class RobotPart{
	public Robot robot;
	public RobotPartHardware hardware;
	public RobotPartSettings settings;

	public RobotPart(Robot robot, RobotPartHardware hardware, RobotPartSettings settings){
		this.robot = robot;
		this.hardware = hardware;
		this.settings = settings;
		robot.parts.add(this);
		construct();
	}

	public void construct(){}

	public void init(){
		hardware.init(robot.hardwareMap);
		settings.init(robot);
		settings.initialized = true;
	}

	public void runForTeleOp(){
		if(settings.sendTelemetry){
			addTelemetry();
		}
	}

	public void addTelemetry(){}

	public void stop(){

	}
}
