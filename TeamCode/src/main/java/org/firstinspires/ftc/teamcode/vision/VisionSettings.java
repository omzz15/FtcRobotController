package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.basethreaded.RobotThreadedPartSettings;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.Utils;

public class VisionSettings extends RobotThreadedPartSettings {
	/////////
	//flags//
	/////////
	//vuforia
	boolean useVuforia = true;
	boolean runVuforiaInThread = true;
	//tensorflow(requires Vuforia to be active)
	boolean useTensorFlow = true;
	boolean runTensorFlowInThread = true;
	//openCV (unsupported for now)
	//boolean useOpenCV = false;

	/////////////
	//dashboard//
	/////////////
	VideoSource dashVideoSource = VideoSource.VUFORIA;
	int maxFPS = 24;


	///////////
	//vuforia//
	///////////
	//key
	String VUFORIA_KEY =
			"Ad6cSm3/////AAABmRkDMfGtWktbjulxwWmgzxl9TiuwUBtfA9n1VM546drOcSfM+JxvMxvI1WrLSLNdapOtOebE6n3BkjTjyj+sTXHoEyyJW/lPPmlX5Ar2AjeYpTW/WZM/lzG8qDPsm0tquhEj3BUisA5GRttyGXffPwfKJZNPy3WDqnPxyY/U2v+jQNfZjsWqNvUfp3a3klhVPYd25N5dliMihK3WogqNQnZM9bwJc1wRT0zcczYBJJrhpws9A5H2FpOZD6Ov7GqT+rJdKrU6bh+smoueINDFeaFuYQVMEeo7VOLgkzOeRDpfFmVOVeJrmUv+mwnxfFthAY5v90e4kgekG5OYzRQDS2ta0dbUpG6GoJMoZU2vASSa";
	//vuforia general
	float[] cameraPosition = new float[]{0,0,0}; //axis order is XYZ
	String VUFORIA_MODEL_ASSET = "FreightFrenzy";
	//phone settings
	VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
	boolean PHONE_IS_PORTRAIT = false;
	float[] phoneRotation = new float[]{0,0,0};
	//webcam settings
	String webcamName = "Webcam 1";


	//////////////
	//tensorflow//
	//////////////
	double magnification = 2;
	String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
	String[] LABELS = {
			Labels.BALL.value,
			Labels.CUBE.value,
			Labels.DUCK.value,
			Labels.MARKER.value
	};
	float minResultConfidence = .8f;


	//////////////////////
	//construct and init//
	//////////////////////
	VisionSettings(){
		runForTeleOp = false;
		if(makeThread())
			makeThread = true;
		else
			makeThread = false;
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


	//////////
	//checks//
	//////////
	boolean runVuforiaInThread(){
		return useVuforia && runVuforiaInThread;
	}

	boolean runTensorFlowInThread(){
		return useVuforia && useTensorFlow && runTensorFlowInThread;
	}

	private boolean makeThread(){
		return runTensorFlowInThread() || runVuforiaInThread();
	}


	////////
	//enum//
	////////
	public enum Labels {
		BALL("Ball"),
		CUBE("Cube"),
		DUCK("Duck"),
		MARKER("Marker");

		String value;

		Labels(String value){
			this.value = value;
		}
	}

	public enum VideoSource{
		VUFORIA,
		TENSORFLOW,
		NONE
	}
}
