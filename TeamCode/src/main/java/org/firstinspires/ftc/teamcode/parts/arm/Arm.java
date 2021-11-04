package org.firstinspires.ftc.teamcode.parts.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPartSettings;

public class Arm extends RobotPart {
    public Arm(Robot robot, ArmHardware hardware, ArmSettings settings) {
        super(robot, hardware, settings);
    }

    public Arm(Robot robot){
        super(robot, new ArmHardware(), new ArmSettings());
    }

    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onUnpause() {

    }

    @Override
    public void onRunLoop(short runMode) {

    }

    @Override
    public void onAddTelemetry() {

    }
}
