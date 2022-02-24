package org.firstinspires.ftc.teamcode.parts.intake;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.input.InputSupplier;

public class IntakeSettings extends RobotPartSettings {
    ////////////
    //settings//
    ////////////
    //input
    //intake
    InputSupplier intakePowerSupplier = new InputSupplier(gamepad -> (gamepad.right_trigger - gamepad.left_trigger), Utils.GamepadNum.TWO);
    InputSupplier intakePresetSupplier = new InputSupplier(gamepad -> ((gamepad.y) ? Intake.IntakePosition.UP : (gamepad.a) ? Intake.IntakePosition.DOWN : null), Utils.GamepadNum.ONE);
    float minInputRegisterVal = 0.1f;
    //servo                                     //up was .8    //down
    double[] intakeServoPresets = new double[]  {0.7,    0.0};
    int servoSpeed = 300;//in deg/sec
    double servoMaxPos = 0.8;
    double servoMinPos = 0;


    @Override
    public void onInit(Robot robot) {
        intakePowerSupplier.init(robot);
        intakePresetSupplier.init(robot);
    }
}
