package org.firstinspires.ftc.teamcode.other.inputsupplier;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplier{
    public Gamepad gamepad;
    public Utils.GamepadNum gamepadNum;
    Function<Gamepad, Object> supplyFunction;

    public InputSupplier(Gamepad gamepad, Function<Gamepad, Object> supplyFunction){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Utils.GamepadNum gamepadNum, Function<Gamepad, Object> supplyFunction){
        this.gamepadNum = gamepadNum;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Function<Gamepad, Object> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    public void init(Robot robot){
        gamepad = gamepadNum.getGamepad(robot);
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

    public Object get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }

}

