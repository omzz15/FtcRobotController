package org.firstinspires.ftc.teamcode.other.supplier;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class DoubleControlSupplier extends Supplier<Gamepad, Boolean>{
    Gamepad gamepad2;
    /**
     * constructs a new input supplier using a lambda supply function and a specific gamepad
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepad the gamepad that should be passed into the gamepad parameter of the supply function
     */
    public DoubleControlSupplier(Function<Gamepad, Boolean> supplyFunction, Gamepad gamepad1, Gamepad gamepad2){
        super(supplyFunction, gamepad1);
        this.gamepad2 = gamepad2;
    }

    /**
     * constructs a new input supplier using a lambda supply function and a gamepad number
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param gamepadNum the number of the gamepad that should be passed into the gamepad parameter of the supply function(converted to gamepad in init)
     */
    public DoubleControlSupplier(Function<Gamepad, Boolean> supplyFunction){
        super(supplyFunction);
    }

    /**
     * runs the lambda function with default INPUT
     * @return the output from the lambda function
     */
    @Override
    public Boolean get(){
        return get(device) || (boolean)get(gamepad2);
    }

    /**
     * *only call if you have to convert gamepadNum to gamepad
     * converts the gamepad number passed in InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum) to a gamepad that can be supplied to the supplyFunction
     * @param robot
     */
    public void init(Robot robot){
        setDevice(robot.gamepad1);
        this.gamepad2 = robot.gamepad2;
    }
}
