package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;

public class Drive extends RobotPart {
    //objects and variables
    public Gamepad gamepad;
    //private double[] lastPowers;
    private double[] currentPowers;

    //constructors
    public Drive(Robot robot){
        super(robot,new DriveHardware(), new DriveSettings());
    }

    public Drive(Robot robot, DriveHardware driveHardware, DriveSettings driveSettings){
        super(robot, driveHardware, driveSettings);
    }

    //methods
    @Override
    public void init() {
        super.init();
        initValues();
    }

    void initValues(){
        //lastPowers = new double[3];
        currentPowers = new double[3];
    }

    void stopMovement(){
        for(int i = 0; i < 3; i++){
            //lastPowers[i] = 0;
            currentPowers[i] = 0;
        }
        hardware.stopMotors();
    }

    @Override
    public void runForTeleOp(){
        super.runForTeleOp();
        runForTeleOp(gamepad);
    }

    void runForTeleOp(Gamepad gamepad){
        moveRobot(((DriveSettings) settings).driveXSupplier.getFloat(gamepad),
                ((DriveSettings) settings).driveYSupplier.getFloat(gamepad),
                ((DriveSettings) settings).driveRSupplier.getFloat(gamepad),
                ((DriveSettings) settings).driveStopSupplier.getBoolean(gamepad));
    }

    @Override
    public void addTelemetry() {
        robot.addTelemetry("drive power X", currentPowers[0]);
        robot.addTelemetry("drive power Y", currentPowers[1]);
        robot.addTelemetry("drive power R", currentPowers[2]);
    }

    public void moveRobot(double[] powers, boolean stop){
        moveRobot(powers[0], powers[1], powers[2], stop);
    }

    public void moveRobot(double X, double Y, double R, boolean stop){
        moveRobot(X, Y, R, ((DriveSettings) settings).driveMode,true, ((DriveSettings) settings).useSmoothing ? ((DriveSettings) settings).smoothingValue : 0, stop);
    }

    public void moveRobot(double X, double Y, double R, DriveSettings.DriveMode driveMode, boolean cap, double smoothingValue, boolean stop){
        if(!stop)
            hardware.setMotorPowers(getRobotMovePowers(X, Y, R, driveMode, cap, smoothingValue));
        else
            stopMovement();
    }

    double[] getRobotMovePowers(double X, double Y, double R, DriveSettings.DriveMode driveMode, boolean cap, double smoothingValue) {
        //calculate current X, Y, and R
        double[] targetPower = new double[]{X,Y,R};
        for(int i = 0; i < 3; i++){
            //test equation
            if(smoothingValue == 0)
                currentPowers[i] = targetPower[i];
            else {
                if(currentPowers[i] < targetPower[i]) {
                    currentPowers[i] += smoothingValue;
                    if(currentPowers[i] > targetPower[i])
                        currentPowers[i] = targetPower[i];
                }
                else if(currentPowers[i] > targetPower[i]) {
                    currentPowers[i] -= smoothingValue;
                    if(currentPowers[i] < targetPower[i])
                        currentPowers[i] = targetPower[i];
                }
            }
        }

        //update last powers
        //lastPowers = currentPowers;

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

        return arr;
    }
}