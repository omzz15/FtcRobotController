package org.firstinspires.ftc.teamcode.other.servo;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

//////////////
//other code//
//////////////
public interface ServoFunction {
	Object run(List<Servo> servos, Object value, boolean separateValues);
}
