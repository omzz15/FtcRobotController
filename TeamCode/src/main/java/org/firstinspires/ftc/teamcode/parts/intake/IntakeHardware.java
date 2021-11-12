package org.firstinspires.ftc.teamcode.parts.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.motor.MotorSettings;
import org.firstinspires.ftc.teamcode.other.servo.ServoSettings;

public class IntakeHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    MotorSettings intakeMotorSettings = new MotorSettings(MotorSettings.Number.TWO_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE);
    ServoSettings intakeServoSettings = new ServoSettings(ServoSettings.Number.TWO);

    ///////////
    //objects//
    ///////////
    DcMotor intakeMotor;
    Servo intakeServo;

    @Override
    public void onInit(Robot robot) {
        intakeMotor = intakeMotorSettings.makeMotor(robot.hardwareMap);
        intakeServo = intakeServoSettings.makeServo(robot.hardwareMap);
    }
}
