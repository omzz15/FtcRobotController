package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Drive extends RobotPart{

    Drive(Robot robot){
        super(robot, new DriveSettings());
    }

    Drive(Robot robot, DriveSettings driveSettings){
        super(robot, driveSettings);
    }

    @Override
    void construct() {

    }

    @Override
    void init() {
        super.init();
    }

    @Override
    void initHardware(){

    }

    @Override
    void runForTeleOp(){
        super.runForTeleOp();
    }

    @Override
    void addTelemetry() {

    }

    void moveRobot(double X, double Y, double R){

    }
}