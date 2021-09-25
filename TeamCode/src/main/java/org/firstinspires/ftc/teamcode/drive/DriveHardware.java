package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.MotorSettings;

import java.util.Arrays;
import java.util.List;

public class DriveHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    MotorSettings topLeftMotorSettings = new MotorSettings(MotorSettings.Number.ONE, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);
    MotorSettings topRightMotorSettings = new MotorSettings(MotorSettings.Number.TWO, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);;
    MotorSettings bottomLeftMotorSettings = new MotorSettings(MotorSettings.Number.THREE, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);;
    MotorSettings bottomRightMotorSettings = new MotorSettings(MotorSettings.Number.FOUR, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);;

    ///////////
    //objects//
    ///////////
    DcMotorEx topLeftMotor;
    DcMotorEx topRightMotor;
    DcMotorEx bottomLeftMotor;
    DcMotorEx bottomRightMotor;
    List<DcMotorEx> motors;


    @Override
    public void init(HardwareMap hardwareMap){
        topLeftMotor = getMotor(hardwareMap, topLeftMotorSettings);
        topRightMotor = getMotor(hardwareMap, topRightMotorSettings);
        bottomLeftMotor = getMotor(hardwareMap, bottomLeftMotorSettings);
        bottomRightMotor = getMotor(hardwareMap, bottomRightMotorSettings);
        motors = Arrays.asList(topLeftMotor,topRightMotor,bottomLeftMotor,bottomRightMotor);
    }
}
