package org.firstinspires.ftc.teamcode.parts.drive;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.supplier.ControlSupplier;
import org.firstinspires.ftc.teamcode.other.supplier.DoubleControlSupplier;
import org.firstinspires.ftc.teamcode.other.supplier.DoubleControlSupplierDouble;

public class DriveSettings extends RobotPartSettings {
    ////////////
    //settings//
    ////////////
    //input
    Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
        //drive
        DoubleControlSupplierDouble driveXSupplier = new DoubleControlSupplierDouble(gamepad -> ((double)gamepad.left_stick_x));
    DoubleControlSupplierDouble driveYSupplier = new DoubleControlSupplierDouble(gamepad -> ((double)-gamepad.left_stick_y));
    DoubleControlSupplierDouble driveRSupplier = new DoubleControlSupplierDouble(gamepad -> ((double)gamepad.right_stick_x * 0.8f));
    DoubleControlSupplier driveStopSupplier = new DoubleControlSupplier(gamepad -> (gamepad.x));
        // InputSupplier driveSpeedSupplier = new InputSupplier(gamepad -> ((gamepad.b) ? 0.5 : 1), gamepadNum);
        ControlSupplier<Integer> driveSpeedSupplier = new ControlSupplier<>(gamepad -> ((gamepad.dpad_left) ? 1 : (gamepad.dpad_right) ? 2 : 0), gamepadNum);

    //other
    public DriveMode driveMode = DriveMode.MECANUM;
    boolean useSmoothing = false;
    double[] smoothingValues = new double[]{.1,.1,.1};
    public double slowModeSpeed = 0.6;
    public double superSlowModeSpeed = 0.4;
    public double superSlowModeActivateDis = 6;


    @Override
    public void onInit(Robot robot) {
        driveXSupplier.init(robot);
        driveYSupplier.init(robot);
        driveRSupplier.init(robot);
        driveStopSupplier.init(robot);
        driveSpeedSupplier.init(robot);
    }

    public enum DriveMode{
        TANK,
        MECANUM,
        OMNI
    }
}
