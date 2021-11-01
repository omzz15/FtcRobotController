package org.firstinspires.ftc.teamcode.base.basethreaded;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public abstract class RobotThreadedPartSettings extends RobotPartSettings {
	public boolean makeThread = true;
	public short threadRunMode = 1;

	public boolean canRunThread(){return threadRunMode > 0;}
}
