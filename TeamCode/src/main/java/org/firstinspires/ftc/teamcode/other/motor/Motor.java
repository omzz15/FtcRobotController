package org.firstinspires.ftc.teamcode.other.motor;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.other.EndPoints;

public class Motor{
	DcMotor motor;

	EndPoints positionEnds;

	Motor(){}

	public void init(@NonNull MotorSettings settings, @NonNull HardwareMap hardwareMap, boolean makeExMotor){
		if(makeExMotor)
			motor = settings.makeExMotor(hardwareMap);
		else
			motor = settings.makeMotor(hardwareMap);

	}
}
