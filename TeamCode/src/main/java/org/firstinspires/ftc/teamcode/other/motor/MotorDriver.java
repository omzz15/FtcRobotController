package org.firstinspires.ftc.teamcode.other.motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class MotorDriver {
	DcMotorEx motor;

	public MotorDriver(DcMotorEx motor){
		this.motor = motor;
	}

	public void updateMotor(MotorSettings settings, boolean restPos){
		settings.updateMotor(motor, restPos);
	}

	public abstract void setPower(double power);

	public abstract void runDriver();

	public void stop(){
		motor.setPower(0);
	}
}
