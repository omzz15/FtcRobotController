package org.firstinspires.ftc.teamcode.other.servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.thread.VirtualThread;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;

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

	public void init(@NonNull ServoSettings settings, @NonNull Robot robot){
		servo = settings.makeServo(robot.hardwareMap);
		if(settings.targetPos != null)
			setPosition(settings.targetPos);
	}

	public void setPosition(double position){
		startPos = getPosition();
		moveStartTime = System.currentTimeMillis();
		servo.setPosition(position);
	}


	public double getPosition(){
		return startPos + (Math.signum(servo.getPosition() - startPos) * servoSpeed * ((System.currentTimeMillis() - moveStartTime) / 1000.0));
	};
}
