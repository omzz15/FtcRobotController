package org.firstinspires.ftc.teamcode.parts.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.input.InputSupplier;

public class ArmSettings extends RobotPartSettings {
    ////////////
    //settings//
    ////////////
    //input
    Utils.GamepadNum gamepadNum = Utils.GamepadNum.ONE;
        //arm
        InputSupplier armMovementSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger), gamepadNum);
        float minInputRegisterVal = 0.1f;
        //bucket
        InputSupplier bucketMovementSupplier = new InputSupplier(gamepad -> (gamepad.x ? -1 : gamepad.b ? 1 : 0), gamepadNum);
        //preset
        InputSupplier presetSupplier = new InputSupplier(gamepad -> (gamepad.dpad_down ? 1 : gamepad.dpad_left ? 2 : gamepad.dpad_right ? 3 : gamepad.dpad_up ? 4 : 0), gamepadNum);

    //servo
        //start
        double bucketStartPos = 0.16;
        //speed
        int servoSpeed = 300; //in degrees/second
        double bucketMovementSpeed = .01;
        //limits
        double servoMinPos = 0;
        double servoMaxPos = 1;
        //servo presets             flat    dump    fdump   cradle
        double[] bucketPresets =   {0.16,	0.68,	0.0,	0.36};

    //arm
        //speed
        int armMovementSpeed = 7;
        //limits
        int armMinPos = 0;
        int armMaxPos = 1600;
        //arm presets       flat    dump    fdump   cradle
        int[] armPresets = {0,      0,      0,      0};
        //other
        int armTolerance = 60;


    @Override
    public void onInit(Robot robot) {
        armMovementSupplier.init(robot);
        bucketMovementSupplier.init(robot);
        presetSupplier.init(robot);
    }
}
