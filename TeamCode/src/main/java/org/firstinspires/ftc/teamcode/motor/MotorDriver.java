package org.firstinspires.ftc.teamcode.motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MotorDriver {
	DcMotorEx motor;

	public MotorDriver(DcMotorEx motor){
		this.motor = motor;
	}

	public void updateMotor(MotorSettings settings, boolean restPos){
		settings.updateMotor(motor, restPos);
	}

	public void setPower(double power){
		motor.setPower(power);
	}

	public void runDriver(){}

	public void stop(){
		motor.setPower(0);
	}
}
