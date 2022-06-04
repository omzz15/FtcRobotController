package org.firstinspires.ftc.teamcode.parts.disklauncher;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartSettings;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.supplier.ControlSupplier;

public class DiskLauncherSettings extends RobotPartSettings {
    ControlSupplier<Double> flipPresetSupplier = new ControlSupplier<>(gamepad -> (gamepad.right_trigger > 0 ? 0.0 : 1.0), Utils.GamepadNum.ONE);
    ControlSupplier<Boolean> launcherPresetSupplier = new ControlSupplier<>(gamepad -> (gamepad.b ? true : false), Utils.GamepadNum.ONE);
    ControlSupplier<Boolean> conveyerPresetSupplier = new ControlSupplier<>(gamepad -> (gamepad.a ? true : false), Utils.GamepadNum.ONE);

    PIDFCoefficients launcherMotorPID = new PIDFCoefficients(100,0,0,12.4);

    double ticksPerRev = 28;
    double gearRatio = 1;
    double startRPM=1000;
    double spinMultiplier = 60 / ticksPerRev * gearRatio;
    double targetWheelRpm = startRPM;

    @Override
    public void onInit(Robot robot) {
        flipPresetSupplier.init(robot);
        launcherPresetSupplier.init(robot);
        conveyerPresetSupplier.init(robot);
    }
}
