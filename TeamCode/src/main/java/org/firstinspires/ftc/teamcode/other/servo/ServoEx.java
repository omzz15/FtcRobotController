package org.firstinspires.ftc.teamcode.other.servo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.EndPoints;

//TODO make class for timed servo
public class ServoEx {
	Servo servo;

	EndPoints positionEnds;
	EndPoints angleEnds;
	int maxSpeed;
	ServoExSettings.RunMode runMode;

	double startPos = 0;
	long moveStartTime = 0;
	double value;

	int currentSpeed = 0;
	double currentPos = 0;


	public ServoEx(Servo servo, EndPoints positionEnds, EndPoints angleEnds, int maxSpeed, ServoExSettings.RunMode runMode){

	}

	public ServoEx(){

	}

	public void init(@NonNull ServoExSettings settings, @NonNull Robot robot){

	}

	public void setPosition(double position){

	}

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
