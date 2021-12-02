package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplier {
    public Gamepad gamepad;
    public Utils.GamepadNum gamepadNum;
    Function<Gamepad, Object> supplyFunction;

    public InputSupplier(Function<Gamepad, Object> supplyFunction, Gamepad gamepad){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum){
        this.gamepadNum = gamepadNum;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Function<Gamepad, Object> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    public void init(Robot robot){
        if(gamepadNum != null)
            gamepad = gamepadNum.getGamepad(robot);
    }

    public Object get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }
    public Object get(){
        return get(gamepad);
    }

    public float getFloat(Gamepad gamepad){
        return (float) get(gamepad);
    }
    public float getFloat(){
        return getFloat(gamepad);
    }

    public double getDouble(Gamepad gamepad){
        return (double) get(gamepad);
    }
    public double getDouble(){
        return getDouble(gamepad);
    }

    public int getInt(Gamepad gamepad){
        return (int) get(gamepad);
    }
    public int getInt(){
        return getInt(gamepad);
    }

    public boolean getBoolean(Gamepad gamepad){
        return (boolean) get(gamepad);
    }
    public boolean getBoolean(){
        return getBoolean(gamepad);
    }
}
