package org.firstinspires.ftc.teamcode.other.inputsupplier;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Function;

public class RampedInputSupplier extends InputSupplier{
	float ramp = 0;
	float currentVal = 0;

	public RampedInputSupplier(Gamepad gamepad, Function<Gamepad, Object> supplyFunction, float ramp) {
		super(gamepad, supplyFunction);
		this.ramp = ramp;
	}

	public RampedInputSupplier(Function<Gamepad, Object> supplyFunction, float ramp) {
		super(supplyFunction);
		this.ramp = ramp;
	}

	public RampedInputSupplier(Function<Gamepad, Object> supplyFunction) {
		super(supplyFunction);
	}

	public float getRampedFloat(Gamepad gamepad){
		float target = (float) get(gamepad);

		if(currentVal < target) {
			currentVal += ramp;
			if(currentVal > target)
				currentVal = target;
		}
		else if(currentVal > target) {
			currentVal -= ramp;
			if(currentVal < target)
				currentVal = target;
		}

		return currentVal;
	}

	public float getRampedFloat(){
		return getRampedFloat(gamepad);
	}

	public void reset(){
		currentVal = 0;
	}
}
