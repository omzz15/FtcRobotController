package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.other.motor.MotorFunction;
import org.firstinspires.ftc.teamcode.other.servo.ServoFunction;

import java.util.Hashtable;
import java.util.List;

public abstract class RobotPartHardware {

    //hardware groups
    public Hashtable<String, List<DcMotorEx>> motorGroups = new Hashtable<>();
    public Hashtable<String, List<Servo>> servoGroups = new Hashtable<>();


    //////////////////
    //initialization//
    //////////////////
    public void init(Robot robot){}


    /////////////////
    //motor methods//
    /////////////////
    public void setMotorPower(String groupName, double power){
        setMotorPower.run(motorGroups.get(groupName), power, false);
    }

    public void setMotorPowers(String groupName, double[] powers){
        setMotorPower.run(motorGroups.get(groupName), powers, true);
    }

    public void stopMotors(String groupName){
        setMotorPower(groupName, 0);
    }

    public int[] getMotorPositions(String groupName){
       return (int[]) getMotorPositions.run(motorGroups.get(groupName), null, false);
    }


    //motor functions
    public MotorFunction setMotorPower = (motors, value, separateValues) -> {
        for(int i = 0; i < motors.size(); i++)
            if(separateValues)
                motors.get(i).setPower(((double[])value)[i]);
            else
                motors.get(i).setPower((double)value);
        return null;
    };

    public MotorFunction setTargetPosition = (motors, value, separateValues) -> {
        for(int i = 0; i < motors.size(); i++)
            if(separateValues)
                motors.get(i).setTargetPosition(((int[])value)[i]);
            else
                motors.get(i).setTargetPosition((int)value);
        return null;
    };

    public MotorFunction getMotorPositions = (motors, value, separateValues) -> {
        int[] pos = new int[motors.size()];
        for(int i = 0; i < motors.size(); i++)
            pos[i] = motors.get(i).getCurrentPosition();
        return pos;
    };

    //servo functions
    public ServoFunction setServoPosition = (servos, value, separateValues) -> {
        for(int i = 0; i < servos.size(); i++)
            if(separateValues)
                servos.get(i).setPosition(((double[])value)[i]);
            else
                servos.get(i).setPosition((double)value);
        return null;
    };
}