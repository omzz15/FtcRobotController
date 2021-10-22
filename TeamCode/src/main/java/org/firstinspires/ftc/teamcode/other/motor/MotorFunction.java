package org.firstinspires.ftc.teamcode.other.motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.List;

public interface MotorFunction {
	Object run(List<DcMotorEx> motors, Object value, boolean separateValues);
}
