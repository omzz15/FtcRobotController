package org.firstinspires.ftc.teamcode.motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class RampedMotorDriver extends MotorDriver {

	double targetPower = 0;
	double ramp = 0.1;

	public RampedMotorDriver(DcMotorEx motor, double ramp) {
		super(motor);
		this.ramp = ramp;
	}

	@Override
	public void setPower(double power) {
		targetPower = power;
	}

	public void setRamp(double ramp){
		this.ramp = ramp;
	}

	@Override
	public void runDriver() {
		double currentPower = motor.getPower();
		if(currentPower < targetPower) {
			currentPower += ramp;
			if(currentPower > targetPower)
				currentPower = targetPower;
			motor.setPower(currentPower);
		}
		else if(currentPower > targetPower) {
			currentPower -= ramp;
			if(currentPower < targetPower)
				currentPower = targetPower;
			motor.setPower(currentPower);
		}
	}

	@Override
	public void stop(){
		super.stop();
		targetPower = 0;
	}
}
