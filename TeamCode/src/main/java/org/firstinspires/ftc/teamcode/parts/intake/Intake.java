package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;
import org.firstinspires.ftc.teamcode.parts.arm.ArmHardware;
import org.firstinspires.ftc.teamcode.parts.arm.ArmSettings;

public class Intake extends RobotPart {
    public boolean intaking = false;

    private short presetPosition;

    private double intakeServoPos = 0;
    private long intakeServoMoveStartTime;
    private int intakeServoMoveTime;

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
        } else {
            intaking = false;
            ((IntakeHardware) hardware).intakeMotor.setPower(0);
        }
    }

    public void setIntakeServoToPreset(short preset){
        if(preset != presetPosition && preset > 0 && preset <= 2) {
            setIntakeServoPosition(((IntakeSettings) settings).intakeServoPresets[preset - 1]);
            presetPosition = preset;
        }
    }

    void setIntakeServoPosition(double position){
        position = Utils.Math.capDouble(position, ((IntakeSettings) settings).servoMinPos, ((IntakeSettings) settings).servoMaxPos);
        intakeServoMoveStartTime = System.currentTimeMillis();
        intakeServoMoveTime = (int)(Math.abs(intakeServoPos - position) / ((IntakeSettings) settings).servoSpeed * 1000);
        ((IntakeHardware) hardware).intakeServo.setPosition(position);
        intakeServoPos = position;
    }

    public boolean intakeServoDoneMoving(){
        return System.currentTimeMillis() - intakeServoMoveStartTime > intakeServoMoveTime;
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {
        setIntakeServoToPreset((short)1);
    }

    @Override
    public void onStart() {
        setIntakeServoToPreset((short)2);
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
        if(runMode == 1){
            teleOpCode();
        }
    }

    // TODO: 11/8/2021 add telemetry
    @Override
    public void onAddTelemetry() {

    }

    @Override
    public void onStop() {

    }
}
