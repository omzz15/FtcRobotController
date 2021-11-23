package org.firstinspires.ftc.teamcode.other.servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.other.EndPoints;
import org.firstinspires.ftc.teamcode.other.Utils;

public class ServoSettings {
    public Number number;
    public Servo.Direction direction;
    public Double targetPos;
    public EndPoints endPoints;

    public ServoSettings(Number number){
        construct(number, Servo.Direction.FORWARD, null, new EndPoints(0,1));
    }

    public ServoSettings(Number number, Servo.Direction direction){
        construct(number,direction,null, new EndPoints(0,1));
    }

    public ServoSettings(Number number, Servo.Direction direction, Double targetPos, EndPoints endPoints){
        construct(number,direction,targetPos, endPoints);
    }

    public void construct(Number number, Servo.Direction direction, Double targetPos, EndPoints endPoints){
        this.number = number;
        this.direction = direction;
        this.targetPos = targetPos;
        this.endPoints = endPoints;
    }

    public Servo makeServo(HardwareMap hardwareMap){
        Servo servo = hardwareMap.get(Servo.class, number.value);
        updateServo(servo);
        return servo;
    }

    public void updateServo(Servo servo){
        servo.setDirection(direction);
        if(targetPos != null)
            servo.setPosition(targetPos);
    }

    public enum Number {
        ONE("servo0"),
        TWO("servo1"),
        THREE("servo2"),
        FOUR("servo3"),
        FIVE("servo4"),
        ONE_B("servo0B"),
        TWO_B("servo1B"),
        THREE_B("servo2B"),
        FOUR_B("servo3B"),
        FIVE_B("servo4B");

        public String value;

        Number(String value){
            this.value = value;
        }
    }
}
