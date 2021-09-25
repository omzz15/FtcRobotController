package org.firstinspires.ftc.teamcode.other;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MotorSettings{
    public Number number;
    public DcMotorSimple.Direction direction = DcMotorSimple.Direction.FORWARD;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;

    public MotorSettings(Number number){
        this.number = number;
    }

    public MotorSettings(Number number, DcMotorSimple.Direction direction, DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        this.number = number;
        this.direction = direction;
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    public enum Number {
        ONE("motor0"),
        TWO("motor1"),
        THREE("motor2"),
        FOUR("motor3"),
        ONE_B("motor0B"),
        TWO_B("motor1B"),
        THREE_B("motor2B"),
        FOUR_B("motor3B");

        public String value;

        Number(String value){
            this.value = value;
        }
    }
}
