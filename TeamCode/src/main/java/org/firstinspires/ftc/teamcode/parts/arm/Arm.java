package org.firstinspires.ftc.teamcode.parts.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;

public class Arm extends RobotPart {
    //arm
    int armPosition = 0;
    //bucket
    double bucketPosition = 0;
    long bucketMoveStartTime;
    int bucketMoveTime;
    //preset
    short presetPosition;
    short lastPresetPosition;
    short presetRunMode;//-1 is done, 0 is starting, and > 0 is moving to preset

    public Arm(Robot robot, ArmHardware hardware, ArmSettings settings) {
        super(robot, hardware, settings);
    }

    public Arm(Robot robot){
        super(robot, new ArmHardware(), new ArmSettings());
    }


    ///////////////
    //Arm Methods//
    ///////////////
    //bucket
    void setBucketPosition(double position){
        position = Utils.Math.capDouble(position, ((ArmSettings) settings).servoMinPos, ((ArmSettings) settings).servoMaxPos);
        bucketMoveStartTime = System.currentTimeMillis();
        bucketMoveTime = (int)(Math.abs(bucketPosition - position) / ((ArmSettings) settings).servoSpeed * 1000);
        ((ArmHardware) hardware).bucketServo.setPosition(position);
        bucketPosition = position;
    }

    void setBucketToPreset(short preset){
        setBucketPosition(((ArmSettings) settings).bucketPresets[preset - 1]);
    }

    void moveBucket(int val){
        if(Math.abs(val) > 0){
            presetPosition = 0;
            setBucketPosition(bucketPosition + (val * ((ArmSettings) settings).bucketMovementSpeed));
        }
    }

    boolean bucketDoneMoving(){
        return System.currentTimeMillis() - bucketMoveStartTime > bucketMoveTime;
    }

    //arm
    void setArmPosition(int position){
        armPosition = Utils.Math.capInt(position, ((ArmSettings) settings).armMinPos, ((ArmSettings) settings).armMaxPos);
        ((ArmHardware) hardware).armMotor.setTargetPosition(armPosition);
    }

    void setArmToPreset(short preset){
        setArmPosition(((ArmSettings) settings).armPresets[preset - 1]);
    }

    void moveArm(float val){
        if(val > ((ArmSettings) settings).minInputRegisterVal) {
            presetPosition = 0;
            setArmPosition(armPosition + (int) (val * ((ArmSettings) settings).armMovementSpeed));
        }
    }

    //preset
    void setPresetPosition(short position){
        lastPresetPosition = presetPosition;
        presetPosition = position;
        presetRunMode = 0;
    }

    int getPresetPosition(){
        if(presetRunMode == -1)
            return presetPosition;
        else
            return -1;
    }

    void setToAPresetPosition(short position){
        if(position != presetPosition && position > 0 && position <= 4) {
            setPresetPosition(position);
            settings.runMode = (short)(position + 1);
        }
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {
        setBucketPosition(((ArmSettings) settings).bucketStartPos);
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
        if(runMode == 1) {
            //teleOp code
            short preset = (short) ((ArmSettings) settings).presetSupplier.getInt();
            if (preset > 0)
                setToAPresetPosition(preset);
            else {
                moveArm(((ArmSettings) settings).armMovementSupplier.getFloat());
                moveBucket(((ArmSettings) settings).bucketMovementSupplier.getInt());
            }
        } else if(runMode == 2){
            //move to flat
            if(presetRunMode == 0){
                //stop intake and move bucket and arm
                robot.getPartByClass(Intake.class).pause();
                setBucketToPreset((short) 4);//set bucket to cradle
                setArmToPreset((short) 4);//set arm to cradle
                presetRunMode = 1;
            }else if(presetRunMode == 1){
                //wait for bucket to finish and drop arm and bucket
                if(bucketDoneMoving()){
                    setArmToPreset((short) 1);//set arm to flat
                    setBucketToPreset((short) 1);//set bucket to flat
                    presetRunMode = 2;
                }
            }else if(presetRunMode == 2){
                //wait for bucket to finish and reset state
                if(bucketDoneMoving()){
                    robot.getPartByClass(Intake.class).unpause();
                    presetRunMode = -1;
                    settings.runMode = 1;
                }
            }
        }else if(runMode == 3){
            //move to dump
        }else if(runMode == 4){
            //move to fdump
        }else if(runMode == 5){
            //move to cradle
        }
    }

    @Override
    public void onAddTelemetry() {

    }
}
