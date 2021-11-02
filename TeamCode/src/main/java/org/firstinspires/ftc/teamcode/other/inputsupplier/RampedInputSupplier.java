package org.firstinspires.ftc.teamcode.other.inputsupplier;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class RampedInputSupplier extends InputSupplier{
	float ramp = 0;
	float currentVal = 0;
	boolean brakeAt0 = false;

	public RampedInputSupplier(Gamepad gamepad, Function<Gamepad, Object> supplyFunction, float ramp, boolean brakeAt0) {
		super(gamepad, supplyFunction);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Utils.GamepadNum gamepadNum, Function<Gamepad, Object> supplyFunction, float ramp, boolean brakeAt0){
		super(gamepadNum, supplyFunction);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Function<Gamepad, Object> supplyFunction, float ramp, boolean brakeAt0) {
		super(supplyFunction);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Function<Gamepad, Object> supplyFunction) {
		super(supplyFunction);
	}

	public float getRampedFloat(Gamepad gamepad){
		float target = (float) get(gamepad);

		if(brakeAt0 && target == 0)
			reset();
		else {
			if (currentVal < target) {
				currentVal += ramp;
				if (currentVal > target)
					currentVal = target;
			} else if (currentVal > target) {
				currentVal -= ramp;
				if (currentVal < target)
					currentVal = target;
			}
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
