package org.firstinspires.ftc.teamcode;

public abstract class RobotPart{
    Robot robot;
    RobotPartSettings partSettings;

    RobotPart(Robot robot, RobotPartSettings partSettings){
        this.robot = robot;
        this.partSettings = partSettings;
        robot.parts.add(this);
    }

    abstract void initHardware();

    void runForTeleOp(){}
    
    abstract void addTelemetry();

}
