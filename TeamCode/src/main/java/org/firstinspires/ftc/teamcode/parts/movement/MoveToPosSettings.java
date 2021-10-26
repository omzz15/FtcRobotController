package org.firstinspires.ftc.teamcode.parts.movement;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

class MoveToPosSettings
{
	double[] tol;
	int timesInTol;
	int maxRuntime;
	double maxPower;
	PIDCoefficients turnPID = null;
	PIDCoefficients xPID = null;
	PIDCoefficients yPID = null;

	MoveToPosSettings(){}
	MoveToPosSettings(double[] tol, int timesInTol, int maxRuntime, double maxPower)
	{
		this.tol = tol;
		this.timesInTol = timesInTol;
		this.maxRuntime = maxRuntime;
		this.maxPower = maxPower;
	}

	MoveToPosSettings(double[] tol, int timesInTol, int maxRuntime, double maxPower, PIDCoefficients xPID, PIDCoefficients yPID, PIDCoefficients turnPID)
	{
		this.tol = tol;
		this.timesInTol = timesInTol;
		this.maxRuntime = maxRuntime;
		this.maxPower = maxPower;
		this.xPID = xPID;
		this.yPID = yPID;
		this.turnPID = turnPID;
	}

	public boolean isPIDValid()
	{
		return turnPID != null && xPID != null && yPID != null;
	}


	RotToAngleSettings toRotAngleSettings()
	{
		if(turnPID != null) return new RotToAngleSettings(tol[2], timesInTol, maxRuntime, maxPower, turnPID);
		return new RotToAngleSettings(tol[2], timesInTol, maxRuntime, maxPower);
	}


}