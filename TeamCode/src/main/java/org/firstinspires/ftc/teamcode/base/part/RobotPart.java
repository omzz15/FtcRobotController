package org.firstinspires.ftc.teamcode.base.part;

import org.firstinspires.ftc.teamcode.base.Robot;

public abstract class RobotPart {
    public Robot robot;
    public RobotPartHardware hardware;
    public RobotPartSettings settings;

    private long waitStartTime;
    private int waitTime;
    private short afterWaitRunMode;

    public RobotPart(Robot robot, RobotPartHardware hardware, RobotPartSettings settings){
        this.robot = robot;
        this.hardware = hardware;
        this.settings = settings;
        onConstruct();
        robot.addPart(this);
    }

    public void init(){
        if(hardware != null) hardware.onInit(robot);
        settings.onInit(robot);
        onInit();
        settings.initialized = true;
    }

    public void start(){
        if(settings.canStart()) {
            onStart();
            settings.started = true;
        }
    }

    public void runPart(){
        if(settings.canUse()) {
            if(settings.runMode == -3){
                if(System.currentTimeMillis() - waitStartTime > waitTime)
                    settings.runMode = afterWaitRunMode;
            }else if (settings.runMode == -2) {
                onPause();
                settings.runMode = 0;
            } else if (settings.runMode == -1){
                onUnpause();
                settings.runMode = 1;
            } else if (settings.runMode > 0) {
                onRunLoop(settings.runMode);
            }
        }
    }

    public void addTelemetry(){
        if(settings.canAddTelemetry()) {
            onAddTelemetry();
        }
    }

    public void pause(){
        settings.runMode = -2;
    }

    public void unpause(){
        settings.runMode = -1;
    }

    public void wait(int time, short runModeAfter){
        waitStartTime = System.currentTimeMillis();
        waitTime = time;
        afterWaitRunMode = runModeAfter;
        settings.runMode = -3;
    }

    public void stop(){
        onStop();
    }

    ////////////////////
    //abstract methods//
    ////////////////////
    public abstract void onConstruct();

    public abstract void onInit();

    public abstract void onStart();

    public abstract void onPause();

    public abstract void onUnpause();

    public abstract void onRunLoop(short runMode);

    public abstract void onAddTelemetry();

    public abstract void onStop();
}
