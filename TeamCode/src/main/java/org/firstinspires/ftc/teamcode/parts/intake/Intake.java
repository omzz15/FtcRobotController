package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPart;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;

public class Intake extends RobotPart {
    public boolean intaking = false;

    public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
        super(robot, hardware, settings);
    }
    public Intake(Robot robot){
        super(robot, new IntakeHardware(), new IntakeSettings());
    }


    //////////////////
    //Intake Methods//
    //////////////////
    void teleOpCode(){
        float intakePower = ((IntakeSettings) settings).intakePowerSupplier.getFloat();
        if(Math.abs(intakePower) >= ((IntakeSettings) settings).minInputRegisterVal){
            ((Arm) robot.getPartByClass(Arm.class)).setToAPresetPosition((short) 1);
            ((IntakeHardware) hardware).intakeMotor.setPower(intakePower);
            intaking = true;
        } else
            intaking = false;
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
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
        ((IntakeHardware) hardware).intakeMotor.setPower(0);
        intaking = false;
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
