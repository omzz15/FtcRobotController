package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.MotorSettings;

import java.util.List;

public abstract class RobotPartHardware {

    public List<DcMotorEx> motors;

    //////////////////
    //initialization//
    //////////////////
    public void init(HardwareMap hardwareMap){}

    public static DcMotorEx makeMotor(HardwareMap hardwareMap, MotorSettings settings){
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, settings.number.value);
        updateMotor(motor,settings);
        return motor;
    }

    /////////
    //Motor//
    /////////
    public static void updateMotor(DcMotorEx motor, MotorSettings settings){
        motor.setDirection(settings.direction);
        motor.setZeroPowerBehavior(settings.zeroPowerBehavior);
    }

    public static void setMotorPowers(List<DcMotorEx> motors, double[] powers){
        for(int i = 0; i < motors.size(); i++)
            motors.get(i).setPower(powers[i]);
    }

    public void setMotorPowers(double[] powers){
        setMotorPowers(motors, powers);
    }

    public static void setMotorPowers(List<DcMotorEx> motors, double power){
        for (DcMotorEx motor: motors)
            motor.setPower(power);
    }

    public void setMotorPowers(double power){
        setMotorPowers(motors, power);
    }

    public static int[] getMotorPositions(List<DcMotorEx> motors){
        int[] out = new int[motors.size()];
        for(int i = 0; i < motors.size(); i++)
            out[i] = motors.get(i).getCurrentPosition();
        return out;
    }

    public int[] getMotorPositions(){
        return getMotorPositions(motors);
    }
}