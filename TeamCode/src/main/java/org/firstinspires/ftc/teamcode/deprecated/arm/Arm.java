package org.firstinspires.ftc.teamcode.deprecated.arm;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.task.TaskManager;
import org.firstinspires.ftc.teamcode.other.task.TaskRunner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
@Deprecated
public class Arm extends RobotPart {
    //task
    TaskRunner armTasks = new TaskRunner();

    //arm
    private int armPosition = 0;
    //bucket
    private double bucketPosition = 0;
    private long bucketMoveStartTime;
    private int bucketMoveTime;
    //preset
    private short presetPosition;
    private short lastPresetPosition;

    short tempRunMode = 0;
    short presetRunMode = 0;

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
        //@TODO track servo move time correctly
        bucketMoveTime = (int)(Math.abs(bucketPosition - position) / ((ArmSettings) settings).servoSpeed * 360000);
        ((ArmHardware) hardware).bucketServo.setPosition(position);
        bucketPosition = position;
    }

    void setBucketToPreset(short preset){
        setBucketPosition(((ArmSettings) settings).bucketPresets[preset - 1]);
    }

    private void moveBucket(int val){
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

    private void moveArm(float val){
        if(Math.abs(val) > ((ArmSettings) settings).minInputRegisterVal) {
            presetPosition = 0;
            setArmPosition(armPosition + (int) (val * ((ArmSettings) settings).armMovementSpeed));
        }
    }

    boolean armDoneMoving(){
        return Math.abs(((ArmHardware) hardware).armMotor.getCurrentPosition() - armPosition) < ((ArmSettings) settings).armTolerance;
    }

    //preset
    void setPresetPosition(short position){
        lastPresetPosition = presetPosition;
        presetPosition = position;
        presetRunMode = 0;
    }

    public int getPresetPosition(){
        return presetPosition;
    }

    public void setToAPresetPosition(short position){
        if(position != presetPosition && position > 0 && position <= 4) {
            setPresetPosition(position);
            settings.runMode = (short)(position + 1);
            if(position == 1)
                startDockArmTask();
        }
    }

    private void addDockArmTask(){
        Task task = new Task();
        Task.Step step;
        Task.EndPoint end;

        //step 1 - stop intake and arm and move bucket and arm
        step = () -> {
            robot.getPartByClass(Intake.class).pause(true);
            pause(false);
            setBucketToPreset((short) 4);//set bucket to cradle
            setArmToPreset((short) 4);//set arm to cradle
        };
        task.addStep(step);

        //step 2 - wait for bucket
        end = () -> (bucketDoneMoving());
        task.addStep(end);

        //step 3 - drop arm
        step = () -> {
            setArmToPreset((short) 1);//set arm to flat
        };
        task.addStep(step);

        //step 4 - wait for arm to finish
        end = () -> (armDoneMoving());
        task.addStep(end);

        //step 5 - drop bucket
        step = () -> {
            setBucketToPreset((short) 1);//set bucket to flat
        };
        task.addStep(step);

        //step 6 - wait for bucket to finish
        end = () -> (bucketDoneMoving());
        task.addStep(end);

        //step 7 - reset state
        step = () -> {
            robot.getPartByClass(Intake.class).unpause();
            unpause();
        };
        task.addStep(step);

        armTasks.addTask("Dock Arm", task, true);
    }

    private void startDockArmTask(){
        robot.taskManager.getMain().getBackgroundTask("Dock Arm").restart();
    }

    boolean dockArm(){
        return robot.taskManager.getMain().getBackgroundTask("Dock Arm").isDone();
    }

    boolean undockArm(){
        if(lastPresetPosition <= 1) {
            if (tempRunMode == -1) {
                //end condition
                tempRunMode = 0;
                robot.getPartByClass(Intake.class).unpause();
                return true;
            }
            if (tempRunMode == 0) {
                //stop intake and move bucket and intake
                robot.getPartByClass(Intake.class).pause(false);
                setBucketToPreset((short) 4);//set bucket to cradle
                tempRunMode = 1;
            } else if (tempRunMode == 1) {
                //wait for bucket and intake to finish and raise arm
                if (bucketDoneMoving()) {
                    setArmToPreset((short) 4);//set arm to cradle
                    tempRunMode = -1;
                }
            }
            return false;
        }
        return true;
    }

    /////////////
    //run modes//
    /////////////
    void teleOpCode(){
        short preset = (short) ((ArmSettings) settings).presetSupplier.getInt();
        if (preset > 0)
            setToAPresetPosition(preset);
        else {
            moveArm(((ArmSettings) settings).armMovementSupplier.getFloat());
            moveBucket(((ArmSettings) settings).bucketMovementSupplier.getInt());
        }
    }

    void setToFlat(){
        if(dockArm()) {
            presetRunMode = -1;
            settings.runMode = 1;
        }
    }

    void setToDump(){
        if(presetRunMode == 0) {
            if (lastPresetPosition == 4 || lastPresetPosition == 0) {
                ((Intake) robot.getPartByClass(Intake.class)).setIntakeToPreset(Intake.IntakePosition.DOWN);
                presetRunMode = 1;
            } else
                presetRunMode = 2;
        }else if(presetRunMode == 1){
            if(((Intake) robot.getPartByClass(Intake.class)).intakeServoDoneMoving())
                presetRunMode = 2;
        } else if(presetRunMode == 2) {
            //undocks the arm if necessary and sets arm to dump
            if(undockArm()) {
                setArmToPreset((short) 2);//set arm to dump
                presetRunMode = 3;
            }
        } else if(presetRunMode == 3){
            //wait for arm to get to dump position, set bucket to dump, and finish
            if(armDoneMoving()) {
                setBucketToPreset((short) 2);//set bucket to dump
                presetRunMode = -1;
                settings.runMode = 1;
            }
        }
    }

    void setToFDump(){
        if(presetRunMode == 0) {
            if (lastPresetPosition == 4 || lastPresetPosition == 0) {
                ((Intake) robot.getPartByClass(Intake.class)).setIntakeToPreset(Intake.IntakePosition.DOWN);
                presetRunMode = 1;
            } else
                presetRunMode = 2;
        }else if(presetRunMode == 1){
            if(((Intake) robot.getPartByClass(Intake.class)).intakeServoDoneMoving())
                presetRunMode = 2;
        } else if(presetRunMode == 2) {
            //undocks the arm if necessary and sets arm to fdump
            if (undockArm()) {
                setArmToPreset((short) 3);//set arm to fdump
                presetRunMode = 3;
            }
        } else if(presetRunMode == 3){
            //wait for arm to get to fdump position, set bucket to fdump, and finish
            if(armDoneMoving()) {
                setBucketToPreset((short) 3);//set bucket to fdump
                presetRunMode = -1;
                settings.runMode = 1;
            }
        }
    }

    void setToCradle(){
        if(presetRunMode == 0) {
            if (lastPresetPosition == 4 || lastPresetPosition == 0) {
                //((Intake) robot.getPartByClass(Intake.class)).setIntakeServoToPreset((short) 2);//set to fdump
                presetRunMode = 1;
            } else
                presetRunMode = 2;
        }else if(presetRunMode == 1){
            if(((Intake) robot.getPartByClass(Intake.class)).intakeServoDoneMoving())
                presetRunMode = 2;
        } else if(presetRunMode == 2) {
            //undocks the arm if necessary and sets arm to cradle
            if(undockArm()) {
                if(lastPresetPosition > 1){
                    setBucketToPreset((short) 4); //set bucket to cradle
                }
                setArmToPreset((short) 4);//set arm to cradle
                presetRunMode = 3;
            }
        } else if(presetRunMode == 3) {
            //wait for arm to get to cradle position, set bucket to cradle, and finish
            if (armDoneMoving()) {
                setBucketToPreset((short) 4);//set bucket to cradle
                presetRunMode = 4;
            }
        } else if(presetRunMode == 4){
            if(bucketDoneMoving()) {
                ((Intake) robot.getPartByClass(Intake.class)).setIntakeToPreset(Intake.IntakePosition.UP);
                presetRunMode = -1;
                settings.runMode = 1;
            }
        }
    }

    private void attachTaskRunner(String name, TaskManager manager){
        manager.attachTaskRunner(name, armTasks);
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
    @Override
    public void onConstruct() {
    }

    @Override
    public void onInit() {
        addDockArmTask();
        attachTaskRunner("arm", robot.taskManager);
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
            teleOpCode();
        } else if(runMode == 2){
            setToFlat();
        }else if(runMode == 3){
            setToDump();
        }else if(runMode == 4){
            setToFDump();
        }else if(runMode == 5){
            setToCradle();
        }
    }

    // TODO: 11/8/2021 add telemetry for arm
    @Override
    public void onAddTelemetry() {
        robot.addTelemetry("Cheese Range Inch", ((ArmHardware) hardware).bucketRange.getDistance(DistanceUnit.INCH));
    }

    @Override
    public void onStop(){

    }
}
