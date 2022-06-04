package org.firstinspires.ftc.teamcode.deprecated.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.supplier.ControlSupplier;

@Deprecated
public class ArmSettings extends RobotPartSettings {
    ////////////
    //settings//
    ////////////
    //input
    Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
        //arm
        ControlSupplier<Float> armMovementSupplier = new ControlSupplier<>(gamepad -> (gamepad.right_trigger - gamepad.left_trigger), Utils.GamepadNum.TWO);
        float minInputRegisterVal = 0.1f;
        //bucket
        ControlSupplier<Integer> bucketMovementSupplier = new ControlSupplier<>(gamepad -> (gamepad.x ? -1 : gamepad.b ? 1 : 0), Utils.GamepadNum.TWO);
        //preset
        ControlSupplier<Short> presetSupplier = new ControlSupplier<>(gamepad -> ((short)(gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0)), Utils.GamepadNum.TWO);

        ControlSupplier<Integer> dumpSupplier = new ControlSupplier<>(gamepad -> (gamepad.x ? 1 : 0), gamepadNum);

    //servo
        //start
        double bucketStartPos = 0.16;
        //speed
        int servoSpeed = 300; //in degrees/second
        double bucketMovementSpeed = .002;
        //limits
        double servoMinPos = 0;
        double servoMaxPos = 1;
        //servo presets             flat    dump    fdump   cradle mdump  bdump  almostDump
        double[] bucketPresets =   {0.23,	1.0,	0.0,	0.36,  0.0,   0.0,   0.75};
    //arm
    //arm
        //speed
        int armMovementSpeed = 10;
        //limits
        int armMinPos = 0;
        int armMaxPos = 1600;
        //arm presets       flat    dump    fdump   cradle  mdump  bdump  ult flat
        int[] armPresets = {30,     1020,     380,     95,  1247,   1410, 0};
        //other
        int armTolerance = 60;


    @Override
    public void onInit(Robot robot) {
        armMovementSupplier.init(robot);
        bucketMovementSupplier.init(robot);
        presetSupplier.init(robot);
        dumpSupplier.init(robot);
    }
}
