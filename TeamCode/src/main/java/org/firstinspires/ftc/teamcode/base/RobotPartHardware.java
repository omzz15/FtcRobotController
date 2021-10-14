package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.MotorSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Function;

public abstract class RobotPartHardware {

    //motor functions
    public MotorFunction setMotorPower = (motor, value) -> {
        motor.setPower((double)value);
        return null;
    };

    public MotorFunction setTargetPosition = (motor, value) -> {
        motor.setTargetPosition((int)value);
        return null;
    };

    public MotorFunction getMotorPower = (motor, value) -> {
      return motor.getPower();
    };

    public MotorFunction getMotorPosition = (motor, value) -> {
      return motor.getCurrentPosition();
    };

    //servo functions
    public ServoFunction setServoPosition = (servo, value) -> {
        servo.setPosition((double)value);
        return null;
    };

    //hardware groups
    public Hashtable<String, List<DcMotorEx>> motorGroups = new Hashtable<>();
    public Hashtable<String, List<Servo>> servoGroups = new Hashtable<>();


    //////////////////
    //initialization//
    //////////////////
    public void init(Robot robot){}


    ////////////////////
    //base run methods//
    ////////////////////
    public List<Object> runMotorFunction(List<DcMotorEx> motors, MotorFunction function, Object value, boolean useSeparateValues){
        List<Object> out = new ArrayList<>();
        if(useSeparateValues) {
            if(value.getClass().isArray()) {
                if (((Object[]) value).length >= motors.size()) {
                    for (int i = 0; i < motors.size(); i++) {
                        out.add(function.run(motors.get(i), ((Object[]) value)[i]));
                    }
                }
            }
            else{
                if (((List<Object>) value).size() >= motors.size()) {
                    for (int i = 0; i < motors.size(); i++) {
                        out.add(function.run(motors.get(i), ((List<Object>) value).get(i)));
                    }
                }
            }
        }
        else{
            for (DcMotorEx motor : motors) {
                out.add(function.run(motor, value));
            }
        }
        return out;
    }

    public List<Object> runMotorFunction(String groupName, MotorFunction function, Object value, boolean useSeparateValues){
        return runMotorFunction(motorGroups.get(groupName), function, value, useSeparateValues);
    }

    public List<Object> runServoFunction(List<Servo> servos, ServoFunction function, Object value, boolean useSeparateValues){
        List<Object> out = new ArrayList<>();
        if(useSeparateValues) {
            if(value.getClass().isArray()) {
                if (((Object[]) value).length >= servos.size()) {
                    for (int i = 0; i < servos.size(); i++) {
                        out.add(function.run(servos.get(i), ((Object[]) value)[i]));
                    }
                }
            }
            else{
                if (((List<Object>) value).size() >= servos.size()) {
                    for (int i = 0; i < servos.size(); i++) {
                        out.add(function.run(servos.get(i), ((List<Object>) value).get(i)));
                    }
                }
            }
        }
        else{
            for (Servo servo : servos) {
                out.add(function.run(servo, value));
            }
        }
        return out;
    }

    public List<Object> runServoFunction(String groupName, ServoFunction function, Object value, boolean useSeparateValues){
        return runServoFunction(servoGroups.get(groupName), function, value, useSeparateValues);
    }

    ////////////////
    //motor method//
    ////////////////
    public void setMotorPower(String groupName, double power){
        runMotorFunction(groupName, setMotorPower, power, false);
    }

    public void setMotorPowers(String groupName, double[] powers){
        runMotorFunction(groupName, setMotorPower, powers, true);
    }

    public void stopMotors(String groupName){
        setMotorPower(groupName, 0);
    }

    public int[] getMotorPositions(String groupName){
        List<Object> vals = runMotorFunction(groupName, getMotorPosition, null, false);
        int[] out = new int[vals.size()];

        for(int i = 0; i < vals.size(); i++)
            out[i] = (int)vals.get(i);

        return out;
    }

    //////////////
    //other code//
    //////////////
    public interface MotorFunction {
        Object run(DcMotorEx motor, Object value);
    }

    public interface ServoFunction {
        Object run(Servo motor, Object value);
    }
}