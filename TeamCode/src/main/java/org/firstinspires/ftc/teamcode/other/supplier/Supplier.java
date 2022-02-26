package org.firstinspires.ftc.teamcode.other.supplier;

import java.util.function.Function;

public class Supplier<INPUT, OUTPUT> {
    /**
     * the device that is passed into the supply function when using default methods
     */
    public INPUT device;
    /**
     * the lambda function that supplies an object output(usually a primitive type) from a device input
     */
    Function<INPUT, OUTPUT> supplyFunction;

    /**
     * constructs a new input supplier using a lambda supply function and a specific device
     * @param supplyFunction the lambda function that is called to supply a new value
     * @param device the device that should be passed into the INPUT parameter of the supply function
     */
    public Supplier(Function<INPUT, OUTPUT> supplyFunction, INPUT device){
        this.device = device;
        this.supplyFunction = supplyFunction;
    }

    /**
     * constructs a new input supplier using a lambda supply function with no default device
     * @param supplyFunction the lambda function that is called to supply a new value
     */
    public Supplier(Function<INPUT, OUTPUT> supplyFunction){
        this.supplyFunction = supplyFunction;
    }



    /**
     * sets the device
     * @param device the device you want to use for input in lambda function
     */
    public void setDevice(INPUT device){
        this.device = device;
    }

    /**
     * runs the lambda function with specified INPUT
     * @param device the device you want to use for lambda function
     * @return the output from the lambda function
     */
    public OUTPUT get(INPUT device){
        return supplyFunction.apply(device);
    }

    /**
     * runs the lambda function with default INPUT
     * @return the output from the lambda function
     */
    public OUTPUT get(){
        return get(device);
    }
}
