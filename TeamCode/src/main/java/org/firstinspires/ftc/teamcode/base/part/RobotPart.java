package org.firstinspires.ftc.teamcode.base.part;

import org.firstinspires.ftc.teamcode.base.Robot;

public abstract class RobotPart {
    public Robot robot;
    public RobotPartHardware hardware;
    public RobotPartSettings settings;

    private long waitStartTime;
    private int waitTime;
    private short afterRunMode;

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
            if(settings.runMode == -1){
                if(System.currentTimeMillis() - waitStartTime > waitTime)
                    settings.runMode = afterRunMode;
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

    public void pause(boolean keepRunMode){
        afterRunMode = keepRunMode ? settings.runMode : (short) 1;
        settings.runMode = 0;
        onPause();
    }

    public void unpause(){
        settings.runMode = afterRunMode;
        onUnpause();
    }

    public void wait(int time, short runModeAfter){
        waitStartTime = System.currentTimeMillis();
        waitTime = time;
        afterRunMode = runModeAfter;
        settings.runMode = -1;
    }

    public void wait(int time){
        wait(time, settings.runMode);
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
