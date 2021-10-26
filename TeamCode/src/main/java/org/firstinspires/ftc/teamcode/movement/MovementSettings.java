package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public class MovementSettings extends RobotPartSettings {
	public PIDCoefficients turnPID = new PIDCoefficients(.03,0,0);
	public PIDCoefficients moveXPID = new PIDCoefficients(.07,0,0);
	public PIDCoefficients moveYPID = new PIDCoefficients(.07,0,0);

	public MoveToPosSettings finalPosSettings = new MoveToPosSettings(new double[]{.75, .75, .5}, 20, 10000, 1);
	public MoveToPosSettings losePosSettings = new MoveToPosSettings(new double[]{4, 4, 7.5}, 1, 10000, 1);
}
