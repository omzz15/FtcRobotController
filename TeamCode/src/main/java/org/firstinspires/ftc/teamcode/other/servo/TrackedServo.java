package org.firstinspires.ftc.teamcode.other.servo;

import com.qualcomm.robotcore.hardware.Servo;
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
}
