package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;

public class VisionHardware extends RobotPartSettings {
/*
	//phone setup for vuforia
	protected final VuforiaLocalizer.CameraDirection CAMERA_CHOICE_V = BACK; // if you are using a phone which camera do you want to use
	protected final boolean PHONE_IS_PORTRAIT = false; // if you are using a phone which orientation is it
	protected final float[] phonePosition = {0,0,0}; // the phone positionTracker from center of robot
	protected final float[] phoneRotation = {0,0,0}; // the phone rotation

	//phone setup for openCV
	protected final OpenCvInternalCamera.CameraDirection CAMERA_CHOICE_O = OpenCvInternalCamera.CameraDirection.BACK; // if you are using a phone which camera do you want to use


	public OpenCvWebcam webcam; //a webcam object to get image
	public OpenCvCamera phoneCam; //a phone camera to get picture from phone
	public int cameraMonitorViewId;
	public boolean usingWebcam;

	@Override
	public void init(Robot robot) {
		initCameraID(robot);
		checkCameraType(robot);
	}

	void initCameraID(Robot robot) //gets the camera Id to make initialization easier for vision objects
	{
		cameraMonitorViewId = robot.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", robot.hardwareMap.appContext.getPackageName());
	}

	void checkCameraType(Robot robot)
	{
		try
		{
			robot.hardwareMap.get(WebcamName.class, "Webcam 1");
			usingWebcam = true;
		}
		catch(Exception e)
		{
			usingWebcam = false;
		}
	}

	void initOpenCVCamera(Robot robot){
		if(usingWebcam)
		{
			//creating a camera object
			webcam = OpenCvCameraFactory.getInstance().createWebcam(robot.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
		} else{
			//creating a camera object
			phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(CAMERA_CHOICE_O, cameraMonitorViewId);
		}
	}

 */
}
