package org.firstinspires.ftc.teamcode.base.part;

import org.firstinspires.ftc.teamcode.base.Robot;

public abstract class RobotPartSettings {
    boolean initialized = false;
    boolean started = false;
    public short runMode = 1; //-3 is for waiting, -2 is pausing, -1 is un-pausing, 0 is stopped, > 0 is for runMode
    public boolean sendTelemetry = true;

    public abstract void onInit(Robot robot);

    public boolean canStart(){return initialized;}

    public boolean canUse(){return initialized && started;}

    public boolean canRun(){return canUse() && runMode > 0;}

    public boolean canAddTelemetry(){return canRun() && sendTelemetry;}
}
