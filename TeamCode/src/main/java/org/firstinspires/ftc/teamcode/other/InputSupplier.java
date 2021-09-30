package org.firstinspires.ftc.teamcode.other;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Function;

public class InputSupplier{
    public Gamepad gamepad;
    Function<Gamepad, Object> supplyFunction;

    public InputSupplier(Gamepad gamepad, Function<Gamepad, Object> supplyFunction){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Function<Gamepad, Object> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    public float getFloat(Gamepad gamepad){
        return (float) get(gamepad);
    }

    public double getFloat(){
        return getFloat(gamepad);
    }

    public boolean getBoolean(Gamepad gamepad){
        return (boolean)get(gamepad);
    }

    public boolean getBoolean(){
        return getBoolean(gamepad);
    }

    public Object get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }

}

