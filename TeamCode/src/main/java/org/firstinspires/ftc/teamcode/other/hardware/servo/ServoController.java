package org.firstinspires.ftc.teamcode.other.hardware.servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Range;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.hardware.Hardware;
import org.firstinspires.ftc.teamcode.other.hardware.HardwareManager;
import org.firstinspires.ftc.teamcode.other.task.Task;

//TODO finish class for servo controller
public class ServoController extends Hardware {
	private Servo servo;

	private ServoControllerSettings settings;

	private long startTime = 0;

	private double runStartPos = 0;
	private long runStartTime = 0;
	private double runSpeed = 0;

	private int time;
	private double speed;

	private double currentPos = 0;
	private double targetPos = 0;

	private boolean done = false;

	public ServoController(){}

	public ServoController(ServoControllerSettings settings){
		updateSettings(settings);
	}

	public void init(@NonNull ServoSettings hardwareSettings, @NonNull ServoControllerSettings settings, @NonNull Robot robot, String name){
		servo = hardwareSettings.makeServo(robot.hardwareMap);
		updateSettings(settings);
		if(name != null)
			robot.hardwareManager.attachHardware(name, this);
	}

	/**
	 * attaches the current hardware device to the specified hardware manager using the specified name
	 * @deprecated use the HardwareManager.attachHardware() method instead
	 * @param name the name of this hardware device you want to attach
	 * @param manager the HardwareManager you want to attach this device to
	 */
	@Deprecated
	public void attachToManager(String name, @NonNull HardwareManager manager){
		manager.attachHardware(name,this);
	}

	public void updateSettings(ServoControllerSettings settings){
		this.settings = settings;
	}

	public void setPosition(double position){
		setRunMode(ServoControllerSettings.RunMode.RUN_AT_MAX);
		start(position);
	}

	public void setPosition(double position, int time){
		setTime(time);
		setRunMode(ServoControllerSettings.RunMode.RUN_WITH_TIME);
		start(position);
	}

	public void setPosition(double position, double speed, ServoSpeedUnit unit, boolean useCorrection){
		setSpeed(speed, unit);
		if(!useCorrection) {
			setRunMode(ServoControllerSettings.RunMode.RUN_AT_RATE);
		}else{
			setTime((int)((position - currentPos) / speed * 1000));
			setRunMode(ServoControllerSettings.RunMode.RUN_WITH_TIME);
		}
		start(position);
	}

	public void setRunMode(ServoControllerSettings.RunMode runMode){
		settings.runMode = runMode;
	}

	public void setTime(int time){
		this.time = time;
	}

	public void setSpeed(double speed, ServoSpeedUnit unit){
		if(unit == ServoSpeedUnit.ANGLE)
			speed = unit.convertToPosition(speed, settings.angleRange, settings.positionRange);
		this.speed = speed;
	}

	public void setHomeFunction(Task.EndPoint homeFunction){
		settings.setHomeFunction(homeFunction);
	}

	private void start(double position){
		targetPos = position;
		startTime = System.currentTimeMillis();
		done = false;
		if(settings.runMode == ServoControllerSettings.RunMode.RUN_AT_MAX)
			servo.setPosition(targetPos);
	}

	@Override
	public void run(){
		if(!isDone()){
			runStartTime = System.currentTimeMillis();
			runStartPos = getCurrentPos();
		}
	}

	public double getPosition(){
		return 0;
	}

	public boolean isDone(){
		return done;
	}

	private double getRunSpeed(){ //speed is in pos/sec
		int sign = Utils.Math.getSign(targetPos - currentPos);
		if(settings.runMode == ServoControllerSettings.RunMode.RUN_AT_RATE)
			return speed * sign;
		else if(settings.runMode == ServoControllerSettings.RunMode.RUN_WITH_TIME){
			int timeRemaining = (int)(time - (System.currentTimeMillis() - startTime)); // in ms
			return (targetPos - currentPos) / timeRemaining * 1000; // in pos/sec
		}else //should not be called unless something was set incorrectly
			return sign * ServoSpeedUnit.POSITION.convertToPosition(settings.maxSpeed, settings.angleRange, settings.positionRange);
	}

	private double getCurrentPos(){
		return 0;
	}

	private double getRawAmountMoved(){
		return ((System.currentTimeMillis() - runStartTime) / 1000) * runSpeed;
	}

	public enum ServoSpeedUnit{
		ANGLE,
		POSITION;

		public double convertToOther(double value, Range angleRange, Range positionRange){
			if(this == ANGLE){
				return convertToPosition(value, angleRange, positionRange);
			}else{
				return convertToAngle(value, angleRange, positionRange);
			}
		}

		public double convertToPosition(double value, Range angleRange, Range positionRange){
			return positionRange.doubleConvert(value, angleRange);
		}

		public double convertToAngle(double value, Range angleRange, Range positionRange){
			return angleRange.doubleConvert(value, positionRange);
		}
	}
}
