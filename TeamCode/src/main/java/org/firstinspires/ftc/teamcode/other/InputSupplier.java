package org.firstinspires.ftc.teamcode.other;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Function;

public class InputSupplier{
    public Gamepad gamepad;
    Function<Gamepad, Float> supplyFunction;

    public InputSupplier(Gamepad gamepad, Function<Gamepad, Float> supplyFunction){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }
    public InputSupplier(Function<Gamepad, Float> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    public double get(){
        return get(gamepad);
    }

    public double get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }

}

