package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.MotorSettings;

import java.util.Arrays;

public class DriveHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    MotorSettings topLeftMotorSettings = new MotorSettings(MotorSettings.Number.ONE, DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE);
    MotorSettings topRightMotorSettings = new MotorSettings(MotorSettings.Number.TWO, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);;
    MotorSettings bottomLeftMotorSettings = new MotorSettings(MotorSettings.Number.THREE, DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE);;
    MotorSettings bottomRightMotorSettings = new MotorSettings(MotorSettings.Number.FOUR, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);;

    ///////////
    //objects//
    ///////////
    DcMotorEx topLeftMotor;
    DcMotorEx topRightMotor;
    DcMotorEx bottomLeftMotor;
    DcMotorEx bottomRightMotor;


    @Override
    public void init(Robot robot){
        topLeftMotor = makeMotor(robot.hardwareMap, topLeftMotorSettings);
        topRightMotor = makeMotor(robot.hardwareMap, topRightMotorSettings);
        bottomLeftMotor = makeMotor(robot.hardwareMap, bottomLeftMotorSettings);
        bottomRightMotor = makeMotor(robot.hardwareMap, bottomRightMotorSettings);
        motors = Arrays.asList(topLeftMotor,topRightMotor,bottomLeftMotor,bottomRightMotor);
    }
}
