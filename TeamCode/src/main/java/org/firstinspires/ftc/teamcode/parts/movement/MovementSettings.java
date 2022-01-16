package org.firstinspires.ftc.teamcode.parts.movement;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;

public class MovementSettings extends RobotPartSettings {
	public PIDCoefficients turnPID = new PIDCoefficients(.03,0,0);
	public PIDCoefficients moveXPID = new PIDCoefficients(.07,0,0);
	public PIDCoefficients moveYPID = new PIDCoefficients(.07,0,0);

	public MoveToPosSettings finalPosSettings = new MoveToPosSettings(new double[]{.75, .75, .5}, 20, 10000, 1);
	public MoveToPosSettings defaultPosSettings = new MoveToPosSettings(new double[]{2, 2, 3}, 5, 10000, 1);
	public MoveToPosSettings losePosSettings = new MoveToPosSettings(new double[]{4, 4, 7.5}, 1, 10000, 1);

	@Override
	public void onInit(Robot robot) {}
}
