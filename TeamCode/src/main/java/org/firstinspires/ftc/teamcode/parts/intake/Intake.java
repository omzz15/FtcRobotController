package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.task.Task;

public class Intake extends RobotPart {
    public boolean intaking = false;

    private IntakePosition presetPosition;

    private double intakeServoPos = 0;
    private long intakeServoMoveStartTime;
    private int intakeServoMoveTime;

    private double intakePower = 0;

    public Intake(Robot robot, IntakeHardware hardware, IntakeSettings settings) {
        super("Intake", robot, hardware, settings);
    }
    public Intake(Robot robot){
        super("Intake", robot, new IntakeHardware(), new IntakeSettings());
    }


    ////////
    //base//
    ////////
    public void runIntake(float power){
        if(Math.abs(power) >= ((IntakeSettings) settings).minInputRegisterVal){
            //TODO change arm to new version
            Arm a = (Arm)robot.getPartByClass(Arm.class);
            if(a != null)
                a.setToAPresetPosition((short) 1);
            setIntakeToPreset(IntakePosition.DOWN);
            ((IntakeHardware) hardware).intakeMotor.setPower(power);
            intakePower = power;
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

    public void setIntakeServoPosToManual(double position){
        ((IntakeHardware) hardware).intakeServo.setPosition(position);
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
            getTaskRunner().getBackgroundTask("Stop TeleOp Until Servo Done").start();
            setIntakeServoPosition(((IntakeSettings) settings).intakeServoPresets[preset.value]);
            presetPosition = preset;
        }
    }


    //////////
    //teleOp//
    //////////
    @Override
    public void teleOpCode(){
        IntakePosition preset = (IntakePosition)((IntakeSettings) settings).intakePresetSupplier.get();
        if(preset != null){
            setIntakeToPreset(preset);
            return;
        }
        runIntake(((IntakeSettings) settings).intakePowerSupplier.getFloat());
    }

    @Override
    public void onTeleOpPause() {
        stopIntake();
    }

    /////////
    //tasks//
    /////////
    void makeAllRandomTasks(){
        Task t = new Task("Stop TeleOp Until Servo Done");
        t.addStep(() -> {pauseTeleOp();});
        t.addStep(() -> (intakeServoDoneMoving()));
        t.addStep(() -> {unpauseTeleOp();});
        getTaskRunner().addTask(t, true, false);
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
    @Override
    public void onInit(){
        makeAllRandomTasks();
    }

    @Override
    public void onPause() {
        ((IntakeHardware) hardware).intakeMotor.setPower(0);
        intaking = false;
    }

    @Override
    public void telemetry() {
        robot.addTelemetry("intake power", intakePower);
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
