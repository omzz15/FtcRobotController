package org.firstinspires.ftc.teamcode.arm;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.deprecated.test.TestHardware;
import org.firstinspires.ftc.teamcode.deprecated.test.TestSettings;
import org.firstinspires.ftc.teamcode.other.Utils;

import java.util.List;

public class Arm extends RobotPart {

	int motorPosition = 0;
	double servoPosition = 0;

	public Arm(Robot robot, ArmHardware hardware, ArmSettings settings) {
		super(robot, hardware, settings);
	}

	public Arm(Robot robot){
		super(robot, new ArmHardware(), new ArmSettings());
	}

	@Override
	public void teleOpRunCode() {
		teleOpRunCode(((ArmSettings) settings).gamepad);
	}

	void teleOpRunCode(Gamepad gamepad){
		motorPosition += ((ArmSettings) settings).armMovementSupplier.getFloat(gamepad) * (float)(((ArmSettings) settings).armMovementSpeed);
		motorPosition = Utils.Math.capInt(motorPosition, ((ArmSettings) settings).armMinPos, ((ArmSettings) settings).armMaxPos);

		if(((ArmSettings) settings).bucketDownSupplier.getBoolean(gamepad))
			servoPosition -= ((ArmSettings) settings).bucketSpeed;
		else if(((ArmSettings) settings).bucketUpSupplier.getBoolean(gamepad))
			servoPosition += ((ArmSettings) settings).bucketSpeed;
		servoPosition = Utils.Math.capDouble(servoPosition, ((ArmSettings) settings).servoMinPos, ((ArmSettings) settings).servoMaxPos);

		((ArmHardware) hardware).armMotor.setTargetPosition(motorPosition);
		((ArmHardware) hardware).bucketServo.setPosition(servoPosition);
	}
}
