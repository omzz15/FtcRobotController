package org.firstinspires.ftc.teamcode.parts.teamcapper;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;

public class TeamCapper extends RobotPart<TeamCapperHardware, TeamCapperSettings> {

    public TeamCapper(Robot robot, TeamCapperHardware hardware, TeamCapperSettings settings) {
        super(robot, hardware, settings);
    }

    public TeamCapper(Robot robot){
        super(robot, new TeamCapperHardware(), new TeamCapperSettings());
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
        if(runMode == 1){
            hardware.capperServo.setPosition(hardware.capperServo.getPosition() + settings.capperPresetSupplier.get());
        }
    }

    @Override
    public void onAddTelemetry() {
        robot.addTelemetry("capper pos", hardware.capperServo.getPosition());
    }

    @Override
    public void onStop() {

    }
}
