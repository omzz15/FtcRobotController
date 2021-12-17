package org.firstinspires.ftc.teamcode.other.hardware.motor;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.other.task.Task;

public class MotorController{
	private DcMotor motor;

	private MotorControllerSettings settings;

	public MotorController(){}

	public MotorController(@NonNull MotorControllerSettings settings){
		updateSettings(settings);
	}

	public void init(@NonNull MotorSettings hardwareSettings, @NonNull MotorControllerSettings settings, @NonNull HardwareMap hardwareMap, boolean makeExMotor){
		if(hardwareSettings.runMode == DcMotor.RunMode.RUN_TO_POSITION) {
			if (makeExMotor)
				motor = hardwareSettings.makeExMotor(hardwareMap);
			else
				motor = hardwareSettings.makeMotor(hardwareMap);

			updateSettings(settings);
		}
	}

	public void updateSettings(@NonNull MotorControllerSettings settings){
		this.settings = settings;
	}

	public void moveMotor(double value){
		setMotorPosition(getTargetPosition() + (int)(value * settings.movementSpeed));
	}

	public DcMotor getMotor(){
		return motor;
	}

	public int getTargetPosition(){
		return motor.getTargetPosition();
	}

	public int getCurrentPosition(){
		return motor.getCurrentPosition();
	}

	public void setMotorPosition(int position){
		motor.setTargetPosition(settings.positionEnds.capInt(position));
	}

	public boolean isMotorInTolerance(){
		return Math.abs(getCurrentPosition() - getTargetPosition()) <= settings.tolerance;
	}

	public Task addGoToPositionToTask(Task task, int position){
		task.addStep(() -> {setMotorPosition(position);});
		task.addStep(() -> (isMotorInTolerance()));
		return task;
	}
}
