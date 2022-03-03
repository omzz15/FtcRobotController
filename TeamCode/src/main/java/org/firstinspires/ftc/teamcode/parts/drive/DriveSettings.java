package org.firstinspires.ftc.teamcode.parts.drive;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.supplier.ControlSupplier;

public class DriveSettings extends RobotPartSettings {
    ////////////
    //settings//
    ////////////
    //input
    Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
        //drive
        ControlSupplier<Float> driveXSupplier = new ControlSupplier<>(gamepad -> (gamepad.left_stick_x), gamepadNum);
        ControlSupplier<Float> driveYSupplier = new ControlSupplier<>(gamepad -> (-gamepad.left_stick_y), gamepadNum);
        ControlSupplier<Float> driveRSupplier = new ControlSupplier<>(gamepad -> (gamepad.right_stick_x * 0.8f), gamepadNum);
        ControlSupplier<Boolean> driveStopSupplier = new ControlSupplier<>(gamepad -> (gamepad.x), gamepadNum);
        // InputSupplier driveSpeedSupplier = new InputSupplier(gamepad -> ((gamepad.b) ? 0.5 : 1), gamepadNum);
        ControlSupplier<Integer> driveSpeedSupplier = new ControlSupplier<>(gamepad -> ((gamepad.b) ? 1 : (gamepad.a) ? 2 : 0), gamepadNum);

    //other
    public DriveMode driveMode = DriveMode.MECANUM;
    boolean useSmoothing = false;
    double[] smoothingValues = new double[]{.1,.1,.1};
    public double slowModeSpeed = 0.6;
    public double superSlowModeSpeed = 0.1;
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
