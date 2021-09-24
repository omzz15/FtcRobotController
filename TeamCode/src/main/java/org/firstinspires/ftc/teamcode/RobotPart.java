package org.firstinspires.ftc.teamcode;

public abstract class RobotPart{
    Robot robot;
    RobotPartSettings settings;

    RobotPart(Robot robot, RobotPartSettings settings){
        this.robot = robot;
        this.settings = settings;
        robot.parts.add(this);
        construct();
    }

    abstract void construct();

    void init(){
        initHardware();
        settings.initialized = true;
    }

    void initHardware(){}

    void runForTeleOp(){
        if(settings.sendTelemetry){
            addTelemetry();
        }
    }

    void addTelemetry(){}

    void stop(){

    }
}
