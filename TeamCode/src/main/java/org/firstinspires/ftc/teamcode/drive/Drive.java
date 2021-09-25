package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;

public class Drive extends RobotPart {
    public Gamepad gamepad;

    public Drive(Robot robot){
        super(robot,new DriveHardware(), new DriveSettings());
    }

    public Drive(Robot robot, DriveHardware driveHardware, DriveSettings driveSettings){
        super(robot, driveHardware, driveSettings);
    }

    @Override
    public void init() {
        super.init();
    }


    @Override
    public void runForTeleOp(){
        super.runForTeleOp();
        runForTeleOp(gamepad);
    }

    void runForTeleOp(Gamepad gamepad){
        moveRobot(((DriveSettings) settings).driveXSupplier.get(gamepad),
                ((DriveSettings) settings).driveYSupplier.get(gamepad),
                ((DriveSettings) settings).driveRSupplier.get(gamepad));
    }

    @Override
    public void addTelemetry() {
    }

    public void moveRobot(double X, double Y, double R){
        moveRobot(X, Y, R, ((DriveSettings) settings).driveMode, false);
    }

    public void moveRobot(double X, double Y, double R, DriveSettings.DriveMode driveMode, boolean cap){
        RobotPartHardware.setMotorPowers(((DriveHardware) hardware).motors, getRobotMovePowers(X, Y, R, driveMode, cap));
    }

    double[] getRobotMovePowers(double X, double Y, double R, DriveSettings.DriveMode driveMode, boolean cap) {
        //get motor powers
        double[] arr = new double[4];
        if (driveMode == DriveSettings.DriveMode.TANK) {
            arr[0] = X + R;
            arr[1] = X - R;
            arr[2] = X + R;
            arr[3] = X - R;
        } else if (driveMode == DriveSettings.DriveMode.MECANUM) {
            arr[0] = X + Y + R;
            arr[1] = -X + Y - R;
            arr[2] = -X + Y + R;
            arr[3] = X + Y - R;
        } else if (driveMode == DriveSettings.DriveMode.OMNI) {
            //expiremental
            arr[0] = Y + R;
            arr[1] = X + R;
            arr[2] = Y - R;
            arr[3] = X - R;
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