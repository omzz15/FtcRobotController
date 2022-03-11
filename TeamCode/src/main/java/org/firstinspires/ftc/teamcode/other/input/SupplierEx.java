package org.firstinspires.ftc.teamcode.other.input;

import java.util.function.Function;

public class SupplierEx<IN> extends Supplier<IN, Double>{
	private static final double defaultMinRegisterVal = 0.1;

	private double minRegisterVal;
	//private int multiPressMaxTime; //add in later
	private boolean wasButtonPressed = false;
	private long lastButtonRelease = System.currentTimeMillis();
	//private int numTimesPressed = 0; //add in later

	public SupplierEx(Function<IN, Double> supplyFunction, IN device, double minRegisterVal) {
		super(supplyFunction, device);
		this.minRegisterVal = minRegisterVal;
	}

	public SupplierEx(Function<IN, Double> supplyFunction, IN device){
		super(supplyFunction, device);
	}

	public SupplierEx(Function<IN, Double> supplyFunction, double minRegisterVal){
		super(supplyFunction);
		this.minRegisterVal = minRegisterVal;
	}

	public SupplierEx(Function<IN, Double> supplyFunction) {
		super(supplyFunction);
		minRegisterVal = defaultMinRegisterVal;
	}

	boolean getButtonHeld(IN device){
		return get(device) > minRegisterVal;
	}

	boolean getButtonHeld(){
		return getButtonHeld(device);
	}

	boolean getButtonHeld(IN device, int time)
	{
		if(getButtonHeld(device))
		{
			return System.currentTimeMillis() - lastButtonRelease > time;
		}
		else lastButtonRelease = System.currentTimeMillis();
		return false;
	}
	boolean getButtonHeld(int time){return getButtonHeld(device, time);}

	boolean getButtonPressed(IN device)
	{
		if(getButtonHeld(device))
		{
			if(!wasButtonPressed)
			{
				wasButtonPressed = true;
				return true;
			}
		}
		else wasButtonPressed = false;
		return false;
	}
	boolean getButtonPressed(){return getButtonPressed(device);}

	boolean getButtonReleased(IN device)
	{
		if(getButtonHeld(device)) wasButtonPressed = true;
		else if(wasButtonPressed)
		{
			wasButtonPressed = false;
			return true;
		}
		return false;
	}
	boolean getButtonReleased(){return getButtonReleased(device);}
}
