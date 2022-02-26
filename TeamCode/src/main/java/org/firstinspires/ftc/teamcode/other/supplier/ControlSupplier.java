package org.firstinspires.ftc.teamcode.other.supplier;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class ControlSupplier<OUTPUT> extends Supplier<Gamepad, OUTPUT> {
    /**
     * the number of the gamepad that should be passed into the supply function when using default methods(converted to gamepad in init)
     */
    private Utils.GamepadNum gamepadNum;

    /**
     * constructs a new input supplier using a lambda supply function and a specific gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepad the gamepad that should be passed into the gamepad parameter of the supply function
     */
    public ControlSupplier(Function<Gamepad, OUTPUT> supplyFunction, Gamepad gamepad){
        super(supplyFunction, gamepad);
    }

    /**
     * constructs a new input supplier using a lambda supply function and a gamepad number
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepadNum the number of the gamepad that should be passed into the gamepad parameter of the supply function(converted to gamepad in init)
     */
    public ControlSupplier(Function<Gamepad, OUTPUT> supplyFunction, Utils.GamepadNum gamepadNum){
        super(supplyFunction);
        this.gamepadNum = gamepadNum;
    }

    /**
     * constructs a new input supplier using a lambda supply function with no default gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     */
    public ControlSupplier(Function<Gamepad, OUTPUT> supplyFunction){
        super(supplyFunction);
    }

    /**
     * *only call if you have to convert gamepadNum to gamepad
     * converts the gamepad number passed in InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum) to a gamepad that can be supplied to the supplyFunction
     * @param robot
     */
    public void init(Robot robot){
        if(gamepadNum != null)
            setDevice(gamepadNum.getGamepad(robot));
    }
}
