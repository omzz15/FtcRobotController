package org.firstinspires.ftc.teamcode.other.servo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareMap;
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

	double lastPos = 0;
	long lastMoveTime = 0;

	int currentSpeed = 0;
	double currentPos = 0;


	public ServoEx(){

	}

	public void init(@NonNull ServoExSettings settings, @NonNull HardwareMap hardwareMap){

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
