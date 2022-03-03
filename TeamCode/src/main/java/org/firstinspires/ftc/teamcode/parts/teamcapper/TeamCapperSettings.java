package org.firstinspires.ftc.teamcode.parts.teamcapper;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.supplier.ControlSupplier;

public class TeamCapperSettings extends RobotPartSettings {


    ControlSupplier<Double> capperPresetSupplier = new ControlSupplier<>(gamepad -> (
        gamepad.dpad_up ? 0.01 : gamepad.dpad_down ? -0.01 : 0
        ), Utils.GamepadNum.ONE);

    //public double

    @Override
    public void onInit(Robot robot) {
        capperPresetSupplier.init(robot);
    }
}
