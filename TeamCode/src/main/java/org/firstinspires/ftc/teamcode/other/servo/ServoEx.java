package org.firstinspires.ftc.teamcode.other.servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.EndPoints;

//TODO make class for timed servo
public class ServoEx {
	Servo servo;
	EndPoints endPoints;
	int maxSpeed;
	ServoExSettings.RunMode runMode;

	double startPos = 0;
	long moveStartTime = 0;
	double value;

	int currentSpeed = 0;
	double currentPos = 0;


	public ServoEx(Servo servo, EndPoints endPoints, int maxSpeed, ServoExSettings.RunMode runMode){
		this.servo = servo;
		this.endPoints = (endPoints == null) ? new EndPoints(0,1) : endPoints;
		this.maxSpeed = maxSpeed;
		this.runMode = runMode;
	}

	public ServoEx(){}

	public void init(@NonNull ServoExSettings settings, @NonNull Robot robot){
		servo = settings.makeServo(robot.hardwareMap);
		endPoints = settings.endPoints;
		maxSpeed = settings.maxSpeed;
		runMode = settings.runMode;

		if(settings.targetPos != null)
			setPosition(settings.targetPos);
	}

	public void setPosition(double position){
		position = endPoints.capDouble(position);

		startPos = getPosition();
		//moveAmount = Math.abs(startPos - position);
		currentSpeed = (int)Math.signum(position - startPos) * maxSpeed;

		servo.setPosition(position);
		moveStartTime = System.currentTimeMillis();
	}

	public void run(){

	}

	public double getPosition(){
		if(isDone()){
			return servo.getPosition();
		}else{
			return startPos + getRawAmountMoved();
		}
	};

	public boolean isDone(){
		return true;//Math.abs(getRawAmountMoved()) >= moveAmount;
	}

	private double getRawAmountMoved(){
		return currentSpeed * ((System.currentTimeMillis() - moveStartTime) / 1000.0);
	}
}
