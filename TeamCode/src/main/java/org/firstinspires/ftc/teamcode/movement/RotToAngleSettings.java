package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

class RotToAngleSettings {
	double tol;
	int timesInTol;
	int maxRuntime;
	double maxPower;
	PIDCoefficients turnPID;

	RotToAngleSettings() {
	}

	RotToAngleSettings(double tol, int timesInTol, int maxRuntime, double maxPower) {
		this.tol = tol;
		this.timesInTol = timesInTol;
		this.maxRuntime = maxRuntime;
		this.maxPower = maxPower;
	}

	RotToAngleSettings(double tol, int timesInTol, int maxRuntime, double maxPower, PIDCoefficients turnPID) {
		this.tol = tol;
		this.timesInTol = timesInTol;
		this.maxRuntime = maxRuntime;
		this.maxPower = maxPower;
		this.turnPID = turnPID;
	}

	boolean isPIDValid() {
		return turnPID != null;
	}
}
