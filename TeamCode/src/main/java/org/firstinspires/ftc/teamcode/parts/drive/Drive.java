package org.firstinspires.ftc.teamcode.parts.drive;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;

public class Drive extends RobotPart<DriveHardware,DriveSettings> {
    private double[] currentPowers;
    public double backDistance = 10;
    private double speedMultiplier = 1;
    private boolean slowMode = false;

    public Drive(Robot robot, DriveHardware hardware, DriveSettings settings) {
        super(robot, hardware, settings);
    }
    public Drive(Robot robot){
        super(robot, new DriveHardware(), new DriveSettings());
    }


    /////////////////
    //Drive Methods//
    /////////////////
    double[] getRobotMovePowers(double[] power, DriveSettings.DriveMode driveMode, double speedMultiplier, boolean cap, double[] smoothingValues) {
        //calculate current X, Y, and R
        if(smoothingValues != null) {
            for (int i = 0; i < 3; i++) {
                if (currentPowers[i] < power[i]) {
                    currentPowers[i] += smoothingValues[i];
                    if (currentPowers[i] > power[i])
                        currentPowers[i] = power[i];
                } else if (currentPowers[i] > power[i]) {
                    currentPowers[i] -= smoothingValues[i];
                    if (currentPowers[i] < power[i])
                        currentPowers[i] = power[i];
                }
            }
        }else{
            currentPowers = power;
        }

        //get motor powers
        double[] arr = new double[4];
        if (driveMode == DriveSettings.DriveMode.TANK) {
            arr[0] = currentPowers[0] + currentPowers[2];
            arr[1] = currentPowers[0] - currentPowers[2];
            arr[2] = currentPowers[0] + currentPowers[2];
            arr[3] = currentPowers[0] - currentPowers[2];
        } else if (driveMode == DriveSettings.DriveMode.MECANUM) {
            arr[0] = currentPowers[0] + currentPowers[1] + currentPowers[2];
            arr[1] = -currentPowers[0] + currentPowers[1] - currentPowers[2];
            arr[2] = -currentPowers[0] + currentPowers[1] + currentPowers[2];
            arr[3] = currentPowers[0] + currentPowers[1] - currentPowers[2];
        } else if (driveMode == DriveSettings.DriveMode.OMNI) {
            //experimental
            arr[0] = currentPowers[1] + currentPowers[2];
            arr[1] = currentPowers[0] + currentPowers[2];
            arr[2] = currentPowers[1] + currentPowers[2];
            arr[3] = currentPowers[0] + currentPowers[2];
        }

        //cap powers
        if(cap){
            //get highest power
            double high = 0;
            for(double val:arr){
                if(Math.abs(val) > high) high = Math.abs(val);
            }
            //range to -1 to 1
            if(high > 1){
                for(int i = 0; i < 4; i++){
                    arr[i] /= high;
                }
            }
        }
        //apply speed multiplier
        for(int i = 0; i < 4; i++){
            arr[i] *= speedMultiplier;
        }
        return arr;
    }
    double[] getRobotMovePowers(double X, double Y, double R, DriveSettings.DriveMode driveMode, double speedMultiplier, boolean cap, double[] smoothingValues){
        return getRobotMovePowers(new double[]{X,Y,R}, driveMode, speedMultiplier, cap, smoothingValues);
    }

    public void moveRobot(double[] power, DriveSettings.DriveMode driveMode, double speedMultiplier, boolean cap, double[] smoothingValue, boolean stop){
        if(!stop)
            hardware.setMotorPowers("drive motors", getRobotMovePowers(power, driveMode, speedMultiplier, cap,smoothingValue));
        else
            stopMovement();
    }
    public void moveRobot(double X, double Y, double R, DriveSettings.DriveMode driveMode, double speedMultiplier, boolean cap, double[] smoothingValue, boolean stop){
        moveRobot(new double[]{X,Y,R}, driveMode, speedMultiplier, cap, smoothingValue, stop);
    }


    public void moveRobot(double[] powers, boolean useSpeedMultiplier, boolean stop){
        moveRobot(powers, settings.driveMode, useSpeedMultiplier ? speedMultiplier : 1,true, settings.useSmoothing ? settings.smoothingValues : null, stop);
    }
    public void moveRobot(double X, double Y, double R, boolean useSpeedMultiplier, boolean stop){
        moveRobot(new double[]{X,Y,R}, useSpeedMultiplier, stop);
    }

    public void stopMovement(){
        for(int i = 0; i < 3; i++){
            currentPowers[i] = 0;
        }
        hardware.stopMotors("drive motors");
    }


    /////////////////////
    //RobotPart Methods//
    /////////////////////
    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {
        currentPowers = new double[3];
        robot.taskManager.getMain().addBackgroundTask(getSlowModeTask(), false);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {
        stopMovement();
    }

    @Override
    public void onUnpause() {

    }

    @Override
    public void onRunLoop(short runMode) {
        if (hardware.backDistanceSensor != null && (Intake) robot.getPartByClass(Intake.class) != null && !((Intake) robot.getPartByClass(Intake.class)).isAutonomous) {
            backDistance = hardware.backDistanceSensor.getDistance(DistanceUnit.INCH);
        }
        if(runMode == 1){
            //set speed mult
            //settings.speedMultiplier = settings.driveSpeedSupplier.getDouble();
            if (settings.driveSpeedSupplier.get() == 1){
                robot.taskManager.getMain().getBackgroundTask("slow mode task").start();
            }
            if (settings.driveSpeedSupplier.get() == 2){
                slowMode = false;
            }
            //teleOp
            moveRobot(settings.driveXSupplier.get(),
                    settings.driveYSupplier.get(),
                    settings.driveRSupplier.get(),
                    true,
                    settings.driveStopSupplier.get());
        }
    }

    private Task getSlowModeTask(){
        Task t = new Task("slow mode task");

        t.addStep(() -> {speedMultiplier = settings.slowModeSpeed; slowMode = true;});
        t.addStep(
                () -> {
                    if(backDistance < (settings).superSlowModeActivateDis){
                        speedMultiplier = settings.superSlowModeSpeed;
                    }else{
                        speedMultiplier = settings.slowModeSpeed;
                    }
                },
                () -> (!slowMode)
        );
        t.addStep(() -> {speedMultiplier = 1;});

        return t;
    }

    @Override
    public void onAddTelemetry() {
        //robot.addTelemetry("drive power X", currentPowers[0]);
        //robot.addTelemetry("drive power Y", currentPowers[1]);
        //robot.addTelemetry("drive power R", currentPowers[2]);
        //robot.addTelemetry("backDistanceSensor", backDistance);
    }

    @Override
    public void onStop() {

    }
}
