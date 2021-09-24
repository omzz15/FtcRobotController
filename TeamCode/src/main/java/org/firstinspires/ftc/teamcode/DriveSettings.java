package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Func;

import java.util.function.Function;

public class DriveSettings extends RobotPartSettings{
    Function<Gamepad, Double> driveXSupplier = gamepad -> { return 0.0;};
    Function<Gamepad, Double> driveYSupplier = gamepad -> { return 0.0;};
    Function<Gamepad, Double> driveRSupplier = gamepad -> { return 0.0;};
}
