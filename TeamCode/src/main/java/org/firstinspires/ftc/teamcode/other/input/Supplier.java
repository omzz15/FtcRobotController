package org.firstinspires.ftc.teamcode.other.input;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public class Supplier<IN extends Object, OUT extends Object>{
    /**
     * the device that is passed into the supply function when using default methods
     */
    public IN device;
    /**
     * the lambda function that supplies an object output(usually a primitive type) from a device input
     */
    Function<IN, OUT> supplyFunction;

    /**
     * constructs a new input supplier using a lambda supply function and a specific device
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param device the device that should be passed into the device parameter of the supply function
     */
    public Supplier(Function<IN, OUT> supplyFunction, IN device){
        this.device = device;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function with no default device
     * @param supplyFunction the lambda function that is called to supply a new value
     */
    public Supplier(Function<IN, OUT> supplyFunction){
        this.supplyFunction = supplyFunction;
    }

    /**
     * sets the default device
     * @param device the new default device
     */
    public void setDevice(IN device){
        this.device = device;
    }

    /**
     * gets the default device
     * @return the default device
     */
    public IN getDevice(){
        return device;
    }

    /**
     * gets the current value from the supply function using the passed in device
     * @param device the passed in device
     * @return value of supply function with type OUT
     */
    public OUT get(IN device) throws DeviceNotDefinedException{
        try {
            return supplyFunction.apply(device);
        }catch (Exception e){
            throw new SupplyFunctionFailException("could not run supply function of input type:" + device.getClass() + " and output type:" + OUT +, e);
        }
    }

    /**
     * gets the current value from the supply function using the default device
     * @return value of supply function with type OUT
     */
    public OUT get() throws SupplyFunctionFailException{
        return get(device);
    }
}
class SupplyFunctionFailException extends Exception{
    public SupplyFunctionFailException(String message, Throwable cause) {
        super(message, cause);
    }
}