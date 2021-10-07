package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.basethreaded.RobotThreadedPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;

public class VisionSettings extends RobotThreadedPartSettings {
	//key
	String VUFORIA_KEY = "";

	//vuforia general
	float[] cameraPosition = new float[]{0,0,0}; //axis order is XYZ

	//phone settings
	VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
	boolean PHONE_IS_PORTRAIT = false;

	float[] phoneRotation = new float[]{0,0,0};

	//webcam settings
	String webcamName = "Webcam 1";


	VisionSettings(){
		runForTeleOp = false;
	}

	@Override
	public void init(Robot robot){
		cameraPosition = Utils.Constants.inchesToMM(cameraPosition);
		if (CAMERA_CHOICE == BACK) {
			phoneRotation[1] -= 90;
		} else {
			phoneRotation[1] += 90;
		}

		// Rotate the phone vertical about the X axis if it's in portrait mode
		if (PHONE_IS_PORTRAIT) {
			phoneRotation[0] += 90 ;
		}
	}
}
