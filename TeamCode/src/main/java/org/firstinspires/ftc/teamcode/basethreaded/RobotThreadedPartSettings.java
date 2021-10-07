package org.firstinspires.ftc.teamcode.basethreaded;

import org.firstinspires.ftc.teamcode.base.RobotPartSettings;

public class RobotThreadedPartSettings extends RobotPartSettings {
	public boolean makeThread = true;
	public boolean useThread = true;

	public boolean useThread(){
		return makeThread && useThread;
	}
}
