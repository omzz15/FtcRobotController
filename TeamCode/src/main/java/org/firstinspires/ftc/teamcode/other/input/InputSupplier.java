package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplier {
    /**
     * the gamepad that is passed into the supply function when using default methods
     */
    private Gamepad gamepad;
    /**
     * the number of the gamepad that should be passed into the supply function when using default methods(converted to gamepad in init)
     */
    private Utils.GamepadNum gamepadNum;
    /**
     * the lambda function that supplies an object output(usually a primitive type) from a gamepad input
     */
    Function<Gamepad, Object> supplyFunction;

    /**
     * constructs a new input supplier using a lambda supply function and a specific gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepad the gamepad that should be passed into the gamepad parameter of the supply function
     */
    public InputSupplier(Function<Gamepad, Object> supplyFunction, Gamepad gamepad){
        this.gamepad = gamepad;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function and a gamepad number
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepadNum the number of the gamepad that should be passed into the gamepad parameter of the supply function(converted to gamepad in init)
     */
    public InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum){
        this.gamepadNum = gamepadNum;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function with no default gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     */
    public InputSupplier(Function<Gamepad, Object> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    /**
     * *only call if you have to convert gamepadNum to gamepad
     * converts the gamepad number passed in InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum) to a gamepad that can be supplied to the supplyFunction
     * @param robot
     */
    public void init(Robot robot){
        if(gamepadNum != null)
            gamepad = gamepadNum.getGamepad(robot);
    }

    /**
     *
     * @param gamepad
     */
    public void setGamepad(Gamepad gamepad){
        this.gamepad = gamepad;
    }

    /**
     *
     * @param gamepad
     * @return
     */
    public Object get(Gamepad gamepad){
        return supplyFunction.apply(gamepad);
    }

    /**
     *
     * @return
     */
    public Object get(){
        return get(gamepad);
    }

    /**
     *
     * @param gamepad
     * @return
     */
    public float getFloat(Gamepad gamepad){
        return (float) get(gamepad);
    }

    /**
     *
     * @return
     */
    public float getFloat(){
        return getFloat(gamepad);
    }

    /**
     *
     * @param gamepad
     * @return
     */
    public double getDouble(Gamepad gamepad){
        return (double) get(gamepad);
    }

    /**
     *
     * @return
     */
    public double getDouble(){
        return getDouble(gamepad);
    }

    /**
     *
     * @param gamepad
     * @return
     */
    public int getInt(Gamepad gamepad){
        return (int) get(gamepad);
    }

    /**
     *
     * @return
     */
    public int getInt(){
        return getInt(gamepad);
    }

    /**
     *
     * @param gamepad
     * @return
     */
    public boolean getBoolean(Gamepad gamepad){
        return (boolean) get(gamepad);
    }

    /**
     *
     * @return
     */
    public boolean getBoolean(){
        return getBoolean(gamepad);
    }
}
