package org.firstinspires.ftc.teamcode.basethreaded;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public class RobotThreadedPartSettings extends RobotPartSettings {
	public boolean makeThread = false;
	public boolean useThread = false;

	public boolean useThread(){
		return makeThread && useThread;
	}
}
