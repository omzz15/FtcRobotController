package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplierEx extends InputSupplier<Float>{
	private float minRegisterVal;
	//private int multiPressMaxTime; //add in later
	private boolean wasButtonPressed = false;
	private long lastButtonRelease = System.currentTimeMillis();
	//private int numTimesPressed = 0; //add in later

	public InputSupplierEx(Function<Gamepad, Float> supplyFunction, Gamepad gamepad, float minRegisterVal) {
		super(supplyFunction, gamepad);
		this.minRegisterVal = minRegisterVal;
	}

	public InputSupplierEx(Function<Gamepad, Float> supplyFunction, Utils.GamepadNum gamepadNum, float minRegisterVal) {
		super(supplyFunction, gamepadNum);
		this.minRegisterVal = minRegisterVal;
	}

	public InputSupplierEx(Function<Gamepad, Float> supplyFunction) {
		super(supplyFunction);
		minRegisterVal = 0.1f;
	}

	boolean getButtonHeld(Gamepad gamepad){
		return get(gamepad) > minRegisterVal;
	}

	boolean getButtonHeld(){
		return getButtonHeld(gamepad);
	}

	boolean getButtonHeld(Gamepad gamepad, int time)
	{
		if(getButtonHeld(gamepad))
		{
			return System.currentTimeMillis() - lastButtonRelease > time;
		}
		else lastButtonRelease = System.currentTimeMillis();
		return false;
	}
	boolean getButtonHeld(int time){return getButtonHeld(gamepad, time);}

	boolean getButtonPressed(Gamepad gamepad)
	{
		if(getButtonHeld(gamepad))
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
	boolean getButtonPressed(){return getButtonPressed(gamepad);}

	boolean getButtonReleased(Gamepad gamepad)
	{
		if(getButtonHeld(gamepad)) wasButtonPressed = true;
		else if(wasButtonPressed)
		{
			wasButtonPressed = false;
			return true;
		}
		return false;
	}
	boolean getButtonReleased(){return getButtonReleased(gamepad);}
}
