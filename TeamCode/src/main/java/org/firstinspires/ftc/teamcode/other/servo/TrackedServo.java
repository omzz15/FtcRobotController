package org.firstinspires.ftc.teamcode.other.servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.thread.VirtualThread;

//TODO make class for timed servo
public class TrackedServo{
	Servo servo;
	int servoSpeed;
	double startPos = 0;
	long moveStartTime = 0;

	public TrackedServo(Servo servo, int servoSpeed){
		this.servo = servo;
		this.servoSpeed = servoSpeed;
	}
	public TrackedServo(int servoSpeed){
		this.servoSpeed = servoSpeed;
	}

	public void init(@NonNull ServoSettings settings, Robot robot){
		servo = settings.makeServo(robot.hardwareMap);
		if(settings.targetPos != null)
			set(settings.targetPos);
	}

	public void set(double position ){

	}

	public void set(){

	}
}
