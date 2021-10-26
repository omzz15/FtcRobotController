package org.firstinspires.ftc.teamcode.base.basethreaded;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public abstract class RobotThreadedPartSettings extends RobotPartSettings {
	public boolean makeThread = true;
	public boolean useThread = true;

	public boolean useThread(){
		return makeThread && useThread;
	}
}
