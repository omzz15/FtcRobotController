package org.firstinspires.ftc.teamcode.base.part.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.motor.MotorFunction;
import org.firstinspires.ftc.teamcode.other.servo.ServoFunction;

import java.util.Hashtable;
import java.util.List;

public abstract class RobotPartHardware {
    //hardware groups
    public Hashtable<String, List<DcMotor>> motorGroups = new Hashtable<>();
    public Hashtable<String, List<Servo>> servoGroups = new Hashtable<>();


    //////////////////
    //initialization//
    //////////////////
    public abstract void onInit(Robot robot);


    ///////////////////
    //motor functions//
    ///////////////////
    public void setMotorPower(String groupName, double power){
        MotorFunction.setMotorPower.run(motorGroups.get(groupName), power, false);
    }

    public void setMotorPowers(String groupName, double[] powers){
        MotorFunction.setMotorPower.run(motorGroups.get(groupName), powers, true);
    }

    public void stopMotors(String groupName){
        setMotorPower(groupName, 0);
    }

    public int[] getMotorPositions(String groupName){
        return (int[]) MotorFunction.getMotorPositions.run(motorGroups.get(groupName), null, false);
    }


    ///////////////////
    //servo functions//
    ///////////////////
    public void setServoPosition(String groupName, double position){
        ServoFunction.setServoPosition.run(servoGroups.get(groupName), position, false);
    }

    public void setServoPositions(String groupName, double[] positions){
        ServoFunction.setServoPosition.run(servoGroups.get(groupName), positions, true);
    }

    public double[] getServoPositions(String groupName){
        return (double[]) ServoFunction.getServoPositions.run(servoGroups.get(groupName), null, true);
    }
}
