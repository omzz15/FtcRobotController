package org.firstinspires.ftc.teamcode.base.part;

import org.firstinspires.ftc.teamcode.base.Robot;

public class RobotPartSettings {
    public boolean makeTaskRunner = true;
    public boolean makeTeleOpCodeTask = true;
    public boolean makeTelemetryTask = true;

    boolean initialized = false;
    boolean started = false;
    boolean running = false;


    public void onInit(Robot robot){}

    public boolean canStart(){return initialized;}

    public boolean canUse(){return initialized && started;}

    public boolean isRunning(){return canUse() && running;}
}
