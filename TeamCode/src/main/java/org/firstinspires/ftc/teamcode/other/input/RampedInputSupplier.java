package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class RampedInputSupplier extends InputSupplier<Float> {
	float ramp = 0;
	float currentVal = 0;
	boolean brakeAt0 = false;

	public RampedInputSupplier(Function<Gamepad, Float> supplyFunction, Gamepad gamepad, float ramp, boolean brakeAt0) {
		super(supplyFunction, gamepad);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Function<Gamepad, Float> supplyFunction, Utils.GamepadNum gamepadNum, float ramp, boolean brakeAt0) {
		super(supplyFunction, gamepadNum);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Function<Gamepad, Float> supplyFunction, float ramp, boolean brakeAt0) {
		super(supplyFunction);
		this.ramp = ramp;
		this.brakeAt0 = brakeAt0;
	}

	public RampedInputSupplier(Function<Gamepad, Float> supplyFunction) {
		super(supplyFunction);
	}

	public float getRamped(Gamepad gamepad){
		float target = get(gamepad);

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

	public float getRamped(){
		return getRamped(device);
	}

	public void reset(){
		currentVal = 0;
	}
}