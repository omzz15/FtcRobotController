package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Func;

import java.util.function.Function;

public class DriveSettings extends RobotPartSettings{
    InputSupplier driveXSupplier = new InputSupplier(gamepad -> { return 0.0;});
    InputSupplier driveYSupplier = new InputSupplier(gamepad -> { return 0.0;});
    InputSupplier driveRSupplier = new InputSupplier(gamepad -> { return 0.0;});
}
