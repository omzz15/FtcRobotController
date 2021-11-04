package org.firstinspires.ftc.teamcode.base.part.threaded;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPartSettings;

public abstract class ThreadedRobotSettings extends RobotPartSettings{
    public boolean makeThread = true;
    public boolean threadMade = false;
    public boolean threadOn = false;
    public short threadRunMode = 1; //-2 is pausing, -1 is un-pausing, 0 is stopped, and > 0 is running

    public boolean isThreadRunning(){return threadMade && threadOn;}
}
