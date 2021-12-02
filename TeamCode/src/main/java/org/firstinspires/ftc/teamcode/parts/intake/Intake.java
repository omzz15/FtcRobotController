package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;

public class Intake extends RobotPart {
    public boolean intaking = false;

    private IntakePosition presetPosition;

    private double intakeServoPos = 0;
    private long intakeServoMoveStartTime;
    private int intakeServoMoveTime;

    public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
        super(robot, hardware, settings);
    }
    public Intake(Robot robot){
        super(robot, new IntakeHardware(), new IntakeSettings());
    }


    ////////
    //base//
    ////////
    public void runIntake(float power){
        if(Math.abs(power) >= ((IntakeSettings) settings).minInputRegisterVal){
            //TODO uncomment once new arm class is done
            //((Arm) robot.getPartByClass(Arm.class)).setToAPresetPosition((short) 1);
            setIntakeToPreset(IntakePosition.DOWN);
            ((IntakeHardware) hardware).intakeMotor.setPower(power);
            intaking = true;
        } else {
            stopIntake();
        }
    }

    public void stopIntake(){
        intaking = false;
        ((IntakeHardware) hardware).intakeMotor.setPower(0);
    }


    /////////
    //servo//
    /////////
    public void setIntakeServoPosition(double position){
        position = Utils.Math.capDouble(position, ((IntakeSettings) settings).servoMinPos, ((IntakeSettings) settings).servoMaxPos);
        intakeServoMoveStartTime = System.currentTimeMillis();
        //TODO track servo move time correctly
        intakeServoMoveTime = (int)(Math.abs(intakeServoPos - position) / ((IntakeSettings) settings).servoSpeed * 360000);
        ((IntakeHardware) hardware).intakeServo.setPosition(position);
        intakeServoPos = position;
    }

    public boolean intakeServoDoneMoving(){
        return System.currentTimeMillis() - intakeServoMoveStartTime > intakeServoMoveTime;
    }


    ///////////
    //presets//
    ///////////
    //intake
    public void setIntakeToPreset(IntakePosition preset) {
        setIntakeServoToPreset(preset);
    }

    //Servo
    void setIntakeServoToPreset(IntakePosition preset){
        if(preset != presetPosition) {
            stopIntake();
            settings.runMode = 2;
            setIntakeServoPosition(((IntakeSettings) settings).intakeServoPresets[preset.value]);
            presetPosition = preset;
        }
    }


    //////////
    //teleOp//
    //////////
    void teleOpCode(){
        IntakePosition preset = (IntakePosition)((IntakeSettings) settings).intakePresetSupplier.get();
        if(preset != null){
            setIntakeToPreset(preset);
            return;
        }
        runIntake(((IntakeSettings) settings).intakePowerSupplier.getFloat());
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
        if(runMode == 1){
            teleOpCode();
        }else if(runMode == 2){
            if(intakeServoDoneMoving())
                settings.runMode = 1;
        }
    }

    // TODO: 11/8/2021 add telemetry
    @Override
    public void onAddTelemetry() {

    }

    @Override
    public void onStop() {

    }


    /////////
    //other//
    /////////
    public enum IntakePosition{
        UP((short) 0),
        DOWN((short) 1);

        short value;

        IntakePosition(short value){
            this.value = value;
        }
    }
}
