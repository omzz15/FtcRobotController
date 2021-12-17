package org.firstinspires.ftc.teamcode.other.hardware.servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.EndPoints;
import org.firstinspires.ftc.teamcode.other.hardware.Hardware;
import org.firstinspires.ftc.teamcode.other.hardware.HardwareManager;

//TODO finish class for servo controller
public class ServoController implements Hardware {
	private Servo servo;

	private ServoControllerSettings settings;

	double startPos = 0;
	long moveStartTime = 0;
	double value;

	double lastPos = 0;
	long lastMoveTime = 0;

	int currentSpeed = 0;
	double currentPos = 0;


	public ServoController(){}

	public ServoController(ServoControllerSettings settings){
		updateSettings(settings);
	}

	public void init(@NonNull ServoSettings hardwareSettings, ServoControllerSettings settings, @NonNull Robot robot, String name){
		servo = hardwareSettings.makeServo(robot.hardwareMap);
		updateSettings(settings);
		if(name != null)
			robot.hardwareManager.attachHardware(name, this);
	}

	public void attachToManager(String name, HardwareManager manager){
		manager.attachHardware(name,this);
	}

	public void updateSettings(ServoControllerSettings settings){
		this.settings = settings;
	}

	public void setPosition(double position){

	}

	public void setRunMode(ServoControllerSettings.RunMode runMode){
		settings.runMode = runMode;
	}

	@Override
	public void run(){

	}

	public double getPosition(){
		return 0;
	}

	public boolean isDone(){
		return false;
	}

	private double getRawAmountMoved(){
		return 0;
	}
}
