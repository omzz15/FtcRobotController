package org.firstinspires.ftc.teamcode.parts.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

public class IntakeHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    MotorSettings intakeMotorSettings = new MotorSettings(MotorSettings.Number.TWO, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);


    ///////////
    //objects//
    ///////////
    DcMotor intakeMotor;


    @Override
    public void onInit(Robot robot) {
        intakeMotor = intakeMotorSettings.makeMotor(robot.hardwareMap);
    }
}
