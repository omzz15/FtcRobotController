package org.firstinspires.ftc.teamcode.parts.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;

import java.util.Arrays;

public class DriveHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    MotorSettings topLeftMotorSettings = new MotorSettings(MotorSettings.Number.ONE, DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE);
    MotorSettings topRightMotorSettings = new MotorSettings(MotorSettings.Number.TWO, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);
    MotorSettings bottomLeftMotorSettings = new MotorSettings(MotorSettings.Number.THREE, DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE);
    MotorSettings bottomRightMotorSettings = new MotorSettings(MotorSettings.Number.FOUR, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);


    ///////////
    //objects//
    ///////////
    DcMotor topLeftMotor;
    DcMotor topRightMotor;
    DcMotor bottomLeftMotor;
    DcMotor bottomRightMotor;


    @Override
    public void onInit(Robot robot) {
        topLeftMotor = topLeftMotorSettings.makeMotor(robot.hardwareMap);
        topRightMotor = topRightMotorSettings.makeMotor(robot.hardwareMap);
        bottomLeftMotor = bottomLeftMotorSettings.makeMotor(robot.hardwareMap);
        bottomRightMotor = bottomRightMotorSettings.makeMotor(robot.hardwareMap);
        motorGroups.put("drive motors", Arrays.asList(topLeftMotor,topRightMotor,bottomLeftMotor,bottomRightMotor));
    }
}
