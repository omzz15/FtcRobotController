package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplier<T>{
    /**
     * the gamepad that is passed into the supply function when using default methods
     */
    public Gamepad gamepad;
    /**
     * the number of the gamepad that should be passed into the supply function when using default methods(converted to gamepad in init)
     */
    private Utils.GamepadNum gamepadNum;
    /**
     * the lambda function that supplies an object output(usually a primitive type) from a gamepad input
     */
    Function<Gamepad, T> supplyFunction;

    /**
     * constructs a new input supplier using a lambda supply function and a specific gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepad the gamepad that should be passed into the gamepad parameter of the supply function
     */
    public InputSupplier(Function<Gamepad, T> supplyFunction, Gamepad gamepad){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function and a gamepad number
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepadNum the number of the gamepad that should be passed into the gamepad parameter of the supply function(converted to gamepad in init)
     */
    public InputSupplier(Function<Gamepad, T> supplyFunction, Utils.GamepadNum gamepadNum){
        this.gamepadNum = gamepadNum;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function with no default gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     */
    public InputSupplier(Function<Gamepad, T> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    /**
     * *only call if you have to convert gamepadNum to gamepad
     * converts the gamepad number passed in InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum) to a gamepad that can be supplied to the supplyFunction
     * @param robot the robot the default gamepad should be set from
     */
    public void init(Robot robot){
        if(gamepadNum != null)
            gamepad = gamepadNum.getGamepad(robot);
    }

    /**
     * sets the default gamepad
     * @param gamepad the new default gamepad
     */
    public void setGamepad(Gamepad gamepad){
        this.gamepad = gamepad;
    }

    /**
     * gets the current value from the supply function using the passed in gamepad
     * @param gamepad the passed in gamepad
     * @return value of supply function with type T
     */
    public T get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }

    /**
     * gets the current value from the supply function using the default gamepad
     * @return value of supply function with type T
     */
    public T get(){
        return get(gamepad);
    }
}
