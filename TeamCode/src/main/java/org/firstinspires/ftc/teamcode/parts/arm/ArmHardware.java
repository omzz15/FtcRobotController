package org.firstinspires.ftc.teamcode.parts.arm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.other.hardware.motor.MotorSettings;
import org.firstinspires.ftc.teamcode.other.hardware.servo.ServoSettings;

public class ArmHardware extends RobotPartHardware {
    ////////////
    //settings//
    ////////////
    public MotorSettings armMotorSettings = new MotorSettings(MotorSettings.Number.ONE_B, DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_TO_POSITION, 0.6);
    public ServoSettings bucketServoSettings = new ServoSettings(ServoSettings.Number.ONE, Servo.Direction.FORWARD);

    ///////////
    //objects//
    ///////////
    DcMotor armMotor;
    Servo bucketServo;

    @Override
    public void onInit(Robot robot) {
        armMotor = armMotorSettings.makeMotor(robot.hardwareMap);
        bucketServo = bucketServoSettings.makeServo(robot.hardwareMap);
    }
}
