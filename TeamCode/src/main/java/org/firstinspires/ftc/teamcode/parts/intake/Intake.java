package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPart;

public class Intake extends RobotPart {
    public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
        super(robot, hardware, settings);
    }
    public Intake(Robot robot){
        super(robot, new IntakeHardware(), new IntakeSettings());
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
