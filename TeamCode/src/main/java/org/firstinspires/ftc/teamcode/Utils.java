package org.firstinspires.ftc.teamcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Function;

public class Utils {
}

class InputSupplier{
	Gamepad gamepad;
	Function<Gamepad, Double> supplyFunction;

	InputSupplier(Gamepad gamepad, Function<Gamepad, Double> supplyFunction){
		this.gamepad = gamepad;
		this.supplyFunction = supplyFunction;
	}
	InputSupplier(Function<Gamepad, Double> supplyFunction){
		this.supplyFunction = supplyFunction;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	double get(){
		return get(gamepad);
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	double get(Gamepad gamepad){
		return supplyFunction.apply(gamepad);
	}
}
