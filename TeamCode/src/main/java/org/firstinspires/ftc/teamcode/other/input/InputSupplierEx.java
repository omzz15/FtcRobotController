package org.firstinspires.ftc.teamcode.other.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.function.Function;

public class InputSupplierEx extends SupplierEx<Gamepad>{

	private Utils.GamepadNum gamepadNum;

	public InputSupplierEx(Function<Gamepad, Double> supplyFunction, Gamepad gamepad, float minRegisterVal) {
		super(supplyFunction, gamepad, minRegisterVal);
	}

	public InputSupplierEx(Function<Gamepad, Double> supplyFunction, Utils.GamepadNum gamepadNum, float minRegisterVal) {
		super(supplyFunction, minRegisterVal);
		this.gamepadNum = gamepadNum;
	}

	public InputSupplierEx(Function<Gamepad, Double> supplyFunction) {
		super(supplyFunction);
	}

	/**
	 * *only call if you have to convert gamepadNum to gamepad
	 * converts the gamepad number passed in InputSupplier(Function<Gamepad, Object> supplyFunction, Utils.GamepadNum gamepadNum) to a gamepad that can be supplied to the supplyFunction
	 * @param robot the robot the default gamepad should be set from
	 */
	public void init(Robot robot){
		if(gamepadNum != null)
			device = gamepadNum.getGamepad(robot);
	}
}
